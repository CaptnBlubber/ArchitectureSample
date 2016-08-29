package de.s3xy.architecturesample.search.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.s3xy.architecturesample.AwesomeApplication;
import de.s3xy.architecturesample.R;
import de.s3xy.architecturesample.base.BaseActivity;
import de.s3xy.architecturesample.base.Presenter;
import de.s3xy.architecturesample.github.model.Repository;
import de.s3xy.architecturesample.login.ui.LoginActivity;
import de.s3xy.architecturesample.search.adapter.RepositoryAdapter;
import de.s3xy.architecturesample.search.presenter.SearchPresenter;
import rx.Observable;

/**
 * @author Angelo Rüggeberg <s3xy4ngc@googlemail.com>
 */


public class SearchRepositoriesActivity extends BaseActivity implements SearchRepositoriesView {

    @Inject
    SearchPresenter mSearchPresenter;

    @BindView(R.id.txt_search_query)
    EditText mTxtSearchQuery;

    @BindView(R.id.list_tweets)
    RecyclerView mListTweets;

    @BindView(R.id.loading)
    ProgressBar mLoading;

    private Unbinder mUnbinder;
    private RepositoryAdapter mRepositoryAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ((AwesomeApplication) getApplication()).getApplicationComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mUnbinder = ButterKnife.bind(this);

        mRepositoryAdapter = new RepositoryAdapter();

        mListTweets.setLayoutManager(new LinearLayoutManager(this));
        mListTweets.setAdapter(mRepositoryAdapter);

        mSearchPresenter.attachView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        if (mSearchPresenter.shouldShowSignIn()) {
            MenuItem logoutItem = menu.findItem(R.id.logout);
            logoutItem.setVisible(false);
        } else {
            MenuItem signInItem = menu.findItem(R.id.signin);
            signInItem.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                mSearchPresenter.logout();
                return true;
            case R.id.signin:
                mSearchPresenter.signIn();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected Unbinder getUnbinder() {
        return mUnbinder;
    }

    @Override
    protected Presenter getPresenter() {
        return mSearchPresenter;
    }

    @Override
    public void showRepositories(List<Repository> repositories) {
        mRepositoryAdapter.setRepositories(repositories);
    }

    @Override
    public void showLoading() {
        mLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mLoading.setVisibility(View.GONE);
    }

    @Override
    public Observable<CharSequence> getQueryTextObservable() {
        return RxTextView.textChanges(mTxtSearchQuery);
    }

    @Override
    public void recreateMenu() {
        invalidateOptionsMenu();
    }

    @Override
    public void goToLoginScreen() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
