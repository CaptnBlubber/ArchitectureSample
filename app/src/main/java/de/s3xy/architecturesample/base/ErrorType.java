package de.s3xy.architecturesample.base;

import android.support.annotation.Nullable;

import java.net.UnknownHostException;

import de.s3xy.architecturesample.R;
import de.s3xy.architecturesample.network.NoNetworkException;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by Vlad Fedorenko <vfedo92@gmail.com>
 * 08.25.2016
 */
public enum ErrorType {
    NO_NETWORK(R.string.error_no_network),
    AUTHORIZATION_FAILED(R.string.error_authorization_failed),
    ACCESS_FORBIDDEN(R.string.error_access_forbidden),
    USER_UNAUTHORIZED(R.string.error_unauthorized),
    UNKNOWN_HOST(R.string.error_unknown_host);

    private int mMessageResource;

    ErrorType(int messageResource) {
        mMessageResource = messageResource;
    }

    public int getMessageResource() {
        return mMessageResource;
    }

    @Nullable
    public static ErrorType getErrorType(Throwable throwable) {
        if (throwable instanceof NoNetworkException) {
            return NO_NETWORK;
        }
        if (throwable instanceof HttpException) {
            Response response = ((HttpException) throwable).response();
            switch (response.code()) {
                case 401:
                    return USER_UNAUTHORIZED;
                case 403:
                    return ACCESS_FORBIDDEN;
                default:
                    return null;
            }
        }
        if (throwable instanceof UnknownHostException) {
            return UNKNOWN_HOST;
        }
        return null;
    }
}
