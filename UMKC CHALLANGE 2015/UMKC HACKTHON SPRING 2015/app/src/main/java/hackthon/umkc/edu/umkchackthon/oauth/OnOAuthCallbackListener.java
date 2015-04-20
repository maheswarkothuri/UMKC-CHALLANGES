package hackthon.umkc.edu.umkchackthon.oauth;

/**
 * Callback that our redirect url has been loaded
 * @author paul.blundell
 *
 */
public interface OnOAuthCallbackListener {
	void onOAuthCallback(String params);
}
