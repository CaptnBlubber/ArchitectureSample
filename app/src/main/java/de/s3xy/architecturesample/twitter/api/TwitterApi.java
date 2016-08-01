package de.s3xy.architecturesample.twitter.api;

import java.util.List;

import de.s3xy.architecturesample.twitter.model.SearchResult;
import de.s3xy.architecturesample.twitter.model.Tweet;
import de.s3xy.architecturesample.twitter.model.User;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Angelo Rüggeberg <s3xy4ngc@googlemail.com>
 */


public interface TwitterApi {

    @GET("search/tweets.json")
    Observable<SearchResult> searchTweets(@Query("q") String searchText);

    @GET("users/show.json")
    Observable<User> getUserByScreenName(@Query("screen_name") String screenName);

    @GET("users/show.json")
    Observable<User> getUserById(@Query("user_id") Long userId);

    @GET("statuses/user_timeline.json")
    Observable<List<Tweet>> getUserTimelineByScreenName(@Query("screen_name") String screenName);

    @GET("statuses/user_timeline.json")
    Observable<List<Tweet>> getUserTimelineById(@Query("user_id") Long userId);


}
