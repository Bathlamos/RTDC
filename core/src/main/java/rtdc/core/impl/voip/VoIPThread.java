package rtdc.core.impl.voip;

import java.util.Collection;

public interface VoIPThread {

    // static VoIPThread getInstance();

    VoIPManager getVoIPManager();

    void addVoIPListener(VoIPListener listener);
    void removeVoIPListener(VoIPListener listener);
    Collection<VoIPListener> getVoIPListeners();

    Call getCall();
    void setCall(Call call);

    Video getVideo();
    void setVideo(Video video);

    Address getRemoteAddress();
    void setRemoteAddress(Address remoteAddress);
}
