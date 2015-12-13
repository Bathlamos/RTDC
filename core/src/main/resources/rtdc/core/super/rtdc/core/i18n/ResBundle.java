package rtdc.core.i18n;

import com.google.gwt.core.client.GWT;

public class ResBundle {

    private static final ResBundleInterface INST = GWT.create(Bundle.class);

    public static ResBundleInterface get() {
        return INST;
    }

}
