package rtdc.web.test;

import org.junit.Assert;
import org.junit.Test;
import rtdc.core.Config;
import rtdc.core.event.*;
import rtdc.core.json.JSONArray;
import rtdc.core.json.JSONObject;
import rtdc.core.json.JSONTokener;
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

    private static final String _url = "http://" + Config.SERVER_IP + ":8888/api/";
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
        User user = new User((JSONObject) object.get("user"));
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
        Assert.assertEquals(ErrorEvent.TYPE.getName(), object.get("_type").toString());
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
        Assert.assertEquals(ErrorEvent.TYPE.getName(), object.get("_type").toString());
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
        Assert.assertEquals(ErrorEvent.TYPE.getName(), object.get("_type").toString());
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
    public void logout_noAuthToken_error() {
        // Arrange
        JSONObject object = executeSyncRequest("auth/login", "username=Nathaniel&password=password", "POST", null);

        // Action
        JSONObject object2 = executeSyncRequest("auth/logout", null, "POST", "");

        // Assert
        Assert.assertEquals(ErrorEvent.TYPE.getName(), object2.get("_type").toString());
    }

    @Test
    public void getUnits_correctBehavior_gotAllUnits() {
        // Arrange

        // Action
        JSONObject object2 = executeSyncRequest("units", null, "GET", "");

        // Assert
        Assert.assertEquals(ErrorEvent.TYPE.getName(), object2.get("_type"));
        // TODO: Check for correct description, but need to put string in resources file before
    }

    @Test
    public void getUnits_noAuthToken_error() {
        // Arrange
        JSONObject object = executeSyncRequest("auth/login", "username=Nathaniel&password=password", "POST", null);
        String authToken = object.get("authenticationToken").toString();

        // Action
        JSONObject object2 = executeSyncRequest("units", null, "GET", authToken);
        JSONArray unitsJsonArray = new JSONArray(new JSONTokener(object2.get("units").toString()));

        // Assert
        Assert.assertEquals(FetchUnitsEvent.TYPE.getName(), object2.get("_type"));
        Assert.assertEquals(unitsJsonArray.length() > 0, true);
    }

    @Test
    public void updateOrSaveUnit_newUnit_unitSaved() {
        // Arrange
        JSONObject object = executeSyncRequest("auth/login", "username=Nathaniel&password=password", "POST", null);
        String authToken = object.get("authenticationToken").toString();

        Unit testUnit = new Unit();

        testUnit.setName("Test Unit " + (int) (Math.random() * 100));
        testUnit.setAvailableBeds(20);

        // Action
        JSONObject object2 = executeSyncRequest("units", "unit=" + testUnit.toString(), "PUT", authToken);

        Unit savedUnit = getSingleUnit(authToken, (int) object2.get("objectId"));

        // Assert
        Assert.assertEquals(ActionCompleteEvent.TYPE.getName(), object2.get("_type"));
        Assert.assertTrue(object2.get("objectId") != null);
        Assert.assertEquals(object2.get("action"), "update");
        Assert.assertEquals(object2.get("objectType"), "unit");

        Assert.assertEquals(savedUnit.getName(), testUnit.getName());
        Assert.assertEquals(savedUnit.getAvailableBeds(), testUnit.getAvailableBeds());
    }

    @Test
    public void updateOrSaveUnit_updateUnit_unitUpdated() {
        // Arrange
        JSONObject object = executeSyncRequest("auth/login", "username=Nathaniel&password=password", "POST", null);
        String authToken = object.get("authenticationToken").toString();

        JSONObject object2 = executeSyncRequest("units", null, "GET", authToken);

        JSONArray unitJsonArray = new JSONArray(new JSONTokener(object2.get("units").toString()));

        // Get units from object2

        // Action
        Unit unit = new Unit(new JSONObject(unitJsonArray.get(0).toString()));
        unit.setName("Modified name");
        JSONObject result = executeSyncRequest("units", "unit=" + unit.toString(), "PUT", authToken);

        Unit savedUnit = getSingleUnit(authToken, unit.getId());

        // Assert
        Assert.assertEquals(ActionCompleteEvent.TYPE.getName(), result.get("_type"));
        Assert.assertEquals(result.get("objectId"), unit.getId());
        Assert.assertEquals(result.get("action"), "update");
        Assert.assertEquals(result.get("objectType"), "unit");

        Assert.assertEquals(savedUnit.getName(), unit.getName());
        Assert.assertEquals(savedUnit.getAvailableBeds(), unit.getAvailableBeds());
    }

    @Test
    public void updateOrSaveUnit_noAuthToken_error() {
        // Arrange
        Unit testUnit = new Unit();

        testUnit.setName("Test Unit " + (int) (Math.random() * 100));
        testUnit.setAvailableBeds(20);

        // Action
        JSONObject object2 = executeSyncRequest("units", "unit=" + testUnit.toString(), "PUT", null);

        // Assert
        Assert.assertEquals(ErrorEvent.TYPE.getName(), object2.get("_type"));
        // TODO: Check for correct description, but need to put string in resources file before
    }

    @Test
    public void deleteUnit_unitExists_unitDeleted() {
        // Arrange
        JSONObject object = executeSyncRequest("auth/login", "username=Nathaniel&password=password", "POST", null);
        String authToken = object.get("authenticationToken").toString();

        // Action
        int unitId = 2;
        JSONObject result = executeSyncRequest("units/" + unitId, null, "DELETE", authToken);

        // Assert
        Unit checkUnit = getSingleUnit(authToken, 1);

        Assert.assertEquals(ActionCompleteEvent.TYPE.getName(), result.get("_type"));
        Assert.assertNull(checkUnit);
        // TODO: Check list of units ?
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
    public void deleteUnit_noAuthToken_error() {
        // Arrange

        // Action
        int unitId = 1;
        JSONObject result = executeSyncRequest("units/" + unitId, null, "DELETE", "");

        // Assert
        Assert.assertEquals(ErrorEvent.TYPE.getName(), result.get("_type"));
        // TODO: Check for correct description, but need to put string in resources file before
    }

    @Test
    public void getUsers_correctBehavior_gotAllUsers() {
        // Arrange
        JSONObject object = executeSyncRequest("auth/login", "username=Nathaniel&password=password", "POST", null);
        String authToken = object.get("authenticationToken").toString();

        // Action
        JSONObject object2 = executeSyncRequest("users", null, "GET", authToken);

        // Assert
        Assert.assertEquals(FetchUsersEvent.TYPE.getName(), object2.get("_type"));
    }

    @Test
    public void getUsers_noAuthToken_error() {
        // Arrange

        // Action
        JSONObject object2 = executeSyncRequest("users", null, "GET", null);

        // Assert
        Assert.assertEquals(ErrorEvent.TYPE.getName(), object2.get("_type"));
    }

    @Test
    public void updateOrSaveUser_newUser_userSaved() {
        // Arrange
        JSONObject object = executeSyncRequest("auth/login", "username=Nathaniel&password=password", "POST", null);
        String authToken = object.get("authenticationToken").toString();

        User testUser = new User();
        testUser.setUsername("test");
        testUser.setFirstName("Test 123");
        testUser.setLastName("Test");

        // Action
        JSONObject object2 = executeSyncRequest("users", "user=" + testUser.toString(), "PUT", authToken);
        // Parse and get list of units

        // Assert
        Assert.assertEquals(ActionCompleteEvent.TYPE.getName(), object2.get("_type"));
        JSONObject savedUserJson = executeSyncRequest("users", testUser.getUsername(), "GET", authToken);
        Assert.assertEquals(FetchUserEvent.TYPE.getName(), savedUserJson.get("_type"));
    }

    @Test
    public void updateOrSaveUser_updateUser_userUpdated() {
        // Arrange
        JSONObject object = executeSyncRequest("auth/login", "username=Nathaniel&password=password", "POST", null);
        String authToken = object.get("authenticationToken").toString();

        JSONObject object2 = executeSyncRequest("users", null, "GET", authToken);

        JSONArray userJsonArray = new JSONArray(new JSONTokener(object2.get("users").toString()));

        // Action
        User user = new User(new JSONObject(userJsonArray.get(0).toString()));
        user.setFirstName("Modified name");
        JSONObject result = executeSyncRequest("users", user.toString(), "PUT", authToken);

        // Assert
        Assert.assertEquals(ActionCompleteEvent.TYPE.getName(), result.get("_type"));
        // TODO: Verify if user is saved correctly?
    }

    @Test
    public void updateOrSaveUser_noAuthToken_error() {
        // Arrange
        User testUser = new User();
        testUser.setFirstName("Test 123");
        testUser.setLastName("Test");

        // Action
        JSONObject result = executeSyncRequest("users", testUser.toString(), "PUT", null);

        // Assert
        Assert.assertEquals(ErrorEvent.TYPE.getName(), result.get("_type"));
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
        // TODO: Check list of units ?
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

    @Test
    public void deleteUser_noAuthToken_error() {
        // Arrange

        // Action
        int userId = 1;
        JSONObject result = executeSyncRequest("users/" + userId, null, "DELETE", "");

        // Assert
        Assert.assertEquals(ErrorEvent.TYPE.getName(), result.get("_type"));
    }


    private static JSONObject executeSyncRequest(String service, String urlParameters, String requestMethod, @Nullable String authToken) {
        try {
            URL urlObj = new URL(_url + service);
            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

            con.setRequestMethod(requestMethod);
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Accept-Language", "en-us,en;q=0.5");
            if (authToken != null)
                con.setRequestProperty("auth_token", authToken);

            con.setDoOutput(true);
            if (urlParameters != null) {
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

    // TODO: ErrorEvent: {"_type":"errorEvent","description":"could not initialize proxy - no Session"}
    // Does not happend when debugging the server and stepping through the getUnit method in UnitServlet ??
    private static Unit getSingleUnit(String authToken, int id) {
        JSONObject unitJson = (executeSyncRequest("units/" + id, null, "GET", authToken));
        if (unitJson.get("_type") == ErrorEvent.TYPE.getName()) {
            return null;
        }
        JSONArray unitJsonArray = new JSONArray(new JSONTokener(unitJson.get("units").toString()));
        Unit unit = new Unit(unitJsonArray.getJSONObject(0));
        return unit;
    }

}