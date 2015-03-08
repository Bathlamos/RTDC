package rtdc.web.server.model;

import rtdc.core.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class UserCredentials implements Serializable {

    private String passwordHash;
    private String salt;
    private User user;

    @NotNull
    @Column(name = "password_hash", nullable = false)
    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @NotNull
    @Column(name = "salt", nullable = false)
    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Id
    @OneToOne(fetch = FetchType.LAZY, targetEntity = User.class, cascade = CascadeType.ALL, optional = false)
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

}
