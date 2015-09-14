package rtdc.web.test;

import org.junit.Assert;
import org.junit.Test;
import rtdc.core.Config;
import rtdc.core.json.JSONObject;
import rtdc.core.model.User;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServiceTest {

    private static final String _url = "http://"+ Config.SERVER_IP+":8888/api/";
    private static final String USER_AGENT = "Mozilla/5.0";

    @Test
    public void authenticateUser_existingUser_getUserPlusAuthToken() {
        // Action
        JSONObject object = executeSyncRequest("authenticate", "username=Nathaniel&password=password", "POST", null);

        // Assert
        Assert.assertNotNull(object);
        Assert.assertEquals("authenticationEvent", object.get("_type").toString());
        Assert.assertTrue(object.has("user"));
        Assert.assertTrue(object.has("authenticationToken"));
        User user = new User((JSONObject)object.get("user"));
        Assert.assertEquals("Nathaniel", user.getFirstName());
        Assert.assertEquals("Aumonttt", user.getLastName());
        Assert.assertEquals(22, user.getId());
    }

    @Test
    public void authenticateUser_badPassword_getNoUser() {
        // Action
        JSONObject object = executeSyncRequest("authenticate", "username=Nathaniel&password=pssword", "POST", null);

        // Assert
        Assert.assertNotNull(object);
        Assert.assertEquals("errorEvent", object.get("_type").toString());
        Assert.assertEquals("Username / password mismatch", object.get("description"));
        Assert.assertFalse(object.has("user"));
        Assert.assertFalse(object.has("authenticationToken"));
    }

    @Test
    public void authenticateUser_badUsername_getNoUser() {
        // Action
        JSONObject object = executeSyncRequest("authenticate", "username=Nahaniel&password=password", "POST", null);

        // Assert
        Assert.assertNotNull(object);
        Assert.assertEquals("errorEvent",object.get("_type").toString());
        Assert.assertEquals("Username / password mismatch", object.get("description"));
        Assert.assertFalse(object.has("user"));
        Assert.assertFalse(object.has("authenticationToken"));
    }

    @Test
    public void authenticateUser_badUsernameBadPassword_getNoUser() {
        // Action
        JSONObject object = executeSyncRequest("authenticate", "username=Nahaniel&password=pasword", "POST", null);

        // Assert
        Assert.assertNotNull(object);
        Assert.assertEquals("errorEvent",object.get("_type").toString());
        Assert.assertEquals("Username / password mismatch", object.get("description"));
        Assert.assertFalse(object.has("user"));
        Assert.assertFalse(object.has("authenticationToken"));
    }

    @Test
    public void authenticateUser_spaceUsernameSpacePassword_getNoUser() {
        // Action
        JSONObject object = executeSyncRequest("authenticate", "username= &password= ", "POST", null);

        // Assert
        Assert.assertNotNull(object);
        Assert.assertEquals("errorEvent",object.get("_type").toString());
        Assert.assertEquals("Username / password mismatch", object.get("description"));
        Assert.assertFalse(object.has("user"));
        Assert.assertFalse(object.has("authenticationToken"));
    }

    @Test
    public void authenticateUser_noUsernameNoPassword_getNoUser() {
        // Action
        JSONObject object = executeSyncRequest("authenticate", "username=&password=", "POST", null);

        // Assert
        Assert.assertNotNull(object);
        Assert.assertEquals("errorEvent",object.get("_type").toString());
        Assert.assertEquals("Username cannot be empty", object.get("description"));
        Assert.assertFalse(object.has("user"));
        Assert.assertFalse(object.has("authenticationToken"));
    }

    @Test
    public void authenticateUser_NoPassword_getNoUser() {
        // Action
        JSONObject object = executeSyncRequest("authenticate", "username=Nathaniel&password=", "POST", null);

        // Assert
        Assert.assertNotNull(object);
        Assert.assertEquals("errorEvent",object.get("_type").toString());
        Assert.assertEquals("Password cannot be empty", object.get("description"));
        Assert.assertFalse(object.has("user"));
        Assert.assertFalse(object.has("authenticationToken"));
    }

    @Test
    public void isAuthTokenValid_validToken_ok() {
        // Arrange
        JSONObject object = executeSyncRequest("authenticate", "username=Nathaniel&password=password", "POST", null);
        String authToken = object.get("authenticationToken").toString();

        // Action
        JSONObject object2 = executeSyncRequest("authenticate/tokenValid", "", "POST", authToken);

        // Assert
        Assert.assertEquals("authenticationEvent", object.get("_type").toString());
    }

    @Test
    public void logout_correctBehavior_userLoggedOut() {
        // Arrange
        JSONObject object = executeSyncRequest("authenticate", "username=Nathaniel&password=password", "POST", null);
        String authToken = object.get("authenticationToken").toString();

        // Action
        JSONObject object2 = executeSyncRequest("authenticate/logout", "", "POST", authToken);

        // Assert
        Assert.assertEquals("sessionExpiredEvent",object.get("_type").toString()); // ??
    }

    @Test
    public void getUnits_correctBehavior_gotAllUnits() {
        // Arrange
        JSONObject object = executeSyncRequest("authenticate", "username=Nathaniel&password=password", "POST", null);
        String authToken = object.get("authenticationToken").toString();

        // Action
        JSONObject object2 = executeSyncRequest("units", "", "GET", authToken);


    }

    private static JSONObject executeSyncRequest(String service, String urlParameters, String requestMethod, String authToken) {
        try  {
            URL urlObj = new URL(_url + service);
            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

            con.setRequestMethod(requestMethod);
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-us,en;q=0.5");
            if (authToken != null) {
                con.setRequestProperty("auth_token", authToken);
            }

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();

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