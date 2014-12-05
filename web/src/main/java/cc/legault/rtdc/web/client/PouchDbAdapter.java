package cc.legault.rtdc.web.client;

import cc.legault.rtdc.core.NoSQLObject;
import com.google.gwt.core.client.JavaScriptObject;
import it.netgrid.gwt.pouchdb.PouchDbDoc;

import java.util.Map;
import java.util.Objects;

public class PouchDbAdapter {

    public static <T extends NoSQLObject> T fromPouDbDoc(PouchDbDoc doc){
        return null;
    }

    public static <T extends NoSQLObject> PouchDbDoc fromNoSQLObject(T object){
        PouchDbDoc doc = JavaScriptObject.createObject().cast();
        for(Map.Entry<String, Object> entry: object.getProperties().entrySet())
            addProperty(doc, entry.getKey(), entry.getValue());
        if(doc.getId() == null)
            doc.setId(UUID.uuid());
        return doc;
    }

    static native final void addProperty(JavaScriptObject object, String key, Object value)/*-{
        object[key] = value;
    }-*/;

}
