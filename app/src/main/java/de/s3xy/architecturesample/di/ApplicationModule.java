package de.s3xy.architecturesample.di;

import android.app.Application;

import dagger.Module;
import dagger.Provides;
import de.s3xy.architecturesample.di.scope.ApplicationScope;

/**
 * @author Angelo Rüggeberg <s3xy4ngc@googlemail.com>
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationScope
    Application provideApplication() {
        return mApplication;
    }

}
