package rtdc.web.server.model;

import rtdc.core.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
public class AuthenticationToken implements Serializable {

    private String authToken;
    private Date dateSet;
    private User user;

    @NotNull
    @Column(name = "auth_token", nullable = false)
    public String getAuthenticationToken() {
        return authToken;
    }
    public void setAuthenticationToken(String authToken) {
        this.authToken = authToken;
    }

    @NotNull
    @Column(name = "date_set", nullable = false)
    public Date getDateSet() {
        return dateSet;
    }
    public void setDateSet(Date dateSet) {
        this.dateSet = dateSet;
    }

    @Id
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class, cascade = CascadeType.ALL, optional = false)
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

}
