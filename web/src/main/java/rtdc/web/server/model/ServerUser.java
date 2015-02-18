package rtdc.web.server.model;

import rtdc.core.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("s")
public class ServerUser extends User{

    private static final String PASSWORD_HASH = "password_hash",
        SALT = "salt";

    private String passwordHash;
    private String salt;
    private Set<ServerUnit> units = new HashSet<>();

    public ServerUser(){}
    public ServerUser(String json){
        super(json);
    }

    @NotNull
    @Column(name = PASSWORD_HASH, nullable = false)
    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @NotNull
    @Column(name = SALT, nullable = false)
    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }

    @ManyToMany(mappedBy = "users")
    public Set<ServerUnit> getUnits() {
        return units;
    }
    public void setUnits(Set<ServerUnit> units) {
        this.units = units;
    }
}
