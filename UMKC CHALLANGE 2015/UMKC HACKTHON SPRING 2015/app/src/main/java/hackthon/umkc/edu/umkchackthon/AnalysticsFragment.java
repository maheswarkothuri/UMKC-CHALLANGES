package hackthon.umkc.edu.umkchackthon;


import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import hackthon.umkc.edu.umkchackthon.oauth.Constants;

public class AnalysticsFragment extends Fragment  implements TextToSpeech.OnInitListener   {
    private EditText channelId;
    private Button execute;
    private EditText endDate;
    private EditText metrics;
    private EditText starDate;
    private TextView results;
    private Context mContext;
    private TextToSpeech tts;
   public AnalysticsFragment(){

   }
    public AnalysticsFragment(Context context)
    {
        mContext = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        tts = new TextToSpeech(mContext, this);

        View rootView = inflater.inflate(R.layout.analytics, container, false);
        channelId = (EditText) rootView.findViewById(R.id.channelid);
        starDate = (EditText) rootView.findViewById(R.id.startdate);
        endDate = (EditText) rootView.findViewById(R.id.enddate);
        metrics = (EditText) rootView.findViewById(R.id.metrics);
        results = (TextView) rootView.findViewById(R.id.results);

        execute = (Button) rootView.findViewById(R.id.execute);
        channelId.setText(Constants.CLIENT_ID);
        execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rootView;
    }

    @Override
    public void onInit(int status) {
        
    }
}
