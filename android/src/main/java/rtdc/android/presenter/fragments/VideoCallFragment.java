package rtdc.android.presenter.fragments;
/*
VideoCallFragment.java
Copyright (C) 2012  Belledonne Communications, Grenoble, France

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.FrameLayout;
import android.widget.TextView;
import org.linphone.mediastream.video.AndroidVideoWindowImpl;
import org.linphone.mediastream.video.capture.hwconf.AndroidCameraConfiguration;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View.OnTouchListener;
import rtdc.android.R;
import rtdc.android.impl.AndroidVoipController;
import rtdc.android.impl.voip.AndroidBandwidthManager;
import rtdc.android.impl.voip.AndroidVideo;
import rtdc.android.impl.voip.AndroidVoIPManager;
import rtdc.android.impl.voip.AndroidVoIPThread;
import rtdc.android.presenter.InCallActivity;
import rtdc.core.Bootstrapper;
import rtdc.core.impl.voip.Call;
import rtdc.core.impl.voip.CallParameters;
import rtdc.core.impl.voip.Video;
import rtdc.core.impl.voip.VoIPManager;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class VideoCallFragment extends AbstractCallFragment {
    private SurfaceView mVideoView;
    private SurfaceView mCaptureView;
    private AndroidVideoWindowImpl androidVideoWindowImpl;
    private OrientationEventListener mOrientationHelper;
    private InCallActivity inCallActivity;
    private int rotation;
    private boolean isFragmentPaused;
    private Future ringingTask;

    @SuppressWarnings("deprecation") // Warning useless because value is ignored and automatically set by new APIs.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in_video_call, container, false);
        this.view = view;

        view.findViewById(R.id.switchCameraButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchCamera(-1);
            }
        });

        AndroidVoIPThread.getInstance().setVideo(new AndroidVideo((AndroidVoIPManager) AndroidVoIPThread.getInstance().getVoIPManager()));

        // If there's a front camera, default to that one
        if(AndroidCameraConfiguration.hasFrontCamera()){
            AndroidCameraConfiguration.AndroidCamera[] cameras = AndroidCameraConfiguration.retrieveCameras();
            for(int i = 0; i < cameras.length; i++){
                AndroidCameraConfiguration.AndroidCamera camera = cameras[i];
                if(camera.frontFacing){
                    switchCamera(i);
                    continue;
                }
            }
        }

        mVideoView = (SurfaceView) view.findViewById(R.id.videoSurface);
        mCaptureView = (SurfaceView) view.findViewById(R.id.videoCaptureSurface);
        mCaptureView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); // Warning useless because value is ignored and automatically set by new APIs.

        fixZOrder(mVideoView, mCaptureView);

        androidVideoWindowImpl = new AndroidVideoWindowImpl(mVideoView, mCaptureView, new AndroidVideoWindowImpl.VideoWindowListener() {
            public void onVideoRenderingSurfaceReady(AndroidVideoWindowImpl vw, SurfaceView surface) {
                AndroidVoIPThread.getInstance().getVideo().setVideoWindow(vw);
                mVideoView = surface;
            }

            public void onVideoRenderingSurfaceDestroyed(AndroidVideoWindowImpl vw) {
                Video video = AndroidVoIPThread.getInstance().getVideo();
                if (video != null)
                    video.setVideoWindow(null);
            }

            public void onVideoPreviewSurfaceReady(AndroidVideoWindowImpl vw, SurfaceView surface) {
                mCaptureView = surface;
                AndroidVoIPThread.getInstance().getVideo().setPreviewWindow(mCaptureView);
            }

            public void onVideoPreviewSurfaceDestroyed(AndroidVideoWindowImpl vw) {
                // Remove references kept in jni code and restart camera
                AndroidVoIPThread.getInstance().getVideo().setPreviewWindow(null);
            }
        });

        mVideoView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if (inCallActivity != null) {
                    //inCallActivity.displayVideoCallControlsIfHidden();
                }

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    View buttonLayout = VideoCallFragment.this.view.findViewById(R.id.buttonLayout);
                    if (buttonLayout.getVisibility() == View.VISIBLE) {
                        if (Bootstrapper.getFactory().getVoipController().isMicMuted())
                            VideoCallFragment.this.view.findViewById(R.id.muteIcon).setVisibility(View.VISIBLE);
                        buttonLayout.setVisibility(View.GONE);
                    } else {
                        VideoCallFragment.this.view.findViewById(R.id.muteIcon).setVisibility(View.INVISIBLE);
                        buttonLayout.setVisibility(View.VISIBLE);
                    }
                }

                return true;
            }
        });

        mCaptureView.setOnTouchListener(new OnTouchListener() {
            float dx = 0, dy = 0;
            @Override
            public boolean onTouch(final View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        // Find the difference from where we touched to the absolute position of the view to help with movement
                        dx = event.getRawX() - view.getX();
                        dy = event.getRawY() - view.getY();
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        // Move the view by setting the margings for left and top
                        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                        params.setMargins((int)(event.getRawX() - dx), (int)(event.getRawY() - dy), 0, 0);
                        params.gravity = Gravity.NO_GRAVITY;
                        view.setLayoutParams(params);
                        break;
                    }case MotionEvent.ACTION_UP: {
                        // Find the absolute position we would like the view to end up at
                        float width = VideoCallFragment.this.view.getWidth();
                        float height = VideoCallFragment.this.view.getHeight() - VideoCallFragment.this.view.findViewById(R.id.buttonLayout).getHeight();
                        float absoluteX = event.getRawX() <= width / 2 ? 0 : width - view.getWidth();
                        float absoluteY = event.getRawY() <= height / 2 ? 0 : height - view.getHeight();

                        // Calculate the gravity the view should have with the absolute position we found
                        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                        int horizontalGravity = absoluteX == 0 ? Gravity.LEFT: Gravity.RIGHT;
                        int verticalGravity = absoluteY == 0 ? Gravity.TOP: Gravity.BOTTOM;
                        params.gravity = (horizontalGravity | verticalGravity);

                        // Set the margins depending on what gravities we calculated above
                        int left = horizontalGravity == Gravity.LEFT ? 10: 0;
                        int right = horizontalGravity == Gravity.RIGHT ? 10: 0;
                        int top = verticalGravity == Gravity.TOP ? 10: 0;
                        int bottom = verticalGravity == Gravity.BOTTOM ? 10: 0;
                        params.setMargins(left, top, right, bottom);

                        view.setLayoutParams(params);
                        break;
                    }
                }
                return true;
            }
        });

        // Set speaker mode on
        AndroidVoipController.get().setSpeaker(true);

        if(AndroidVoIPThread.getInstance().getCall().getState() == Call.State.outgoingProgress) {
            // Display a ringing message

            view.findViewById(R.id.callStatus).setVisibility(View.VISIBLE);
            ((TextView) view.findViewById(R.id.callStatus)).setText("Ringing");
            final TextView ringingDots = ((TextView) view.findViewById(R.id.ringingDots));
            ringingDots.setVisibility(View.VISIBLE);
            ringingTask = inCallActivity.getExecutor().scheduleWithFixedDelay(new Runnable(){
                @Override
                public void run() {
                    inCallActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(ringingDots.getText().equals("\n\n ."))
                                ringingDots.setText("\n\n . .");
                            else if(ringingDots.getText().equals("\n\n . ."))
                                ringingDots.setText("\n\n . . . ");
                            else
                                ringingDots.setText("\n\n .");
                        }
                    });
                }
            }, 0, 1000, TimeUnit.MILLISECONDS);
        }else{
            // If remote user isn't displaying video, we say so on screen
            if(!AndroidVoipController.get().isReceivingRemoteVideo()) {
                view.findViewById(R.id.callStatus).setVisibility(View.VISIBLE);
                ((TextView) view.findViewById(R.id.callStatus)).setText("Other user isn't showing video");
            }
        }

        // If we're not capturing video, we don't need to show the preview
        if(!AndroidVoipController.get().isVideoEnabled())
            mCaptureView.setVisibility(View.INVISIBLE);

        startOrientationSensor();

        return view;
    }

    private void fixZOrder(SurfaceView video, SurfaceView preview) {
        video.setZOrderOnTop(false);
        preview.setZOrderOnTop(true);
        preview.setZOrderMediaOverlay(true); // Needed to be able to display control layout over
    }

    public void switchCamera(int videoDeviceId) {
        try {
            if(videoDeviceId == -1) {
                videoDeviceId = AndroidVoIPThread.getInstance().getVideo().getCameraId();
                videoDeviceId = (videoDeviceId + 1) % AndroidCameraConfiguration.retrieveCameras().length;
            }
            AndroidVoIPThread.getInstance().getVideo().setCameraId(videoDeviceId);

            VoIPManager vm = AndroidVoIPThread.getInstance().getVoIPManager();
            Call call = AndroidVoIPThread.getInstance().getCall();
            if (call == null) {
                // Trying to updateCall while not in call: doing nothing
                return;
            }

            CallParameters params = call.getCurrentParamsCopy();
            AndroidBandwidthManager.getInstance().updateWithProfileSettings(vm, params);
            vm.updateCall(call, null);

            // previous call will cause graph reconstruction -> regive preview
            // window
            if (mCaptureView != null) {
                AndroidVoIPThread.getInstance().getVideo().setPreviewWindow(mCaptureView);
            }
        } catch (ArithmeticException ae) {
            // Cannot switch camera : no camera
            ae.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mVideoView != null) {
            ((GLSurfaceView) mVideoView).onResume();
        }

        if (androidVideoWindowImpl != null) {
            synchronized (androidVideoWindowImpl) {
                AndroidVoIPThread.getInstance().getVideo().setVideoWindow(androidVideoWindowImpl);
                if(isFragmentPaused)
                    AndroidVoipController.get().setVideo(true);
                isFragmentPaused = false;
            }
        }
    }

    @Override
    public void onPause() {
        if (androidVideoWindowImpl != null) {
            synchronized (androidVideoWindowImpl) {
				/*
				 * this call will destroy native opengl renderer which is used by
				 * androidVideoWindowImpl
				 */
                AndroidVoIPThread.getInstance().getVideo().setVideoWindow(null);
                AndroidVoipController.get().setVideo(false);
                isFragmentPaused = true;
            }
        }

        if (mVideoView != null) {
            ((GLSurfaceView) mVideoView).onPause();
        }

        super.onPause();
    }

    @Override
    public void onDestroy() {
        inCallActivity = null;

        if (mOrientationHelper != null) {
            mOrientationHelper.disable();
            mOrientationHelper = null;
        }

        mCaptureView = null;
        if (mVideoView != null) {
            mVideoView.setOnTouchListener(null);
            mVideoView = null;
        }

        if (androidVideoWindowImpl != null) {
            // Prevent linphone from crashing if correspondent hang up while you are rotating
            androidVideoWindowImpl.release();
            androidVideoWindowImpl = null;
        }

        super.onDestroy();
    }

    @Override
 public void onCallEstablished() {
    ringingTask.cancel(true);
    inCallActivity.runOnUiThread(new Runnable() {
        @Override
        public void run() {
            view.findViewById(R.id.callStatus).setVisibility(View.INVISIBLE);
            ((TextView) view.findViewById(R.id.callStatus)).setText("Paused");
            view.findViewById(R.id.ringingDots).setVisibility(View.INVISIBLE);
            // If remote user isn't displaying video, we say so on screen
            if (!AndroidVoipController.get().isReceivingRemoteVideo()) {
                view.findViewById(R.id.callStatus).setVisibility(View.VISIBLE);
                ((TextView) view.findViewById(R.id.callStatus)).setText("Other user isn't showing video");
            }
        }
    });
}

    @Override
    public void onCallHangup() {
        inCallActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //mVideoView.setVisibility(View.INVISIBLE);
                mCaptureView.setVisibility(View.INVISIBLE);
                view.findViewById(R.id.callStatus).setVisibility(View.VISIBLE);
                ((TextView) view.findViewById(R.id.callStatus)).setText("Call ended");
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        inCallActivity = (InCallActivity) activity;
        if (inCallActivity != null) {
            //inCallActivity.bindVideoFragment(this);
        }
    }

    /**
     * Register a sensor to track phoneOrientation changes
     */
    private synchronized void startOrientationSensor() {
        if (mOrientationHelper == null) {
            mOrientationHelper = new LocalOrientationEventListener(getActivity());
        }
        mOrientationHelper.enable();
    }

    private int mAlwaysChangingPhoneAngle = -1;

    private class LocalOrientationEventListener extends OrientationEventListener {
        public LocalOrientationEventListener(Context context) {
            super(context);
        }

        @Override
        public void onOrientationChanged(final int o) {
            if (o == OrientationEventListener.ORIENTATION_UNKNOWN) {
                return;
            }

            int degrees = 270;
            if (o < 45 || o > 315)
                degrees = 0;
            else if (o < 135)
                degrees = 90;
            else if (o < 225)
                degrees = 180;

            if (mAlwaysChangingPhoneAngle == degrees) {
                return;
            }
            mAlwaysChangingPhoneAngle = degrees;

            Logger.getLogger(VideoCallFragment.class.getName()).info("Phone orientation changed to " + degrees);
            boolean oldRotationPortrait = rotation == 0 || rotation == 180;
            rotation = (360 - degrees) % 360;
            AndroidVoIPThread.getInstance().getVoIPManager().setDeviceRotation(rotation);

            // Change camera preview orientation if necessary
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mCaptureView.getLayoutParams();
            if((oldRotationPortrait && (rotation == 90 || rotation == 270)) || (!oldRotationPortrait && (rotation == 0 || rotation == 180))) {
                int oldHeight = params.height;
                params.height = params.width;
                params.width = oldHeight;
            }
            mCaptureView.setLayoutParams(params);

            AndroidVoIPThread.getInstance().getVoIPManager().updateCall(AndroidVoIPThread.getInstance().getCall(), null);
        }
    }
}