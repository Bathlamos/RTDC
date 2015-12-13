/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Olivier Clermont, Jonathan Ermel, Mathieu Fortin-Boulay, Philippe Legault & Nicolas Ménard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
            String content = "";
            if(message.getContent().contains(Config.COMMAND_EXEC_KEY) && !message.getContent().equals(Config.COMMAND_EXEC_KEY)){
                String[] contents = message.getContent().split(Config.COMMAND_EXEC_KEY);
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
        return message.getContent().equals(Config.COMMAND_EXEC_KEY) ? 1 : 0;
    }
}
