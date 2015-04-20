package hackthon.umkc.edu.umkchackthon;

import android.support.v7.app.ActionBar;
import android.app.ActionBar.Tab;
import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;

public class TabListener implements ActionBar.TabListener {

	private Fragment fragment;
	
	// The contructor.
	public TabListener(Fragment fragment) {
		this.fragment = fragment;
	}

	// When a tab is tapped, the FragmentTransaction replaces
	// the content of our main layout with the specified fragment;
	// that's why we declared an id for the main layout.


	// When a tab is unselected, we have to hide it from the user's view. 


    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

       ft.replace(R.id.activity_main, fragment);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }
}
