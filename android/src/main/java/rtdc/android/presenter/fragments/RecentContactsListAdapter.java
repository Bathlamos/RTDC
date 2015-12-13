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
import rtdc.core.model.Message;
import rtdc.core.model.User;
import rtdc.core.util.Cache;

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

        boolean senderIsSessionUser = message.getSender().getId() ==  ((User) Cache.getInstance().get("sessionUser")).getId();

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
