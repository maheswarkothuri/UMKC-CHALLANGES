package hackthon.umkc.edu.umkchackthon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import hackthon.umkc.edu.umkchackthon.NetworkUtil;

/**
 * Created by maheswarkothuri on 4/18/2015.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {
    private static final String TAG ="NetworkChangeReceiver" ;

    @Override
    public void onReceive(final Context context, final Intent intent) {

        int status = NetworkUtil.getConnectivityStatusString(context);
        android.util.Log.e(TAG, "onReceive  "+intent.getAction());
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            if(status==NetworkUtil.NETWORK_STATUS_NOT_CONNECTED){
//                newText ForceExitPause(context).execute();
                Log.v(TAG,"Not Connected");

            }else{
//                new ResumeForceExitPause(context).execute();
                Log.v(TAG," Connected");

            }

        }
    }

}
