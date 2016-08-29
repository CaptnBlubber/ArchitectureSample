package de.s3xy.architecturesample.network;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import de.s3xy.architecturesample.BuildConfig;
import de.s3xy.architecturesample.di.scope.AuthScope;
import de.s3xy.architecturesample.github.GithubApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author Andrey Bondarenko <andrey.cooper@gmail.com>
 */
@Module
public class NetworkAuthModule {

    public static final String AUTH_FLOW = "GithubAuthFlow";
    public static final String CLIENT_ID = "clientId";
    public static final String CLIENT_SECRET = "clientSecret";

    @Provides
    @AuthScope
    @Named(AUTH_FLOW)
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)
                .build();
    }

    @Provides
    @AuthScope
    @Named(AUTH_FLOW)
    Retrofit provideAuthRetrofit(@Named(AUTH_FLOW) String endpoint, @Named(AUTH_FLOW) OkHttpClient okHttpClient, ObjectMapper mapper) {
        return new Retrofit.Builder()
                .baseUrl(endpoint)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .client(okHttpClient).build();
    }

    @Provides
    @AuthScope
    @Named(AUTH_FLOW)
    GithubApi providesGithubAuthApi(@Named(AUTH_FLOW) Retrofit retrofit) {
        return retrofit.create(GithubApi.class);
    }

    @Provides
    @AuthScope
    @Named(AUTH_FLOW)
    String provideGithubAuthEndpoint() {
        return "https://github.com/login/oauth/";
    }

    @Provides
    @AuthScope
    @Named(CLIENT_ID)
    String provideClientId() {
        return BuildConfig.CLIENT_ID;
    }

    @Provides
    @AuthScope
    @Named(CLIENT_SECRET)
    String provideClientSecret() {
        return BuildConfig.CLIENT_SECRET;
    }
}
