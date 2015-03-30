package rtdc.android.presenter.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.app.Activity;
import org.w3c.dom.Text;
import rtdc.android.R;
import rtdc.core.model.Unit;
import rtdc.core.model.User;

import java.util.List;

public class UnitListAdapter extends ArrayAdapter {

    private Context context;

    public UnitListAdapter(Context context, List items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.context = context;
    }

    private static class ViewHolder {
        TextView nameText;
        TextView secondLine;
    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Unit unit = (Unit)getItem(position);
        View viewToUse = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            viewToUse = mInflater.inflate(R.layout.unit_list_item, null);
            holder = new ViewHolder();
            holder.nameText = (TextView)viewToUse.findViewById(R.id.unit_textView);
            holder.secondLine = (TextView) viewToUse.findViewById(R.id.unit_secondLine);
            viewToUse.setTag(holder);
        } else {
            viewToUse = convertView;
            holder = (ViewHolder) viewToUse.getTag();
        }

        holder.nameText.setText(unit.getName());
        holder.secondLine.setText("Total number of beds: " + unit.getTotalBeds());
        return viewToUse;
    }
}
