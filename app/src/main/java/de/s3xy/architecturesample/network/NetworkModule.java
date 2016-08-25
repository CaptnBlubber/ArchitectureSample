package de.s3xy.architecturesample.network;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import de.s3xy.architecturesample.BuildConfig;
import de.s3xy.architecturesample.di.scope.ApplicationScope;
import de.s3xy.architecturesample.github.GithubApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author Angelo Rüggeberg <s3xy4ngc@googlemail.com>
 */

@Module
public class NetworkModule {
    private static final String GITHUB_API_ENDPOINT = "GithubApiEndpoint";

    public static final String AUTH_FLOW = "GithubAuthFlow";

    @Provides
    @ApplicationScope
    ObjectMapper provideJacksonObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    @Provides
    @ApplicationScope
    HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return interceptor;
    }

    @Provides
    @ApplicationScope
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)
                .build();
    }

    @Provides
    @ApplicationScope
    Retrofit provideRetrofit(@Named(GITHUB_API_ENDPOINT) String endpoint, OkHttpClient okHttpClient, ObjectMapper mapper) {
        return new Retrofit.Builder()
                .baseUrl(endpoint)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .client(okHttpClient).build();
    }

    @Provides
    @ApplicationScope
    @Named(AUTH_FLOW)
    Retrofit provideAuthRetrofit(@Named(AUTH_FLOW) String endpoint, OkHttpClient okHttpClient, ObjectMapper mapper) {
        return new Retrofit.Builder()
                .baseUrl(endpoint)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .client(okHttpClient).build();
    }

    @Provides
    @ApplicationScope
    GithubApi provideGithubApi(Retrofit retrofit) {
        return retrofit.create(GithubApi.class);
    }

    @Provides
    @ApplicationScope
    @Named(AUTH_FLOW)
    GithubApi providesGithubAuthApi(@Named(AUTH_FLOW) Retrofit retrofit) {
        return retrofit.create(GithubApi.class);
    }

    @Provides
    @ApplicationScope
    @Named(GITHUB_API_ENDPOINT)
    String provideGithubEndpoint() {
        return "https://api.github.com/";
    }

    @Provides
    @ApplicationScope
    @Named(AUTH_FLOW)
    String provideGithubAuthEndpoint() {
        return "https://github.com/login/oauth/";
    }
}