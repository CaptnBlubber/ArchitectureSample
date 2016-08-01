package de.s3xy.architecturesample.twitter.api;

import de.s3xy.architecturesample.twitter.model.SearchResult;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Angelo Rüggeberg <s3xy4ngc@googlemail.com>
 */


public interface TwitterApi {

    @GET("search/tweets.json")
    Observable<SearchResult> searchTweets(@Query("q") String searchText);
}
