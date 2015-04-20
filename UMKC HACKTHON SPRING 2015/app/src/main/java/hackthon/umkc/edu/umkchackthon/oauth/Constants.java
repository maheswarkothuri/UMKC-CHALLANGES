package hackthon.umkc.edu.umkchackthon.oauth;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.speech.tts.TextToSpeech;

/**
 *
 * @author paul.blundell
 *
 */
public class Constants {

    private final Context mContext;
    static TextToSpeech tts;

    public Constants(Context ct)
    {
        mContext = ct;
    }
	// Client ID from https://code.google.com/apis/console API Access
	public static final String CLIENT_ID = "1021743452980-6hghhp872r59pabeapioi47pacsvskeu.apps.googleusercontent.com";
	// Callback URL from https://code.google.com/apis/console API Access
	public static final String CALLBACK_URL = "http://localhost";
    public static final String DEVELOPER_KEY ="AI39si4526j8H5ub8QF6FTY11nDOorBaaQGz47QYmN2TgnMTm_rT25y369S_4nc3QaIJRlKmDEJFjrc18LMmltOhJ6j7ea3f5w";
    public static final String API_KEY = "AIzaSyD1mCPARKE7_7HH6w3GFbyO_zUgB45-sXw";
    public static final String APP_NAME = "UMMKCHackthon";
    public static final String OAUTH_URL =
										"https://accounts.google.com/o/oauth2/auth?" +
										"client_id=" + CLIENT_ID + "&" +
										"redirect_uri=" + CALLBACK_URL + "&" +
										"scope=https://gdata.youtube.com https://www.googleapis.com/auth/userinfo.profile&" +
										"response_type=code&" +
										"access_type=offline";

	public static final String AUTH_CODE_PARAM = "?code=";
	// This is the url used to exchange your auth code for an access token
	public static final String TOKENS_URL = "https://accounts.google.com/o/oauth2/token";
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager)mContext. getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

public static void speak(Context context,String text)
    {
        if(tts==null)
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

            }
        });
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

}
