/*
 * Copyright (c) 2015 Olivier Clermont, Jonathan Ermel, Mathieu Fortin-Boulay, Philippe Legault & Nicolas Ménard
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package rtdc.android.impl.voip;

import org.linphone.core.LinphoneCoreException;
import org.linphone.core.LinphoneCoreFactory;
import org.linphone.core.LinphoneCoreListenerBase;
import rtdc.android.AndroidBootstrapper;
import rtdc.core.impl.voip.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AndroidVoIPThread extends Thread implements VoIPThread{

    private static final VoIPThread INSTANCE = new AndroidVoIPThread();

    private List<VoIPListener> voIPListeners = new ArrayList<>();

    private VoIPManager voIPManager;
    private Address remoteAddress;
    private Call call;
    private Video video;

    private boolean running;

    private AndroidVoIPThread(){
        try {
            voIPManager = new AndroidVoIPManager(LinphoneCoreFactory.instance().createLinphoneCore(new AndroidVoIPListener(), AndroidBootstrapper.getAppContext()));
            start();
        } catch (LinphoneCoreException e) {
            e.printStackTrace();
        }
    }

    public static VoIPThread getInstance() {
        return INSTANCE;
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            voIPManager.iterate();
            try {
                Thread.sleep(50);
            } catch (InterruptedException ie) {
                Logger.getLogger(AndroidVoIPThread.class.getName()).log(Level.SEVERE, "Interrupted! Aborting...");
                return;
            }
        }
        Logger.getLogger(AndroidVoIPThread.class.getName()).log(Level.INFO, "Shutting down...");
        voIPManager.destroy();
        Logger.getLogger(AndroidVoIPThread.class.getName()).log(Level.INFO, "Exited");
    }



    @Override
    public VoIPManager getVoIPManager() {
        return voIPManager;
    }

    @Override
    public void addVoIPListener(VoIPListener listener) {
        voIPListeners.add(listener);
    }

    @Override
    public void removeVoIPListener(VoIPListener listener) {
        voIPListeners.remove(listener);
    }

    @Override
    public Collection<VoIPListener> getVoIPListeners() {
        return voIPListeners;
    }

    @Override
    public Call getCall() {
        return call;
    }

    @Override
    public void setCall(Call call) {
        this.call = call;
    }

    @Override
    public Video getVideo() {
        return video;
    }

    @Override
    public void setVideo(Video video) {
        this.video = video;
    }

    @Override
    public Address getRemoteAddress() {
        return remoteAddress;
    }

    @Override
    public void setRemoteAddress(Address remoteAddress) {
        this.remoteAddress = remoteAddress;
    }
}
