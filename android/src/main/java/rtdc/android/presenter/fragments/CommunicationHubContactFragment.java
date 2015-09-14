package rtdc.android.presenter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.google.common.collect.ImmutableSet;
import rtdc.android.R;
import rtdc.core.Bootstrapper;
import rtdc.core.event.Event;
import rtdc.core.event.FetchUsersEvent;
import rtdc.core.model.User;
import rtdc.core.service.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CommunicationHubContactFragment extends AbstractFragment implements FetchUsersEvent.Handler{

    private View view;
    private String callName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_communication_hub_contact, container, false);

        view.findViewById(R.id.callButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callName = ((EditText) CommunicationHubContactFragment.this.view.findViewById(R.id.personToCall)).getText().toString();
                Service.getUsers();
            }
        });
        Event.subscribe(FetchUsersEvent.TYPE, this);

        return view;
    }

    @Override
    public void onUsersFetched(FetchUsersEvent event) {
        ImmutableSet<User> users = event.getUsers();
        Logger.getLogger(CommunicationHubContactListFragment.class.getName()).log(Level.INFO, "Searching for name " + callName);
        for(User user: users){
            //if(user.getFirstName().equals(callName.split(" ")[0]) && user.getFirstName().equals(callName.split(" ")[1])){
            if(user.getUsername().equals(callName)){
                Logger.getLogger(CommunicationHubContactListFragment.class.getName()).log(Level.INFO, "Calling " + user.getId());
                Bootstrapper.FACTORY.getVoipController().call(user);
                return;
            }
        }
    }
}
