package de.s3xy.architecturesample.github;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Andrey Bondarenko <andrey.cooper@gmail.com>
 */
public class TokenInterceptor implements Interceptor {

    private GithubAuthManager authManager;

    @Inject
    public TokenInterceptor(GithubAuthManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (!authManager.shouldBeUnauthorized()) {
            //Build new request
            Request.Builder builder = request.newBuilder();
            builder.header("Accept", "application/json");

            String accessToken = authManager.getAccessToken();
            if (accessToken != null) {
                builder.header("Authorization", String.format("token %s", accessToken));
            }
            request = builder.build();
        }

        return chain.proceed(request);
    }
}
