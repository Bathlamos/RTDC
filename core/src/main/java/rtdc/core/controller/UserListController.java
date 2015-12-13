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

import rtdc.core.Bootstrapper;
import rtdc.core.event.Event;
import rtdc.core.event.FetchUsersEvent;
import rtdc.core.model.SimpleComparator;
import rtdc.core.model.User;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;
import rtdc.core.util.Pair;
import rtdc.core.view.UserListView;

import java.util.*;

public class UserListController extends Controller<UserListView> implements FetchUsersEvent.Handler {

    private ArrayList<User> users;

    public UserListController(UserListView view){
        super(view);
        Event.subscribe(FetchUsersEvent.TYPE, this);
        Service.getUsers();
    }

    @Override
    String getTitle() {
        return "Users";
    }

    public void sortUsers(User.Properties property, boolean ascending){
        ArrayList<User> sortedUsers = new ArrayList<>(users);
        Collections.sort(sortedUsers, SimpleComparator.forProperty(property).setAscending(ascending).build());
        view.setUsers(sortedUsers);
    }

    public void deleteUser(User user){
        users.remove(user);
        Service.deleteUser(user.getId());
    }

    public void editUser(User user){
        Cache.getInstance().put("user", user);
        Bootstrapper.getFactory().newDispatcher().goToEditUser(this);
    }

    @Override
    public void onUsersFetched(FetchUsersEvent event) {
        users = new ArrayList<>(event.getUsers());
        sortUsers(User.Properties.lastName, true);
    }

    // Update edited user when returning from CreateUserActivity
    public void updateUsers(){
        Pair<String, User> pair = (Pair<String, User>) Cache.getInstance().remove("user");
        if(pair != null) {
            String action = pair.getFirst();
            User user = pair.getSecond();
            if(action.equals("add")) {
                users.add(user);
                sortUsers(User.Properties.lastName, true);
            } else {
                int userID = user.getId();
                int userCount = users.size();
                for (int i = 0; i < userCount; i++) {
                    if (users.get(i).getId() == userID) {
                        if(action.equals("edit")) {
                            users.set(i, user);
                            sortUsers(User.Properties.lastName, true);
                        } else {
                            users.remove(i);
                            view.setUsers(users);
                        }
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Event.unsubscribe(FetchUsersEvent.TYPE, this);
    }
}
