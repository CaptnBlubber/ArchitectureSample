package de.s3xy.architecturesample.login.presenter;

import javax.inject.Inject;
import javax.inject.Named;

import de.s3xy.architecturesample.base.ErrorType;
import de.s3xy.architecturesample.base.Presenter;
import de.s3xy.architecturesample.github.GithubApi;
import de.s3xy.architecturesample.github.GithubAuthHelper;
import de.s3xy.architecturesample.github.GithubAuthManager;
import de.s3xy.architecturesample.login.ui.LoginView;
import de.s3xy.architecturesample.network.NetworkModule;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Vlad Fedorenko <vfedo92@gmail.com>
 * 08.25.2016
 */
public class LoginPresenter implements Presenter<LoginView> {
    private LoginView mView;
    private GithubApi mGithubApi;
    private GithubAuthManager mAuthManager;

    @Inject
    LoginPresenter(@Named(NetworkModule.AUTH_FLOW) GithubApi githubApi, GithubAuthManager authManager) {
        mGithubApi = githubApi;
        mAuthManager = authManager;
    }

    @Override
    public void attachView(LoginView view) {
        mView = view;

        if (mAuthManager.shouldBeUnauthorized() || mAuthManager.getAccessToken() != null) {
            // We already have token or we should be unauthorized so go to search screen
            mView.goToSearchScreen();
        }
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void signIn() {
        mView.showWebViewForLogin(GithubAuthHelper.buildAuthUrl());
    }

    public void codeReady(String code) {
        mGithubApi.getAccessToken(GithubAuthHelper.CLIENT_ID, GithubAuthHelper.CLIENT_SECRET, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        accessToken -> {
                            mAuthManager.saveAccessToken(accessToken.getAccessToken());
                            mView.goToSearchScreen();
                        },
                        throwable -> mView.showError(ErrorType.getErrorType(throwable)),
                        () -> Timber.d("Fetching access token completed"));
    }

    public void skipLogin() {
        mAuthManager.setShouldBeUnauthorized(true);
        mView.goToSearchScreen();
    }
}
