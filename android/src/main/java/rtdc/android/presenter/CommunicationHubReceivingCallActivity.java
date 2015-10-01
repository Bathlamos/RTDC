package rtdc.android.presenter;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import rtdc.android.R;
import rtdc.android.impl.AndroidVoipController;
import rtdc.android.presenter.AbstractActivity;
import rtdc.android.voip.LiblinphoneThread;
import rtdc.core.Bootstrapper;

public class CommunicationHubReceivingCallActivity extends AbstractActivity{

    private static boolean activityVisible;

    private static CommunicationHubReceivingCallActivity currentInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        currentInstance = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_communication_hub_receiving_call);

        ((TextView) findViewById(R.id.callerText)).setText(LiblinphoneThread.get().getCurrentCall().getCallLog().getFrom().getDisplayName());

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
    }

    // The back button should not do anything in this activity since we need the user to choose an option

    @Override
    public void onBackPressed() {
    }

    public static CommunicationHubReceivingCallActivity getInstance(){
        return currentInstance;
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

    public static boolean isActivityVisible() {
        return activityVisible;
    }
}
