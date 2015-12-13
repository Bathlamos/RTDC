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

package rtdc.core.impl.voip;

import rtdc.core.model.User;

public interface VoIPManager {

    CallParameters createDefaultCallParameters();

    void iterate();

    void destroy();

    void setNetworkReachable(boolean reachable);
    boolean isNetworkReachable();

    void registerUser(User user, String asteriskPassword);
    void unregisterUser();

    Address buildAddress(String sipAddress);

    Call initiateCall(Address destination, CallParameters callParameters);
    void updateCall(Call call, CallParameters callParameters);
    void acceptCall(Call call);
    void declineCall(Call call, Reason reason);
    void terminateCall(Call call);

    void acceptCallWithParameters(Call call, CallParameters callParameters);
    boolean isCallEstablished(Call call);

    void enableSpeaker(boolean enabled);
    void muteMic(boolean muted);
    void enableCamera(boolean enabled);

    TextGroup getOrCreateTextGroup(String sipAddress);

    enum Reason{
        none,
        noResponse,
        badCredentials,
        declined,
        notFound,
        notAnswered,
        busy,
        media,
        ioError,
        doNotDisturb,
        unauthorized,
        notAcceptable,
        noMatch,
        movedPermanently,
        gone,
        temporarilyUnavailable,
        addressIncomplete,
        notImplemented,
        badGateway,
        serverTimeout,
        unknown
    }
}
