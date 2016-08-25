package de.s3xy.architecturesample.github;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * @author Andrey Bondarenko <andrey.cooper@gmail.com>
 */
public class TokenAuthenticator implements Authenticator {

    private static final String AUTHORIZATION = "Authorization";

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        // TODO: get access_token from storage
        String accessToken = null;

        // Add new header to rejected request and retry it
        return response.request().newBuilder()
                .header(AUTHORIZATION, "token " + accessToken)
                .build();
    }
}
