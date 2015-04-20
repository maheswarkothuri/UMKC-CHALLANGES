package hackthon.umkc.edu.umkchackthon.search;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.GuideCategory;
import com.google.api.services.youtube.model.GuideCategoryListResponse;

import java.io.IOException;
import java.util.List;

import hackthon.umkc.edu.umkchackthon.oauth.Auth;
import hackthon.umkc.edu.umkchackthon.oauth.Constants;


/**
 * Created by praveen on 4/18/2015.
 */
public class GetCategoriesList extends AsyncTask {
    String appName = Constants.APP_NAME;
    private static final long NUMBER_OF_VIDEOS_RETURNED = 25;
    private static YouTube youtube;
    public List<GuideCategory> categoriesResponseList;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected List<GuideCategory> doInBackground(Object[] objects) {
        youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() {
            public void initialize(HttpRequest request) throws IOException {
            }
        }).setApplicationName(appName).build();
        try {
            YouTube.GuideCategories.List guideCategories = youtube.guideCategories().list("snippet");
            String apiKey = Constants.API_KEY;
            guideCategories.setKey(apiKey);
            guideCategories.setRegionCode("US");
            GuideCategoryListResponse categoriesResponse = guideCategories.execute();
            categoriesResponseList = categoriesResponse.getItems();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return categoriesResponseList;
    }
}
