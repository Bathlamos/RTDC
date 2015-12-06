package rtdc.android.impl.voip;

import org.linphone.core.LinphoneCall;
import org.linphone.core.Reason;
import rtdc.core.impl.voip.Address;
import rtdc.core.impl.voip.Call;
import rtdc.core.impl.voip.CallParameters;
import rtdc.core.impl.voip.VoIPManager;

public class AndroidCall implements Call{

    private LinphoneCall linphoneCall;

    public AndroidCall(LinphoneCall linphoneCall){
        this.linphoneCall = linphoneCall;
    }

    public LinphoneCall getLinphoneCall(){
        return linphoneCall;
    }

    @Override
    public CallParameters getCurrentParamsCopy() {
        return new AndroidCallParameters(linphoneCall.getCurrentParamsCopy());
    }

    @Override
    public int getDuration() {
        return linphoneCall.getDuration();
    }

    @Override
    public State getState() {
        for (State state: State.values()) {
            if (state.toString().toUpperCase().equals(linphoneCall.getState().toString().toUpperCase())) {
                return state;
            }
        }
        return State.error;
    }

    @Override
    public Address getRemoteAddress() {
        return new AndroidAddress(linphoneCall.getRemoteAddress());
    }

    @Override
    public Address getFrom() {
        return new AndroidAddress(linphoneCall.getCallLog().getFrom());
    }

    @Override
    public Address getTo() {
        return new AndroidAddress(linphoneCall.getCallLog().getTo());
    }

    @Override
    public VoIPManager.Reason getReasonForError() {
        org.linphone.core.Reason linReason = linphoneCall.getErrorInfo().getReason();

        for (VoIPManager.Reason reason: VoIPManager.Reason.values()){
            if(reason.toString().toUpperCase().equals(linReason.toString().toUpperCase())){
                return reason;
            }
        }
        return VoIPManager.Reason.unknown;
    }

}
