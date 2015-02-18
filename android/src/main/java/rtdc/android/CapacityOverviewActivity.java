package rtdc.android;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.*;
import android.widget.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;


public class CapacityOverviewActivity extends Activity {

    private String[] headerTitles = new String[]{               // Used for the table header
            "Unit",
            "Available\nBeds",
            "Potential\nDC's",
            "DC's\nby 2pm",
            "Total\nAdmits",
            "Admits\nby 2pm",
            "Status\nat 2pm"
    };
    private boolean editing;
    private boolean offsetColor;                                // Used to alternate row colors in the table
    private int chosenSortedColumn = 0;
    private boolean reverseSort = false;
    private final String tableBorderColor = "#CCCCCC";
    private final String tableHeaderColor = "#B9B9B9";
    private final String tableRowColor = "#EEEEEE";
    private final String tableRowAlternateColor = "#FFFFFF";
    private final String textColor = "#000000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capacity_overview);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Set the custom action bar

        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.actionbar_capacity_overview);

        // Register the listener for the edit button

        findViewById(R.id.editButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                // Loop through all rows in the table and change all TextViews to EditTexts

                TableLayout table = (TableLayout) findViewById(R.id.table);
                for(int i = 0; i < table.getChildCount(); i++){
                    TableRow row = (TableRow) table.getChildAt(i);
                    for(int j = 0; j < row.getChildCount(); j++){
                        TextView originalTextView = (TextView) row.getChildAt(j);
                        TextView newTextView;

                        if(editing)
                            newTextView = new TextView(CapacityOverviewActivity.this);
                        else
                            newTextView = new EditText(CapacityOverviewActivity.this);

                        newTextView.setBackgroundColor(((ColorDrawable) originalTextView.getBackground()).getColor());
                        newTextView.setGravity(Gravity.CENTER);
                        newTextView.setText(originalTextView.getText());
                        newTextView.setTextSize(12);
                        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
                        newTextView.setHeight(px);
                        newTextView.setTextColor(Color.parseColor(textColor));
                        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                        params.setMargins(1, 1, 1, 1);
                        row.removeViewAt(j);
                        row.addView(newTextView, j, params);
                    }
                }
                editing = !editing;
            }
        });

        // Build the table

        buildTable();
    }

    protected void buildTable(){
        TableLayout staticHeader = (TableLayout) findViewById(R.id.staticHeader);
        TableLayout table = (TableLayout) findViewById(R.id.table);

        // Adjust the table according to the screen's width

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);  // Get the screen dimensions
        if(point.x > table.getWidth()) {                        // If the screen width is bigger then the current table width
            table.setStretchAllColumns(true);                   // Stretch the table to fit the screen
            staticHeader.setStretchAllColumns(true);            // Stretch the static header to fit the screen
        }

        // Build the headers for the table

        TableRow headerRow = new TableRow(this);
        TableRow staticHeaderRow = new TableRow(this);
        headerRow.setBackgroundColor(Color.parseColor(tableBorderColor));          // Set the color of the border
        staticHeaderRow.setBackgroundColor(Color.parseColor(tableBorderColor));    // Set the color of the border
        for(int i = 0; i < headerTitles.length; i++){
            final int column = i;

            // Create a text view for each header for the table

            TextView tableHeaderText = new TextView(this);
            tableHeaderText.setText(headerTitles[i]);
            tableHeaderText.setGravity(Gravity.CENTER);
            tableHeaderText.setTextSize(20);
            tableHeaderText.setBackgroundColor(Color.parseColor(tableHeaderColor));
            tableHeaderText.setPadding(10, 10, 10, 10);
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT);
            params.setMargins(1, 1, 1, 1);                                  // Add a margin to add a border to the table
            headerRow.addView(tableHeaderText, params);

            // Create a text view for each header for the static header

            TextView staticHeaderText = new TextView(this);
            staticHeaderText.setText(headerTitles[i]);
            staticHeaderText.setGravity(Gravity.CENTER);
            staticHeaderText.setTextSize(20);
            staticHeaderText.setBackgroundColor(Color.parseColor(tableHeaderColor));
            staticHeaderText.setPadding(10, 10, 10, 10);
            TableRow.LayoutParams params2 = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT);
            params2.setMargins(1, 1, 1, 1);                                 // Add a margin to add a border to the table
            staticHeaderRow.addView(staticHeaderText, params2);
            staticHeaderText.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    sortRows(column);
                }
            });
        }

        // Add the headers to the tables

        table.addView(headerRow);
        staticHeader.addView(staticHeaderRow);

        // For each unit we add a row displaying all its information

        addRow("12E", 1, 12, 3, 10, 6, -2);
        addRow("11E", 10, 8, 4, 10, 6, 10);
        addRow("10E", 0, 14, 7, 12, 6, 1);
        addRow("9E", 3, 5, 3, 2, 9, -2);
        addRow("8E", 13, 6, 3, 1, 12, 6);
        addRow("7F", 4, 3, 3, 10, 5, 4);
        addRow("NCCU", 0, 0, 0, 0, 0, 0);
        addRow("5A", 0, 12, 3, 2, 2, -1);
    }

    private void addRow(String unit, int availableBeds, int potentialDCs, int dcsBy2, int totalAdmits, int admitsBy2, int statusAt2) {
        TableLayout table = (TableLayout) findViewById(R.id.table);
        final TableRow row = new TableRow(this);
        row.setBackgroundColor(Color.parseColor(tableBorderColor));
        row.setBaselineAligned(false);

        // Create and add all views to the row

        for(int i = 0; i < headerTitles.length; i++){
            TextView b = new TextView(this);
            if(offsetColor)
                b.setBackgroundColor(Color.parseColor(tableRowColor));
            else
                b.setBackgroundColor(Color.parseColor(tableRowAlternateColor));
            b.setGravity(Gravity.CENTER);
            b.setTextSize(12);
            b.setEnabled(false);
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
            b.setHeight(px);
            b.setTextColor(Color.parseColor(textColor));
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            params.setMargins(1, 1, 1, 1);
            row.addView(b, params);
        }
        table.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        offsetColor = !offsetColor;

        // Set the text of all the newly added views

        ((TextView)row.getChildAt(0)).setText(unit);
        ((TextView)row.getChildAt(1)).setText(String.valueOf(availableBeds));
        ((TextView)row.getChildAt(2)).setText(String.valueOf(potentialDCs));
        ((TextView)row.getChildAt(3)).setText(String.valueOf(dcsBy2));
        ((TextView)row.getChildAt(4)).setText(String.valueOf(totalAdmits));
        ((TextView)row.getChildAt(5)).setText(String.valueOf(admitsBy2));
        ((TextView)row.getChildAt(6)).setText(String.valueOf(statusAt2));
    }

    public void sortRows(int sortingColumn){
        offsetColor = false;
        TableLayout table = (TableLayout) findViewById(R.id.table);
        HashMap<String, TableRow> rows = new HashMap<>();
        Object[] objectsToSort = new Object[table.getChildCount()-1];

        // Loop through all rows and get all the values that are in the wanted column

        for(int i = 1; i < table.getChildCount(); i++){
            TableRow row = (TableRow) table.getChildAt(i);

            // If we're sorting the first column, we want to sort strings (unit names)
            // All other columns, we want to sort integers

            if(sortingColumn == 0)
                objectsToSort[i-1] = ((TextView)row.getChildAt(sortingColumn)).getText();
            else
                objectsToSort[i-1] = Integer.parseInt((String) ((TextView) row.getChildAt(sortingColumn)).getText());

            // We want to keep the row in a hash map for easier access later
            // Key is "<current row number in the table> <value from the sorting column>"

            rows.put(i+" "+String.valueOf(objectsToSort[i-1]), row);
        }

        // If the column we're sorting was already the one that the table was sorted with, we change the sorting order

        if(chosenSortedColumn == sortingColumn)
            reverseSort = !reverseSort;
        else
            reverseSort = false;

        // Sort the array of values

        if(!reverseSort)
            Arrays.sort(objectsToSort);
        else
            Arrays.sort(objectsToSort, Collections.reverseOrder());

        for(int i = 0; i < objectsToSort.length; i++){
            TableRow row = null;

            // Find the row with the wanted value in the column

            for(int j = 1; j <= objectsToSort.length; j++) {
                row = rows.get(j + " " + objectsToSort[i]);
                if(row != null) {
                    rows.remove(j + " " + objectsToSort[i]);
                    break;
                }
            }

            // If somehow the row was not found, we just skip this value (mostly a prevention, it should always find the row)

            if(row == null)
                continue;

            // Readd the row in the table at the end of the table

            table.removeView(row);
            table.addView(row);

            // Reset the background color of the row

            for(int j = 0; j < row.getChildCount(); j++){
                TextView text = (TextView) row.getChildAt(j);
                if(offsetColor)
                    text.setBackgroundColor(Color.parseColor(tableRowColor));
                else
                    text.setBackgroundColor(Color.parseColor(tableRowAlternateColor));
            }
            offsetColor = !offsetColor;
        }
        chosenSortedColumn = sortingColumn;
    }
}
