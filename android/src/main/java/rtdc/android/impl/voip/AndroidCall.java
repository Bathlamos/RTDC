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

import org.linphone.core.LinphoneCall;
import org.linphone.core.Reason;
import rtdc.core.impl.voip.Address;
import rtdc.core.impl.voip.Call;
import rtdc.core.impl.voip.CallParameters;
import rtdc.core.impl.voip.VoIPManager;

public class AndroidCall implements Call{

    private LinphoneCall linphoneCall;

    public AndroidCall(LinphoneCall linphoneCall){
        this.linphoneCall = linphoneCall;
    }

    public LinphoneCall getLinphoneCall(){
        return linphoneCall;
    }

    @Override
    public CallParameters getCurrentParamsCopy() {
        return new AndroidCallParameters(linphoneCall.getCurrentParamsCopy());
    }

    @Override
    public int getDuration() {
        return linphoneCall.getDuration();
    }

    @Override
    public State getState() {
        return linphoneStateToState(linphoneCall.getState());
    }

    @Override
    public Address getRemoteAddress() {
        return new AndroidAddress(linphoneCall.getRemoteAddress());
    }

    @Override
    public Address getFrom() {
        return new AndroidAddress(linphoneCall.getCallLog().getFrom());
    }

    @Override
    public Address getTo() {
        return new AndroidAddress(linphoneCall.getCallLog().getTo());
    }

    @Override
    public VoIPManager.Reason getReasonForError() {
        org.linphone.core.Reason linReason = linphoneCall.getErrorInfo().getReason();

        for (VoIPManager.Reason reason: VoIPManager.Reason.values()){
            if(reason.toString().toUpperCase().equals(linReason.toString().toUpperCase())){
                return reason;
            }
        }
        return VoIPManager.Reason.unknown;
    }

    public static Call.State linphoneStateToState(LinphoneCall.State linphoneState){
        for (State state: State.values()) {
            if (state.toString().toUpperCase().equals(linphoneState.toString().toUpperCase())) {
                return state;
            }
        }

        return null;
    }

}
