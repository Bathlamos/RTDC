package rtdc.android.presenter.fragments;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import rtdc.android.R;
import rtdc.core.Session;
import rtdc.core.config.Conf;
import rtdc.core.model.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
        String commandKeyExec = Conf.get().commandExecKey();
        if(isMessageRow){
            String content = "";
            if(message.getContent().contains(commandKeyExec) && !message.getContent().equals(commandKeyExec)){
                String[] contents = message.getContent().split(commandKeyExec);
                for(String part: contents){
                    if(part.startsWith("Missed call")){
                        if(sessionUser)
                            part = part.replace("Missed call", "Call unanswered");
                        else
                            part = part.replace("Missed call", "Missed call from " + message.getSender().getFirstName());
                    }else if(part.startsWith("Call rejected")){
                        if(sessionUser)
                            part = part.replace("Call rejected", "Your call was rejected; user is busy or unavailable");
                        else
                            part = part.replace("Call rejected", "Call rejected: busy");
                    }
                    content += part;
                }
            }else{
                content = message.getContent();
            }
            setupColumn(view, R.id.senderNameTextView, sessionUser ? "Me" : message.getSender().getFirstName(), sessionUser);
            setupColumn(view, R.id.messageTextView, content, sessionUser);
            setupColumn(view, R.id.timeSentTextView, new SimpleDateFormat("hh:mm a").format(message.getTimeSent()), sessionUser);
        } else {
            setupColumn(view, R.id.dateSeparatorTextView, new SimpleDateFormat("EEE MMM dd, yyyy").format(message.getTimeSent()), true);
        }

        view.setTag(position);

        return view;
    }

    private void setupColumn(View view, int resourceId, String text, boolean noBackgroundColor){
        TextView textView = (TextView) view.findViewById(resourceId);
        textView.setText(text);
        if(resourceId == R.id.senderNameTextView && !noBackgroundColor)
            textView.setTextColor(getContext().getResources().getColor(R.color.RTDC_dark_blue));
    }

    @Override
    public int getViewTypeCount(){
        return 2;
    }

    @Override
    public int getItemViewType(int position){
        Message message = (Message)getItem(position);
        return message.getContent().equals(Conf.get().commandExecKey()) ? 1 : 0;
    }
}
