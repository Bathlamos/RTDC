package rtdc.web.server.service;

import rtdc.core.Config;
import rtdc.core.model.User;

import java.sql.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AsteriskRealTimeService {

    // Run Asterisk requests on separate threads to not cause server latency
    private static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(4);

    private static Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String hostname = Config.ASTERISK_IP;
            String dbName = "asterisk_realtime";
            String dbUserName = "user";
            String dbPassword = "password";
            DriverManager.setLoginTimeout(3);   // The time (in seconds) it takes until we declare the connection request timed out
            return DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName, dbUserName, dbPassword);
        } catch (ClassNotFoundException e) {
            Logger.getLogger(AsteriskRealTimeService.class.getName()).log(Level.SEVERE, "MySQL JDBC Driver not found!");
        } catch (SQLException e) {
            Logger.getLogger(AsteriskRealTimeService.class.getName()).log(Level.SEVERE, "Could not connect to the Asterisk database; " + e.getMessage());
        }
        return null;
    }

    public static void addUser(final User user, final String password){
        executor.submit(new Runnable(){
            @Override
            public void run() {
                Connection connection = getConnection();
                if (connection == null) {
                    Logger.getLogger(AsteriskRealTimeService.class.getName()).log(
                            Level.SEVERE, user.getUsername() + " was NOT added to Asterisk; no connection was established");
                    return;
                }
                Logger.getLogger(AsteriskRealTimeService.class.getName()).log(Level.INFO, "Adding user " + user.getUsername() + " to Asterisk with extension " + user.getId());
                String sipQuery = "INSERT INTO sip_buddies (" +
                        " id, name, defaultuser, callerid, secret, context, HOST, TYPE) VALUES (" +
                        " ?, ?, ?, ?, ?," +
                        " 'users', 'dynamic', 'friend');";          // context, host, type
                String extensionQuery = "INSERT INTO extensions (context, exten, priority, app, appdata) VALUES (" +
                        " 'users', ?, 1, 'Dial', ?);";
                try {
                    PreparedStatement sipStatement = connection.prepareStatement(sipQuery);
                    sipStatement.setInt(1, user.getId());                                           // id
                    sipStatement.setString(2, user.getUsername());                                  // name
                    sipStatement.setString(3, user.getUsername());                                  // defaultuser
                    sipStatement.setString(4, user.getFirstName() + " " + user.getLastName());      // callerid
                    sipStatement.setString(5, password);                                            // secret
                    sipStatement.executeUpdate();

                    PreparedStatement extensionStatement = connection.prepareStatement(extensionQuery);
                    extensionStatement.setInt(1, user.getId());                                     // exten
                    extensionStatement.setString(2, "SIP/" + user.getUsername());                   // appdata
                    extensionStatement.executeUpdate();
                } catch (SQLException e) {
                    Logger.getLogger(AsteriskRealTimeService.class.getName()).log(Level.SEVERE, "SQL Query failed: " + sipQuery);
                    e.printStackTrace();
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void editUser(final User user){
        executor.submit(new Runnable() {
            @Override
            public void run() {
                Connection connection = getConnection();
                if (connection == null) {
                    Logger.getLogger(AsteriskRealTimeService.class.getName()).log(
                            Level.SEVERE, "User " + user.getUsername() + " could not be edited!");
                    return;
                }

                String sipQuery = "UPDATE sip_buddies SET name=?, defaultuser=?, callerid=? where id=?;";          // context, host, type
                String extensionQuery = "UPDATE extensions SET appdata=? where exten=?;";
                try {
                    PreparedStatement sipStatement = connection.prepareStatement(sipQuery);
                    sipStatement.setString(1, user.getUsername());                                  // name
                    sipStatement.setString(2, user.getUsername());                                  // defaultuser
                    sipStatement.setString(3, user.getFirstName() + " " + user.getLastName());      // callerid
                    sipStatement.setInt(4, user.getId());                                           // id
                    sipStatement.executeUpdate();

                    PreparedStatement extensionStatement = connection.prepareStatement(extensionQuery);
                    extensionStatement.setString(1, "SIP/" + user.getUsername());                   // exten
                    extensionStatement.setInt(2, user.getId());                                     // appdata
                    extensionStatement.executeUpdate();
                } catch (SQLException e) {
                    Logger.getLogger(AsteriskRealTimeService.class.getName()).log(Level.SEVERE, "SQL Query failed: " + sipQuery);
                    e.printStackTrace();
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void deleteUser(final User user){
        executor.submit(new Runnable() {
            @Override
            public void run() {
                Connection connection = getConnection();
                if (connection == null) {
                    Logger.getLogger(AsteriskRealTimeService.class.getName()).log(
                            Level.SEVERE, "User " + user.getUsername() + " could not be deleted from Asterisk; no connection was established");
                    return;
                }
                Logger.getLogger(AsteriskRealTimeService.class.getName()).log(Level.INFO, "Deleting user " + user.getUsername() + " from Asterisk");
                String sipQuery = "DELETE FROM sip_buddies WHERE NAME=?;";
                String extensionQuery = "DELETE FROM extensions WHERE exten=?;";
                try {
                    PreparedStatement sipStatement = connection.prepareStatement(sipQuery);
                    sipStatement.setString(1, user.getUsername());
                    sipStatement.executeUpdate();

                    PreparedStatement extensionStatement = connection.prepareStatement(extensionQuery);
                    extensionStatement.setInt(1, user.getId());
                    extensionStatement.executeUpdate();
                } catch (SQLException e) {
                    Logger.getLogger(AsteriskRealTimeService.class.getName()).log(Level.SEVERE, "SQL Query failed: " + sipQuery);
                    e.printStackTrace();
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
