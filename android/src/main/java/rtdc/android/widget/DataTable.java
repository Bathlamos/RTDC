package rtdc.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import rtdc.android.R;

import java.util.ArrayList;

public class DataTable extends LinearLayout {

    private ListView listView;
    private LinearLayout listViewHeader;

    public DataTable(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.widget_data_table, this);
        listView = (ListView) findViewById(R.id.list_view);
        listViewHeader = (LinearLayout) findViewById(R.id.list_view_header);
    }

    public <T> Builder<T> builder(ArrayAdapter<T> adapter){
        return new Builder<T>(this, adapter);
    }

    public static class Builder<T>{
        private DataTable dataTable;
        private ArrayList<Column> columns = new ArrayList<Column>();
        private ArrayAdapter<T> adapter;

        public Builder(DataTable dataTable, ArrayAdapter<T> adapter){
            this.dataTable = dataTable;
            this.adapter = adapter;
        }

        public Builder addColumn(int headerString) {
            addColumn(headerString, false, null);
            return this;
        }

        public Builder addColumn(int headerString, boolean sortable, SortHandler sortHandler) {
            Column column = new Column();
            column.header = headerString;
            column.sortable = sortable;
            column.sortHandler = sortHandler;
            columns.add(column);
            return this;
        }

        public void buid(){
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);

            for(Column column: columns){
                TextView headerText = (TextView) View.inflate(dataTable.getContext(), R.layout.widget_data_table_header_item, null);
                headerText.setText(column.header);
                dataTable.listViewHeader.addView(headerText);
                Space space = new Space(dataTable.getContext());

                space.setLayoutParams(params);
                dataTable.listViewHeader.addView(space);
            }
            dataTable.listView.setAdapter(adapter);
        }

        private static class Column {
            private int header;
            private boolean sortable;
            private SortHandler sortHandler;
        }
    }

    public interface SortHandler{
        void onSort(boolean ascending);
    }

}
