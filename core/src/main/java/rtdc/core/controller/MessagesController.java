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

package rtdc.core.controller;

import rtdc.core.event.*;
import rtdc.core.i18n.ResBundle;
import rtdc.core.model.Message;
import rtdc.core.model.SimpleComparator;
import rtdc.core.model.User;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;
import rtdc.core.view.CommunicationHubView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class MessagesController extends Controller<CommunicationHubView> implements FetchRecentContactsEvent.Handler, FetchMessagesEvent.Handler,  MessageSavedEvent.Handler, FetchUsersEvent.Handler {

    private ArrayList<Message> recentContacts;
    private ArrayList<Message> messages;
    private ArrayList<User> contacts;

    public static final int FETCHING_SIZE = 25; // The number of messages to request from the server at once

    public MessagesController(CommunicationHubView view){
        super(view);
        Event.subscribe(FetchRecentContactsEvent.TYPE, this);
        Event.subscribe(FetchMessagesEvent.TYPE, this);
        Event.subscribe(MessageSavedEvent.TYPE, this);
        Event.subscribe(FetchUsersEvent.TYPE, this);

        Service.getRecentContacts(((User) Cache.getInstance().get("sessionUser")).getId());
        Service.getUsers();
    }

    @Override
    String getTitle() {
        return ResBundle.get().messagesTitle();
    }

    public void sortRecentContacts(Message.Properties property){
        if(recentContacts.isEmpty()){
            view.setRecentContacts(recentContacts);
            return;
        }

        Collections.sort(recentContacts, SimpleComparator.forProperty(property).build());
        view.setRecentContacts(recentContacts);

        // We load the messages for the most recent contact
        Service.getMessages(recentContacts.get(0).getSender().getId(), recentContacts.get(0).getReceiver().getId(), 0, FETCHING_SIZE);
    }

    public void sortContacts(User.Properties property){
        Collections.sort(contacts, SimpleComparator.forProperty(property).build());
        view.setContacts(contacts);
    }

    @Override
    public void onMessagesFetched(FetchMessagesEvent event) {
        User messagingUser = event.getUser1().getId() != ((User)Cache.getInstance().get("sessionUser")).getId() ? event.getUser1() : event.getUser2();
        if(event.getMessages().isEmpty() && view.getMessagingUser() != null && messagingUser.getId() == view.getMessagingUser().getId())
            return;
        if(view.getMessagingUser() == null || messagingUser.getId() != view.getMessagingUser().getId()) {
            messages = new ArrayList<>(event.getMessages());
            view.setMessages(messages, messagingUser);
        }else{
            messages.addAll(0, event.getMessages());
            ArrayList<Message> temp = new ArrayList<>();
            temp.addAll(event.getMessages().asList());
            view.addMessagesAtStart(temp);
        }
    }

    @Override
    public void onRecentContactsFetched(FetchRecentContactsEvent event) {
        recentContacts = new ArrayList<>(event.getRecentContacts());
        sortRecentContacts(Message.Properties.timeSent);
    }

    @Override
    public void onUsersFetched(FetchUsersEvent event) {
        contacts = new ArrayList<>(event.getUsers());
        sortContacts(User.Properties.lastName);
    }

    @Override
    public void onMessageSaved(MessageSavedEvent event) {
        view.addMessage(event.getMessage());
    }

    public ArrayList<Message> getMessages(){
        return messages;
    }

    @Override
    public void onStop() {
        super.onStop();
        Event.unsubscribe(FetchRecentContactsEvent.TYPE, this);
        Event.unsubscribe(FetchMessagesEvent.TYPE, this);
        Event.unsubscribe(MessageSavedEvent.TYPE, this);
        Event.unsubscribe(FetchUsersEvent.TYPE, this);
    }
}
