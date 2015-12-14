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
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;
import rtdc.android.R;
import rtdc.core.Bootstrapper;
import rtdc.core.controller.MessagesController;
import rtdc.core.model.User;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContactListAdapter extends ArrayAdapter<User> {

    private LayoutInflater inflater;
    private ArrayList<User> contacts;
    private ArrayList<User> contactsAll;
    private ArrayList<User> suggestions;

    public ContactListAdapter(Context context, ArrayList<User> contacts) {
        super(context, R.layout.adapter_contact, contacts);
        this.contacts = contacts;
        this.contactsAll = (ArrayList<User>) contacts.clone();
        this.suggestions = new ArrayList<User>();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if(view == null)
            view = inflater.inflate(R.layout.adapter_contact, parent, false);

        User currentUser = contacts.get(position);
        String initials = currentUser.getFirstName().substring(0, 1) + currentUser.getLastName().substring(0,1);

        setupColumn(view, R.id.userNameText, currentUser.getFirstName() + " " + currentUser.getLastName());
        setupColumn(view, R.id.roleUnitText, User.Role.getStringifier().toString(currentUser.getRole()));
        setupColumn(view, R.id.userIcon,     initials);

        GradientDrawable background = (GradientDrawable) view.findViewById(R.id.userIcon).getBackground();
        background.setColor(Color.HSVToColor(currentUser.getProfileColor()));

        ImageButton textMessageButton = (ImageButton) view.findViewById(R.id.textMessageButton);
        textMessageButton.setTag(position);
        textMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User sessionUser = (User) Cache.getInstance().get("sessionUser");
                Service.getMessages(sessionUser.getId(), contacts.get(Integer.parseInt(v.getTag().toString())).getId(), 0, MessagesController.FETCHING_SIZE);
            }
        });

        ImageButton audioCallButton = (ImageButton) view.findViewById(R.id.audioCallButton);
        audioCallButton.setTag(position);
        audioCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User clickedUser = contacts.get(Integer.parseInt(v.getTag().toString()));
                Logger.getLogger(ContactListAdapter.class.getName()).log(Level.INFO, "Calling " + clickedUser.getId());
                Bootstrapper.FACTORY.getVoipController().call(clickedUser, false);
            }
        });

        ImageButton videoCallButton = (ImageButton) view.findViewById(R.id.videoCallButton);
        videoCallButton.setTag(position);
        videoCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User clickedUser = contacts.get(Integer.parseInt(v.getTag().toString()));
                Logger.getLogger(ContactListAdapter.class.getName()).log(Level.INFO, "Calling with video " + clickedUser.getId());
                Bootstrapper.FACTORY.getVoipController().call(clickedUser, true);
            }
        });

        return view;
    }

    private void setupColumn(View view, int resourceId, String text){
        TextView textView = (TextView) view.findViewById(resourceId);
        textView.setText(text);
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((User)(resultValue)).getFirstName()+" "+((User)(resultValue)).getLastName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                suggestions.clear();
                for (User user : contactsAll) {
                    if((user.getFirstName()+" "+user.getLastName()).toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(user);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<User> filteredList = (ArrayList<User>) results.values;
            if(results != null && results.count > 0) {
                clear();
                for (User u : filteredList) {
                    add(u);
                }
                notifyDataSetChanged();
            }
        }
    };
}
