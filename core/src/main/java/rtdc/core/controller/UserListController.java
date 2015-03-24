package rtdc.core.controller;

import rtdc.core.model.User;
import rtdc.core.service.AsyncCallback;
import rtdc.core.service.Service;
import rtdc.core.view.UserListView;

import java.util.List;

public class UserListController extends Controller<UserListView>{

    public UserListController(UserListView view){
        super(view);
        Service.getUsers();
    }

    public void onClickUser(User user){

    }

    public void onClickNewUser(){

    }



}
