package rtdc.android.impl.voip;

import org.linphone.core.LinphoneCore;
import rtdc.core.impl.voip.Video;

public class AndroidVideo implements Video{

    private LinphoneCore linphoneCore;

    public AndroidVideo(LinphoneCore linphoneCore){
        this.linphoneCore = linphoneCore;
    }

    @Override
    public int getCameraId() {
        return linphoneCore.getVideoDevice();
    }

    @Override
    public void setCameraId(int cameraId) {
        linphoneCore.setVideoDevice(cameraId);
    }

    @Override
    public void setVideoWindow(Object windowId) {
        linphoneCore.setVideoWindow(windowId);
    }

    @Override
    public void setPreviewWindow(Object windowId) {
        linphoneCore.setPreviewWindow(windowId);
    }

    @Override
    public void zoomVideo(float factor, float x, float y) {
        linphoneCore.getCurrentCall().zoomVideo(factor,x,y);
    }
}
