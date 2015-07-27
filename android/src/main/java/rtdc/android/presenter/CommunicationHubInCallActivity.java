package rtdc.android.presenter;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import rtdc.android.AndroidBootstrapper;
import rtdc.android.R;
import rtdc.android.presenter.AbstractActivity;
import rtdc.android.voip.LiblinphoneThread;
import rtdc.core.Bootstrapper;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CommunicationHubInCallActivity extends AbstractActivity {

    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    private Future timerTask;
    private static boolean activityVisible;

    private int callDuration;
    private boolean speaker;
    private boolean micMuted;

    private static CommunicationHubInCallActivity currentInstance;

    public final static int IN_CALL_NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        currentInstance = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_communication_hub_in_call);

        // Display the name of the person we're in call with
        ((TextView) findViewById(R.id.callerText)).setText(LiblinphoneThread.get().getCurrentCall().getCallLog().getFrom().getDisplayName());

        callDuration = LiblinphoneThread.get().getCurrentCall().getDuration();  // We start with the correct time

        findViewById(R.id.muteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                micMuted = !micMuted;

                ImageButton button = (ImageButton) view;
                setButtonPressed(button, micMuted);

                Bootstrapper.FACTORY.getVoipController().setMicMuted(micMuted);
            }
        });

        findViewById(R.id.speakerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioManager audioManager = (AudioManager) AndroidBootstrapper.getAppContext().getSystemService(
                        AndroidBootstrapper.getAppContext().AUDIO_SERVICE);

                speaker = !speaker;

                ImageButton button = (ImageButton) view;
                setButtonPressed(button, speaker);

                Bootstrapper.FACTORY.getVoipController().setSpeaker(speaker);
                audioManager.setSpeakerphoneOn(speaker);
            }
        });

        findViewById(R.id.endCallButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hangup the call and clean up the interface

                Bootstrapper.FACTORY.getVoipController().hangup();
                hangupCleanup();
            }
        });

        // Keeps track of the duration of the call, by incrementing a counter every second

        timerTask = executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                callDuration++;
                final String minutes = String.format("%02d", callDuration / 60);
                final String seconds = String.format("%02d", callDuration % 60);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView) findViewById(R.id.callStatus)).setText(minutes + ":" + seconds);
                    }
                });
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);

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

    public void hangupCleanup(){
        timerTask.cancel(true);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView) findViewById(R.id.callStatus)).setText("Call ended");
            }
        });

        // Drop the notification for the call
        ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).cancel(IN_CALL_NOTIFICATION_ID);

        //TODO: Redirect to communication hub
        // After 3 seconds change the interface
        executor.schedule(new Callable() {
            @Override
            public Object call() throws Exception {
                Intent intent = new Intent(AndroidBootstrapper.getAppContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                AndroidBootstrapper.getAppContext().startActivity(intent);
                return null;
            }
        }, 3000, TimeUnit.MILLISECONDS);
    }

    public void setButtonPressed(ImageButton button, boolean pressed){
        if (pressed) {
            button.setBackgroundResource(R.drawable.circle_blue);
            button.setColorFilter(Color.WHITE);
        }else {
            button.setBackgroundResource(R.drawable.circle_blue_border);
            button.setColorFilter(Color.BLACK);
        }
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

    public static CommunicationHubInCallActivity getCurrentInstance(){
        return currentInstance;
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }
}
