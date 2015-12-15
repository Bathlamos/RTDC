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

package rtdc.android.impl;

import android.content.Intent;
import rtdc.android.AndroidBootstrapper;
import rtdc.android.impl.voip.AndroidBandwidthManager;
import rtdc.android.impl.voip.AndroidVoIPThread;
import rtdc.android.presenter.InCallActivity;
import rtdc.core.config.Conf;
import rtdc.core.impl.VoipController;
import rtdc.core.impl.voip.*;
import rtdc.core.model.Message;
import rtdc.core.model.User;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Android implementation of VoipController
 */
public class AndroidVoipController implements VoipController{

    private static final AndroidVoipController INST = new AndroidVoipController();

    private boolean speakerEnabled;
    private boolean micMuted;
    private boolean videoEnabled;
    private boolean remoteVideo;

    private AndroidVoipController(){}

    public static AndroidVoipController get(){ return INST; }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerUser(User user, String password) {
        AndroidVoIPThread.getInstance().getVoIPManager().registerUser(user, password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unregisterCurrentUser() {
        AndroidVoIPThread.getInstance().getVoIPManager().unregisterUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void call(User user, boolean videoEnabled) {
        try {
            String sipAddress = "sip:" + user.getId() + "@" + Conf.get().asteriskHost();
            Address address = AndroidVoIPThread.getInstance().getVoIPManager().buildAddress(sipAddress);
            address.setDisplayName(user.getFirstName() + " " + user.getLastName());

            CallParameters params = AndroidVoIPThread.getInstance().getVoIPManager().createDefaultCallParameters();
            params.setVideoEnabled(true);

            Call call = AndroidVoIPThread.getInstance().getVoIPManager().initiateCall(address, params);

            // Need to set the username of the address after the call or else Asterisk will try to initiate the call using the
            // username instead of the user extension, which fails the call
            address.setUsername(user.getUsername());
            AndroidVoIPThread.getInstance().setRemoteAddress(address);

            // Reset call options
            setRemoteVideo(false);
            setSpeaker(false);
            setMicMuted(false);
            setVideo(videoEnabled);

            if (call == null)
                Logger.getLogger(AndroidVoipController.class.getName()).log(Level.WARNING, "Could not place call to " + sipAddress + ", aborting...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMicMuted(boolean mute) {
        micMuted = mute;
        AndroidVoIPThread.getInstance().getVoIPManager().muteMic(mute);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSpeaker(boolean enabled) {
        speakerEnabled = enabled;
        AndroidVoIPThread.getInstance().getVoIPManager().enableSpeaker(enabled);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVideo(boolean enabled) {
        VoIPManager vm = AndroidVoIPThread.getInstance().getVoIPManager();
        Call call = AndroidVoIPThread.getInstance().getCall();

        if (call == null)
            return;

        videoEnabled = enabled;
        vm.enableCamera(enabled);

        if(enabled){
            Logger.getLogger(AndroidVoipController.class.getName()).log(Level.INFO, "Enabling video");
            CallParameters callParameters = call.getCurrentParamsCopy();

            // Check if video possible regarding bandwidth limitations
            AndroidBandwidthManager.getInstance().updateWithProfileSettings(AndroidVoIPThread.getInstance().getVoIPManager(), callParameters);

            if (!callParameters.getVideoEnabled()){
                Logger.getLogger(AndroidVoipController.class.getName()).log(Level.INFO, "Video cannot be enabled: not enough bandwidth");
                return;
            }

            String sipAddress = AndroidVoIPThread.getInstance().getRemoteAddress().asStringURI();
            TextMessage tm = vm.getOrCreateTextGroup(sipAddress).createTextMessage(Conf.get().commandExecKey() + "Video: true");
            vm.getOrCreateTextGroup(sipAddress).sendTextMessage(tm);
        }else{
            Logger.getLogger(AndroidVoipController.class.getName()).log(Level.INFO, "Disabling video");

            String sipAddress = AndroidVoIPThread.getInstance().getRemoteAddress().asStringURI();
            TextMessage tm = vm.getOrCreateTextGroup(sipAddress).createTextMessage(Conf.get().commandExecKey() + "Video: false");
            vm.getOrCreateTextGroup(sipAddress).sendTextMessage(tm);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMicMuted() { return micMuted; }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSpeakerEnabled() { return speakerEnabled; }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVideoEnabled(){ return videoEnabled; }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRemoteVideo(boolean enabled) { remoteVideo = enabled; }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isReceivingRemoteVideo() { return remoteVideo; }

    /**
     * {@inheritDoc}
     */
    @Override
    public void acceptCall() {
        try {
            CallParameters params = AndroidVoIPThread.getInstance().getVoIPManager().createDefaultCallParameters();

            // Reset call options
            setSpeaker(false);
            setMicMuted(false);

            params.setVideoEnabled(true);

            AndroidVoIPThread.getInstance().getVoIPManager().acceptCallWithParameters(AndroidVoIPThread.getInstance().getCall(), params);

            setVideo(remoteVideo);

            Intent intent = new Intent(AndroidBootstrapper.getAppContext(), InCallActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            AndroidBootstrapper.getAppContext().startActivity(intent);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void declineCall() {
        AndroidVoIPThread.getInstance().getVoIPManager().declineCall(AndroidVoIPThread.getInstance().getCall(), VoIPManager.Reason.declined);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(Message message) {
        String sipAddress = "sip:" + message.getReceiver().getUsername() + "@" + Conf.get().asteriskHost();
        TextGroup tg = AndroidVoIPThread.getInstance().getVoIPManager().getOrCreateTextGroup(sipAddress);
        TextMessage tm = tg.createTextMessage(message.toString());
        AndroidVoIPThread.getInstance().getVoIPManager().getOrCreateTextGroup(sipAddress).sendTextMessage(tm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hangup() {
        AndroidVoIPThread.getInstance().getVoIPManager().terminateCall(AndroidVoIPThread.getInstance().getCall());
    }
}
