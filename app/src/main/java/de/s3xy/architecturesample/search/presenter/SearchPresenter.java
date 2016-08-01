package de.s3xy.architecturesample.search.presenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import de.s3xy.architecturesample.base.Presenter;
import de.s3xy.architecturesample.search.ui.SearchTweetsView;
import de.s3xy.architecturesample.twitter.api.TwitterApi;
import de.s3xy.architecturesample.twitter.model.SearchResult;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * @author Angelo Rüggeberg <s3xy4ngc@googlemail.com>
 */


public class SearchPresenter implements Presenter<SearchTweetsView> {

    private Subscription mSubscription = Subscriptions.empty();
    private SearchTweetsView mView;

    private final TwitterApi mTwitterApi;

    @Inject
    SearchPresenter(TwitterApi twitterApi) {
        mTwitterApi = twitterApi;
    }

    @Override
    public void attachView(SearchTweetsView view) {
        mView = view;

        setupSearchListener();
    }

    @Override
    public void dettachView() {
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
                .concatMap(mTwitterApi::searchTweets)
                .map(SearchResult::getStatuses)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tweets -> {
                    mView.hideLoading();
                    mView.showTweets(tweets);
                });
    }
}
