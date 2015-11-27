package rtdc.core.config;

import com.google.gwt.core.client.GWT;

public class Conf {

    private static final ConfigInterface INST = GWT.create(Config.class);

    public static ConfigInterface get() {
        return INST;
    }

}
