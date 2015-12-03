package rtdc.core.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageBundle {

    public static ResourceBundle getBundle(Locale locale){
        return ResourceBundle.getBundle("rtdc.core.i18n.MessageBundle", locale);
    }
}
