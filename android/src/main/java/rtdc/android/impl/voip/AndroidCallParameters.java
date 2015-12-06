package rtdc.android.impl.voip;


import org.linphone.core.LinphoneCallParams;
import rtdc.core.impl.voip.CallParameters;

public class AndroidCallParameters implements CallParameters{

    LinphoneCallParams linphoneCallParams;

    public AndroidCallParameters(LinphoneCallParams linphoneCallParams){
        this.linphoneCallParams = linphoneCallParams;
    }

    public LinphoneCallParams getLinphoneCallParams(){
        return linphoneCallParams;
    }

    @Override
    public void setVideoEnabled(boolean enabled) {
        linphoneCallParams.setVideoEnabled(enabled);
    }

    @Override
    public boolean getVideoEnabled() {
        return linphoneCallParams.getVideoEnabled();
    }
}
