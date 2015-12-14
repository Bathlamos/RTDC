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
import rtdc.core.impl.voip.CallParameters;

public class AndroidCallParameters implements CallParameters{

    LinphoneCallParams linphoneCallParams;

    public AndroidCallParameters(LinphoneCallParams linphoneCallParams){
        this.linphoneCallParams = linphoneCallParams;
    }

    public LinphoneCallParams getLinphoneCallParams(){
        return linphoneCallParams;
    }

    @Override
    public void setVideoEnabled(boolean enabled) {
        linphoneCallParams.setVideoEnabled(enabled);
    }

    @Override
    public boolean getVideoEnabled() {
        return linphoneCallParams.getVideoEnabled();
    }
}
