package rtdc.core.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

/**
 * Uses a File Reader to access the .properties file
 * This is not a valid solution under GWT, which is why
 * the super path is use in the .gwt.xml file
 */
public class JavaIOConfig implements ConfigInterface {

    private static final Logger LOGGER = Logger.getLogger(JavaIOConfig.class.getName());

    private static final String PATH = "rtdc" + File.separator +
            "core" + File.separator +
            "config" + File.separator +
            "Config.properties";
    private static final Properties prop = new Properties();
    //private static final AtomicBoolean hasLoaded = new AtomicBoolean();

    /*public boolean hasLoaded(){
        return hasLoaded.get();
    }*/

    public static void setReader(Reader reader) {
        try {
            prop.load(new InputStreamReader(reader.getContent(PATH), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Notify all threads that made requests that we're ready
        /*synchronized (hasLoaded) {
            hasLoaded.set(true);
            hasLoaded.notifyAll();
        }*/

        LOGGER.info("Finished loading Config.properties");
    }

    private static String getProperty(String property){
        waitIfLoading();
        return prop.getProperty(property);
    }

    private static int getPropertyAsInt(String property){
        waitIfLoading();
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
        waitIfLoading();
        return Boolean.parseBoolean(prop.getProperty(property));
    }

    private static void waitIfLoading(){
        /*while(!hasLoaded.get()) {
            try {
                synchronized (hasLoaded) {
                    hasLoaded.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }

    @Override
    public boolean isDebug() {
        return getPropertyAsBoolean("isDebug");
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
