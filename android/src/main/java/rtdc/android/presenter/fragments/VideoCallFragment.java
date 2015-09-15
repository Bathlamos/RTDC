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
import android.graphics.Point;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import org.linphone.BandwidthManager;
import org.linphone.LinphoneUtils;
import org.linphone.compatibility.Compatibility;
import org.linphone.compatibility.CompatibilityScaleGestureDetector;
import org.linphone.compatibility.CompatibilityScaleGestureListener;
import org.linphone.core.LinphoneCall;
import org.linphone.core.LinphoneCallParams;
import org.linphone.core.LinphoneCore;
import org.linphone.mediastream.Log;
import org.linphone.mediastream.video.AndroidVideoWindowImpl;
import org.linphone.mediastream.video.capture.hwconf.AndroidCameraConfiguration;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import rtdc.android.R;
import rtdc.android.presenter.CommunicationHubInCallActivity;
import rtdc.android.voip.LiblinphoneThread;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Sylvain Berfini
 */
public class VideoCallFragment extends AbstractCallFragment implements OnGestureListener, OnDoubleTapListener, CompatibilityScaleGestureListener {
    private SurfaceView mVideoView;
    private SurfaceView mCaptureView;
    private AndroidVideoWindowImpl androidVideoWindowImpl;
    private GestureDetector mGestureDetector;
    private float mZoomFactor = 1.f;
    private float mZoomCenterX, mZoomCenterY;
    private CompatibilityScaleGestureDetector mScaleDetector;
    private CommunicationHubInCallActivity inCallActivity;

    int _xDelta = 0;
    int _yDelta = 0;

    @SuppressWarnings("deprecation") // Warning useless because value is ignored and automatically set by new APIs.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in_video_call, container, false);
        this.view = view;

