package rtdc.web.server.auth;

import rtdc.core.model.User;

import javax.persistence.*;

@Entity
@DiscriminatorValue("a")
public class AuthenticatedUser extends User{

    private static final String PASSWORD_HASH = "password_hash",
        SALT = "salt";

    private String passwordHash;
    private String salt;

    @Column(name = PASSWORD_HASH)
    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Column(name = SALT)
    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }
}
