package rtdc.android.asterisk;

import android.content.Context;
import org.linphone.core.*;
import rtdc.android.AndroidBootstrapper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LiblinphoneTest {
    private boolean running;
    private static LiblinphoneTest instance;
    private Context app;
    private LinphoneCore lc;

    private LiblinphoneTest() {
        app = AndroidBootstrapper.getAppContext();
    }

    public static LiblinphoneTest get() {
        if (instance == null)
            instance = new LiblinphoneTest();
        return instance;
    }

    public void registerAccount(String sipAddress, String password, LinphoneCoreListener listener) {
        try {
            lc = LinphoneCoreFactory.instance().createLinphoneCore(listener, app);

            LinphoneAddress address = LinphoneCoreFactory.instance().createLinphoneAddress(sipAddress);
            String username = address.getUserName();
            String domain = address.getDomain();

            LinphoneAuthInfo info = LinphoneCoreFactory.instance().createAuthInfo(username, password, null, "sip:" + domain);
            lc.addAuthInfo(info);

            LinphoneProxyConfig proxyCfg = lc.createProxyConfig(sipAddress, domain, null, true);
            proxyCfg.setExpires(60);
            lc.addProxyConfig(proxyCfg);

            // main loop for receiving notifications and doing background linphonecore work
            new Thread(){
                @Override
                public void run() {
                    running = true;
                    while (running) {
                        lc.iterate();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException ie) {
                            Logger.getLogger(LiblinphoneTest.class.getName()).log(Level.SEVERE, "Interrupted! Aborting...");
                            return;
                        }
                    }
                    Logger.getLogger(LiblinphoneTest.class.getName()).log(Level.INFO, "Shutting down...");
                    lc.destroy();
                    Logger.getLogger(LiblinphoneTest.class.getName()).log(Level.INFO, "Exited");
                }
            }.start();
        } catch (LinphoneCoreException e) {
            e.printStackTrace();
        }
    }


    public void call(final String sipAddress) {
        try {
            // Send the INVITE message to destination SIP address
            LinphoneCall call = lc.invite(sipAddress);

            if (call == null) {
                Logger.getLogger(LiblinphoneTest.class.getName()).log(Level.WARNING, "Could not place call to " + sipAddress + ", aborting...");
                return;
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void stopMainLoop() {
        running = false;
    }

    public LinphoneCore getLinphoneCore(){
        return lc;
    }
}