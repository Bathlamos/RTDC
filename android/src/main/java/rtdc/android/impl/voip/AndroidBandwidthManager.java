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

import org.linphone.core.LinphoneCallParams;
import org.linphone.core.LinphoneCore;
import rtdc.core.impl.voip.BandwidthManager;
import rtdc.core.impl.voip.CallParameters;
import rtdc.core.impl.voip.VoIPManager;

public class AndroidBandwidthManager implements BandwidthManager {

    private static final AndroidBandwidthManager INSTANCE = new AndroidBandwidthManager();

    private AndroidBandwidthManager(){
    }

    public static AndroidBandwidthManager getInstance() {
        return INSTANCE;
    }

    @Override
    public void updateWithProfileSettings(VoIPManager voIPManager, CallParameters callParameters) {
        LinphoneCore linphoneCore= ((AndroidVoIPManager) voIPManager).getLinphoneCore();
        LinphoneCallParams linphoneCallParams = ((AndroidCallParameters) callParameters).getLinphoneCallParams();

        org.linphone.BandwidthManager.getInstance().updateWithProfileSettings(linphoneCore, linphoneCallParams);
    }


}
