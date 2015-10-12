package rtdc.web.server.servlet;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rtdc.core.event.*;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Message;
import rtdc.core.model.Permission;
import rtdc.core.model.User;
import rtdc.web.server.config.PersistenceConfig;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.sql.SQLException;
import java.util.*;

@Path("messages")
public class MessageServlet {

    private static final Logger log = LoggerFactory.getLogger(MessageServlet.class);

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    @RolesAllowed({Permission.USER, Permission.ADMIN})
    public String addMessage(@Context HttpServletRequest req, @FormParam("message") String messageString){
        Message message = new Message(new JSONObject(messageString));

        Set<ConstraintViolation<Message>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(message);
        if(!violations.isEmpty())
            return new ErrorEvent(violations.toString()).toString();

        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.saveOrUpdate(message);
            transaction.commit();

            // TODO: Replace string with actual username
            log.info("{}: MESSAGE: New message added: {}", "Message", messageString);
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            session.close();
        }

        return new ActionCompleteEvent(message.getId(), "message", "add").toString();
    }

    @POST
    @Path("{userId1}/{userId2}")
    @Consumes("application/x-www-form-urlencoded")
    @RolesAllowed({Permission.USER, Permission.ADMIN})
    public String getMessages(@Context HttpServletRequest req, @PathParam("userId1") String userId1String, @PathParam("userId2") String userId2String){
        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Message> messages = null;
        try{
            transaction = session.beginTransaction();
            messages = (List<Message>) session.createCriteria(Message.class).list();
            for(Message message: messages){
                message.setSender((User) session.get(User.class, message.getSenderID()));
                message.setReceiver((User) session.get(User.class, message.getReceiverID()));
            }
            transaction.commit();

            log.info("{}: MESSAGE: Getting messages {}", "Message");
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally{
            session.close();
        }

        // Only return the messages that we're sent and received by the two users given in the request

        int user1Id = Integer.parseInt(userId1String);
        int user2Id = Integer.parseInt(userId2String);
        for(Iterator<Message> iterator = messages.iterator(); iterator.hasNext();){
            Message message = iterator.next();
            if((message.getSender().getId() == user1Id && message.getReceiver().getId() == user2Id)
                    || (message.getSender().getId() == user2Id && message.getReceiver().getId() == user1Id)){
            }else{
                iterator.remove();
            }
        }

        return new FetchMessagesEvent(messages).toString();
    }

    @POST
    @Path("{userId}/")
    @Consumes("application/x-www-form-urlencoded")
    @RolesAllowed({Permission.USER, Permission.ADMIN})
    public String getRecentContacts(@Context HttpServletRequest req, @PathParam("userId") String userId1String){
        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Message> messages = null;
        try{
            transaction = session.beginTransaction();
            messages = (List<Message>) session.createCriteria(Message.class).list();
            for(Message message: messages){
                message.setSender((User) session.get(User.class, message.getSenderID()));
                message.setReceiver((User) session.get(User.class, message.getReceiverID()));
            }
            transaction.commit();

            log.info("{}: MESSAGE: Getting most recent messages {}", "Message");
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            session.close();
        }

        // Only return the most recent message for each user in conversation with our given user

        HashMap<Integer, Message> recentMessages = new HashMap<>();
        int userId = Integer.parseInt(userId1String);
        for(Iterator<Message> iterator = messages.iterator(); iterator.hasNext();){
            Message message = iterator.next();
            if(message.getSender().getId() == userId || message.getReceiver().getId() == userId){
                User otherUser = message.getSender().getId() == userId ? message.getReceiver(): message.getSender();
                recentMessages.put(otherUser.getId(), message);
            }
        }

        return new FetchRecentContactsEvent(recentMessages.values()).toString();
    }

    @PUT
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    @RolesAllowed({Permission.USER, Permission.ADMIN})
    public String editMessage(@Context HttpServletRequest req, @FormParam("message") String messageString){
        Message message = new Message(new JSONObject(messageString));

        Set<ConstraintViolation<Message>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(message);
        if(!violations.isEmpty())
            return new ErrorEvent(violations.toString()).toString();

        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            message.setTimeSent(new Date()); // Set to universal server time
            transaction = session.beginTransaction();
            session.saveOrUpdate(message);
            transaction.commit();

            // TODO: Replace string with actual username
            log.info("{}: MESSAGE: Message updated: {}", "Username", messageString);
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            session.close();
        }

        return new MessageSavedEvent(message.getId(), message.getTimeSent()).toString();
    }
}
