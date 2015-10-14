package rtdc.android.presenter.fragments;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import rtdc.android.R;
import rtdc.core.Config;
import rtdc.core.Session;
import rtdc.core.model.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MessageListAdapter extends ArrayAdapter {

    private LayoutInflater inflater;
    private static final int DATE_ROW = 1;
    private static final int MESSAGE_ROW = 0;

    public MessageListAdapter(Context context, ArrayList<Message> messages) {
        super(context, R.layout.adapter_message, messages);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Message message = (Message)getItem(position);
        boolean isMessageRow = getItemViewType(position) == MESSAGE_ROW;

        if (view == null) {
            if(isMessageRow){
                view = inflater.inflate(R.layout.adapter_message, parent, false);
            } else {
                view = inflater.inflate(R.layout.adapter_date_separator, parent, false);
            }
        }

        boolean sessionUser = message.getSender().getId() == Session.getCurrentSession().getUser().getId();
        if(isMessageRow){
            setupColumn(view, R.id.senderNameTextView, sessionUser ? "Me" : message.getSender().getFirstName(), sessionUser);
            setupColumn(view, R.id.messageTextView, message.getContent(), sessionUser);
            setupColumn(view, R.id.timeSentTextView, new SimpleDateFormat("hh:mm a").format(message.getTimeSent()), sessionUser);
        } else {
            setupColumn(view, R.id.dateSeparatorTextView, new SimpleDateFormat("EEE MMM dd").format(message.getTimeSent()), false);
        }

        view.setTag(position);

        return view;
    }

    private void setupColumn(View view, int resourceId, String text, boolean noBackgroundColor){
        TextView textView = (TextView) view.findViewById(resourceId);
        textView.setText(text);
        view.setBackgroundColor(noBackgroundColor ? Color.TRANSPARENT : Color.parseColor("#FFFFFF"));
    }

    @Override
    public int getViewTypeCount(){
        return 2;
    }

    @Override
    public int getItemViewType(int position){
        Message message = (Message)getItem(position);
        return message.getContent().equals(Config.COMMAND_EXEC_KEY) ? 1 : 0;
    }
}
