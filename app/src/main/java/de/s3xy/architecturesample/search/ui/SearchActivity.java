package de.s3xy.architecturesample.search.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import de.s3xy.architecturesample.AwesomeApplication;
import de.s3xy.architecturesample.twitter.api.TwitterApi;
import de.s3xy.architecturesample.twitter.model.SearchResult;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * @author Angelo Rüggeberg <s3xy4ngc@googlemail.com>
 */


public class SearchActivity extends AppCompatActivity {

    @Inject
    TwitterApi mTwitterApi;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AwesomeApplication) getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mTwitterApi
                .searchTweets("awesome")
                .map(SearchResult::getStatuses)
                .flatMap(Observable::from)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        tweet -> {
                            Timber.d(tweet.getText());
                        },
                        throwable -> {
                            Timber.e(throwable.getMessage());
                        }
                );
    }
}
