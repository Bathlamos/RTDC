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

    public RecentContactsListAdapter(Context context, List items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.context = context;
    }

    /**
     *
     * @param position
     * @param view
     * @param parent
     * @return */
    public View getView(int position, View view, ViewGroup parent) {
        final Message message = (Message)getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = mInflater.inflate(R.layout.recent_contacts_list_item, null);
        }

        boolean sessionUser = message.getSender().getId() == Session.getCurrentSession().getUser().getId();
        setupColumn(view, R.id.senderRecentContactsTextView, sessionUser ? "Me": message.getSender().getFirstName()+" "+message.getSender().getLastName());
        setupColumn(view, R.id.messagePreviewTextView, message.getContent());
        setupColumn(view, R.id.lastTimeSentTextView, message.getTimeSent().toString());

        if(!sessionUser && message.getStatus() != Message.Status.read){
            ((TextView)view.findViewById(R.id.senderRecentContactsTextView)).setTypeface(Typeface.DEFAULT_BOLD);
            ((TextView)view.findViewById(R.id.messagePreviewTextView)).setTypeface(Typeface.DEFAULT_BOLD);
            ((TextView)view.findViewById(R.id.messagePreviewTextView)).setTextColor(getContext().getResources().getColor(R.color.RTDC_grey));
            ((TextView)view.findViewById(R.id.lastTimeSentTextView)).setTextColor(getContext().getResources().getColor(R.color.RTDC_light_blue));
        } else {
            ((TextView)view.findViewById(R.id.senderRecentContactsTextView)).setTypeface(Typeface.DEFAULT);
            ((TextView)view.findViewById(R.id.messagePreviewTextView)).setTypeface(Typeface.DEFAULT);
            ((TextView)view.findViewById(R.id.messagePreviewTextView)).setTextColor(getContext().getResources().getColor(R.color.RTDC_grey));
            ((TextView)view.findViewById(R.id.lastTimeSentTextView)).setTextColor(getContext().getResources().getColor(R.color.RTDC_grey));
            view.setBackgroundColor(Color.TRANSPARENT);
        }

        view.setTag(position);

        return view;
    }

    private void setupColumn(View view, int resourceId, String text){
        TextView textView = (TextView) view.findViewById(resourceId);
        textView.setText(text);
    }
}
