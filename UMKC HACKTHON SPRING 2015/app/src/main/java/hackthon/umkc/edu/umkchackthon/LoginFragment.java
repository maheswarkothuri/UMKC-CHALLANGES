package hackthon.umkc.edu.umkchackthon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import hackthon.umkc.edu.umkchackthon.domain.Tokens;
import hackthon.umkc.edu.umkchackthon.oauth.Constants;
import hackthon.umkc.edu.umkchackthon.oauth.GetUserProfile;
import hackthon.umkc.edu.umkchackthon.util.Log;

public class LoginFragment extends Fragment implements TextToSpeech.OnInitListener {
    private static final String TAG = "LoginFragment";
    private static final int REQ_OAUTH = 123;
    private Button network;
    private TextView text;
    private TextView mailName;
    private TextView gender;
    private TextView locale;
    private ImageView profPic;
    private Button login;
    boolean isAuth=false;
    Intent mData;
    private Context mContext;//=getActivity().getApplicationContext();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mContext=getActivity().getApplicationContext();
        View rootView = inflater.inflate(R.layout.login, container, false);
        network = (Button)rootView.findViewById(R.id.netcheck);
        text = (TextView) rootView.findViewById(R.id.displaytext);
        profPic=(ImageView)rootView.findViewById(R.id.profilepic);
        network = (Button)rootView.findViewById(R.id.netcheck);
        login = (Button)rootView.findViewById(R.id.login_login);
        mailName = (TextView)rootView.findViewById(R.id.mailname);
        gender = (TextView)rootView.findViewById(R.id.gender);
        locale = (TextView)rootView.findViewById(R.id.locale);
        network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mData!=null) {
            dealWithResult(mData);
//            login.setVisibility(View.INVISIBLE);
        }
        if(new Constants(getActivity().getApplication()).isOnline()){

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startAuthorisationForYouTube();
                    Constants.speak(mContext,"Loading ");
                }
            });
            login.setVisibility(View.VISIBLE);
            network.setVisibility(View.GONE);
            text.setVisibility(View.GONE);


        }
        else
        {

//            login.setVisibility(View.INVISIBLE);
            network.setVisibility(View.VISIBLE);
            text.setVisibility(View.VISIBLE);
        }
    }
    private void startAuthorisationForYouTube() {
        Intent intent = new Intent(mContext, OAuthActivity.class);
        startActivityForResult(intent, REQ_OAUTH);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        android.util.Log.v(TAG,"onActivityResult"+requestCode+"  result code"+resultCode);
        if(requestCode == REQ_OAUTH){
            if(resultCode == getActivity().RESULT_OK){
                dealWithResult(data);
            } else
            if(resultCode == getActivity().RESULT_CANCELED){
                startRefusalActivity();
            }
        }
    }
    private void dealWithResult(Intent data) {
        {
            mData=data;
            isAuth = true;
            Constants.speak(mContext,"Successfully Authenticated");
            Tokens tokens = (Tokens) data.getSerializableExtra(OAuthActivity.EXTRA_TOKENS);
            Toast.makeText(mContext, mContext.getResources().getString(R.string.token), Toast.LENGTH_SHORT).show();
//            login.setVisibility(View.INVISIBLE);
            try {
               String response =(String) new GetUserProfile(tokens.getAccessToken()).execute().get();
                System.out.println(response);
                JSONObject responseJson= new JSONObject(response);
                new ImageLoadTask(new URL(responseJson.getString("picture")),profPic,mContext).execute();
                login.setText("Log out");
                mailName.setText("Name:"+responseJson.getString("name"));
                if(!responseJson.has("gender") )
                {
                    gender.setText("\nGender:"+"Male");
                }else {
                    gender.setText(responseJson.getString("gender"));
                }if(!responseJson.has("locale")) {
                    locale.setText("USA");
                }else{
                    locale.setText("Loc:"+responseJson.getString("locale"));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        logResult(tokens);
//        invalidateOptionsMenu();
//        finish();
//
//        Intent i = new Intent(getApplicationContext(),SearchViewActivity.class);
//        startActivity(i);
        }
    }
    private static void logResult(Tokens tokens) {
        Log.i("Got access token: " + tokens.getAccessToken());
        Log.i("Got refresh token: "+ tokens.getRefreshToken());
        Log.i("Got token type: "+ tokens.getTokenType());
        Log.i("Got expires in: " + tokens.getExpiresIn());
    }
    private void startRefusalActivity() {
        Constants.speak(mContext,"Authentication Refused");
        Intent intent = new Intent(mContext, RefusedAuthActivity.class);
        startActivity(intent);
    }

    @Override
    public void onInit(int status) {

    }
}
