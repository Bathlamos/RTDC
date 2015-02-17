package rtdc.web.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.DOM;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import rtdc.core.Bootstrapper;
import rtdc.web.client.impl.GwtFactory;
import rtdc.web.client.presenter.LoginPresenter;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class Rtdc implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        Bootstrapper.initialize(new GwtFactory());

        RootPanel.get().add(new LoginPresenter());

    }

    private static class MyAsyncCallback implements AsyncCallback<String> {
        private Label label;

        public MyAsyncCallback(Label label) {
            this.label = label;
        }

        public void onSuccess(String result) {
            label.getElement().setInnerHTML(result);
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!");
        }
    }
}
