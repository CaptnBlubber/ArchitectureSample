package de.s3xy.architecturesample.network;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

/**
 * @author Angelo Rüggeberg <s3xy4ngc@googlemail.com>
 */

@Module
public class NetworkModule {
    static final String TWITTER_API_ENDPOINT = "TwitterApiEndpoint";

    @Provides
    @ApplicationScope
    ObjectMapper provideJacksonObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }


    @Provides
    @ApplicationScope
    SigningInterceptor provideSigningInterceptor() {
        OkHttpOAuthConsumer oAuthConsumer = new OkHttpOAuthConsumer(BuildConfig.TWITTER_CONSUMER_KEY, BuildConfig.TWITTER_CONSUMER_SECRET);
        oAuthConsumer.setTokenWithSecret(BuildConfig.TWITTER_OAUTH_TOKEN, BuildConfig.TWITTER_OAUTH_SECRET);
        return new SigningInterceptor(oAuthConsumer);
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
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor, SigningInterceptor signingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(signingInterceptor)
                .retryOnConnectionFailure(true)
                .build();
    }

    @Provides
    @ApplicationScope
    Retrofit provideRetrofit(@Named(TWITTER_API_ENDPOINT) String endpoint, OkHttpClient okHttpClient, ObjectMapper mapper) {
        return new Retrofit.Builder()
                .baseUrl(endpoint)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .client(okHttpClient).build();
    }

    @Provides
    @ApplicationScope
    TwitterApi provideTwitterApi(Retrofit retrofit) {
        return retrofit.create(TwitterApi.class);
    }


    @Provides
    @ApplicationScope
    @Named(TWITTER_API_ENDPOINT)
    String provideTwitterEndpoint() {
        return "https://api.twitter.com/1.1/";
    }
}
