package rtdc.core.config;

public interface ConfigInterface {

    boolean isDebug();

    String gcmServerProjectId();

    String gcmServerApiKey();

    String commandExecKey();

    String asteriskHost();

    /**
     * In ms
     */
    int sessionLifetime();

    String apiHost();

    String apiPort();

    String apiPath();

}
