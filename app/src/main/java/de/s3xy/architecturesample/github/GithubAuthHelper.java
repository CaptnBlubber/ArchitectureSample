package de.s3xy.architecturesample.github;

import okhttp3.HttpUrl;

/**
 * Created by Vlad Fedorenko <vfedo92@gmail.com>
 * 08.25.2016
 */
public class GithubAuthHelper {
    public static final String CALLBACK_SCHEME = "de.s3xy.architecturesample";

    private static final String CALLBACK_URL = "://oauth";

    private static final String OAUTH_HOST = "github.com";
    private static final String SCOPE = "user,public_repo,repo";

    public static String buildAuthUrl() {
        HttpUrl.Builder url = new HttpUrl.Builder()
                .scheme("https")
                .host(OAUTH_HOST)
                .addPathSegment("login")
                .addPathSegment("oauth")
                .addPathSegment("authorize")
                .addQueryParameter("client_id", GithubConfig.CLIENT_ID)
                .addQueryParameter("scope", SCOPE)
                .addQueryParameter("redirect_uri", CALLBACK_SCHEME + CALLBACK_URL);

        return url.build().toString();
    }
}
