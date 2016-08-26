package de.s3xy.architecturesample.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;

/**
 * Created by Vlad Fedorenko <vfedo92@gmail.com>
 * 08.26.2016
 */
public class NetworkConnectivityManager {
    private Context mContext;

    @Inject
    NetworkConnectivityManager(Context context) {
        mContext = context;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork.isConnectedOrConnecting();
    }
}
