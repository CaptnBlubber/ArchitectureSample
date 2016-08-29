package de.s3xy.architecturesample.github;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import de.s3xy.architecturesample.github.model.RepositoriesSearchResult;
import de.s3xy.architecturesample.network.NetworkConnectivityManager;
import rx.Observable;

/**
 * Created by Vlad Fedorenko <vfedo92@gmail.com>
 * 08.26.2016
 */
public class GithubInteractor {
    final private GithubApi mGithubApi;
    final private GithubAuthManager mGithubAuthManager;
    final private NetworkConnectivityManager mNetworkConnectivityManager;

    @Inject
    GithubInteractor(GithubApi api,
                     GithubAuthManager authManager,
                     NetworkConnectivityManager networkConnectivityManager) {
        mGithubApi = api;
        mGithubAuthManager = authManager;
        mNetworkConnectivityManager = networkConnectivityManager;
    }

    @NonNull
    public Observable<RepositoriesSearchResult> searchRepositories(String query) {
        return mNetworkConnectivityManager.checkIsOnline()
                .flatMap(booleanObservable -> mGithubApi.searchRepositories(query));
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
}
