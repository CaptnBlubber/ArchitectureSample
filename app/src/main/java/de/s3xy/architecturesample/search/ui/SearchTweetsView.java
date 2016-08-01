package de.s3xy.architecturesample.search.ui;

import java.util.List;

import de.s3xy.architecturesample.twitter.model.Tweet;
import rx.Observable;

/**
 * @author Angelo Rüggeberg <s3xy4ngc@googlemail.com>
 */


public interface SearchTweetsView {
    void showTweets(List<Tweet> tweets);

    void showLoading();

    void hideLoading();

    Observable<CharSequence> getQueryTextObservable();
}
