package rtdc.android.impl;

import rtdc.core.impl.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class AndroidConfig implements Config {

    private static final String PATH = "rtdc/core/impl/Config.properties";
    private static final Properties prop = new Properties();

    static {
        try {
            File propertiesFile = new File(PATH);
            prop.load(new FileInputStream(propertiesFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getProperty(String property){
        return prop.getProperty(property);
    }

    private static Integer getPropertyAsInt(String property){
        try{
            return Integer.parseInt(prop.getProperty(property));
        } catch(NumberFormatException e) {
            return null;
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
