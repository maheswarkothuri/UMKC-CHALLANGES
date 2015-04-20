package hackthon.umkc.edu.umkchackthon;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.api.services.youtubeAnalytics.YouTubeAnalytics;

import java.util.List;

import hackthon.umkc.edu.umkchackthon.oauth.Constants;
import hackthon.umkc.edu.umkchackthon.search.GetSearchResults;
import hackthon.umkc.edu.umkchackthon.search.sppechtotext.VoicetoText;

public class SearchFragment extends Fragment implements TextToSpeech.OnInitListener {
    private Button login;
    private String TAG="MainActivity";
    private ListView listView;
    private SearchView search;
    private String query_String;
    private Button network;
    private TextView text;
    private static final int REQ_OAUTH = 123;
    private Button voice;
    private Context mContext;
    public SearchFragment() {

    }

    public SearchFragment(Context context) {
        mContext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.searchview, container, false);
        search = (SearchView)rootView. findViewById(R.id.searchView);
        network = (Button) rootView.findViewById(R.id.network);
        text = (TextView) rootView.findViewById(R.id.text);
        listView = (ListView)rootView.findViewById(R.id.listView2);
        voice = (Button)rootView.findViewById(R.id.speak);

       voice.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Log.v(TAG,"voice setOnClickListener");
               VoicetoText vt= new VoicetoText(mContext);
               vt.voiceToText();
               Constants.speak(mContext,"Loading ");

           }
       });
        return rootView;
    }
    public void analyzeVoiceResults(List<String> matches) {
        android.util.Log.v("ListResults",matches.toString());
        query_String=matches.get(0);
        search.setIconified(false);
        search.setQuery(query_String,true);
    }
    @Override
    public void onResume() {
            super.onResume();
        YouTubeAnalytics ds;
            if(new Constants(getActivity().getApplication()).isOnline()){
                android.util.Log.v(TAG,"isOnline");
                search.setQueryHint("Start typing to search...");

                search.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

                    @Override
                    public boolean onQueryTextSubmit(String query) { // This method called after submetting the query text
                        android.util.Log.v(TAG,"onQueryTextSubmit"+query);
                        query_String=query;
                    YouTubeAnalytics sd;
//                        new Runnable() {
//                            @Override
//                            public void run() {
                        Constants.speak(mContext,"Query submitted");
                        new GetSearchResults(getActivity().getApplicationContext(),listView,query_String).execute(); // Retrive  data and Display in the ListView.
//                            }
//                        }.run();
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        android.util.Log.v(TAG,"onQueryTextChange");
                        return false;
                    }
                });
                    new GetSearchResults(mContext,listView,query_String).execute();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Constants.speak(mContext,"Playing youtube vedio");
                        YoutubeAdapter.Holder holder = (YoutubeAdapter.Holder) view.getTag();

                        Intent intent = YouTubeStandalonePlayer.createVideoIntent((TabAcitivity)mContext, Constants.DEVELOPER_KEY, holder.video_Id);
                        startActivity(intent);


                        android.util.Log.v(TAG,"onItemClick  "+holder.video_Id);
//                String videoUrl = "http://someurl/video.mp4";
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(holder.video_Id));
//                startActivity(i);
                    }
                });
                network.setVisibility(View.GONE);
                text.setVisibility(View.GONE);
            }
            else{
                android.util.Log.v(TAG,"else onresume");
                network.setVisibility(View.VISIBLE);
                text.setVisibility(View.VISIBLE);
                voice.setVisibility(View.INVISIBLE);
                search.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
                network.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
                    }
                });
            }
        }
    
    private void startAuthorisationForYouTube() {
        Intent intent = new Intent(getActivity().getApplicationContext(), OAuthActivity.class);
        startActivityForResult(intent, REQ_OAUTH);
    }

    @Override
    public void onInit(int status) {

    }
}
