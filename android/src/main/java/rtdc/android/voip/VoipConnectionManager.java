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

package rtdc.android.voip;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import rtdc.android.impl.AndroidVoipController;
import rtdc.android.impl.voip.AndroidVoIPThread;
import rtdc.core.impl.voip.VoIPManager;

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
            final VoIPManager vm = AndroidVoIPThread.getInstance().getVoIPManager();
            Logger.getLogger(VoipConnectionManager.class.getName()).warning("No connectivity: setting network to unreachable");
            vm.setNetworkReachable(false);

            executor.schedule(new Callable(){
                @Override
                public Object call() throws Exception {
                    // Check if network is still unreachable. If its not, drop all calls
                    if(AndroidVoIPThread.getInstance().getCall() != null && !vm.isNetworkReachable()){
                        AndroidVoipController.get().hangup();
                    }
                    return null;
                }
            }, CALL_TIMEOUT, TimeUnit.SECONDS);

        } else if (eventInfo.getState() == NetworkInfo.State.CONNECTED && eventInfo.getType() == ConnectivityManager.TYPE_WIFI){
            Logger.getLogger(VoipConnectionManager.class.getName()).info("Wifi is now connected: setting network to reachable");
            AndroidVoIPThread.getInstance().getVoIPManager().setNetworkReachable(true);
        }
    }
}
