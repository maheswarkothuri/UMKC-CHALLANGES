package hackthon.umkc.edu.umkchackthon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.api.services.youtube.model.SearchResult;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by maheswarkothuri on 4/17/2015.
 */
public class YoutubeAdapter extends BaseAdapter {

    private static final String TAG = "YoutubeAdapter";
    Context mContext;
    Holder holder;
    private LayoutInflater mInflater;
    List<SearchResult> mSearchResults;
    public YoutubeAdapter(Context context, List<SearchResult> searchResultList) {
        Log.v(TAG,"YoutubeAdapter Constructor");
        mContext = context;
        mSearchResults=searchResultList;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class Holder{
        ImageView thumbnail;
        TextView title;
        TextView desc;
        String video_Id;
        URL url;
    }
    @Override
    public int getCount() {
        Log.v(TAG,"getCount");
        return mSearchResults.size();
    }

    @Override
    public Object getItem(int position) {

        Log.v(TAG,"getItem");
        return position;
    }

    @Override
    public long getItemId(int position) {
        Log.v(TAG,"getItemId");
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v(TAG,"getView");
        View view = convertView;
        if(convertView ==null)
        {
            view = mInflater.inflate(R.layout.row, null);
            holder = new Holder();
            holder.thumbnail = (ImageView) view.findViewById(R.id.imageView);
            holder.title = (TextView) view.findViewById(R.id.title);
//            holder.desc = (TextView) view.findViewById(R.id.description);
            view.setTag(holder);
        }
        else
        {
            holder = (Holder) convertView.getTag();
        }
        holder.title.setText(mSearchResults.get(position).getSnippet().getTitle());
//        holder.desc.setText(mSearchResults.get(position).getSnippet().getDescription());
        holder.video_Id = mSearchResults.get(position).getId().getVideoId();//"https://www.youtube.com/watch?v="
        try {
            holder.url=new URL( mSearchResults.get(position).getSnippet().getThumbnails().getDefault().getUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        new ImageLoadTask(holder.url, holder.thumbnail,mContext).execute();
        return view;
        }
}