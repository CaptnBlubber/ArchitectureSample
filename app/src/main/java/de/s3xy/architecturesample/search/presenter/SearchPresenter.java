package de.s3xy.architecturesample.search.presenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import de.s3xy.architecturesample.base.Presenter;
import de.s3xy.architecturesample.github.GithubApi;
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

    @Inject
    SearchPresenter(GithubApi githubApi) {
        mGithubApi = githubApi;
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
        mView.showLoading();
        mSubscription = mView
                .getQueryTextObservable()
                .subscribeOn(AndroidSchedulers.mainThread())
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter(charSequence -> charSequence.length() > 5)
                .map(CharSequence::toString)
                .subscribeOn(Schedulers.io())
                .concatMap(mGithubApi::searchRepositories)
                .map(RepositoriesSearchResult::getItems)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(repositories -> {
                    mView.hideLoading();
                    mView.showRepositories(repositories);
                });
    }
}
