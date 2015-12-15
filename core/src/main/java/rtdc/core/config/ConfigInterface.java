package rtdc.core.config;

/**
 * Defines accessors for the properties
 */
public interface ConfigInterface {

    /**
     * @return If the application runs in debug or in production mode.
     */
    boolean isDebug();

    /**
     * @return Asterisk's key
     */
    String commandExecKey();

    /**
     * @return The hostname or IP address of the Asterisk server. This can be localhost.
     */
    String asteriskHost();

    /**
     * @return The lifetime of a server session, in Android and GWT, in ms.
     */
    int sessionLifetime();

    /**
     * @return The protocol (e.g. http, https) of the server component.
     */
    String apiProtocol();

    /**
     * @return The hostname or IP address of the server component.
     */
    String apiHost();

    /**
     * @return The port which the server component binds. This cannot be omitted. Usually 80.
     */
    String apiPort();

    /**
     * @return The relative path of the API with regards to the hostname. (e.g. /rtdc/api/)
     */
    String apiPath();

}
