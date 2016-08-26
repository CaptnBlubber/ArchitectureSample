package de.s3xy.architecturesample.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dagger.Module;
import dagger.Provides;
import de.s3xy.architecturesample.di.scope.ApplicationScope;

/**
 * @author Angelo Rüggeberg <s3xy4ngc@googlemail.com>
 */

@Module
public class ApplicationModule {

    private final Context mAppContext;

    public ApplicationModule(Application application) {
        mAppContext = application.getApplicationContext();
    }

    @Provides
    @ApplicationScope
    Context provideContext() {
        return mAppContext;
    }


    @Provides
    @ApplicationScope
    SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mAppContext);
    }

}
