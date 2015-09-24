package rtdc.android.presenter;

import android.app.*;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.ImageButton;
import org.linphone.BandwidthManager;
import org.linphone.core.LinphoneCall;
import org.linphone.core.LinphoneCallParams;
import org.linphone.core.LinphoneCore;
import org.linphone.core.LinphoneCoreException;
import rtdc.android.AndroidBootstrapper;
import rtdc.android.R;
import rtdc.android.impl.AndroidVoipController;
import rtdc.android.presenter.fragments.AbstractCallFragment;
import rtdc.android.presenter.fragments.AudioCallFragment;
import rtdc.android.presenter.fragments.VideoCallFragment;
import rtdc.android.voip.LiblinphoneThread;
import rtdc.core.Bootstrapper;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommunicationHubInCallActivity extends AbstractActivity implements View.OnClickListener{

    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    private static boolean activityVisible;
    private AbstractCallFragment callFragment;

    private static CommunicationHubInCallActivity currentInstance;

    public final static int IN_CALL_NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        currentInstance = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_call);

        if(AndroidVoipController.get().isReceivingRemoteVideo() || AndroidVoipController.get().isVideoEnabled()){
            displayVideo();
        }else{
            displayAudio();
        }

        // Build the intent that will be used by the notification

        Intent resultIntent = new Intent(this, CommunicationHubInCallActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Build stack trace for the notification

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(AndroidBootstrapper.getAppContext());
        stackBuilder.addParentStack(CommunicationHubInCallActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        // Build notification

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_phone_white_24dp)
                        .setContentTitle("RTDC")
                        .setContentText("In call with " + LiblinphoneThread.get().getCurrentCall().getCallLog().getFrom().getDisplayName());
        PendingIntent inCallPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(inCallPendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(IN_CALL_NOTIFICATION_ID, mBuilder.build());
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
                Intent intent = new Intent(AndroidBootstrapper.getAppContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                AndroidBootstrapper.getAppContext().startActivity(intent);
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
                if(display)
                    callFragment.getView().findViewById(R.id.callStatus).setVisibility(View.VISIBLE);
                else
                    callFragment.getView().findViewById(R.id.callStatus).setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityVisible = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityVisible = false;
    }

    @Override
    public void onStop(){
        super.onStop();
        executor.shutdownNow();
    }

    public ScheduledThreadPoolExecutor getExecutor(){
        return executor;
    }

    public static CommunicationHubInCallActivity getCurrentInstance(){
        return currentInstance;
    }

    public static boolean isActivityVisible() {
        return activityVisible;
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
            }else if(!videoEnabled && !AndroidVoipController.get().isReceivingRemoteVideo()) {
                // We're not receiving any video from the remote user and we disabled our video. No need to stay in the video fragment
                displayAudio();
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
}
