package de.s3xy.architecturesample.github;

import de.s3xy.architecturesample.github.model.RepositoriesSearchResult;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Angelo Rüggeberg <s3xy4ngc@googlemail.com>
 */


public interface GithubApi {


    @GET("search/repositories")
    Observable<RepositoriesSearchResult> searchRepositories(@Query("q") String searchText);
}
