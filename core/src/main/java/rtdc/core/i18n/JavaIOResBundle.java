package rtdc.core.i18n;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Uses a File Reader to access the .properties file
 * This is not a valid solution under GWT, which is why
 * the super path is use in the .gwt.xml file
 */
class JavaIOResBundle implements ResBundleInterface {

    private static final Logger LOGGER = Logger.getLogger(JavaIOResBundle.class.getName());

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("Bundle");

    static {
        LOGGER.info("Finished loading Bundle.properties");
    }


    @Override
    public String usernameMinLength() {
        return BUNDLE.getString("usernameMinLength");
    }

}
