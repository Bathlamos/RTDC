package rtdc.web.client;

import rtdc.core.Unit;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import it.netgrid.gwt.pouchdb.PouchDb;
import it.netgrid.gwt.pouchdb.PouchDbDoc;
import it.netgrid.gwt.pouchdb.handler.IDocUpdateHandler;
import it.netgrid.gwt.pouchdb.options.EmptyOptionsFactory;
import it.netgrid.gwt.pouchdb.options.IPouchdbOptionsFactory;
import it.netgrid.gwt.pouchdb.options.ReplicationOptions;
import it.netgrid.gwt.pouchdb.response.DocUpdateResponse;
import it.netgrid.gwt.pouchdb.response.ErrorResponse;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class web implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final Button button = new Button("Click me");
        final Label label = new Label();

        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (label.getText().equals("")) {
                    webService.App.getInstance().getMessage("Hello, World!", new MyAsyncCallback(label));
                } else {
                    label.setText("");
                }
            }
        });

        // Assume that the host HTML has elements defined whose
        // IDs are "slot1", "slot2".  In a real app, you probably would not want
        // to hard-code IDs.  Instead, you could, for example, search for all
        // elements with a particular CSS class and replace them with widgets.
        //
        RootPanel.get("slot1").add(button);
        RootPanel.get("slot2").add(label);

        IPouchdbOptionsFactory factory = new EmptyOptionsFactory();
        ReplicationOptions options = factory.buildReplicationOptions();
        options.setContinuous(true);

        PouchDb localDb = new PouchDb("local_db_name");
        //PouchDb.replicate("http://localhost:5984/remote_db_name", "local_db_name", options);


        Unit unit = new Unit("12E");
        PouchDbDoc doc = PouchDbAdapter.fromNoSQLObject(unit);
        localDb.put(doc, new IDocUpdateHandler() {

            @Override
            public void onSuccess(DocUpdateResponse response) {
                // Success handling
                GWT.log(response.getRev());
            }

            @Override
            public void onError(ErrorResponse error) {
                // Error handling
                GWT.log(error.getReason());
            }
        });
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
