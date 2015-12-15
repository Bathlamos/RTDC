package rtdc.core.config;

/**
 * Public accesor for Config package.
 * The Config may typically be accessed using <code>Conf.get()</code>.
 */
public class Conf {

    // Java-based applications, namely the server and Android rely on Java's IO mechanism
    // This class is replaced at compile time, for GWT (see package super)
    private static final ConfigInterface INST = new JavaIOConfig();

    /**
     * Accessor method for the application configurations
     * @return A ConfigInterface, which contains getters to the configuration properties.
     */
    public static ConfigInterface get() {
        return INST;
    }

}
