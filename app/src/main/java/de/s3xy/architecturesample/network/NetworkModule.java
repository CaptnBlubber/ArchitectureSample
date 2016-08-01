package de.s3xy.architecturesample.network;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import de.s3xy.architecturesample.BuildConfig;
import de.s3xy.architecturesample.di.scope.ApplicationScope;
import de.s3xy.architecturesample.twitter.api.TwitterApi;
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
    static final String TWITTER_API_ENDPOINT = "TwitterApiEndpoint";

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
    Retrofit provideRetrofit(@Named(TWITTER_API_ENDPOINT) String endpoint, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(endpoint)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient).build();
    }

    @Provides
    @ApplicationScope
    TwitterApi provideTwitterApi(Retrofit retrofit) {
        return retrofit.create(TwitterApi.class);
    }


}
