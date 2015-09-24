package rtdc.android.presenter.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import org.linphone.core.LinphoneCall;
import rtdc.android.R;
import rtdc.android.presenter.CommunicationHubInCallActivity;
import rtdc.android.voip.LiblinphoneThread;
import rtdc.core.Bootstrapper;
import rtdc.core.model.User;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class AudioCallFragment extends AbstractCallFragment{

    private int callDuration;
    private Future timerTask;
    private Future ringingTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_in_audio_call, container, false);
        this.view = view;

        view.findViewById(R.id.speakerButton).setOnClickListener(inCallActivity);
        inCallActivity.setButtonPressed((ImageButton) view.findViewById(R.id.speakerButton), Bootstrapper.FACTORY.getVoipController().isSpeakerEnabled());

        // Display the name of the person we're in call with
        if(LiblinphoneThread.get().getCurrentCallRemoteAddress() != null)
            ((TextView) view.findViewById(R.id.callerText)).setText(LiblinphoneThread.get().getCurrentCallRemoteAddress().getDisplayName());
        else
            ((TextView) view.findViewById(R.id.callerText)).setText("Unknown");

        if(LiblinphoneThread.get().getCurrentCall().getState() == LinphoneCall.State.OutgoingProgress) {
            // Display a ringing message

            ((TextView) view.findViewById(R.id.callStatus)).setText("Ringing");
            final TextView ringingDots = ((TextView) view.findViewById(R.id.ringingDots));
            ringingDots.setVisibility(View.VISIBLE);
            ringingTask = inCallActivity.getExecutor().scheduleWithFixedDelay(new Runnable(){
                @Override
                public void run() {
                    inCallActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(ringingDots.getText().equals("."))
                                ringingDots.setText(". .");
                            else if(ringingDots.getText().equals(". ."))
                                ringingDots.setText(". . . ");
                            else
                                ringingDots.setText(".");
                        }
                    });
                }
            }, 0, 1000, TimeUnit.MILLISECONDS);
        }else{
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
        }
        return view;
    }

    @Override
    public void onCallEstablished(){
        // Stop ringing

        ringingTask.cancel(true);
        inCallActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.findViewById(R.id.ringingDots).setVisibility(View.INVISIBLE);
            }
        });

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
    }

    public void onCallHangup() {
        if (timerTask != null)
            timerTask.cancel(true);
        inCallActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView) view.findViewById(R.id.callStatus)).setText("Call ended");
            }
        });
    }
}
