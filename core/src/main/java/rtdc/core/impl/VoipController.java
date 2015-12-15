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

package rtdc.core.impl;

import rtdc.core.model.Message;
import rtdc.core.model.User;

/**
 * An abstraction of a Voice over IP mechanism
 */
public interface VoipController {

    /**
     * Register the current device to the VoIP server
     * @param user The user information
     * @param password The user password
     */
    void registerUser(User user, String password);

    /**
     * Unregister the current device, when registered, to the VoIP server
     */
    void unregisterCurrentUser();

    /**
     * Place a call to another user
     * @param user Information for the target user
     * @param videoEnabled Streams video from the current device, when true.
     */
    void call(User user, boolean videoEnabled);

    /**
     * Confirm a call, on the receiving end.
     */
    void acceptCall();

    /**
     * Decline a call, on the receiving end
     */
    void declineCall();

    /**
     * Send a message to the VoIP server, to be relayed to recipients
     * @param message The message to be sent
     */
    void sendMessage(Message message);

    /**
     * Mute or unmute the microphone
     * @param mute Mute the microphone, when true
     */
    void setMicMuted(boolean mute);

    /**
     * Relay audio via the speakers, or via headphones
     * @param enabled Relay the audio via the speakers, when true
     */
    void setSpeaker(boolean enabled);

    /**
     * @param enabled Enable sending video, when true
     */
    void setVideo(boolean enabled);

    /**
     * @param enabled Enable receiving video, when true
     */
    void setRemoteVideo(boolean enabled);

    /**
     * @return True if the microphone is on mute
     */
    boolean isMicMuted();

    /**
     * @return Ture if the speakers are used to relay the audio
     */
    boolean isSpeakerEnabled();

    /**
     * @return True if video from the device is streamed.
     */
    boolean isVideoEnabled();

    /**
     * @return True if video from the recipient's device is streamed
     */
    boolean isReceivingRemoteVideo();

    /**
     * End a call
     */
    void hangup();
}
