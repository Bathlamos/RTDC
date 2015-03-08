package rtdc.core.controller;

import rtdc.core.model.User;
import rtdc.core.service.AsyncCallback;
import rtdc.core.service.Service;
import rtdc.core.view.UserListView;

import java.util.List;

public class UserListController extends Controller<UserListView>{

    public UserListController(UserListView view){
        super(view);
        Service.getUsers(new AsyncCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> users) {
                UserListController.this.view.setUsers(users);
            }

            @Override
            public void onError(String message) {
                UserListController.this.view.displayError("Error", message);
            }
        });
    }

    public void onClickUser(User user){

    }

    public void onClickNewUser(){

    }



}
