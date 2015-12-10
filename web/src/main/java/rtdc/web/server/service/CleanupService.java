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

package rtdc.web.server.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
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
    private Future cleanupTask;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        cleanupTask = executor.scheduleWithFixedDelay(new Runnable(){
            @Override
            public void run() {
                Logger.getLogger(CleanupService.class.getName()).log(Level.INFO, "Cleaning task initiated...");

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

                    Logger.getLogger(CleanupService.class.getName()).log(Level.INFO, "Cleaning task was completed successfully");
                } catch (RuntimeException e) {
                    if(transaction != null)
                        transaction.rollback();
                    Logger.getLogger(CleanupService.class.getName()).log(Level.SEVERE, "Cleanup task failed!");
                } finally{
                    session.close();
                }
            }
        }, 1, 1, TimeUnit.DAYS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        cleanupTask.cancel(true);
    }
}
