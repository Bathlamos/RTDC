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
import rtdc.core.model.User;

import java.util.List;


/**
 * Created by Mathieu on 2015-02-18.
 */
public class UserListAdapter extends ArrayAdapter {

    private Context context;
    private boolean useList = true;

    public UserListAdapter(Context context, List items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.context = context;
    }

    private class ViewHolder {
        TextView usernameText;
    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        User user = (User)getItem(position);
        View viewToUse = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            if (useList) {
                viewToUse = mInflater.inflate(R.layout.user_list_item, null);
            } else {
                viewToUse = mInflater.inflate(R.layout.user_grid_item, null);
            }
            holder = new ViewHolder();
            holder.usernameText = (TextView)viewToUse.findViewById(R.id.username_textView);
            viewToUse.setTag(holder);
        } else {
            viewToUse = convertView;
            holder = (ViewHolder) viewToUse.getTag();
        }

        holder.usernameText.setText(user.getUsername());
        return viewToUse;
    }
 }
