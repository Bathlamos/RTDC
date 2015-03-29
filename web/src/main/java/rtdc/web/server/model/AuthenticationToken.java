package rtdc.web.server.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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

    @Id
    @NotNull
    @Column(name = "auth_token")
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

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class, cascade = CascadeType.ALL, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

}
