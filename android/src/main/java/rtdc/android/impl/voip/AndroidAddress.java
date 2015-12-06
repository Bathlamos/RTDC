package rtdc.android.impl.voip;

import org.linphone.core.LinphoneAddress;
import rtdc.core.impl.voip.Address;

public class AndroidAddress implements Address{

    private LinphoneAddress linphoneAddress;

    public AndroidAddress(LinphoneAddress linphoneAddress){
        this.linphoneAddress = linphoneAddress;
    }

    LinphoneAddress getLinphoneAddress(){
        return linphoneAddress;
    }

    @Override
    public String getDisplayName() {
        return linphoneAddress.getDisplayName();
    }

    @Override
    public void setDisplayName(String displayName) {
        linphoneAddress.setDisplayName(displayName);
    }

    @Override
    public String getUsername() {
        return linphoneAddress.getUserName();
    }

    @Override
    public void setUsername(String username) {
        linphoneAddress.setUserName(username);
    }

    @Override
    public String asStringURI() {
        return linphoneAddress.asStringUriOnly();
    }
}
