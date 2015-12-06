package rtdc.web.server.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import rtdc.core.model.Action;
import rtdc.core.model.Message;
import rtdc.web.server.config.PersistenceConfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CleanupService implements ServletContextListener {

    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    private Future textMessagesCleanupTask;
    private Future actionCleanupTask;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        textMessagesCleanupTask = executor.scheduleWithFixedDelay(new Runnable(){
            @Override
            public void run() {
                Logger.getLogger(CleanupService.class.getName()).log(Level.INFO, "Text message cleaning task initiated...");

                Session session = PersistenceConfig.getSessionFactory().openSession();
                Transaction transaction = null;

                try{
                    transaction = session.beginTransaction();

                    // Delete all messages that are older than 30 days

                    List<Message> messages = (List<Message>) session.createCriteria(Message.class).list();
                    for(Message message: messages) {
                        Date currentDate = new Date();
                        long dayDifference = TimeUnit.MILLISECONDS.toDays(currentDate.getTime() - message.getTimeSent().getTime());
                        if(dayDifference > 30)
                            session.delete(message);
                    }
                    transaction.commit();

                    Logger.getLogger(CleanupService.class.getName()).log(Level.INFO, "Text message cleaning task was completed successfully");
                } catch (RuntimeException e) {
                    if(transaction != null)
                        transaction.rollback();
                    Logger.getLogger(CleanupService.class.getName()).log(Level.SEVERE, "Text message cleanup task failed!", e);
                } finally{
                    session.close();
                }
            }
        }, 1, 1, TimeUnit.DAYS);

        actionCleanupTask = executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                Logger.getLogger(CleanupService.class.getName()).log(Level.INFO, "Action cleaning task initiated...");

                Session session = PersistenceConfig.getSessionFactory().openSession();
                Transaction transaction = null;

                try{
                    transaction = session.beginTransaction();

                    // Delete all messages that are older than 30 days

                    List<Action> actions = (List<Action>) session.createCriteria(Action.class).list();
                    for(Action action: actions) {

                    }
                    transaction.commit();

                    Logger.getLogger(CleanupService.class.getName()).log(Level.INFO, "Action cleaning task was completed successfully");
                } catch (RuntimeException e) {
                    if(transaction != null)
                        transaction.rollback();
                    Logger.getLogger(CleanupService.class.getName()).log(Level.SEVERE, "Action cleanup task failed!", e);
                } finally{
                    session.close();
                }
            }
        }, 12, 12, TimeUnit.HOURS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        textMessagesCleanupTask.cancel(true);
        actionCleanupTask.cancel(true);
    }
}
