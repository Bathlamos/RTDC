package rtdc.android.presenter;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import rtdc.android.AndroidBootstrapper;
import rtdc.android.R;
import rtdc.android.impl.AndroidVoipController;
import rtdc.android.impl.voip.AndroidVoIPThread;
import rtdc.android.presenter.fragments.AbstractCallFragment;
import rtdc.android.presenter.fragments.AudioCallFragment;
import rtdc.android.presenter.fragments.VideoCallFragment;
import rtdc.core.Bootstrapper;
import rtdc.core.Config;
import rtdc.core.impl.voip.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class InCallActivity extends AbstractActivity implements View.OnClickListener, VoIPListener {

    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    private AbstractCallFragment callFragment;

    public final static int IN_CALL_NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_call);

        // Force screen to stay on during the call

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if(getResources().getBoolean(R.bool.isTablet))
            AndroidVoipController.get().setSpeaker(true);

        if(AndroidVoipController.get().isReceivingRemoteVideo() || AndroidVoipController.get().isVideoEnabled()){
            displayVideo();
        }else{
            displayAudio();
        }

        // Build the intent that will be used by the notification

        Intent resultIntent = new Intent(this, InCallActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Build stack trace for the notification

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(AndroidBootstrapper.getAppContext());
        stackBuilder.addParentStack(InCallActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        // Build notification

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_phone_white_24dp)
                        .setContentTitle("RTDC")
                        .setContentText("In call with " + AndroidVoIPThread.getInstance().getCall().getFrom().getDisplayName());
        PendingIntent inCallPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(inCallPendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(IN_CALL_NOTIFICATION_ID, mBuilder.build());

        AndroidVoIPThread.getInstance().addVoIPListener(this);
    }

    public void onCallEstablished(){
        callFragment.onCallEstablished();
    }

    public void onCallHangup(){
        callFragment.onCallHangup();

        // Drop the notification for the call
        ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).cancel(IN_CALL_NOTIFICATION_ID);

        //TODO: Redirect to communication hub
        // After 1.5 seconds change the interface
        executor.schedule(new Callable() {
            @Override
            public Object call() throws Exception {
                finish();
                return null;
            }
        }, 1500, TimeUnit.MILLISECONDS);
    }

    public void setButtonPressed(ImageButton button, boolean pressed){
        if (pressed) {
            button.setBackgroundResource(R.drawable.circle_blue);
            button.setColorFilter(Color.WHITE);
        }else {
            button.setBackgroundResource(R.drawable.circle_dark_blue);
            button.setColorFilter(getResources().getColor(R.color.RTDC_midnight_blue));
        }
    }

    public void displayVideo(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        callFragment = new VideoCallFragment();
        transaction.replace(R.id.in_call_fragment_wrapper, callFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void displayAudio(){
        if(AndroidVoipController.get().isVideoEnabled())
            AndroidVoipController.get().setVideo(false);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        callFragment = new AudioCallFragment();
        transaction.replace(R.id.in_call_fragment_wrapper, callFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public AbstractCallFragment getCurrentFragment(){
        return callFragment;
    }

    public void displayPauseVideoStatus(final boolean display){
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                if(display) {
                    callFragment.getView().findViewById(R.id.callStatus).setVisibility(View.VISIBLE);
                    ((TextView)callFragment.getView().findViewById(R.id.callStatus)).setText("Paused");
                }else {
                    callFragment.getView().findViewById(R.id.callStatus).setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onStop(){
        super.onStop();
        executor.shutdownNow();
        AndroidVoIPThread.getInstance().removeVoIPListener(this);
    }

    public ScheduledThreadPoolExecutor getExecutor(){
        return executor;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.muteButton){
            boolean micMuted = !Bootstrapper.FACTORY.getVoipController().isMicMuted();

            ImageButton button = (ImageButton) view;
            setButtonPressed(button, micMuted);

            Bootstrapper.FACTORY.getVoipController().setMicMuted(micMuted);
        }else if(view.getId() == R.id.videoButton){
            boolean videoEnabled = !Bootstrapper.FACTORY.getVoipController().isVideoEnabled();

            ImageButton button = (ImageButton) view;
            setButtonPressed(button, videoEnabled);

            Bootstrapper.FACTORY.getVoipController().setVideo(videoEnabled);

            if(videoEnabled && !(callFragment instanceof VideoCallFragment)) {
                // We just enabled video and we're not in the video fragment yet. Go to video fragment
                displayVideo();
            }else if(videoEnabled && callFragment instanceof VideoCallFragment){
                // We just turned on video and we're already in the video fragment, make sure that the video preview is visible
                callFragment.getView().findViewById(R.id.videoCaptureSurface).setVisibility(View.VISIBLE);
            }else if(!videoEnabled && !AndroidVoipController.get().isReceivingRemoteVideo()) {
                // We're not receiving any video from the remote user and we disabled our video. No need to stay in the video fragment
                displayAudio();
            }else if(!videoEnabled && callFragment instanceof VideoCallFragment){
                // We just turned off video and we're still in the video fragment, turn the video preview invisible
                callFragment.getView().findViewById(R.id.videoCaptureSurface).setVisibility(View.INVISIBLE);
            }
        }else if(view.getId() == R.id.speakerButton){
            boolean speaker = !Bootstrapper.FACTORY.getVoipController().isSpeakerEnabled();

            ImageButton button = (ImageButton) view;
            setButtonPressed(button, speaker);

            Bootstrapper.FACTORY.getVoipController().setSpeaker(speaker);
        }else if(view.getId() == R.id.endCallButton){
            // Hangup the call and clean up the interface

            Bootstrapper.FACTORY.getVoipController().hangup();
            onCallHangup();
        }
    }

    @Override
    public void callState(VoIPManager voIPManager, Call call, Call.State state, String message) {
        if(state == Call.State.connected){
            onCallEstablished();
        }else if(state == Call.State.callEnd || state == Call.State.error || state == Call.State.callReleased){
            Context context = AndroidBootstrapper.getAppContext();

            // Remove the notification for the call
            ((NotificationManager) context.getSystemService(
                    context.NOTIFICATION_SERVICE)).cancel(IN_CALL_NOTIFICATION_ID);

            // Call a cleaning method
            onCallHangup();
        }
    }

    @Override
    public void messageReceived(VoIPManager voIPManager, TextGroup textGroup, TextMessage textMessage) {
        if(textMessage.getText().startsWith(Config.COMMAND_EXEC_KEY + "Video: ")) {
            // Check to make sure that if we are in a call that the one that sent the message is the one we're in a call with
            // (It could be someone that's trying to request a video call, but we're in a call with someone already)
            if (AndroidVoIPThread.getInstance().getCall() != null &&
                    !AndroidVoIPThread.getInstance().getRemoteAddress().getUsername().equals(textMessage.getFrom().getUsername()))
                return;
            // There was an update regarding the video of the call
            boolean video = Boolean.valueOf(textMessage.getText().replace(Config.COMMAND_EXEC_KEY + "Video: ", ""));
            AndroidVoipController.get().setRemoteVideo(video);
            if (video) {
                if (AndroidVoipController.get().isVideoEnabled()) {
                    // Remote video is on and we're already in the video fragment. Make sure the pause screen is off
                    displayPauseVideoStatus(false);
                } else {
                    // Remote video is on and we're not in the video fragment. Go to the video fragment
                    displayVideo();
                }
            }else{
                if(AndroidVoipController.get().isVideoEnabled()){
                    // Remote video is off and and we're broadcasting video. Pause the video call
                    displayPauseVideoStatus(true);
                }else{
                    displayAudio();
                }
            }
        }
    }
}
