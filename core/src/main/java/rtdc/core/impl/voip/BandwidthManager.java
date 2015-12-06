package rtdc.core.impl.voip;

public interface BandwidthManager {

    void updateWithProfileSettings(VoIPManager voIPManager, CallParameters callParameters);

}