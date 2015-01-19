package rtdc.android;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.sip.*;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MyActivity extends Activity {

    public SipManager mSipManager = null;
    public SipProfile mSipProfile = null;
    public SipAudioCall.Listener listener = null;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void login(View view){
        if(mSipManager == null) {
            mSipManager = SipManager.newInstance(this);
        }

        try{
            SipProfile.Builder builder = new SipProfile.Builder("nicolas", domain);
            builder.setPassword("password");
            mSipProfile = builder.build();

            Intent intent = new Intent();
            intent.setAction("android.SipDemo.INCOMING_CALL");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, Intent.FILL_IN_DATA);
            mSipManager.open(mSipProfile, pendingIntent, null);

            mSipManager.setRegistrationListener(mSipProfile.getUriString(), new SipRegistrationListener() {

                public void onRegistering(String localProfileUri) {
                    System.out.println("Registering with SIP Server...");
                }

                public void onRegistrationDone(String localProfileUri, long expiryTime) {
                    System.out.println("Ready");
                }

                public void onRegistrationFailed(String localProfileUri, int errorCode,
                                                 String errorMessage) {
                    System.out.println("Registration failed.  Please check settings.");
                }
            });

            listener = new SipAudioCall.Listener() {

                @Override
                public void onCallEstablished(SipAudioCall call) {
                    call.startAudio();
                    call.setSpeakerMode(true);
                    call.toggleMute();
                }

                @Override
                public void onCallEnded(SipAudioCall call) {
                    call.toggleMute();
                    call.setSpeakerMode(false);
                }
            };


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void call(View view) throws SipException {
        mSipManager.makeAudioCall(mSipProfile.getUriString(), sipAddress, listener, 30);
    }

    public void logout(View view){
        if (mSipManager == null) {
            return;
        }
        try {
            if (mSipProfile != null) {
                mSipManager.close(mSipProfile.getUriString());
            }
        } catch (Exception ee) {
            Log.d("WalkieTalkieActivity/onDestroy", "Failed to close local profile.", ee);
        }
    }

}
