package rtdc.web.test;

import org.junit.Assert;
import org.junit.Test;
import rtdc.core.Config;
import rtdc.core.event.ActionCompleteEvent;
import rtdc.core.event.AuthenticationEvent;
import rtdc.core.event.ErrorEvent;
import rtdc.core.event.LogoutEvent;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Unit;
import rtdc.core.model.User;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ServiceTest {

    private static final String _url = "http://"+ Config.SERVER_IP+":8888/api/";
    private static final String USER_AGENT = "Mozilla/5.0";

    @Test
    public void authenticateUser_existingUser_getUserPlusAuthToken() {
        // Action;
        JSONObject object = executeSyncRequest("auth/login", "username=Nathaniel&password=password", "POST", null);

        // Assert
        Assert.assertNotNull(object);
        Assert.assertEquals(AuthenticationEvent.TYPE.getName(), object.get("_type").toString());
        Assert.assertTrue(object.has("user"));
        Assert.assertTrue(object.has("authenticationToken"));
        User user = new User((JSONObject)object.get("user"));
        Assert.assertEquals("Nathaniel", user.getFirstName());
        Assert.assertEquals("Aumonttt", user.getLastName());
    }

    @Test
    public void authenticateUser_badPassword_getNoUser() {
        // Action
        JSONObject object = executeSyncRequest("auth/login", "username=Nathaniel&password=pssword", "POST", null);

        // Assert
        Assert.assertNotNull(object);
        Assert.assertEquals(ErrorEvent.TYPE.getName(), object.get("_type").toString());
        Assert.assertEquals("Username / password mismatch", object.get("description"));
        Assert.assertFalse(object.has("user"));
        Assert.assertFalse(object.has("authenticationToken"));
    }

    @Test
    public void authenticateUser_badUsername_getNoUser() {
        // Action
        JSONObject object = executeSyncRequest("auth/login", "username=Nahaniel&password=password", "POST", null);

        // Assert
        Assert.assertNotNull(object);
        Assert.assertEquals(ErrorEvent.TYPE.getName(), object.get("_type").toString());
        Assert.assertEquals("Username / password mismatch", object.get("description"));
        Assert.assertFalse(object.has("user"));
        Assert.assertFalse(object.has("authenticationToken"));
    }

    @Test
    public void authenticateUser_badUsernameBadPassword_getNoUser() {
        // Action
        JSONObject object = executeSyncRequest("auth/login", "username=Nahaniel&password=pasword", "POST", null);

        // Assert
        Assert.assertNotNull(object);
        Assert.assertEquals(ErrorEvent.TYPE.getName(),object.get("_type").toString());
        Assert.assertEquals("Username / password mismatch", object.get("description"));
        Assert.assertFalse(object.has("user"));
        Assert.assertFalse(object.has("authenticationToken"));
    }

    @Test
    public void authenticateUser_spaceUsernameSpacePassword_getNoUser() {
        // Action
        JSONObject object = executeSyncRequest("auth/login", "username= &password= ", "POST", null);

        // Assert
        Assert.assertNotNull(object);
        Assert.assertEquals(ErrorEvent.TYPE.getName(), object.get("_type").toString());
        Assert.assertEquals("Username / password mismatch", object.get("description"));
        Assert.assertFalse(object.has("user"));
        Assert.assertFalse(object.has("authenticationToken"));
    }

    @Test
    public void authenticateUser_noUsernameNoPassword_getNoUser() {
        // Action
        JSONObject object = executeSyncRequest("auth/login", "username=&password=", "POST", null);

        // Assert
        Assert.assertNotNull(object);
        Assert.assertEquals(ErrorEvent.TYPE.getName(),object.get("_type").toString());
        Assert.assertEquals("Username cannot be empty", object.get("description"));
        Assert.assertFalse(object.has("user"));
        Assert.assertFalse(object.has("authenticationToken"));
    }

    @Test
    public void authenticateUser_NoPassword_getNoUser() {
        // Action
        JSONObject object = executeSyncRequest("auth/login", "username=Nathaniel&password=", "POST", null);

        // Assert
        Assert.assertNotNull(object);
        Assert.assertEquals(ErrorEvent.TYPE.getName(),object.get("_type").toString());
        Assert.assertEquals("Password cannot be empty", object.get("description"));
        Assert.assertFalse(object.has("user"));
        Assert.assertFalse(object.has("authenticationToken"));
    }

    @Test
    public void isAuthTokenValid_validToken_ok() {
        // Arrange
        JSONObject object = executeSyncRequest("auth/login", "username=Nathaniel&password=password", "POST", null);
        String authToken = object.get("authenticationToken").toString();

        // Action
        JSONObject object2 = executeSyncRequest("auth/tokenValid", "", "POST", authToken);

        // Assert
        Assert.assertEquals(AuthenticationEvent.TYPE.getName(), object2.get("_type").toString());
    }

    @Test
    public void logout_correctBehavior_userLoggedOut() {
        // Arrange
        JSONObject object = executeSyncRequest("auth/login", "username=Nathaniel&password=password", "POST", null);
        String authToken = object.get("authenticationToken").toString();

        // Action
        JSONObject object2 = executeSyncRequest("auth/logout", null, "POST", authToken);

        System.out.println(object2);

        // Assert
        Assert.assertEquals(LogoutEvent.TYPE.getName(), object2.get("_type").toString());
    }

    @Test
    public void getUnits_correctBehavior_gotAllUnits() {
        // Arrange
        JSONObject object = executeSyncRequest("auth/login", "username=Nathaniel&password=password", "POST", null);
        String authToken = object.get("authenticationToken").toString();

        // Action
        JSONObject object2 = executeSyncRequest("units", null, "GET", authToken);

        // Assert
        Assert.assertNotEquals(ErrorEvent.TYPE.getName(), object2.get("_type"));
    }

    @Test
    public void updateOrSaveUnit_newUnit_unitSaved() {
        // Arrange
        JSONObject object = executeSyncRequest("auth/login", "username=Nathaniel&password=password", "POST", null);
        String authToken = object.get("authenticationToken").toString();

        Unit testUnit = new Unit();
        testUnit.setId(123);
        testUnit.setName("Test Unit");
        testUnit.setAvailableBeds(20);

        // Action
        JSONObject object2 = executeSyncRequest("units", testUnit.toString(), "PUT", authToken);
        // Parse and get list of units

        // Assert
        //Assert.assertEquals(ActionCompleteEvent.TYPE.getName(), object2.get("_type"));
        // TO-DO: Verify if unit is saved correctly?
    }

    @Test
    public void updateOrSaveUnit_updateUnit_unitUpdated() {
        // Arrange
        JSONObject object = executeSyncRequest("auth/login", "username=Nathaniel&password=password", "POST", null);
        String authToken = object.get("authenticationToken").toString();

        JSONObject object2 = executeSyncRequest("units", null, "GET", authToken);

        ArrayList<Unit> units = new ArrayList<>();
        // Get units from object2

        // Action
        Unit unit = units.get(0);
        unit.setName("Modified name");
        JSONObject result = executeSyncRequest("units", unit.toString(), "PUT", authToken);

        // Assert
        //Assert.assertEquals(ActionCompleteEvent.TYPE.getName(), result.get("_type"));
        // TO-DO: Verify if unit is saved correctly?
    }

    @Test
    public void deleteUnit_unitExists_unitDeleted() {
        // Arrange
        JSONObject object = executeSyncRequest("auth/login", "username=Nathaniel&password=password", "POST", null);
        String authToken = object.get("authenticationToken").toString();

        // Action
        int unitId = 1;
        JSONObject result = executeSyncRequest("units/" + unitId, null, "DELETE", authToken);

        // Assert
        Assert.assertEquals(ActionCompleteEvent.TYPE.getName(), result.get("_type"));
        // TO-DO: Check list of units ?
    }

    @Test
    public void deleteUnit_unitNotFound_errorEvent() {
        // Arrange
        JSONObject object = executeSyncRequest("auth/login", "username=Nathaniel&password=password", "POST", null);
        String authToken = object.get("authenticationToken").toString();

        // Action
        int unitId = 99999;
        JSONObject result = executeSyncRequest("units/" + unitId, null, "DELETE", authToken);

        // Assert
        Assert.assertEquals(ErrorEvent.TYPE.getName(), result.get("_type"));
    }

    @Test
    public void getUsers_correctBehavior_gotAllUsers() {
        // Arrange
        JSONObject object = executeSyncRequest("auth/login", "username=Nathaniel&password=password", "POST", null);
        String authToken = object.get("authenticationToken").toString();

        // Action
        JSONObject object2 = executeSyncRequest("users", null, "GET", authToken);

        // Assert
        Assert.assertNotEquals(ErrorEvent.TYPE.getName(), object2.get("_type"));
    }

    @Test
    public void updateOrSaveUser_newUser_userSaved() {
        // Arrange
        JSONObject object = executeSyncRequest("auth/login", "username=Nathaniel&password=password", "POST", null);
        String authToken = object.get("authenticationToken").toString();

        User testUser = new User();
        testUser.setId(123456);
        testUser.setFirstName("Test");
        testUser.setLastName("Test");

        // Action
        JSONObject object2 = executeSyncRequest("users", testUser.toString(), "PUT", authToken);
        // Parse and get list of units

        // Assert
        //Assert.assertEquals(ActionCompleteEvent.TYPE.getName(), object2.get("_type"));
        // TO-DO: Verify if unit is saved correctly?
    }

    @Test
    public void updateOrSaveUser_updateUser_userUpdated() {
        // Arrange
        JSONObject object = executeSyncRequest("auth/login", "username=Nathaniel&password=password", "POST", null);
        String authToken = object.get("authenticationToken").toString();

        JSONObject object2 = executeSyncRequest("users", null, "GET", authToken);

        ArrayList<User> users = new ArrayList<>();
        // Get units from object2

        // Action
        User user = users.get(0);
        user.setFirstName("Modified name");
        JSONObject result = executeSyncRequest("users", user.toString(), "PUT", authToken);

        // Assert
        //Assert.assertEquals(ActionCompleteEvent.TYPE.getName(), result.get("_type"));
        // TO-DO: Verify if unit is saved correctly?
    }

    @Test
    public void deleteUser_userExists_userDeleted() {
        // Arrange
        JSONObject object = executeSyncRequest("auth/login", "username=Nathaniel&password=password", "POST", null);
        String authToken = object.get("authenticationToken").toString();

        // Action
        int userId = 1;
        JSONObject result = executeSyncRequest("users/" + userId, null, "DELETE", authToken);

        // Assert
        Assert.assertEquals(ActionCompleteEvent.TYPE.getName(), result.get("_type"));
        // TO-DO: Check list of units ?
    }

    @Test
    public void deleteUser_userNotFound_errorMessage() {
        // Arrange
        JSONObject object = executeSyncRequest("auth/login", "username=Nathaniel&password=password", "POST", null);
        String authToken = object.get("authenticationToken").toString();

        // Action
        int userId = 99999;
        JSONObject result = executeSyncRequest("users/" + userId, null, "DELETE", authToken);

        // Assert
        Assert.assertEquals(ErrorEvent.TYPE.getName(), result.get("_type"));
    }

    private static JSONObject executeSyncRequest(String service, String urlParameters, String requestMethod, @Nullable String authToken) {
        try  {
            URL urlObj = new URL(_url + service);
            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

            con.setRequestMethod(requestMethod);
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-us,en;q=0.5");
            if (authToken != null)
                con.setRequestProperty("auth_token", authToken);

            con.setDoOutput(true);
            if(urlParameters != null){
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject object = new JSONObject(response.toString());

            return object;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}