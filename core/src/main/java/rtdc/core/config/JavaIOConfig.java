package rtdc.core.config;

import rtdc.core.Bootstrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Uses a File Reader to access the .properties file
 * This is not a valid solution under GWT, which is why
 * the super path is use in the .gwt.xml file
 */
class JavaIOConfig implements ConfigInterface {

    private static final Logger LOGGER = Logger.getLogger(JavaIOConfig.class.getName());

    private static final String WEB_PATH = "WEB-INF" + File.separator +
            "classes" + File.separator +
            "rtdc" + File.separator +
            "core" + File.separator +
            "config" + File.separator +
            "Config.properties";
    private static final String PATH = "core" + File.separator +
            "src" + File.separator +
            "main" + File.separator +
            "resources" + File.separator +
            "rtdc" + File.separator +
            "core" + File.separator +
            "config" + File.separator +
            "Config.properties";
    private static final Properties prop = new Properties();

    static {
        try {
            File propertiesFile;
            if( Bootstrapper.getFactory().isServer())
                propertiesFile = new File(WEB_PATH);
            else
                propertiesFile = new File(PATH);
            prop.load(new FileInputStream(propertiesFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.info("Finished loading Config.properties");
    }

    private static String getProperty(String property){
        return prop.getProperty(property);
    }

    private static int getPropertyAsInt(String property){
        try{
            String propertyString = prop.getProperty(property);
            if(propertyString == null)
                return 0;
            return Integer.parseInt(propertyString);
        } catch(NumberFormatException e) {
            return 0;
        }
    }

    private static Boolean getPropertyAsBoolean(String property){
        return Boolean.parseBoolean(prop.getProperty(property));
    }

    @Override
    public boolean isDebug() {
        return getPropertyAsBoolean("isDebug");
    }

    @Override
    public String gcmServerProjectId() {
        return getProperty("gcmServerProjectId");
    }

    @Override
    public String gcmServerApiKey() {
        return getProperty("gcmServerApiKey");
    }

    @Override
    public String commandExecKey() {
        return getProperty("commandExecKey");
    }

    @Override
    public String asteriskHost() {
        return getProperty("asteriskHost");
    }

    @Override
    public int sessionLifetime() {
        return getPropertyAsInt("sessionLifetime");
    }

    @Override
    public String apiProtocol() {
        return getProperty("apiProtocol");
    }

    @Override
    public String apiHost() {
        return getProperty("apiHost");
    }

    @Override
    public String apiPort() {
        return getProperty("apiPort");
    }

    @Override
    public String apiPath() {
        return getProperty("apiPath");
    }
}
