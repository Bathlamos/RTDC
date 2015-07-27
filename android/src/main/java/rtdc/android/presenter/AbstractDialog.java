package rtdc.android.presenter;

import rtdc.core.view.Dialog;

public abstract class AbstractDialog extends AbstractActivity implements Dialog {

    @Override
    public void closeDialog() {
        finish();
    }
}
