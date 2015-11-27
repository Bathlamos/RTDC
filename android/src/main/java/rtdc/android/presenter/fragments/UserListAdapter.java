package rtdc.android.presenter.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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

public class UserListAdapter extends ArrayAdapter {

    private Context context;

    public UserListAdapter(Context context, List items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.context = context;
    }

    private class ViewHolder {
        TextView usernameText;
        TextView secondLine;
        TextView userIcon;
    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        User user = (User) getItem(position);
        View viewToUse = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            viewToUse = mInflater.inflate(R.layout.user_list_item, null);
            holder = new ViewHolder();
            holder.usernameText = (TextView)viewToUse.findViewById(R.id.username_textView);
            holder.secondLine = (TextView) viewToUse.findViewById(R.id.user_secondLine);
            holder.userIcon = (TextView) viewToUse.findViewById(R.id.userIcon);
            viewToUse.setTag(holder);
        } else {
            viewToUse = convertView;
            holder = (ViewHolder) viewToUse.getTag();
        }


        holder.usernameText.setText(user.getLastName() + ", " + user.getFirstName());
        holder.secondLine.setText(user.getUsername() + ", " + User.Role.getStringifier().toString(user.getRole()));
        String initials = user.getFirstName().substring(0, 1) + user.getLastName().substring(0,1);
        holder.userIcon.setText(initials);

        GradientDrawable background = (GradientDrawable) holder.userIcon.getBackground();
        background.setColor(Color.HSVToColor(user.getProfileColor()));
        background.setCornerRadius(32);

        return viewToUse;
    }
 }
