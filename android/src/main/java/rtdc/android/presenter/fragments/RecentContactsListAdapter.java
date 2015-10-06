package rtdc.android.presenter.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import rtdc.android.R;
import rtdc.core.Session;
import rtdc.core.event.Event;
import rtdc.core.event.FetchMessagesEvent;
import rtdc.core.model.Message;
import rtdc.core.model.User;
import rtdc.core.service.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecentContactsListAdapter extends ArrayAdapter {

    private Context context;
    private MessageListFragment messageListFragment;

    public RecentContactsListAdapter(Context context, List items, MessageListFragment messageListFragment) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.context = context;
        this.messageListFragment = messageListFragment;
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
        final Message message = (Message)getItem(position);
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

        if(message.getStatus() != Message.Status.read){
            holder.receiver.setTypeface(Typeface.DEFAULT_BOLD);
            holder.contentPreview.setTypeface(Typeface.DEFAULT_BOLD);
            holder.contentPreview.setTextColor(getContext().getResources().getColor(R.color.RTDC_grey));
            holder.lastTimeSent.setTextColor(getContext().getResources().getColor(R.color.RTDC_light_blue));
        } else {
            viewToUse.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.receiver.setText(message.getReceiver().getFirstName()+" "+message.getReceiver().getLastName());
        holder.contentPreview.setText(message.getContent());
        holder.lastTimeSent.setText(message.getTimeSent().toString());

        viewToUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User otherUser = message.getSender().getId() == Session.getCurrentSession().getUser().getId() ? message.getReceiver() : message.getSender();
                Logger.getLogger(RecentContactsListAdapter.class.getName()).log(Level.INFO, "Getting conversation with " + otherUser.getFirstName() + " " + otherUser.getLastName());
                Event.subscribe(FetchMessagesEvent.TYPE, new FetchMessagesEvent.Handler() {
                    @Override
                    public void onMessagesFetched(FetchMessagesEvent event) {
                        Event.unsubscribe(FetchMessagesEvent.TYPE, this);
                        Logger.getLogger(RecentContactsListAdapter.class.getName()).log(Level.INFO, "Conversation was fetched");
                        for(Message message: event.getMessages())
                            Logger.getLogger(RecentContactsListAdapter.class.getName()).log(Level.INFO, message.toString());
                        messageListFragment.setMessages(event.getMessages().asList());
                    }
                });
                Service.getMessages(message.getSender().getId(), message.getReceiver().getId());
            }
        });

        return viewToUse;
    }
}
