package de.s3xy.architecturesample.login.presenter;

import javax.inject.Inject;

import de.s3xy.architecturesample.base.Presenter;
import de.s3xy.architecturesample.github.GithubAuthHelper;
import de.s3xy.architecturesample.github.GithubAuthManager;
import de.s3xy.architecturesample.login.ui.LoginView;

/**
 * Created by Vlad Fedorenko <vfedo92@gmail.com>
 * 08.25.2016
 */
public class LoginPresenter implements Presenter<LoginView> {
    private LoginView mView;
    private GithubAuthManager mAuthManager;

    @Inject
    LoginPresenter(GithubAuthManager authManager) {
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
        // TODO begin retrofit request to POST https://github.com/login/oauth/access_token for fetching token
    }

    public void skipLogin() {
        mAuthManager.setShouldBeUnauthorized(true);
        mView.goToSearchScreen();
    }
}
