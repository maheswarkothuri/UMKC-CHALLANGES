package hackthon.umkc.edu.umkchackthon.search;

import android.os.AsyncTask;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.GuideCategoryListResponse;

import java.io.IOException;
import java.util.List;

import hackthon.umkc.edu.umkchackthon.oauth.Auth;
import hackthon.umkc.edu.umkchackthon.oauth.Constants;


/**
 * Created by praveen on 4/18/2015.
 */
public class GetChannelsList extends AsyncTask {
    String appName = Constants.APP_NAME;
    private static final long NUMBER_OF_VIDEOS_RETURNED = 25;
    private static YouTube youtube;
    public List<Channel> channelList;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Object doInBackground(Object[] objects) {
        youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() {
            public void initialize(HttpRequest request) throws IOException {
            }
        }).setApplicationName(appName).build();
        try {
            YouTube.Channels.List channels = youtube.channels().list("snippet,id");
            String apiKey = Constants.API_KEY;
            channels.setKey(apiKey);
            channels.setCategoryId("GCQmVzdCBvZiBZb3VUdWJl");
            ChannelListResponse channelsResponse = channels.execute();
            channelList = channelsResponse.getItems();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
