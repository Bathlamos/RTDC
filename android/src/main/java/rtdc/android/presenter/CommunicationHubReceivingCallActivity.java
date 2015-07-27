package rtdc.android.presenter;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import rtdc.android.R;
import rtdc.android.presenter.AbstractActivity;
import rtdc.android.voip.LiblinphoneThread;
import rtdc.core.Bootstrapper;

public class CommunicationHubReceivingCallActivity extends AbstractActivity{

    private int callDuration = 0; // Seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_communication_hub_receiving_call);

        ((TextView) findViewById(R.id.callerText)).setText(LiblinphoneThread.get().getCurrentCall().getCallLog().getFrom().getDisplayName());

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
            }
        });
    }
}
