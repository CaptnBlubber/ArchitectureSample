package de.s3xy.architecturesample.network;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import de.s3xy.architecturesample.BuildConfig;
import de.s3xy.architecturesample.di.scope.ApplicationScope;
import de.s3xy.architecturesample.github.GithubApi;
import de.s3xy.architecturesample.github.GithubAuthManager;
import de.s3xy.architecturesample.github.TokenInterceptor;
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
    TokenInterceptor provideTokenInterceptor(GithubAuthManager authManager) {
        return new TokenInterceptor(authManager);
    }

    @Provides
    @ApplicationScope
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor, TokenInterceptor tokenInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(tokenInterceptor)
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
    GithubApi provideGithubApi(Retrofit retrofit) {
        return retrofit.create(GithubApi.class);
    }

    @Provides
    @ApplicationScope
    @Named(GITHUB_API_ENDPOINT)
    String provideGithubEndpoint() {
        return "https://api.github.com/";
    }
}