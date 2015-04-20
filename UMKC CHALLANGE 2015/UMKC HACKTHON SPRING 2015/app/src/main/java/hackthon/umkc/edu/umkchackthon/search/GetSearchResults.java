package hackthon.umkc.edu.umkchackthon.search;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import hackthon.umkc.edu.umkchackthon.YoutubeAdapter;
import hackthon.umkc.edu.umkchackthon.oauth.Auth;
import hackthon.umkc.edu.umkchackthon.oauth.Constants;

/**
 * Created by praveen on 4/17/2015.
 */
public class GetSearchResults extends AsyncTask {
    private final ListView mListView;
    private final Context mContext;
    String queryTerm="india";
    String appName = Constants.APP_NAME;
    private static final long NUMBER_OF_VIDEOS_RETURNED = 25;
    private static YouTube youtube;
    ProgressDialog pd;
    private static final String PROPERTIES_FILENAME = "youtube.properties";
    private List<SearchResult> searchResultList;


    public GetSearchResults(Context context, ListView listView,String query) {
        mContext=context;
        mListView=listView ;
        queryTerm=query;
        pd = new ProgressDialog(mContext);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd.setMessage("Loading....");
//        pd.show();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Properties properties = new Properties();
        /*try {
            InputStream in = GetSearchResults.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
            properties.load(in);

        } catch (IOException e) {
            System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause()
                    + " : " + e.getMessage());
            System.exit(1);
        }*/
        youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() {
            public void initialize(HttpRequest request) throws IOException {
            }
        }).setApplicationName(appName).build();
        try {
            YouTube.Search.List search = youtube.search().list("id,snippet");
            String apiKey = Constants.API_KEY;
            search.setKey(apiKey);
            search.setQ(queryTerm);
            search.setType("video");

            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            SearchListResponse searchResponse = search.execute();
            searchResultList = searchResponse.getItems();
            Log.v("Search Respose",searchResponse.toString());

//            if (searchResultList != null) {
//                prettyPrint(searchResultList.iterator(), queryTerm);
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

//    private static void prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query) {
//
//        System.out.println("\n=============================================================");
//        System.out.println(
//                "   First " + NUMBER_OF_VIDEOS_RETURNED + " videos for search on \"" + query + "\".");
//        System.out.println("=============================================================\n");
//
//        if (!iteratorSearchResults.hasNext()) {
//            System.out.println(" There aren't any results for your query.");
//        }
//
//        while (iteratorSearchResults.hasNext()) {
//
//            SearchResult singleVideo = iteratorSearchResults.next();
//            ResourceId rId = singleVideo.getId();
//
//            // Confirm that the result represents a video. Otherwise, the
//            // item will not contain a video ID.
//            if (rId.getKind().equals("youtube#video")) {
//                Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();
//
//                System.out.println(" Video Id -- " + rId.getVideoId());
//                System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
//                System.out.println(" Thumbnail: " + thumbnail.getUrl());
//                System.out.println("\n-------------------------------------------------------------\n");
//            }
//        }
//    }

    @Override
    protected void onPostExecute(Object o) {
        pd.dismiss();
        super.onPostExecute(o);
        mListView.setAdapter(new YoutubeAdapter(mContext,searchResultList));
    }
}
