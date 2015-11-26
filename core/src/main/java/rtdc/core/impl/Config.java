package rtdc.core.impl;

public interface Config {

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
