package rtdc.web.server.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import rtdc.core.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class UserCredentials implements Serializable {

    @NotNull
    @Column(name = "passwordHash", nullable = false)
    private String passwordHash;

    @NotNull
    @Column(name = "salt", nullable = false)
    private String salt;

    @Id
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

}
