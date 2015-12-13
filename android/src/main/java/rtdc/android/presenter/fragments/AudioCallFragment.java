/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Olivier Clermont, Jonathan Ermel, Mathieu Fortin-Boulay, Philippe Legault & Nicolas Ménard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package rtdc.android.presenter.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import rtdc.android.R;
import rtdc.android.impl.voip.AndroidVoIPThread;
import rtdc.core.Bootstrapper;
import rtdc.core.impl.voip.Call;

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

        if(!getResources().getBoolean(R.bool.isTablet)){
            // Not a tablet, we should give the user the possibility of switching to speaker mode
            view.findViewById(R.id.speakerButton).setOnClickListener(inCallActivity);
            inCallActivity.setButtonPressed((ImageButton) view.findViewById(R.id.speakerButton), Bootstrapper.getFactory().getVoipController().isSpeakerEnabled());
        }else{
            // Tablets always have speaker mode on
            ImageButton speakerButton = (ImageButton) view.findViewById(R.id.speakerButton);
            view.findViewById(R.id.speakerLayout).setAlpha(0.5f);
            speakerButton.setBackgroundResource(R.drawable.circle_blue);
            speakerButton.setColorFilter(getResources().getColor(R.color.RTDC_light_grey));
        }

        // Display the name of the person we're in call with
        if(AndroidVoIPThread.getInstance().getRemoteAddress() != null)
            ((TextView) view.findViewById(R.id.callerText)).setText(AndroidVoIPThread.getInstance().getRemoteAddress().getDisplayName());
        else
            ((TextView) view.findViewById(R.id.callerText)).setText("Unknown");

        if(AndroidVoIPThread.getInstance().getCall().getState() == Call.State.outgoingProgress) {
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
            callDuration = AndroidVoIPThread.getInstance().getCall().getDuration();  // We start with the correct time

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

        callDuration = AndroidVoIPThread.getInstance().getCall().getDuration();  // We start with the correct time

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
