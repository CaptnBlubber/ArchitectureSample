package de.s3xy.architecturesample.login.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.s3xy.architecturesample.AwesomeApplication;
import de.s3xy.architecturesample.R;
import de.s3xy.architecturesample.base.BaseActivity;
import de.s3xy.architecturesample.github.GithubAuthHelper;
import de.s3xy.architecturesample.login.presenter.LoginPresenter;
import de.s3xy.architecturesample.search.ui.SearchRepositoriesActivity;

/**
 * Created by Vlad Fedorenko <vfedo92@gmail.com>
 * 08.25.2016
 */
public class LoginActivity extends BaseActivity implements LoginView {

    @Inject
    LoginPresenter mLoginPresenter;

    @BindView(R.id.web_view)
    WebView mWebView;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((AwesomeApplication) getApplication()).getApplicationComponent().inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUnbinder = ButterKnife.bind(this);
        mLoginPresenter.attachView(this);
    }

    protected Unbinder getUnbinder() {
        return mUnbinder;
    }

    protected LoginPresenter getPresenter() {
        return mLoginPresenter;
    }

    public void onSignInClick(View view) {
        mLoginPresenter.signIn();
    }

    public void onSkipClick(View view) {
        mLoginPresenter.skipLogin();
    }

    @Override
    public void showWebViewForLogin(String url) {
        mWebView.setVisibility(View.VISIBLE);
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
                showLoading();
            }

            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                hideLoading();
            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                final Uri uri = request.getUrl();
                return handleUri(uri);
            }

            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                final Uri uri = Uri.parse(url);
                return handleUri(uri);
            }

            private boolean handleUri(@NonNull final Uri uri) {
                if (uri.getScheme().equals(GithubAuthHelper.CALLBACK_SCHEME)) {
                    String code = uri.getQueryParameter("code");

                    if (code != null) {
                        mLoginPresenter.codeReady(code);
                    }

                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    @Override
    public void goToSearchScreen() {
        startActivity(new Intent(this, SearchRepositoriesActivity.class));
        finish();
    }
}

