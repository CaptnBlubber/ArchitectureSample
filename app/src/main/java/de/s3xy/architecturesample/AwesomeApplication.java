package de.s3xy.architecturesample;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import de.s3xy.architecturesample.di.ApplicationModule;
import de.s3xy.architecturesample.di.component.ApplicationComponent;
import de.s3xy.architecturesample.di.component.DaggerApplicationComponent;
import de.s3xy.architecturesample.network.NetworkModule;
import timber.log.Timber;

/**
 * @author Angelo Rüggeberg <s3xy4ngc@googlemail.com>
 */


public class AwesomeApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
        initTimber();
    }

    private void initDagger() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule())
                .build();
    }

    private void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
