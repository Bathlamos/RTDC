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
