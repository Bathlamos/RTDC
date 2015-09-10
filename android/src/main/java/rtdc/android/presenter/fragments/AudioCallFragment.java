package rtdc.android.presenter.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import rtdc.android.R;
import rtdc.android.presenter.CommunicationHubInCallActivity;
import rtdc.android.voip.LiblinphoneThread;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class AudioCallFragment extends AbstractCallFragment{

    private int callDuration;
    private Future timerTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_in_audio_call, container, false);
        this.view = view;

        // Display the name of the person we're in call with
        ((TextView) view.findViewById(R.id.callerText)).setText(LiblinphoneThread.get().getCurrentCall().getCallLog().getFrom().getDisplayName());

        callDuration = LiblinphoneThread.get().getCurrentCall().getDuration();  // We start with the correct time

        // Keeps track of the duration of the call, by incrementing a counter every second

        timerTask = inCallActivity.getExecutor().scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                callDuration++;
                final String minutes = String.format("%02d", callDuration / 60);
                final String seconds = String.format("%02d", callDuration % 60);
                inCallActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView) view.findViewById(R.id.callStatus)).setText(minutes + ":" + seconds);
                    }
                });
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);

        return view;
    }

    public void hangupCleanup() {
        timerTask.cancel(true);
        inCallActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView) view.findViewById(R.id.callStatus)).setText("Call ended");
            }
        });
    }
}
