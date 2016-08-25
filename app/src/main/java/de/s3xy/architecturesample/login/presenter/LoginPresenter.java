package de.s3xy.architecturesample.login.presenter;

import javax.inject.Inject;
import javax.inject.Named;

import de.s3xy.architecturesample.base.Presenter;
import de.s3xy.architecturesample.github.GithubApi;
import de.s3xy.architecturesample.github.GithubAuthHelper;
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

    @Inject
    LoginPresenter(@Named(NetworkModule.AUTH_FLOW) GithubApi githubApi) {
        mGithubApi = githubApi;
    }

    @Override
    public void attachView(LoginView view) {
        mView = view;

        // TODO check if we already have token or we should be unauthorized. If true - call goToSearchScreen()
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
                            Timber.d("Access token = " + accessToken.getAccessToken());
                        },
                        Throwable::printStackTrace,
                        () -> Timber.d("Getting fetching access token completed"));
    }

    public void skipLogin() {
        // TODO write to prefs that we should be unauthorized
    }
}
