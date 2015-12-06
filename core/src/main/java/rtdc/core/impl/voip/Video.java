package rtdc.core.impl.voip;

/**
 * Created by Nicolas Ménard on 2015-11-29.
 */
public interface Video {

    int getCameraId();

    void setCameraId(int cameraId);

    void setVideoWindow(Object windowId);

    void setPreviewWindow(Object windowId);

    void zoomVideo(float factor, float x, float y);

}
