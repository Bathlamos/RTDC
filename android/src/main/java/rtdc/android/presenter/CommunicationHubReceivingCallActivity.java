package rtdc.android.presenter;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import org.linphone.core.LinphoneCall;
import org.linphone.core.LinphoneChatMessage;
import rtdc.android.R;
import rtdc.android.impl.AndroidVoipController;
import rtdc.android.voip.LiblinphoneThread;
import rtdc.android.voip.VoipListener;
import rtdc.core.Bootstrapper;
import rtdc.core.config.Conf;

public class CommunicationHubReceivingCallActivity extends AbstractActivity implements VoipListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_communication_hub_receiving_call);

        ((TextView) findViewById(R.id.callerText)).setText(LiblinphoneThread.get().getCurrentCall().getCallLog().getFrom().getDisplayName());

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
                CommunicationHubReceivingCallActivity.this.finish();
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

        LiblinphoneThread.get().addVoipListener(this);
    }

    // The back button should not do anything in this activity since we need the user to choose an option

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onStop(){
        super.onStop();
        LiblinphoneThread.get().removeVoipListener(this);
    }

    @Override
    public void onCallStateChanged(LinphoneCall call, LinphoneCall.State state) {
        if(state == LinphoneCall.State.CallEnd || state == LinphoneCall.State.Error || state == LinphoneCall.State.CallReleased){
            finish();
        }
    }

    @Override
    public void onMessageReceived(LinphoneChatMessage chatMessage) {
        String commandExecKey = Conf.get().commandExecKey();
        if(chatMessage.getText().startsWith(commandExecKey + "Video: ")){
            // Check to make sure that if we are in a call that the one that sent the message is the one we're in a call with
            // (It could be someone that's trying to request a video call, but we're in a call with someone already)
            if(LiblinphoneThread.get().getCurrentCall() != null &&
                    !LiblinphoneThread.get().getCurrentCallRemoteAddress().getUserName().equals(chatMessage.getFrom().getUserName()))
                return;
            // There was an update regarding the video of the call
            boolean video = Boolean.valueOf(chatMessage.getText().replace(commandExecKey + "Video: ", ""));
            AndroidVoipController.get().setRemoteVideo(video);
            if(video){
                if(!AndroidVoipController.get().isVideoEnabled()){
                    ((TextView)findViewById(R.id.incomingCallText)).setText("Incoming video call");
                }
            }else{
                if(!AndroidVoipController.get().isVideoEnabled()){
                    ((TextView)findViewById(R.id.incomingCallText)).setText("Incoming call");
                }
            }
        }
    }
}
