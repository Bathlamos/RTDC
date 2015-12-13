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

package rtdc.android.presenter;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import rtdc.android.R;
import rtdc.android.impl.AndroidVoipController;
import rtdc.android.impl.voip.AndroidVoIPThread;
import rtdc.core.Bootstrapper;
import rtdc.core.config.Conf;
import rtdc.core.impl.voip.*;

public class IncomingCallActivity extends AbstractActivity implements VoIPListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_communication_hub_receiving_call);

        ((TextView) findViewById(R.id.callerText)).setText(AndroidVoIPThread.getInstance().getCall().getFrom().getDisplayName());

        if(AndroidVoipController.get().isReceivingRemoteVideo())
            ((TextView) findViewById(R.id.incomingCallText)).setText("Incoming video call");

        findViewById(R.id.acceptCall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bootstrapper.getFactory().getVoipController().acceptCall();
            }
        });

        findViewById(R.id.refuseCall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bootstrapper.getFactory().getVoipController().declineCall();
                IncomingCallActivity.this.finish();
            }
        });

        // Wake up the phone if it is sleeping

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        AndroidVoIPThread.getInstance().addVoIPListener(this);
    }

    // The back button should not do anything in this activity since we need the user to choose an option

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onStop(){
        super.onStop();
        AndroidVoIPThread.getInstance().removeVoIPListener(this);
    }

    @Override
    public void callState(VoIPManager voIPManager, Call call, Call.State state, String message) {
        if(state == Call.State.callEnd || state == Call.State.error || state == Call.State.callReleased){
            finish();
        }
    }

    @Override
    public void messageReceived(VoIPManager voIPManager, TextGroup textGroup, TextMessage textMessage) {
        if(textMessage.getText().startsWith(Conf.get().commandExecKey() + "Video: ")){
            // Check to make sure that if we are in a call that the one that sent the message is the one we're in a call with
            // (It could be someone that's trying to request a video call, but we're in a call with someone already)
            if(AndroidVoIPThread.getInstance().getCall() != null &&
                    !AndroidVoIPThread.getInstance().getRemoteAddress().getUsername().equals(textMessage.getFrom().getUsername()))
                return;
            // There was an update regarding the video of the call
            final boolean video = Boolean.valueOf(textMessage.getText().replace(Conf.get().commandExecKey() + "Video: ", ""));
            AndroidVoipController.get().setRemoteVideo(video);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(video){
                        ((TextView)findViewById(R.id.incomingCallText)).setText("Incoming video call");
                    }else{
                        ((TextView)findViewById(R.id.incomingCallText)).setText("Incoming call");
                    }
                }
            });
        }
    }
}
