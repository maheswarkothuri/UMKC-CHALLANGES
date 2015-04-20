package hackthon.umkc.edu.umkchackthon.oauth;


import hackthon.umkc.edu.umkchackthon.domain.Tokens;

/**
 * Callback to determine which option the user selected when asking for OAuth
 * @author paul.blundell
 *
 */
public interface OnOAuthListener {
	void onAuthorized(String authCode);
	void onRefused();

    void onTokensRetrieved(Tokens tokens);
}