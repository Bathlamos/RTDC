package rtdc.android.asterisk;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.sip.*;
import rtdc.android.AndroidBootstrapper;
import rtdc.android.Rtdc;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Deprecated
public class AndroidSIPTest {

    public SipManager mSipManager = null;
    public SipProfile mSipProfile = null;
    private static AndroidSIPTest instance;
    private Context app;

    public static AndroidSIPTest get(){
        if(instance == null)
            instance = new AndroidSIPTest();
        return instance;
    }

    private AndroidSIPTest(){
        this.app = AndroidBootstrapper.getAppContext();
        instance = this;
        mSipManager = SipManager.newInstance(app);
        System.out.println("api "+SipManager.isApiSupported(app));
        System.out.println("voip " + SipManager.isVoipSupported(app));
        SipProfile.Builder builder = null;
        try {
            builder = new SipProfile.Builder("6002", "192.168.0.23");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        builder.setPassword("password");
        mSipProfile = builder.build();

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.RTDC.INCOMING_CALL");
        app.registerReceiver(new IncomingCallReceiver(), filter);
    }

    public void makeCall(){
        try {
            Intent intent = new Intent();
            intent.setAction("android.RTDC.INCOMING_CALL");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(app, 0, intent, Intent.FILL_IN_DATA);
            mSipManager.open(mSipProfile, pendingIntent, null);

            SipRegistrationListener listener = new SipRegistrationListener() {

                public void onRegistering(String localProfileUri) {
                    Logger.getLogger(AndroidSIPTest.class.getName()).log(Level.INFO, localProfileUri + " -  Registering with the Asterisk server...");
                }

                public void onRegistrationDone(String localProfileUri, long expiryTime) {
                    Logger.getLogger(AndroidSIPTest.class.getName()).log(Level.INFO, localProfileUri + " -  Registration successful!");
                }

                public void onRegistrationFailed(String localProfileUri, int errorCode, String errorMessage) {
                    Logger.getLogger(AndroidSIPTest.class.getName()).log(Level.SEVERE, localProfileUri + " -  Registration failed.  Error code " + errorCode + ": " + errorMessage);
                }
            };
            mSipManager.setRegistrationListener(mSipProfile.getUriString(), listener);
        } catch (SipException e) {
            e.printStackTrace();
        }

        final SipAudioCall.Listener audioListener = new SipAudioCall.Listener() {

            @Override
            public void onCallEstablished(SipAudioCall call) {
                call.startAudio();
                //call.setSpeakerMode(true);
                Logger.getLogger(AndroidSIPTest.class.getName()).log(Level.INFO, "Call started.");
            }

            @Override
            public void onCallEnded(SipAudioCall call) {
                try {
                    mSipManager.close(mSipProfile.getUriString());
                    Logger.getLogger(AndroidSIPTest.class.getName()).log(Level.INFO, "Call ended.");
                } catch (SipException e) {
                    e.printStackTrace();
                }
            }
        };
        try {
            mSipManager.makeAudioCall(mSipProfile.getUriString(), "sip:6001@192.168.0.23", audioListener, 30);
        } catch (SipException e) {
            e.printStackTrace();
        }
    }
}