        view.findViewById(R.id.switchCameraButton).setOnClickListener(inCallActivity);
        view.findViewById(R.id.switchCameraButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchCamera();
            }
        });

        mVideoView = (SurfaceView) view.findViewById(R.id.videoSurface);
        mCaptureView = (SurfaceView) view.findViewById(R.id.videoCaptureSurface);
        mCaptureView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); // Warning useless because value is ignored and automatically set by new APIs.

        fixZOrder(mVideoView, mCaptureView);

        androidVideoWindowImpl = new AndroidVideoWindowImpl(mVideoView, mCaptureView, new AndroidVideoWindowImpl.VideoWindowListener() {
            public void onVideoRenderingSurfaceReady(AndroidVideoWindowImpl vw, SurfaceView surface) {
                LiblinphoneThread.get().getLinphoneCore().setVideoWindow(vw);
                mVideoView = surface;
            }

            public void onVideoRenderingSurfaceDestroyed(AndroidVideoWindowImpl vw) {
                LinphoneCore lc = LiblinphoneThread.get().getLinphoneCore();
                if (lc != null) {
                    lc.setVideoWindow(null);
                }
            }

            public void onVideoPreviewSurfaceReady(AndroidVideoWindowImpl vw, SurfaceView surface) {
                mCaptureView = surface;
                LiblinphoneThread.get().getLinphoneCore().setPreviewWindow(mCaptureView);
            }

            public void onVideoPreviewSurfaceDestroyed(AndroidVideoWindowImpl vw) {
                // Remove references kept in jni code and restart camera
                LiblinphoneThread.get().getLinphoneCore().setPreviewWindow(null);
            }
        });

        mVideoView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (mScaleDetector != null) {
                    mScaleDetector.onTouchEvent(event);
                }

                mGestureDetector.onTouchEvent(event);
                if (inCallActivity != null) {
                    //inCallActivity.displayVideoCallControlsIfHidden();
                }

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    View buttonLayout = VideoCallFragment.this.view.findViewById(R.id.buttonLayout);
                    if (buttonLayout.getVisibility() == View.VISIBLE) {
                        if (inCallActivity.isMicMuted())
                            VideoCallFragment.this.view.findViewById(R.id.muteIcon).setVisibility(View.VISIBLE);
                        buttonLayout.setVisibility(View.INVISIBLE);
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
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        dx = event.getRawX() - view.getX();
                        dy = event.getRawY() - view.getY();

                        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                        params.gravity = Gravity.NO_GRAVITY;
                        view.setLayoutParams(params);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        view.setX(event.getRawX() - dx);
                        view.setY(event.getRawY() - dy);
                        break;
                    }case MotionEvent.ACTION_UP: {
                        float width = VideoCallFragment.this.view.getWidth();
                        float height = VideoCallFragment.this.view.getHeight() - VideoCallFragment.this.view.findViewById(R.id.buttonLayout).getHeight();

                        float lockX = event.getRawX() <= width / 2 ? 0 : width - view.getWidth();
                        float lockY = event.getRawY() <= height / 2 ? 0 : height - view.getHeight();

                        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                        int horizontalGravity = lockX == 0 ? Gravity.LEFT: Gravity.RIGHT;
                        int verticalGravity = lockY == 0 ? Gravity.TOP: Gravity.BOTTOM;
                        params.gravity = (horizontalGravity | verticalGravity);
                        view.setLayoutParams(params);

                        // Need to set the position to 0, 0 or else the gravity position isn't accurate

                        view.setX(0);
                        view.setY(0);

                        // The following should animate the view to translate to the correct location, but does not work

                        /*TranslateAnimation anim = new TranslateAnimation(
                                TranslateAnimation.RELATIVE_TO_PARENT, 0,
                                TranslateAnimation.RELATIVE_TO_PARENT, lockX/width,
                                TranslateAnimation.RELATIVE_TO_PARENT, 0,
                                TranslateAnimation.RELATIVE_TO_PARENT, lockY/height);
                        Logger.getLogger(VideoCallFragment.class.getName()).log(Level.INFO, lockX + ", " + lockY);
                        anim.setDuration(1000);
                        anim.setFillAfter(true);
                        view.startAnimation(anim);*/
                        break;
                    }
                }
                return true;
            }
        });

        return view;
    }

    private void fixZOrder(SurfaceView video, SurfaceView preview) {
        video.setZOrderOnTop(false);
        preview.setZOrderOnTop(true);
        preview.setZOrderMediaOverlay(true); // Needed to be able to display control layout over
    }

    public void switchCamera() {
        try {
            int videoDeviceId = LiblinphoneThread.get().getLinphoneCore().getVideoDevice();
            videoDeviceId = (videoDeviceId + 1) % AndroidCameraConfiguration.retrieveCameras().length;
            LiblinphoneThread.get().getLinphoneCore().setVideoDevice(videoDeviceId);

            LinphoneCore lc = LiblinphoneThread.get().getLinphoneCore();
            LinphoneCall lCall = lc.getCurrentCall();
            if (lCall == null) {
                Log.e("Trying to updateCall while not in call: doing nothing");
                return;
            }
            LinphoneCallParams params = lCall.getCurrentParamsCopy();
            BandwidthManager.getInstance().updateWithProfileSettings(lc, params);
            lc.updateCall(lCall, null);

            // previous call will cause graph reconstruction -> regive preview
            // window
            if (mCaptureView != null) {
                LiblinphoneThread.get().getLinphoneCore().setPreviewWindow(mCaptureView);
            }
        } catch (ArithmeticException ae) {
            Log.e("Cannot swtich camera : no camera");
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
                LiblinphoneThread.get().getLinphoneCore().setVideoWindow(androidVideoWindowImpl);
            }
        }

        mGestureDetector = new GestureDetector(inCallActivity, this);
        mScaleDetector = Compatibility.getScaleGestureDetector(inCallActivity, this);
    }

    @Override
    public void onPause() {
        if (androidVideoWindowImpl != null) {
            synchronized (androidVideoWindowImpl) {
				/*
				 * this call will destroy native opengl renderer which is used by
				 * androidVideoWindowImpl
				 */
                LiblinphoneThread.get().getLinphoneCore().setVideoWindow(null);
            }
        }

        if (mVideoView != null) {
            ((GLSurfaceView) mVideoView).onPause();
        }

        super.onPause();
    }

    public boolean onScale(CompatibilityScaleGestureDetector detector) {
        mZoomFactor *= detector.getScaleFactor();
        // Don't let the object get too small or too large.
        // Zoom to make the video fill the screen vertically
        float portraitZoomFactor = ((float) mVideoView.getHeight()) / (float) ((3 * mVideoView.getWidth()) / 4);
        // Zoom to make the video fill the screen horizontally
        float landscapeZoomFactor = ((float) mVideoView.getWidth()) / (float) ((3 * mVideoView.getHeight()) / 4);
        mZoomFactor = Math.max(0.1f, Math.min(mZoomFactor, Math.max(portraitZoomFactor, landscapeZoomFactor)));

        LinphoneCall currentCall = LiblinphoneThread.get().getLinphoneCore().getCurrentCall();
        if (currentCall != null) {
            currentCall.zoomVideo(mZoomFactor, mZoomCenterX, mZoomCenterY);
            return true;
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (LinphoneUtils.isCallEstablished(LiblinphoneThread.get().getLinphoneCore().getCurrentCall())) {
            if (mZoomFactor > 1) {
                // Video is zoomed, slide is used to change center of zoom
                if (distanceX > 0 && mZoomCenterX < 1) {
                    mZoomCenterX += 0.01;
                } else if(distanceX < 0 && mZoomCenterX > 0) {
                    mZoomCenterX -= 0.01;
                }
                if (distanceY < 0 && mZoomCenterY < 1) {
                    mZoomCenterY += 0.01;
                } else if(distanceY > 0 && mZoomCenterY > 0) {
                    mZoomCenterY -= 0.01;
                }

                if (mZoomCenterX > 1)
                    mZoomCenterX = 1;
                if (mZoomCenterX < 0)
                    mZoomCenterX = 0;
                if (mZoomCenterY > 1)
                    mZoomCenterY = 1;
                if (mZoomCenterY < 0)
                    mZoomCenterY = 0;

                LiblinphoneThread.get().getLinphoneCore().getCurrentCall().zoomVideo(mZoomFactor, mZoomCenterX, mZoomCenterY);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        if (LinphoneUtils.isCallEstablished(LiblinphoneThread.get().getLinphoneCore().getCurrentCall())) {
            if (mZoomFactor == 1.f) {
                // Zoom to make the video fill the screen vertically
                float portraitZoomFactor = ((float) mVideoView.getHeight()) / (float) ((3 * mVideoView.getWidth()) / 4);
                // Zoom to make the video fill the screen horizontally
                float landscapeZoomFactor = ((float) mVideoView.getWidth()) / (float) ((3 * mVideoView.getHeight()) / 4);

                mZoomFactor = Math.max(portraitZoomFactor, landscapeZoomFactor);
            }
            else {
                resetZoom();
            }

            LiblinphoneThread.get().getLinphoneCore().getCurrentCall().zoomVideo(mZoomFactor, mZoomCenterX, mZoomCenterY);
            return true;
        }

        return false;
    }

    private void resetZoom() {
        mZoomFactor = 1.f;
        mZoomCenterX = mZoomCenterY = 0.5f;
    }

    @Override
    public void onDestroy() {
        inCallActivity = null;

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
        if (mGestureDetector != null) {
            mGestureDetector.setOnDoubleTapListener(null);
            mGestureDetector = null;
        }
        if (mScaleDetector != null) {
            mScaleDetector.destroy();
            mScaleDetector = null;
        }

        super.onDestroy();
    }

    @Override
    public void hangupCleanup() {
        inCallActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mVideoView.setVisibility(View.INVISIBLE);
                mCaptureView.setVisibility(View.INVISIBLE);
                view.findViewById(R.id.callEndedText).setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        inCallActivity = (CommunicationHubInCallActivity) activity;
        if (inCallActivity != null) {
            //inCallActivity.bindVideoFragment(this);
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true; // Needed to make the GestureDetector working
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }
}