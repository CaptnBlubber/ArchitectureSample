package de.s3xy.architecturesample.search.presenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import de.s3xy.architecturesample.base.Presenter;
import de.s3xy.architecturesample.github.GithubApi;
import de.s3xy.architecturesample.github.GithubAuthManager;
import de.s3xy.architecturesample.github.model.RepositoriesSearchResult;
import de.s3xy.architecturesample.search.ui.SearchRepositoriesView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * @author Angelo Rüggeberg <s3xy4ngc@googlemail.com>
 */


public class SearchPresenter implements Presenter<SearchRepositoriesView> {

    private Subscription mSubscription = Subscriptions.empty();
    private SearchRepositoriesView mView;

    private final GithubApi mGithubApi;
    private final GithubAuthManager mGithubAuthManager;

    @Inject
    SearchPresenter(GithubApi githubApi, GithubAuthManager githubAuthManager) {
        mGithubApi = githubApi;
        mGithubAuthManager = githubAuthManager;
    }

    @Override
    public void attachView(SearchRepositoriesView view) {
        mView = view;

        setupSearchListener();
    }

    @Override
    public void detachView() {
        mSubscription.unsubscribe();
    }

    public void setupSearchListener() {
        mSubscription = mView
                .getQueryTextObservable()
                .subscribeOn(AndroidSchedulers.mainThread())
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter(charSequence -> charSequence.length() > 5)
                .map(CharSequence::toString)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(s -> mView.showLoading())
                .observeOn(Schedulers.io())
                .concatMap(mGithubApi::searchRepositories)
                .map(RepositoriesSearchResult::getItems)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(repositories -> {
                    mView.hideLoading();
                    mView.showRepositories(repositories);
                });
    }

    public boolean shouldShowSignIn() {
        return mGithubAuthManager.shouldBeUnauthorized();
    }

    public void logout() {
        mGithubAuthManager.resetAuthentication();
        mGithubAuthManager.setShouldBeUnauthorized(true);
        mView.recreateMenu();
    }
}
