package rtdc.web.server.model;

import rtdc.core.model.Unit;
import rtdc.core.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("s")
public class ServerUnit extends Unit {

    public static final String REL_USER_UNIT = "user_unit";

    private Set<ServerUser> users = new HashSet<>();

    public ServerUnit(){

    }
    public ServerUnit(String json){
        super(json);
    }

    @ManyToMany
    @JoinTable(name = REL_USER_UNIT,
        joinColumns = {@JoinColumn(name = ServerUser.ID)},
        inverseJoinColumns = {@JoinColumn(name = ID)})
    public Set<ServerUser> getUsers() {
        return users;
    }
    public void setUsers(Set<ServerUser> users) {
        this.users = users;
    }


}
