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

package rtdc.web.client.impl;

import rtdc.core.impl.VoipController;
import rtdc.core.model.Message;
import rtdc.core.model.User;

/**
 * Created by Jonathan on 2015-07-26.
 */
public class GwtVoipController implements VoipController{

    private static final GwtVoipController INST = new GwtVoipController();

    public static GwtVoipController get(){
        return INST;
    }

    @Override
    public void registerUser(User user, String password) {

    }

    @Override
    public void unregisterCurrentUser() {

    }

    @Override
    public void call(User user, boolean videoEnabled) {

    }

    @Override
    public void setMicMuted(boolean mute) {

    }

    public void setSpeaker(boolean enabled){

    }

    @Override
    public void setVideo(boolean enabled) {

    }

    @Override
    public boolean isVideoEnabled() {
        return false;
    }

    @Override
    public void setRemoteVideo(boolean enabled) {

    }

    @Override
    public boolean isMicMuted() {
        return false;
    }

    @Override
    public boolean isSpeakerEnabled() {
        return false;
    }

    @Override
    public boolean isReceivingRemoteVideo() {
        return false;
    }

    @Override
    public void acceptCall() {

    }

    @Override
    public void declineCall() {

    }

    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public void hangup() {

    }
}
