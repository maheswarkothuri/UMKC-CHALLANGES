package hackthon.umkc.edu.umkchackthon;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import hackthon.umkc.edu.umkchackthon.oauth.Constants;

public class TabAcitivity extends ActionBarActivity  implements TextToSpeech.OnInitListener{
public static final int LOGIN_TAB=0;
    public static int SELECTED_TAB =1;
    public static final int ANALYTICS_TAB=2;
    private static final String PREFERENC_NAME = "edu.umkc";
    private static final String TAB_KEY = "SELECTED_TAB";
    // Declaring our tabs and the corresponding fragments.
	ActionBar.Tab loginTab, searchTab, analyticsTab;
	Fragment loginFragment = new LoginFragment();
	Fragment analysticsFragment = new AnalysticsFragment(this);
	public SearchFragment searchFragment = new SearchFragment(this);
    private android.support.v7.app.ActionBar  actionBar;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Asking for the default ActionBar element that our platform supports.
		actionBar = this.getSupportActionBar();
		 
        // Screen handling while hiding ActionBar icon.
        actionBar.setDisplayShowHomeEnabled(false);

        // Screen handling while hiding Actionbar title.
        actionBar.setDisplayShowTitleEnabled(false);
 
        // Creating ActionBar tabs.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
 
        // Setting custom tab icons.
                loginTab = actionBar.newTab().setText("Login");
                analyticsTab = actionBar.newTab().setText("Analytics");
                searchTab = actionBar.newTab().setText("YT Search");
        // Setting tab listeners.
        loginTab.setTabListener(new TabListener(loginFragment));
        analyticsTab.setTabListener((ActionBar.TabListener) new TabListener(analysticsFragment));
        searchTab.setTabListener((ActionBar.TabListener) new TabListener(searchFragment));
       
        // Adding tabs to the ActionBar.
        actionBar.addTab(searchTab);
        actionBar.addTab(analyticsTab);
        actionBar.addTab(loginTab);
        actionBar.setSelectedNavigationItem(SELECTED_TAB);
        SELECTED_TAB=actionBar.getSelectedNavigationIndex();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SELECTED_TAB=actionBar.getSelectedNavigationIndex();
    }

    @Override
    protected void onResume() {
        super.onResume();

        actionBar.setSelectedNavigationItem(SELECTED_TAB);

    }

    @Override
    public void onInit(int status) {
        Constants.speak(this,"Welcome to Youtube DNA");
    }
}