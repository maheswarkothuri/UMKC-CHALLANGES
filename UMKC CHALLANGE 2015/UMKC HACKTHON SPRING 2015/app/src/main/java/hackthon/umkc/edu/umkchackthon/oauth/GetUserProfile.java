package hackthon.umkc.edu.umkchackthon.oauth;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by praveen on 4/18/2015.
 */
public class GetUserProfile extends AsyncTask {
    String accessToken;
    public GetUserProfile(String AccessToken){
        accessToken = AccessToken;
    }
    @Override
    protected String doInBackground(Object[] objects) {
        URL obj = null;
        String response = "";
        try {
            obj = new URL("https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token="+accessToken);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response = response+inputLine;
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
