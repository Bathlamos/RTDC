package rtdc.android.presenter;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import rtdc.android.R;
import rtdc.android.impl.AndroidVoipController;
import rtdc.android.impl.voip.AndroidVoIPThread;
import rtdc.core.Bootstrapper;
import rtdc.core.Config;
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
                Bootstrapper.FACTORY.getVoipController().acceptCall();
            }
        });

        findViewById(R.id.refuseCall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bootstrapper.FACTORY.getVoipController().declineCall();
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
        if(textMessage.getText().startsWith(Config.COMMAND_EXEC_KEY + "Video: ")){
            // Check to make sure that if we are in a call that the one that sent the message is the one we're in a call with
            // (It could be someone that's trying to request a video call, but we're in a call with someone already)
            if(AndroidVoIPThread.getInstance().getCall() != null &&
                    !AndroidVoIPThread.getInstance().getRemoteAddress().getUsername().equals(textMessage.getFrom().getUsername()))
                return;
            // There was an update regarding the video of the call
            final boolean video = Boolean.valueOf(textMessage.getText().replace(Config.COMMAND_EXEC_KEY + "Video: ", ""));
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
