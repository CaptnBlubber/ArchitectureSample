package de.s3xy.architecturesample.search.presenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import de.s3xy.architecturesample.base.Presenter;
import de.s3xy.architecturesample.github.GithubInteractor;
import de.s3xy.architecturesample.github.model.RepositoriesSearchResult;
import de.s3xy.architecturesample.search.ui.SearchRepositoriesView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;
import timber.log.Timber;

/**
 * @author Angelo Rüggeberg <s3xy4ngc@googlemail.com>
 */


public class SearchPresenter implements Presenter<SearchRepositoriesView> {

    private Subscription mSubscription = Subscriptions.empty();
    private SearchRepositoriesView mView;

    private GithubInteractor mInteractor;

    @Inject
    SearchPresenter(GithubInteractor interactor) {
        mInteractor = interactor;
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
                .concatMap(mInteractor::searchRepositories)
                .map(RepositoriesSearchResult::getItems)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(repositories -> {
                            mView.hideLoading();
                            mView.showRepositories(repositories);
                        },
                        throwable -> {
                            mView.hideLoading();
                            Timber.e(throwable, "Repositories fetching error");
                            //TODO show error
                        },
                        () -> Timber.i("Repositories fetching complete"));
    }

    public boolean shouldShowSignIn() {
        return mInteractor.shouldBeUnauthorized();
    }

    public void logout() {
        mInteractor.logout();
        mView.recreateMenu();
    }

    public void signIn() {
        mInteractor.setShouldBeUnauthorized(false);
        mView.goToLoginScreen();
    }
}
