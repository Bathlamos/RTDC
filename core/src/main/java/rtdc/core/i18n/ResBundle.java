package rtdc.core.i18n;

public class ResBundle {

    private static final ResBundleInterface INST = new JavaIOResBundle();

    public static ResBundleInterface get() {
        return INST;
    }

}
