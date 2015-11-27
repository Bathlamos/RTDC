package rtdc.core.config;

public class Conf {

    private static final ConfigInterface INST = new JavaIOConfig();

    public static ConfigInterface get() {
        return INST;
    }

}
