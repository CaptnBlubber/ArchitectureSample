package de.s3xy.architecturesample.network;

import dagger.Module;
import dagger.Provides;
import de.s3xy.architecturesample.di.scope.AuthScope;
import de.s3xy.architecturesample.github.GithubAuthInteractor;
import de.s3xy.architecturesample.login.presenter.LoginPresenter;

/**
 * @author Andrey Bondarenko <andrey.cooper@gmail.com>
 */
@Module
public class AuthPresenterModule {

    @Provides
    @AuthScope
    LoginPresenter provideLoginPresenter(GithubAuthInteractor githubInteractor) {
        return new LoginPresenter(githubInteractor);
    }
}
