package rtdc.web.server.service;

import rtdc.core.Bootstrapper;
import rtdc.core.model.User;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AsteriskRealTimeService {

    private static Connection getConnection() throws SQLException{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String hostname = Bootstrapper.getFactory().getConfig().asteriskHost();
            String dbName = "asterisk_realtime";
            String dbUserName = "user";
            String dbPassword = "password";
            return DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName, dbUserName, dbPassword);
        } catch (ClassNotFoundException e) {
            Logger.getLogger(AsteriskRealTimeService.class.getName()).log(Level.SEVERE, "MySQL JDBC Driver not found!");
        }
        return null;
    }

    public static void addUser(User user, String password) throws SQLException {
        Connection connection = getConnection();
        Logger.getLogger(AsteriskRealTimeService.class.getName()).log(Level.INFO, "Adding user " + user.getUsername() + " to Asterisk with extension " + user.getId());
        if (connection == null) {
            Logger.getLogger(AsteriskRealTimeService.class.getName()).log(Level.SEVERE, user.getUsername() + " was NOT added to Asterisk!");
            return;
        }
        String sipQuery = "INSERT INTO sip_buddies (" +
                " NAME, defaultuser, callerid, secret, context, HOST, TYPE, allow) VALUES (" +
                " ?," + " ?," + " ?," + " ?," +
                " 'users', 'dynamic', 'friend'," +                      // context, host, type
                " 'g729;ilbc;gsm;ulaw;alaw;vp8');";                     // allow (should be updated with which codecs we want users to be able to use)
        String extensionQuery = "INSERT INTO extensions (context, exten, priority, app, appdata) VALUES (" +
                " 'users', ?, 1, 'Dial', ?);";
        try {
            PreparedStatement sipStatement = connection.prepareStatement(sipQuery);
            sipStatement.setString(1, user.getUsername());                                  // name
            sipStatement.setString(2, user.getUsername());                                  // defaultuser
            sipStatement.setString(3, user.getFirstName() + " " + user.getLastName());      // callerid
            sipStatement.setString(4, password);                                            // secret
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

    public static void deleteUser(User user) throws SQLException{
        Connection connection = getConnection();
        Logger.getLogger(AsteriskRealTimeService.class.getName()).log(Level.INFO, String.valueOf(user.getId()));
        if (connection == null) {
            Logger.getLogger(AsteriskRealTimeService.class.getName()).log(Level.SEVERE, user.getUsername() + " could not be deleted from Asterisk!");
            return;
        }
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
}
