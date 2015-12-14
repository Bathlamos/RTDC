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

import org.linphone.LinphoneUtils;
import org.linphone.core.*;
import rtdc.core.config.Conf;
import rtdc.core.impl.voip.*;
import rtdc.core.model.User;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AndroidVoIPManager implements VoIPManager {

    private LinphoneCore linphoneCore;
    private static LinphoneProxyConfig linphoneProxyConfig;
    private static LinphoneAuthInfo linphoneAuthInfo;

    public AndroidVoIPManager(LinphoneCore linphoneCore){
        this.linphoneCore = linphoneCore;
        this.linphoneCore.setPreferredVideoSize(VideoSize.VIDEO_SIZE_VGA);
    }

    public LinphoneCore getLinphoneCore(){
        return linphoneCore;
    }

    @Override
    public CallParameters createDefaultCallParameters() {
        return new AndroidCallParameters(linphoneCore.createDefaultCallParameters());
    }

    @Override
    public void iterate() {
        linphoneCore.iterate();
    }

    @Override
    public void destroy() {
        linphoneCore.destroy();
    }

    @Override
    public void setNetworkReachable(boolean reachable) {
        linphoneCore.setNetworkReachable(reachable);
    }

    @Override
    public boolean isNetworkReachable() {
        return linphoneCore.isNetworkReachable();
    }

    @Override
    public void registerUser(User user, String asteriskPassword) {
        try {
            Logger.getLogger(AndroidVoIPManager.class.getName()).log(Level.INFO, "Registering user...");

            String sipAddress = "sip:" + user.getUsername() + "@" + Conf.get().asteriskHost();

            linphoneProxyConfig = linphoneCore.createProxyConfig(sipAddress, Conf.get().asteriskHost(), null, true);
            linphoneProxyConfig.setExpires(60);
            linphoneCore.addProxyConfig(linphoneProxyConfig);
            linphoneAuthInfo = LinphoneCoreFactory.instance().createAuthInfo(user.getUsername(), asteriskPassword, null, "sip:" + Conf.get().asteriskHost());

            linphoneCore.addAuthInfo(linphoneAuthInfo);
            linphoneCore.addProxyConfig(linphoneProxyConfig);

            Logger.getLogger(AndroidVoIPManager.class.getName()).log(Level.INFO, "Registered user " + user.getId() + " on SIP server");
        } catch (LinphoneCoreException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unregisterUser() {
        // Set the user to be unregistered
        linphoneProxyConfig.edit();
        linphoneProxyConfig.enableRegister(false);
        linphoneProxyConfig.done();

        // Let the Liblinphone thread unregister
        if(linphoneProxyConfig.getState() == LinphoneCore.RegistrationState.RegistrationOk) {
            while (linphoneProxyConfig.getState() != LinphoneCore.RegistrationState.RegistrationCleared) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // Once we've been successfully unregistered, remove all authentication info
        linphoneCore.removeAuthInfo(linphoneAuthInfo);
        linphoneCore.removeProxyConfig(linphoneProxyConfig);

        linphoneAuthInfo = null;
        linphoneProxyConfig = null;
        Logger.getLogger(AndroidVoIPManager.class.getName()).log(Level.INFO, "Unregistered user from the SIP server");
    }

    @Override
    public Address buildAddress(String sipAddress) {
        try {
            return new AndroidAddress(linphoneCore.interpretUrl(sipAddress));
        } catch (LinphoneCoreException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Call initiateCall(Address destination, CallParameters callParameters) {
        LinphoneAddress linphoneAddress = ((AndroidAddress) destination).getLinphoneAddress();
        LinphoneCallParams linphoneCallParams = ((AndroidCallParameters) callParameters).getLinphoneCallParams();
        try {
            return new AndroidCall(linphoneCore.inviteAddressWithParams(linphoneAddress, linphoneCallParams));
        } catch (LinphoneCoreException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void acceptCall(Call call) throws ClassCastException{
        LinphoneCall linphoneCall = ((AndroidCall) call).getLinphoneCall();
        try{
            linphoneCore.acceptCall(linphoneCall);
        } catch (LinphoneCoreException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateCall(Call call, CallParameters callParameters){
        LinphoneCall linphoneCall = ((AndroidCall) call).getLinphoneCall();
        LinphoneCallParams linphoneCallParams = null;
        if(callParameters != null)
            linphoneCallParams = ((AndroidCallParameters) callParameters).getLinphoneCallParams();
        linphoneCore.updateCall(linphoneCall, linphoneCallParams);
    }

    @Override
    public void declineCall(Call call, Reason reason) {
        LinphoneCall linphoneCall = ((AndroidCall) call).getLinphoneCall();

        org.linphone.core.Reason linReason = null;
        try{
            boolean found = false;
            int i = 0;
            while(!found){
                linReason = org.linphone.core.Reason.fromInt(i++);
                found = linReason.toString().toUpperCase().equals(reason.toString().toUpperCase());
            }
        } catch (Exception e){
            e.printStackTrace();
            linReason = org.linphone.core.Reason.fromInt(20);
        }

        linphoneCore.declineCall(linphoneCall, linReason);
    }

    @Override
    public void terminateCall(Call call) {
        LinphoneCall linphoneCall = ((AndroidCall) call).getLinphoneCall();
        linphoneCore.terminateCall(linphoneCall);
    }

    @Override
    public void acceptCallWithParameters(Call call, CallParameters callParameters){
        LinphoneCall linphoneCall = ((AndroidCall) call).getLinphoneCall();
        LinphoneCallParams linphoneCallParams = ((AndroidCallParameters) callParameters).getLinphoneCallParams();
        try{
            linphoneCore.acceptCallWithParams(linphoneCall, linphoneCallParams);
        } catch (LinphoneCoreException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean isCallEstablished(Call call) {
        LinphoneCall linphoneCall = ((AndroidCall) call).getLinphoneCall();
        return LinphoneUtils.isCallEstablished(linphoneCall);
    }

    @Override
    public void enableSpeaker(boolean enabled) {
        linphoneCore.enableSpeaker(enabled);
    }

    @Override
    public void muteMic(boolean muted) {
        linphoneCore.muteMic(muted);
    }

    @Override
    public void enableCamera(boolean enabled) {
        linphoneCore.getCurrentCall().enableCamera(enabled);
    }

    @Override
    public TextGroup getOrCreateTextGroup(String sipAddress) {
        return new AndroidTextGroup(linphoneCore.getOrCreateChatRoom(sipAddress));
    }

    @Override
    public void setDeviceRotation(int rotation){
        linphoneCore.setDeviceRotation(rotation);
    }

}