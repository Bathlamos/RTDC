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
import rtdc.core.model.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RecentContactsListAdapter extends ArrayAdapter {

    private Context context;

    public RecentContactsListAdapter(Context context, ArrayList<Message> recentContacts) {
        super(context, R.layout.adapter_recent_contact, recentContacts);
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
            view = mInflater.inflate(R.layout.adapter_recent_contact, null);
        }

        boolean senderIsSessionUser = message.getSender().getId() == Session.getCurrentSession().getUser().getId();

        setupColumn(view, R.id.senderRecentContactsTextView, senderIsSessionUser ? message.getReceiver().getFirstName()+" "+message.getReceiver().getLastName(): message.getSender().getFirstName()+" "+message.getSender().getLastName());
        setupColumn(view, R.id.messagePreviewTextView, message.getContent());
        setupColumn(view, R.id.lastTimeSentTextView, new SimpleDateFormat("MMM dd, yyyy").format(message.getTimeSent()));

        if(!senderIsSessionUser && message.getStatus() != Message.Status.read){
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
