package rtdc.web.server.service;

import rtdc.core.Config;
import rtdc.core.model.User;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AsteriskRealTimeService {

    private static Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String hostname = Config.ASTERISK_IP;
            String dbName = "asterisk_realtime";
            String dbUserName = "user";
            String dbPassword = "password";
            return DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName, dbUserName, dbPassword);
        } catch (ClassNotFoundException e) {
            Logger.getLogger(AsteriskRealTimeService.class.getName()).log(Level.SEVERE, "MySQL JDBC Driver not found!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addUser(User user, String password){
        Connection connection = getConnection();
        Logger.getLogger(AsteriskRealTimeService.class.getName()).log(Level.INFO, String.valueOf(user.getId()));
        if (connection == null) {
            Logger.getLogger(AsteriskRealTimeService.class.getName()).log(Level.SEVERE, user.getUsername() + " was NOT added to Asterisk!");
            return;
        }
        try {
            String sipQuery = "INSERT INTO sip_buddies (" +
                    " NAME, defaultuser, callerid, secret, context, HOST, TYPE, allow) VALUES (" +
                    " '"+user.getUsername()+"'," +                          // name
                    " '"+user.getUsername()+"'," +                          // defaultuser
                    " '"+user.getFirstName()+" "+user.getLastName()+"'," +  // callerid
                    " '"+password+"'," +                                    // secret
                    " 'users', 'dynamic', 'friend'," +                      // context, host, type
                    " 'g729;ilbc;gsm;ulaw;alaw;h263;h263p;h264;vp8');";     // allow (should be updated with which codecs we want users to be able to use)
            String extensionQuery = "INSERT INTO extensions (context, exten, priority, app, appdata) VALUES (" +
                    " 'users','"+user.getId()+"',1,'Dial','SIP/"+user.getUsername()+"');";
            connection.createStatement().executeUpdate(sipQuery);
            connection.createStatement().executeUpdate(extensionQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteUser(User user){
        Connection connection = getConnection();
        Logger.getLogger(AsteriskRealTimeService.class.getName()).log(Level.INFO, String.valueOf(user.getId()));
        if (connection == null) {
            Logger.getLogger(AsteriskRealTimeService.class.getName()).log(Level.SEVERE, user.getUsername() + " could not be deleted from Asterisk!");
            return;
        }
        try {
            String sipQuery = "DELETE FROM sip_buddies WHERE NAME='"+user.getUsername()+"';";
            String extensionQuery = "DELETE FROM extensions WHERE exten='"+user.getId()+"';";
            connection.createStatement().executeUpdate(sipQuery);
            connection.createStatement().executeUpdate(extensionQuery);
        } catch (SQLException e) {
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
