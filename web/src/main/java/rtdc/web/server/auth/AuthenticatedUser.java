package rtdc.web.server.auth;

import rtdc.core.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("a")
public class AuthenticatedUser extends User{

    private static final String PASSWORD_HASH = "password_hash",
        SALT = "salt";

    private String passwordHash;
    private String salt;

    public AuthenticatedUser(String json){
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
}
