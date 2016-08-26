package de.s3xy.architecturesample.github;

import javax.inject.Inject;
import javax.inject.Named;

import de.s3xy.architecturesample.github.model.RepositoriesSearchResult;
import de.s3xy.architecturesample.network.NetworkConnectivityManager;
import de.s3xy.architecturesample.network.NetworkModule;
import de.s3xy.architecturesample.network.NoNetworkException;
import rx.Observable;

/**
 * Created by Vlad Fedorenko <vfedo92@gmail.com>
 * 08.26.2016
 */
public class GithubInteractor {
    private GithubApi mGithubAuthApi;
    private GithubApi mGithubApi;
    private GithubAuthManager mGithubAuthManager;
    private NetworkConnectivityManager mNetworkConnectivityManager;

    @Inject
    GithubInteractor(@Named(NetworkModule.AUTH_FLOW) GithubApi githubAuthApi,
                     GithubApi api,
                     GithubAuthManager authManager,
                     NetworkConnectivityManager networkConnectivityManager) {
        mGithubAuthApi = githubAuthApi;
        mGithubApi = api;
        mGithubAuthManager = authManager;
        mNetworkConnectivityManager = networkConnectivityManager;
    }

    public boolean shouldAuthorize() {
        return !mGithubAuthManager.shouldBeUnauthorized() && mGithubAuthManager.getAccessToken() == null;
    }

    public Observable<Boolean> getAccessToken(String code) {
        if (!mNetworkConnectivityManager.isOnline()) {
            return throwNoNetworkError();
        }

        return mGithubAuthApi.getAccessToken(GithubAuthHelper.CLIENT_ID, GithubAuthHelper.CLIENT_SECRET, code)
                .map(accessToken -> {
                    if (accessToken.getAccessToken() != null) {
                        mGithubAuthManager.saveAccessToken(accessToken.getAccessToken());
                        return true;
                    } else {
                        return false;
                    }
                });
    }

    public Observable<RepositoriesSearchResult> searchRepositories(String query) {
        if (!mNetworkConnectivityManager.isOnline()) {
            return throwNoNetworkError();
        }

        return mGithubApi.searchRepositories(query);
    }

    public void skipLogin() {
        mGithubAuthManager.setShouldBeUnauthorized(true);
    }

    public boolean shouldBeUnauthorized() {
        return mGithubAuthManager.shouldBeUnauthorized();
    }

    public void setShouldBeUnauthorized(boolean shouldBeUnauthorized) {
        mGithubAuthManager.setShouldBeUnauthorized(shouldBeUnauthorized);
    }

    public void logout() {
        mGithubAuthManager.resetAuthentication();
        mGithubAuthManager.setShouldBeUnauthorized(true);
    }

    private <T> Observable<T> throwNoNetworkError() {
        return Observable.create(subscriber -> subscriber.onError(new NoNetworkException()));
    }
}
