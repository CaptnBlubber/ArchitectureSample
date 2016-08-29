package de.s3xy.architecturesample.github;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Lazy;
import de.s3xy.architecturesample.network.NetworkAuthModule;
import de.s3xy.architecturesample.network.NetworkConnectivityManager;
import rx.Observable;

/**
 * @author Andrey Bondarenko <andrey.cooper@gmail.com>
 */
public class GithubAuthInteractor {

    // used lazy for later injection
    final private Lazy<GithubApi> mGithubAuthApi;
    final private GithubAuthManager mGithubAuthManager;
    final private NetworkConnectivityManager mNetworkConnectivityManager;

    final private String mClientId;
    final private String mClientSecret;

    @Inject
    public GithubAuthInteractor(@Named(NetworkAuthModule.AUTH_FLOW) Lazy<GithubApi> githubApiLazy,
                                GithubAuthManager authManager,
                                NetworkConnectivityManager connectivityManager,
                                @Named(NetworkAuthModule.CLIENT_ID) String clientId,
                                @Named(NetworkAuthModule.CLIENT_SECRET)String clientSecret) {
        mGithubAuthApi = githubApiLazy;
        mGithubAuthManager = authManager;
        mNetworkConnectivityManager = connectivityManager;
        mClientId = clientId;
        mClientSecret = clientSecret;
    }

    public boolean shouldAuthorize() {
        return !mGithubAuthManager.shouldBeUnauthorized() && mGithubAuthManager.getAccessToken() == null;
    }

    public void skipLogin() {
        mGithubAuthManager.setShouldBeUnauthorized(true);
    }

    public Observable<Boolean> getAccessToken(String code) {
        return mNetworkConnectivityManager.checkIsOnline()
                .flatMap(booleanObservable -> mGithubAuthApi.get().getAccessToken(mClientId, mClientSecret, code)
                        .map(accessToken -> {
                            if (accessToken.getAccessToken() != null) {
                                mGithubAuthManager.saveAccessToken(accessToken.getAccessToken());
                                return true;
                            } else {
                                return false;
                            }
                        }));
    }

    public String getClientId() {
        return mClientId;
    }
}
