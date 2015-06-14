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

    @Id
    @NotNull
    @Column(name = "authToken")
    private String authToken;

    @NotNull
    @Column(name = "dateSet", nullable = false)
    private Date dateSet;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class, optional = false)
    private User user;

    public String getAuthenticationToken() {
        return authToken;
    }
    public void setAuthenticationToken(String authToken) {
        this.authToken = authToken;
    }

    public Date getDateSet() {
        return dateSet;
    }
    public void setDateSet(Date dateSet) {
        this.dateSet = dateSet;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

}
