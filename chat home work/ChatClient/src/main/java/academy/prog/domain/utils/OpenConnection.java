package academy.prog.domain.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenConnection {
    public static HttpURLConnection openConnection(String endPoint,String methodType,boolean doOutput) {
        HttpURLConnection conn = null;
        try {
            URL obj = new URL(Utils.getURL() + endPoint);
             conn = (HttpURLConnection) obj.openConnection();

            conn.setRequestMethod(methodType);
            conn.setDoOutput(doOutput);

        }

        catch (IOException e) {
            e.printStackTrace();
        }

        return conn;
    }

}
