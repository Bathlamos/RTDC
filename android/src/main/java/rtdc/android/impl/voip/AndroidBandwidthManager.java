package rtdc.android.impl.voip;

import org.linphone.core.LinphoneCallParams;
import org.linphone.core.LinphoneCore;
import rtdc.core.impl.voip.BandwidthManager;
import rtdc.core.impl.voip.CallParameters;
import rtdc.core.impl.voip.VoIPManager;

public class AndroidBandwidthManager implements BandwidthManager {

    private static final AndroidBandwidthManager INSTANCE = new AndroidBandwidthManager();

    private AndroidBandwidthManager(){
    }

    public static AndroidBandwidthManager getInstance() {
        return INSTANCE;
    }

    @Override
    public void updateWithProfileSettings(VoIPManager voIPManager, CallParameters callParameters) {
        LinphoneCore linphoneCore= ((AndroidVoIPManager) voIPManager).getLinphoneCore();
        LinphoneCallParams linphoneCallParams = ((AndroidCallParameters) callParameters).getLinphoneCallParams();

        org.linphone.BandwidthManager.getInstance().updateWithProfileSettings(linphoneCore, linphoneCallParams);
    }


}
