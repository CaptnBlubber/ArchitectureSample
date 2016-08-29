package de.s3xy.architecturesample.github;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Lazy;
import de.s3xy.architecturesample.BuildConfig;
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

    @Inject
    public GithubAuthInteractor(@Named(NetworkAuthModule.AUTH_FLOW) Lazy<GithubApi> githubApiLazy,
                                GithubAuthManager authManager,
                                NetworkConnectivityManager connectivityManager) {
        this.mGithubAuthApi = githubApiLazy;
        this.mGithubAuthManager = authManager;
        this.mNetworkConnectivityManager = connectivityManager;
    }

    public boolean shouldAuthorize() {
        return !mGithubAuthManager.shouldBeUnauthorized() && mGithubAuthManager.getAccessToken() == null;
    }

    public void skipLogin() {
        mGithubAuthManager.setShouldBeUnauthorized(true);
    }

    public Observable<Boolean> getAccessToken(String code) {
        return mNetworkConnectivityManager.checkIsOnline()
                .flatMap(booleanObservable -> mGithubAuthApi.get().getAccessToken(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, code)
                        .map(accessToken -> {
                            if (accessToken.getAccessToken() != null) {
                                mGithubAuthManager.saveAccessToken(accessToken.getAccessToken());
                                return true;
                            } else {
                                return false;
                            }
                        }));
    }
}
