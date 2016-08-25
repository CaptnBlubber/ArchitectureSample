package de.s3xy.architecturesample.github;

import de.s3xy.architecturesample.github.model.AccessToken;
import de.s3xy.architecturesample.github.model.RepositoriesSearchResult;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Angelo Rüggeberg <s3xy4ngc@googlemail.com>
 */

public interface GithubApi {

    @GET("search/repositories")
    Observable<RepositoriesSearchResult> searchRepositories(@Query("q") String searchText);

    @FormUrlEncoded
    @POST("access_token")
    @Headers("Accept: application/json")
    Observable<AccessToken> getAccessToken(@Field("client_id") String clientId,
                                           @Field("client_secret") String clientSecret,
                                           @Field("code") String code);

}
