package rtdc.android.voip;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import org.linphone.core.LinphoneCore;
import rtdc.android.impl.AndroidVoipController;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/*
    This class takes care of keeping track of network changes for the VOIP library.
    It is also responsible to drop all calls in progress after a certain amount of time if the network is declared unreachable
 */

public class VoipConnectionManager extends BroadcastReceiver {

    private final int CALL_TIMEOUT = 5; // The time (in seconds) it takes after the network is declared unreachable to hangup all calls
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo eventInfo = cm.getActiveNetworkInfo();

        if (eventInfo == null || (eventInfo.getState() == NetworkInfo.State.DISCONNECTED && eventInfo.getType() == ConnectivityManager.TYPE_WIFI)) {
            final LinphoneCore lc = LiblinphoneThread.get().getLinphoneCore();
            Logger.getLogger(VoipConnectionManager.class.getName()).warning("No connectivity: setting network to unreachable");
            lc.setNetworkReachable(false);

            executor.schedule(new Callable(){
                @Override
                public Object call() throws Exception {
                    // Check if network is still unreachable. If its not, drop all calls
                    if(LiblinphoneThread.get().getCurrentCall() != null && !lc.isNetworkReachable()){
                        AndroidVoipController.get().hangup();
                    }
                    return null;
                }
            }, CALL_TIMEOUT, TimeUnit.SECONDS);

        } else if (eventInfo.getState() == NetworkInfo.State.CONNECTED && eventInfo.getType() == ConnectivityManager.TYPE_WIFI){
            Logger.getLogger(VoipConnectionManager.class.getName()).info("Wifi is now connected: setting network to reachable");
            LiblinphoneThread.get().getLinphoneCore().setNetworkReachable(true);
        }
    }
}
