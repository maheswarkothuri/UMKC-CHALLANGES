package hackthon.umkc.edu.umkchackthon;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

/**
 * Created by maheswarkothuri on 4/18/2015.
 */
public class ImageLoadTask extends AsyncTask {


    private static final String TAG = "ImageLoadTask";
    private final Context mContext;
    URL url;
    private ImageView imageView;
    ProgressDialog pd;
    public ImageLoadTask(URL url, ImageView imageView,Context context) {
        Log.v(TAG, "ImageLoadTask");
        this.url = url;
        mContext=context;
        this.imageView = imageView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(mContext);
        pd.setMessage("Loading....");
//        pd.show();
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        Log.v(TAG,"onPostExecute");
        imageView.setImageBitmap((Bitmap) result);
        pd.dismiss();
    }

    @Override
    protected Bitmap doInBackground(Object[] params) {
        Log.v(TAG,"doInBackground");
        Bitmap myBitmap = null;
        try {
            URL urlConnection = url;
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myBitmap;
    }
}