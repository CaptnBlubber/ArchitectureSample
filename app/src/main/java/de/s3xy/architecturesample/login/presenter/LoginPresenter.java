package de.s3xy.architecturesample.login.presenter;

import javax.inject.Inject;

import de.s3xy.architecturesample.base.ErrorType;
import de.s3xy.architecturesample.base.Presenter;
import de.s3xy.architecturesample.github.GithubAuthHelper;
import de.s3xy.architecturesample.github.GithubInteractor;
import de.s3xy.architecturesample.login.ui.LoginView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Vlad Fedorenko <vfedo92@gmail.com>
 * 08.25.2016
 */
public class LoginPresenter implements Presenter<LoginView> {
    private LoginView mView;
    private GithubInteractor mInteractor;

    @Inject
    LoginPresenter(GithubInteractor interactor) {
        mInteractor = interactor;
    }

    @Override
    public void attachView(LoginView view) {
        mView = view;

        if (!mInteractor.shouldAuthorize()) {
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
        mInteractor.getAccessToken(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isLoggedIn -> {
                            if (isLoggedIn) {
                                mView.goToSearchScreen();
                            } else {
                                mView.showError(ErrorType.AUTHORIZATION_FAILED);
                            }
                        }, throwable -> {
                            Timber.e(throwable, "Sign in error");
                            mView.showError(ErrorType.getErrorType(throwable));
                        },
                        () -> Timber.i("Signed in successful"));
    }

    public void skipLogin() {
        mInteractor.skipLogin();
        mView.goToSearchScreen();
    }
}
