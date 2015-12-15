package rtdc.core.i18n;

import com.google.gwt.core.client.GWT;

/**
 * GWT version of rtdc.core.i18n.ResBundle.
 * This class replaces its sibling at compile time. The main difference is that
 * the ConfigInterface is instantiated using GWT built-in automatic generation of
 * interfaces, so that the config is "built-in" the application.
 */
public class ResBundle {

    /**
     * GWT-specific instantiation of the ResBundleInterface.
     * The body of the interface is generated automatically.
     */
    private static final ResBundleInterface INST = GWT.create(Bundle.class);

    /**
     * Accessor method for the application messages
     * @return A ResBundleInterface, which contains getters to the application language constants.
     */
    public static ResBundleInterface get() {
        return INST;
    }

}
