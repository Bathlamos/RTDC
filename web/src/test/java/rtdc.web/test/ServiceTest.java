package rtdc.web.test;

import org.junit.Assert;
import org.junit.Test;
import rtdc.core.Config;
import rtdc.core.event.*;
import rtdc.core.json.JSONArray;
import rtdc.core.json.JSONObject;
import rtdc.core.json.JSONTokener;
import rtdc.core.model.Action;
import rtdc.core.model.Unit;
import rtdc.core.model.User;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class ServiceTest {

    private static final String _url = "http://" + Config.SERVER_IP + ":8888/api/";
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String TEST_USERNAME = "Nathaniel";
    private static final String TEST_PASSWORD = "password";

    // Tests for AuthServlet

    @Test
    public void authenticateUser_existingUser_getUserPlusAuthToken() {
        // Action;
        JSONObject loginResult = executeSyncRequest("auth/login", "username=Nathaniel&password=password", "POST", null);

        // Assert
        Assert.assertNotNull(loginResult);
        Assert.assertEquals(AuthenticationEvent.TYPE.getName(), loginResult.get("_type"));
        Assert.assertTrue(loginResult.has("user"));
        Assert.assertTrue(loginResult.has("authenticationToken"));
        User user = new User(loginResult.getJSONObject("user"));
        Assert.assertEquals("Nathaniel", user.getFirstName());
        Assert.assertEquals("Aumonttt", user.getLastName());
    }

    @Test
    public void authenticateUser_badPassword_getNoUser() {
        // Action
        JSONObject loginResult = executeSyncRequest("auth/login", "username=Nathaniel&password=pssword", "POST", null);

        // Assert
        Assert.assertNotNull(loginResult);
        Assert.assertEquals(ErrorEvent.TYPE.getName(), loginResult.get("_type"));
        Assert.assertEquals("Username / password mismatch", loginResult.get("description"));
        Assert.assertFalse(loginResult.has("user"));
        Assert.assertFalse(loginResult.has("authenticationToken"));
    }

    @Test
    public void authenticateUser_badUsername_getNoUser() {
        // Action
        JSONObject loginResult = executeSyncRequest("auth/login", "username=Nahaniel&password=password", "POST", null);

        // Assert
        Assert.assertNotNull(loginResult);
        Assert.assertEquals(ErrorEvent.TYPE.getName(), loginResult.get("_type"));
        Assert.assertEquals("Username / password mismatch", loginResult.get("description"));
        Assert.assertFalse(loginResult.has("user"));
        Assert.assertFalse(loginResult.has("authenticationToken"));
    }

    @Test
    public void authenticateUser_badUsernameBadPassword_getNoUser() {
        // Action
        JSONObject loginResult = executeSyncRequest("auth/login", "username=Nahaniel&password=pasword", "POST", null);

        // Assert
        Assert.assertNotNull(loginResult);
        Assert.assertEquals(ErrorEvent.TYPE.getName(), loginResult.get("_type"));
        Assert.assertEquals("Username / password mismatch", loginResult.get("description"));
        Assert.assertFalse(loginResult.has("user"));
        Assert.assertFalse(loginResult.has("authenticationToken"));
    }

    @Test
    public void authenticateUser_spaceUsernameSpacePassword_getNoUser() {
        // Action
        JSONObject loginResult = executeSyncRequest("auth/login", "username= &password= ", "POST", null);

        // Assert
        Assert.assertNotNull(loginResult);
        Assert.assertEquals(ErrorEvent.TYPE.getName(), loginResult.get("_type"));
        Assert.assertEquals("Username / password mismatch", loginResult.get("description"));
        Assert.assertFalse(loginResult.has("user"));
        Assert.assertFalse(loginResult.has("authenticationToken"));
    }

    @Test
    public void authenticateUser_noUsernameNoPassword_getNoUser() {
        // Action
        JSONObject loginResult = executeSyncRequest("auth/login", "username=&password=", "POST", null);

        // Assert
        Assert.assertNotNull(loginResult);
        Assert.assertEquals(ErrorEvent.TYPE.getName(), loginResult.get("_type"));
        Assert.assertEquals("Username cannot be empty", loginResult.get("description"));
        Assert.assertFalse(loginResult.has("user"));
        Assert.assertFalse(loginResult.has("authenticationToken"));
    }

    @Test
    public void authenticateUser_NoPassword_getNoUser() {
        // Action
        JSONObject loginResult = executeSyncRequest("auth/login", "username=Nathaniel&password=", "POST", null);

        // Assert
        Assert.assertNotNull(loginResult);
        Assert.assertEquals(ErrorEvent.TYPE.getName(), loginResult.get("_type"));
        Assert.assertEquals("Password cannot be empty", loginResult.get("description"));
        Assert.assertFalse(loginResult.has("user"));
        Assert.assertFalse(loginResult.has("authenticationToken"));
    }

    @Test
    public void isAuthTokenValid_validToken_ok() {
        // Arrange
        String authToken = getAuthToken(TEST_USERNAME, TEST_PASSWORD);

        // Action
        JSONObject result = executeSyncRequest("auth/tokenValid", "", "POST", authToken);

        // Assert
        Assert.assertEquals(AuthenticationEvent.TYPE.getName(), result.get("_type"));
    }

    @Test
    public void logout_correctBehavior_userLoggedOut() {
        // Arrange
        String authToken = getAuthToken(TEST_USERNAME, TEST_PASSWORD);

        // Action
        JSONObject result = executeSyncRequest("auth/logout", null, "POST", authToken);

        // Assert
        Assert.assertEquals(LogoutEvent.TYPE.getName(), result.get("_type"));
    }

    @Test
    public void logout_noAuthToken_error() {
        // Arrange
        JSONObject login = executeSyncRequest("auth/login", "username=Nathaniel&password=password", "POST", null);

        // Action
        JSONObject logoutResult = executeSyncRequest("auth/logout", null, "POST", "");

        // Assert
        Assert.assertEquals(ErrorEvent.TYPE.getName(), logoutResult.get("_type"));
    }

    // Tests for UnitServlet

    @Test
    public void getUnits_correctBehavior_gotAllUnits() {
        // Arrange
        String authToken = getAuthToken(TEST_USERNAME, TEST_PASSWORD);

        // Action
        JSONObject result = executeSyncRequest("units", null, "GET", authToken);
        JSONArray unitsJsonArray = result.getJSONArray("units");

        // Assert
        Assert.assertEquals(FetchUnitsEvent.TYPE.getName(), result.get("_type"));
        Assert.assertEquals(unitsJsonArray.length() > 0, true);
    }

    @Test
    public void getUnits_noAuthToken_error() {
        // Arrange

        // Action
        JSONObject result = executeSyncRequest("units", null, "GET", "");

        // Assert
        Assert.assertEquals(ErrorEvent.TYPE.getName(), result.get("_type"));
        // TODO: Check for correct description, but need to put string in resources file before
    }

    @Test
    public void updateOrSaveUnit_newUnit_unitSaved() {
        // Arrange
        String authToken = getAuthToken(TEST_USERNAME, TEST_PASSWORD);

        Unit testUnit = new Unit();

        testUnit.setName("Test Unit " + (int) (Math.random() * 100));
        testUnit.setAvailableBeds(20);

        // Action
        JSONObject result = executeSyncRequest("units", "unit=" + testUnit.toString(), "PUT", authToken);

        Unit savedUnit = getSingleUnit(authToken, (int) result.get("objectId"));

        // Assert
        Assert.assertEquals(ActionCompleteEvent.TYPE.getName(), result.get("_type"));
        Assert.assertTrue(result.has("objectId"));
        Assert.assertEquals(result.get("action"), "update");
        Assert.assertEquals(result.get("objectType"), "unit");

        Assert.assertNotNull(savedUnit);
        Assert.assertEquals(savedUnit.getName(), testUnit.getName());
        Assert.assertEquals(savedUnit.getAvailableBeds(), testUnit.getAvailableBeds());
    }

    @Test
    public void updateOrSaveUnit_updateUnit_unitUpdated() {
        // Arrange
        String authToken = getAuthToken(TEST_USERNAME, TEST_PASSWORD);

        JSONObject unitsJSON = executeSyncRequest("units", null, "GET", authToken);

        JSONArray unitJsonArray = unitsJSON.getJSONArray("units");

        // Action
        Unit unit = new Unit(unitJsonArray.getJSONObject(0));
        unit.setName("Modified name");
        JSONObject result = executeSyncRequest("units", "unit=" + unit.toString(), "PUT", authToken);

        Unit savedUnit = getSingleUnit(authToken, unit.getId());

        // Assert
        Assert.assertEquals(ActionCompleteEvent.TYPE.getName(), result.get("_type"));
        Assert.assertEquals(result.get("objectId"), unit.getId());
        Assert.assertEquals(result.get("action"), "update");
        Assert.assertEquals(result.get("objectType"), "unit");

        Assert.assertNotNull(savedUnit);
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
        JSONObject result = executeSyncRequest("units", "unit=" + testUnit.toString(), "PUT", null);

        // Assert
        Assert.assertEquals(ErrorEvent.TYPE.getName(), result.get("_type"));
        // TODO: Check for correct description, but need to put string in resources file before
    }

    // TODO: Broken: Unit is not deleted. DELETE request returns an errorEvent
    @Test
    public void deleteUnit_unitExists_unitDeleted() {
        // Arrange
        String authToken = getAuthToken(TEST_USERNAME, TEST_PASSWORD);

        // Action
        int unitId = 3;
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
        String authToken = getAuthToken(TEST_USERNAME, TEST_PASSWORD);

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

    // Tests for UserServlet

    @Test
    public void getUsers_correctBehavior_gotAllUsers() {
        // Arrange
        String authToken = getAuthToken(TEST_USERNAME, TEST_PASSWORD);

        // Action
        JSONObject result = executeSyncRequest("users", null, "GET", authToken);
        JSONArray usersArray = result.getJSONArray("users");

        // Assert
        Assert.assertEquals(FetchUsersEvent.TYPE.getName(), result.get("_type"));
        Assert.assertTrue(usersArray.length() > 0);
    }

    @Test
    public void getUsers_noAuthToken_error() {
        // Arrange

        // Action
        JSONObject result = executeSyncRequest("users", null, "GET", null);

        // Assert
        Assert.assertEquals(ErrorEvent.TYPE.getName(), result.get("_type"));
    }

    @Test
    public void getUser_correctBehavior_getUser() {
        // Arrange
        String authToken = getAuthToken(TEST_USERNAME, TEST_PASSWORD);

        // Action
        JSONObject userJson = executeSyncRequest("users/" + TEST_USERNAME, null, "GET", authToken);

        // Assert
        Assert.assertEquals(FetchUserEvent.TYPE.getName(), userJson.get("_type"));
        User user = new User(userJson.getJSONObject("user"));
        Assert.assertEquals(user.getUsername(), TEST_USERNAME);
    }

    @Test
    public void getUser_userNotFound_error() {
        // Arrange
        String authToken = getAuthToken(TEST_USERNAME, TEST_PASSWORD);

        // Action
        JSONObject userJson = executeSyncRequest("users/" + "notauser", null, "GET", authToken);

        // Assert
        Assert.assertEquals(ErrorEvent.TYPE.getName(), userJson.get("_type"));
    }

    @Test
    public void getUser_noAuthToken_error() {
        // Arrange

        // Action
        JSONObject userJson = executeSyncRequest("users/" + TEST_USERNAME, null, "GET", "");

        // Assert
        Assert.assertEquals(ErrorEvent.TYPE.getName(), userJson.get("_type"));
    }

    // TODO: Broken: User is not added in DB
    @Test
    public void updateOrSaveUser_newUser_userSaved() {
        // Arrange
        String authToken = getAuthToken(TEST_USERNAME, TEST_PASSWORD);

        User testUser = new User();
        testUser.setUsername("test" + (int) (Math.random() * 100));
        testUser.setFirstName("Test1232");
        testUser.setLastName("Test2");

        // Action
        JSONObject object2 = executeSyncRequest("users", "password=password&user=" + testUser.toString(), "POST", authToken);
        // Parse and get list of units

        // Assert
        Assert.assertEquals(ActionCompleteEvent.TYPE.getName(), object2.get("_type"));
        JSONObject savedUserJson = executeSyncRequest("users/" + testUser.getUsername(), null, "GET", authToken);
        Assert.assertEquals(FetchUserEvent.TYPE.getName(), savedUserJson.get("_type"));
    }

    // TODO: Broken
    @Test
    public void updateOrSaveUser_updateUser_userUpdated() {
        // Arrange
        String authToken = getAuthToken(TEST_USERNAME, TEST_PASSWORD);

        JSONObject object2 = executeSyncRequest("users", null, "GET", authToken);

        JSONArray userJsonArray = object2.getJSONArray("users");

        // Action
        User user = new User(userJsonArray.getJSONObject(0));
        user.setFirstName("Modified name");
        JSONObject result = executeSyncRequest("users", user.toString(), "PUT", authToken);

        // Assert
        Assert.assertEquals(ActionCompleteEvent.TYPE.getName(), result.get("_type"));
        JSONObject savedUserJson = executeSyncRequest("users/" + user.getUsername(), null, "GET", authToken);
        User savedUser = new User(savedUserJson.getJSONObject("user"));
        Assert.assertEquals(FetchUserEvent.TYPE.getName(), savedUserJson.get("_type"));
        Assert.assertEquals(user.getFirstName(), savedUser.getFirstName());
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
        String authToken = getAuthToken(TEST_USERNAME, TEST_PASSWORD);

        // Action
        int userId = 2;
        JSONObject result = executeSyncRequest("users/" + userId, null, "DELETE", authToken);

        // Assert
        Assert.assertEquals(ActionCompleteEvent.TYPE.getName(), result.get("_type"));

        JSONObject listOfUsersJson = executeSyncRequest("users", null, "GET", authToken);
        JSONArray usersJsonArray = listOfUsersJson.getJSONArray("users");
        boolean userFound = false;
        for (int i = 0; i < usersJsonArray.length(); i++) {
            User user = new User(usersJsonArray.getJSONObject(i));
            if (user.getId() == userId) {
                userFound = true;
                break;
            }
        }
        Assert.assertFalse(userFound);
    }

    @Test
    public void deleteUser_userNotFound_errorMessage() {
        // Arrange
        String authToken = getAuthToken(TEST_USERNAME, TEST_PASSWORD);

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

    // Tests for ActionServlet

    @Test
    public void getActions_correctBehavior_getAllActions() {
        // Arrange
        String authToken = getAuthToken(TEST_USERNAME, TEST_PASSWORD);

        // Action
        JSONObject result = executeSyncRequest("actions", null, "GET", authToken);

        // Assert
        Assert.assertEquals(FetchActionsEvent.TYPE.getName(), result.get("_type"));

        JSONArray arrayOfActions = result.getJSONArray("actions");
        Assert.assertTrue(arrayOfActions.length() > 0);
    }

     @Test
     public void getActions_noAuthToken_error() {
         // Arrange

         // Action
         JSONObject result = executeSyncRequest("actions", null, "GET", "");

         // Assert
         Assert.assertEquals(ErrorEvent.TYPE.getName(), result.get("_type"));
     }

    @Test
    public void getAction_actionFound_getAction() {
        // Arrange
        String authToken = getAuthToken(TEST_USERNAME, TEST_PASSWORD);
        int actionId = 1;

        // Action
        JSONObject result = executeSyncRequest("actions/" + actionId, null, "GET", authToken);

        // Assert
        Assert.assertEquals(FetchActionEvent.TYPE.getName(), result.get("_type"));
        Action action = new Action(result.getJSONObject("action"));
        Assert.assertEquals(actionId, action.getId());
    }

    @Test
    public void getAction_actionNotFound_error() {
        // Arrange
        String authToken = getAuthToken(TEST_USERNAME, TEST_PASSWORD);
        int actionId = 99999;

        // Action
        JSONObject result = executeSyncRequest("actions/" + actionId, null, "GET", authToken);

        // Assert
        Assert.assertEquals(ErrorEvent.TYPE.getName(), result.get("_type"));
        Assert.assertEquals("Id " + actionId + " doesn't exist", result.get("description"));
    }

    @Test
    public void getAction_noAuthToken_error() {
        // Arrange
        int actionId = 1;

        // Action
        JSONObject result = executeSyncRequest("actions/" + actionId, null, "GET", "");

        // Assert
        Assert.assertEquals(ErrorEvent.TYPE.getName(), result.get("_type"));
    }

    @Test
    public void updateOrSaveActions_newAction_actionSaved() {
        // Arrange
        String authToken = getAuthToken(TEST_USERNAME, TEST_PASSWORD);
        Action newAction = new Action();
        newAction.setTask(Action.Task.pushForDischarge);
        newAction.setDescription("TESTESTSET");
        newAction.setStatus(Action.Status.inProgress);
        User user = new User(executeSyncRequest("users/" + TEST_USERNAME, null, "GET", authToken).getJSONObject("user"));
        newAction.setPersonResponsible(user);
        Unit unit1 = getSingleUnit(authToken, 3);
        newAction.setUnit(unit1);
        Date now = new Date();
        newAction.setLastUpdate(now);

        // Action
        JSONObject saveResult = executeSyncRequest("actions", "action=" + newAction.toString(), "PUT", authToken);

        // Assert
        Assert.assertEquals(ActionCompleteEvent.TYPE.getName(), saveResult.get("_type"));
        Assert.assertEquals("action", saveResult.get("objectType"));
        JSONObject actionJson = executeSyncRequest("actions/" + saveResult.get("objectId"), null, "GET", authToken);
        Action savedAction = new Action(actionJson.getJSONObject("action"));
        Assert.assertEquals(Action.Task.pushForDischarge, savedAction.getTask());
        Assert.assertEquals("TESTESTSET", savedAction.getDescription());
        Assert.assertEquals(Action.Status.inProgress, savedAction.getStatus());
        Assert.assertEquals(user.getId(), savedAction.getPersonResponsible().getId());
        Assert.assertEquals(unit1, savedAction.getUnit());
        //Assert.assertEquals(now.getTime(), savedAction.getLastUpdate().getTime());
    }

    @Test
    public void updateOrSaveAction_updateAction_actionUpdated() {
        // Arrange
        String authToken = getAuthToken(TEST_USERNAME, TEST_PASSWORD);
        int actionId = 1;
        Action action = new Action(executeSyncRequest("actions/" + actionId, null, "GET", authToken).getJSONObject("action"));
        action.setDescription("TEST");

        // Action
        JSONObject result = executeSyncRequest("actions", "action=" + action.toString(), "PUT", authToken);

        // Assert
        Assert.assertEquals(ActionCompleteEvent.TYPE.getName(), result.get("_type"));
        Assert.assertEquals("action", result.get("objectType"));

        Action updatedAction = new Action(executeSyncRequest("actions/" + actionId, null, "GET", authToken).getJSONObject("action"));
        Assert.assertEquals("TEST", updatedAction.getDescription());
    }

    @Test
    public void updateOrSaveAction_noAuthToken_error() {
        // Arrange
        String authToken = getAuthToken(TEST_USERNAME, TEST_PASSWORD);
        Action newAction = new Action();
        newAction.setTask(Action.Task.pushForDischarge);
        newAction.setDescription("TESTESTSET");
        newAction.setStatus(Action.Status.inProgress);
        User user = new User(executeSyncRequest("users/" + TEST_USERNAME, null, "GET", authToken).getJSONObject("user"));
        newAction.setPersonResponsible(user);
        Unit unit1 = getSingleUnit(authToken, 3);
        newAction.setUnit(unit1);
        Date now = new Date();
        newAction.setLastUpdate(now);

        // Action
        JSONObject saveResult = executeSyncRequest("actions", "action=" + newAction.toString(), "PUT", "");

        // Assert
        Assert.assertEquals(ErrorEvent.TYPE.getName(), saveResult.get("_type"));
    }

    @Test
    public void deleteAction_actionFound_actionDeleted() {
        // Arrange
        String authToken = getAuthToken(TEST_USERNAME, TEST_PASSWORD);
        int actionId = 5;

        // Action
        JSONObject result = executeSyncRequest("actions/" + actionId, null, "DELETE", authToken);

        // Assert
        Assert.assertEquals(ActionCompleteEvent.TYPE.getName(), result.get("_type"));
        Assert.assertEquals(actionId, (int) result.get("objectId"));
        Assert.assertEquals("action", result.get("objectType"));

        JSONObject resultSearch = executeSyncRequest("actions/" + actionId, null, "GET", authToken);
        Assert.assertEquals(ErrorEvent.TYPE.getName(), resultSearch.get("_type"));
        Assert.assertEquals("Id " + actionId + " doesn't exist", resultSearch.get("description"));
    }

    @Test
    public void deleteAction_actionNotFound_error() {
        // Arrange
        String authToken = getAuthToken(TEST_USERNAME, TEST_PASSWORD);
        int actionId = 99999;

        // Action
        JSONObject result = executeSyncRequest("actions/" + actionId, null, "DELETE", authToken);

        // Assert
        Assert.assertEquals(ErrorEvent.TYPE.getName(), result.get("_type"));
        Assert.assertEquals("No row with the given identifier exists: [rtdc.core.model.Action#" + actionId + "]", result.get("description"));
    }

    @Test
    public void deleteAction_noAuthToken_error() {
        // Arrange
        int actionId = 99999;

        // Action
        JSONObject result =executeSyncRequest("actions/" + actionId, null, "DELETE", "");

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

    // Does not happen when debugging the server and stepping through the getUnit method in UnitServlet ??
    private static Unit getSingleUnit(String authToken, int id) {
        JSONObject unitJson = executeSyncRequest("units/" + id, null, "GET", authToken);
        if (unitJson.get("_type") == ErrorEvent.TYPE.getName()) {
            return null;
        }
        JSONArray unitJsonArray = unitJson.getJSONArray("units");
        Unit unit = new Unit(unitJsonArray.getJSONObject(0));
        return unit;
    }

    private String getAuthToken(String username, String password) {
        JSONObject object = executeSyncRequest("auth/login", "username=" + username + "&password=" + password, "POST", null);
        return object.get("authenticationToken").toString();
    }
}