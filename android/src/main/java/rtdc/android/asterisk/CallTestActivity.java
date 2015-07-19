package rtdc.android.asterisk;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.common.collect.ImmutableSet;
import rtdc.android.R;
import rtdc.core.Config;
import rtdc.core.event.Event;
import rtdc.core.event.FetchUsersEvent;
import rtdc.core.model.User;
import rtdc.core.service.Service;
import org.linphone.core.*;

import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CallTestActivity extends Activity implements LinphoneCoreListener, FetchUsersEvent.Handler {

    private LinphoneCore lc;
    private final String username = "Qwe";
    private final String password = "password";
    private final String domain = Config.ASTERISK_IP;
    String callName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_test);

        LiblinphoneTest.get().registerAccount("sip:" + username + "@" + domain, password, this);
        TextView registeredText = (TextView) findViewById(R.id.registered);
        registeredText.setText("Registered as: " + username);

        findViewById(R.id.call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callName = ((EditText) findViewById(R.id.callName)).getText().toString();
                Service.getUsers();
            }
        });

        findViewById(R.id.answerCall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    lc.acceptCall(lc.getCurrentCall());
                } catch (LinphoneCoreException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.endCall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lc.terminateCall(lc.getCurrentCall());
            }
        });

        findViewById(R.id.destroyLiblinphone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LiblinphoneTest.get().stopMainLoop();
            }
        });

        lc = LiblinphoneTest.get().getLinphoneCore();

        Event.subscribe(FetchUsersEvent.TYPE, this);
    }

    public void callState(LinphoneCore lc, LinphoneCall call, final LinphoneCall.State cstate, final String msg) {
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                TextView status = (TextView) findViewById(R.id.status);
                status.setText("Status: " + msg + " (" + cstate + ")");
            }
        });
    }

    @Override
    public void onUsersFetched(FetchUsersEvent event) {
        ImmutableSet<User> users = event.getUsers();
        String callTo = null;
        for(User user: users){
            //if(user.getFirstName().equals(callName.split(" ")[0]) && user.getFirstName().equals(callName.split(" ")[1])){
            if(user.getFirstName().equals(callName.split(" ")[0])){
                callTo = String.valueOf(user.getId());
                break;
            }
        }
        Logger.getLogger(CallTestActivity.class.getName()).log(Level.INFO, callTo);
        if(callTo != null){
            //callTo = username.equals("6002") ? "6001" : "6002";
            LiblinphoneTest.get().call("sip:"+callTo+"@"+domain);
        }
    }

    @Override
    public void authInfoRequested(LinphoneCore linphoneCore, String s, String s1, String s2) {

    }

    @Override
    public void globalState(LinphoneCore linphoneCore, LinphoneCore.GlobalState globalState, String s) {

    }

    @Override
    public void callStatsUpdated(LinphoneCore linphoneCore, LinphoneCall linphoneCall, LinphoneCallStats linphoneCallStats) {

    }

    @Override
    public void callEncryptionChanged(LinphoneCore linphoneCore, LinphoneCall linphoneCall, boolean b, String s) {

    }

    @Override
    public void registrationState(LinphoneCore linphoneCore, LinphoneProxyConfig linphoneProxyConfig, LinphoneCore.RegistrationState registrationState, String s) {

    }

    @Override
    public void newSubscriptionRequest(LinphoneCore linphoneCore, LinphoneFriend linphoneFriend, String s) {

    }

    @Override
    public void notifyPresenceReceived(LinphoneCore linphoneCore, LinphoneFriend linphoneFriend) {

    }

    @Override
    public void textReceived(LinphoneCore linphoneCore, LinphoneChatRoom linphoneChatRoom, LinphoneAddress linphoneAddress, String s) {

    }

    @Override
    public void messageReceived(LinphoneCore lc, LinphoneChatRoom cr,
                                LinphoneChatMessage message) {
    }

    @Override
    public void isComposingReceived(LinphoneCore linphoneCore, LinphoneChatRoom linphoneChatRoom) {

    }

    @Override
    public void dtmfReceived(LinphoneCore linphoneCore, LinphoneCall linphoneCall, int i) {

    }

    @Override
    public void ecCalibrationStatus(LinphoneCore linphoneCore, LinphoneCore.EcCalibratorStatus ecCalibratorStatus, int i, Object o) {

    }

    @Override
    public void notifyReceived(LinphoneCore linphoneCore, LinphoneCall linphoneCall, LinphoneAddress linphoneAddress, byte[] bytes) {

    }

    @Override
    public void transferState(LinphoneCore linphoneCore, LinphoneCall linphoneCall, LinphoneCall.State state) {

    }

    @Override
    public void infoReceived(LinphoneCore linphoneCore, LinphoneCall linphoneCall, LinphoneInfoMessage linphoneInfoMessage) {

    }

    @Override
    public void subscriptionStateChanged(LinphoneCore linphoneCore, LinphoneEvent linphoneEvent, SubscriptionState subscriptionState) {

    }

    @Override
    public void notifyReceived(LinphoneCore linphoneCore, LinphoneEvent linphoneEvent, String s, LinphoneContent linphoneContent) {

    }

    @Override
    public void publishStateChanged(LinphoneCore linphoneCore, LinphoneEvent linphoneEvent, PublishState publishState) {

    }

    @Override
    public void configuringStatus(LinphoneCore linphoneCore, LinphoneCore.RemoteProvisioningState remoteProvisioningState, String s) {

    }

    @Override
    public void show(LinphoneCore linphoneCore) {

    }

    @Override
    public void displayStatus(LinphoneCore linphoneCore, String s) {

    }

    @Override
    public void displayMessage(LinphoneCore linphoneCore, String s) {

    }

    @Override
    public void displayWarning(LinphoneCore linphoneCore, String s) {

    }

    @Override
    public void fileTransferProgressIndication(LinphoneCore linphoneCore, LinphoneChatMessage linphoneChatMessage, LinphoneContent linphoneContent, int i) {

    }

    @Override
    public void fileTransferRecv(LinphoneCore linphoneCore, LinphoneChatMessage linphoneChatMessage, LinphoneContent linphoneContent, byte[] bytes, int i) {

    }

    @Override
    public int fileTransferSend(LinphoneCore linphoneCore, LinphoneChatMessage linphoneChatMessage, LinphoneContent linphoneContent, ByteBuffer byteBuffer, int i) {
        return 0;
    }
}
