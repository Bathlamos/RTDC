package rtdc.core;

import rtdc.core.model.User;

public class Session {

    private User user;
    private static Session currentSession;

    public Session(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    public static void setCurrentSession(Session session){
        currentSession = session;
    }

    public static Session getCurrentSession(){
        return currentSession;
    }
}
