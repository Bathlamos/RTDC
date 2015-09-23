package rtdc.android.presenter.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import rtdc.android.R;
import rtdc.core.model.Message;

import java.util.List;

public class RecentContactsListAdapter extends ArrayAdapter {

    private Context context;

    public RecentContactsListAdapter(Context context, List items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.context = context;
    }

    private static class ViewHolder {
        TextView receiver;
        TextView contentPreview;
        TextView lastTimeSent;
    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Message message = (Message)getItem(position);
        View viewToUse = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            viewToUse = mInflater.inflate(R.layout.recent_contacts_list_item, null);
            holder = new ViewHolder();
            holder.receiver = (TextView)viewToUse.findViewById(R.id.senderRecentContactsTextView);
            holder.contentPreview = (TextView) viewToUse.findViewById(R.id.messagePreviewTextView);
            holder.lastTimeSent = (TextView) viewToUse.findViewById(R.id.lastTimeSentTextView);
            viewToUse.setTag(holder);
        } else {
            viewToUse = convertView;
            holder = (ViewHolder) viewToUse.getTag();
        }

        holder.receiver.setText(message.getReceiver().getFirstName()+" "+message.getReceiver().getLastName());
        holder.contentPreview.setText(message.getContent());
        holder.lastTimeSent.setText(message.getTimeSent().toString());

        return viewToUse;
    }
}
