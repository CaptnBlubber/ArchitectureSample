package de.s3xy.architecturesample.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Vlad Fedorenko <vfedo92@gmail.com>
 * 08.26.2016
 */
public class NetworkConnectivityManager {
    final private Context mContext;

    @Inject
    NetworkConnectivityManager(Context context) {
        mContext = context;
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public Observable<Boolean> checkIsOnline() {
        return Observable.create(subscriber -> {
            if (!subscriber.isUnsubscribed()) {
                if (isOnline()) {
                    subscriber.onNext(true);
                } else {
                    subscriber.onError(new NoNetworkException());
                }
            }
        });
    }
}
