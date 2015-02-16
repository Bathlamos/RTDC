package rtdc.web.server.util;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

public class Util {

    public static String getHttpRequestData(HttpServletRequest req){
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jb.toString();
    }

}
