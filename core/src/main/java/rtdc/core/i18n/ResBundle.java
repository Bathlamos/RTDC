package rtdc.core.i18n;

/**
 * Public accesor for i18n package.
 * The language resources may typically be accessed using <code>ResBundle.get()</code>.
 */
public class ResBundle {

    // Java-based applications, namely the server and Android rely on Java's IO mechanism
    // This class is replaced at compile time, for GWT (see package super)
    private static final ResBundleInterface INST = new JavaIOResBundle();

    /**
     * Accessor method for the application messages
     * @return A ResBundleInterface, which contains getters to the language constants.
     */
    public static ResBundleInterface get() {
        return INST;
    }

}
