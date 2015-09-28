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
import rtdc.core.model.User;

import java.util.Date;
import java.util.List;

public class MessageListAdapter extends ArrayAdapter {

    private Context context;
    private User lastSender;
    private Date lastTimeSent;

    public MessageListAdapter(Context context, List items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.context = context;
    }

    private static class ViewHolder {
        TextView sender;
        TextView content;
        TextView timeSent;
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
        String sender, timeSent;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            viewToUse = mInflater.inflate(R.layout.message_list_item, null);
            holder = new ViewHolder();
            holder.sender = (TextView)viewToUse.findViewById(R.id.senderNameTextView);
            holder.content = (TextView) viewToUse.findViewById(R.id.messageTextView);
            holder.timeSent = (TextView) viewToUse.findViewById(R.id.timeSentTextView);
            viewToUse.setTag(holder);
        } else {
            viewToUse = convertView;
            holder = (ViewHolder) viewToUse.getTag();
        }

        if(lastSender == message.getSender()){
            sender =  timeSent = "";
        } else {
            sender = message.getSender().getFirstName()+" "+message.getSender().getLastName();
            timeSent = message.getTimeSent().toString();
        }

        if(message.getSender().getFirstName().equals("Me")) {
            viewToUse.setBackgroundColor(Color.TRANSPARENT);
        } else {
            viewToUse.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        holder.sender.setText(sender);
        holder.content.setText(message.getContent());
        holder.timeSent.setText(timeSent);

        lastSender = message.getSender();
        lastTimeSent = message.getTimeSent();

        return viewToUse;
    }
}
