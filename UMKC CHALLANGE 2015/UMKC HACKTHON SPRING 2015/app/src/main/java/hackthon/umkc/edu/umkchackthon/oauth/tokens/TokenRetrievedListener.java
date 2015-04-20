package hackthon.umkc.edu.umkchackthon.oauth.tokens;


import hackthon.umkc.edu.umkchackthon.domain.Tokens;

/**
 * @author paul.blundell
 *
 */
public interface TokenRetrievedListener {
	void onTokensRetrieved(Tokens tokens);
}
