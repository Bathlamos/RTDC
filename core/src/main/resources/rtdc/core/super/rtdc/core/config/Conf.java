package rtdc.core.config;

import com.google.gwt.core.client.GWT;

/**
 * GWT version of rtdc.core.config.Conf.
 * This class replaces its sibling at compile time. The main difference is that
 * the ConfigInterface is instantiated using GWT built-in automatic generation of
 * interfaces, so that the config is "built-in" the application.
 */
public class Conf {

    /**
     * GWT-specific instantiation of the ConfigInterface.
     * The body of the interface is generated automatically.
     */
    private static final ConfigInterface INST = GWT.create(Config.class);

    /**
     * Accessor method for the application configurations
     * @return A ConfigInterface, which contains getters to the configuration properties.
     */
    public static ConfigInterface get() {
        return INST;
    }

}
