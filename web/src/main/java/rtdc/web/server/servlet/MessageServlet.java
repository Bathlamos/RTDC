/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Olivier Clermont, Jonathan Ermel, Mathieu Fortin-Boulay, Philippe Legault & Nicolas Ménard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package rtdc.web.server.servlet;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
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
import java.util.*;

@Path("messages")
public class MessageServlet {

    private static final Logger log = LoggerFactory.getLogger(MessageServlet.class);

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    @RolesAllowed({Permission.USER, Permission.MANAGER, Permission.ADMIN})
    public String addMessage(@Context HttpServletRequest req, @Context User user, @FormParam("message") String messageString){
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

            log.info("{}: MESSAGE: New message added: {}", user.getUsername(), messageString);
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            session.close();
        }

        return new ActionCompleteEvent(message.getId(), "message", "add").toString();
    }

    @GET
    @Path("{userId1}/{userId2}/{startIndex}/{length}")
    @Consumes("application/x-www-form-urlencoded")
    @RolesAllowed({Permission.USER, Permission.MANAGER, Permission.ADMIN})
    public String getMessages(@Context HttpServletRequest req, @Context User user, @PathParam("userId1") String userId1String, @PathParam("userId2") String userId2String,
                              @PathParam("startIndex") String startIndexString, @PathParam("length") String lengthString){
        int user1Id = Integer.parseInt(userId1String);
        int user2Id = Integer.parseInt(userId2String);
        int startIndex = Integer.parseInt(startIndexString);
        int length = Integer.parseInt(lengthString);

        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Message> messages = null;
        User user1 = null;
        User user2 = null;
        try{
            transaction = session.beginTransaction();

            // Only return the messages that we're sent and received by the two users given in the request

            messages = (List<Message>) session.createCriteria(Message.class)
                    .add(Restrictions.or(
                            Restrictions.and(
                                    Restrictions.eq("senderID", user1Id),
                                    Restrictions.eq("receiverID", user2Id)
                            ),
                            Restrictions.and(
                                    Restrictions.eq("senderID", user2Id),
                                    Restrictions.eq("receiverID", user1Id)
                            )
                    )).addOrder(Order.desc("timeSent")).list();
            user1 = (User) session.get(User.class, user1Id);
            user2 = (User) session.get(User.class, user2Id);
            for(Message message: messages){
                if(message.getSenderID() == user1.getId()) {
                    message.setSender(user1);
                    message.setReceiver(user2);
                }else {
                    message.setSender(user2);
                    message.setReceiver(user1);
                }
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

        if(messages.size() - 1 < startIndex || messages.isEmpty()) {
            // The requested start index is out of bounds, return an empty list
            return new FetchMessagesEvent (user1, user2, new ArrayList<Message>()).toString();
        }else if(messages.size() - 1 < startIndex + length) {
            // The requested length goes out of the list's bounds, we need to adjust it
            length = messages.size() - 1 - startIndex;
        }

        List<Message> subList = messages.subList(startIndex, startIndex + length + 1);
        // After getting the sub-list, reverse it as we want the messages to be in order from oldest to newest
        Collections.reverse(subList);
        return new FetchMessagesEvent(user1, user2, subList).toString();
    }

    @GET
    @Path("{userId}")
    @Consumes("application/x-www-form-urlencoded")
    @RolesAllowed({Permission.USER, Permission.MANAGER, Permission.ADMIN})
    public String getRecentContacts(@Context HttpServletRequest req, @Context User user, @PathParam("userId") String userId1String){
        int userId = Integer.parseInt(userId1String);
        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Message> messages = null;
        try{
            transaction = session.beginTransaction();

            messages = (List<Message>) session.createCriteria(Message.class)
                    .add(Restrictions.or(
                            Restrictions.eq("senderID", userId),
                            Restrictions.eq("receiverID", userId)
                    )).addOrder(Order.desc("timeSent")).list();

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
        for(Iterator<Message> iterator = messages.iterator(); iterator.hasNext();){
            Message message = iterator.next();
            User otherUser = message.getSender().getId() == userId ? message.getReceiver(): message.getSender();
            if(!recentMessages.containsKey(otherUser.getId()) || message.getTimeSent().after(recentMessages.get(otherUser.getId()).getTimeSent()))
                recentMessages.put(otherUser.getId(), message);
        }

        return new FetchRecentContactsEvent(recentMessages.values()).toString();
    }

    @PUT
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    @RolesAllowed({Permission.USER, Permission.MANAGER, Permission.ADMIN})
    public String editMessage(@Context HttpServletRequest req, @Context User user, @FormParam("message") String messageString){
        Message message = new Message(new JSONObject(messageString));

        Set<ConstraintViolation<Message>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(message);
        if(!violations.isEmpty())
            return new ErrorEvent(violations.toString()).toString();

        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            if(message.getId() == -1) // No id for this message, thus it isn't in the database yet
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

        return new MessageSavedEvent(message).toString();
    }
}
