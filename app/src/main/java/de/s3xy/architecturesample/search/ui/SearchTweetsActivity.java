package de.s3xy.architecturesample.search.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import de.s3xy.architecturesample.search.adapter.TweetAdapter;
import de.s3xy.architecturesample.search.presenter.SearchPresenter;
import de.s3xy.architecturesample.twitter.model.Tweet;
import rx.Observable;

/**
 * @author Angelo Rüggeberg <s3xy4ngc@googlemail.com>
 */


public class SearchTweetsActivity extends AppCompatActivity implements SearchTweetsView {

    @Inject
    SearchPresenter mSearchPresenter;

    @BindView(R.id.txt_search_query)
    EditText mTxtSearchQuery;

    @BindView(R.id.list_tweets)
    RecyclerView mListTweets;

    @BindView(R.id.loading)
    ProgressBar mLoading;

    private Unbinder mUnbinder;
    private TweetAdapter mTweetAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ((AwesomeApplication) getApplication()).getApplicationComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mUnbinder = ButterKnife.bind(this);

        mTweetAdapter = new TweetAdapter();

        mListTweets.setLayoutManager(new LinearLayoutManager(this));
        mListTweets.setAdapter(mTweetAdapter);

        mSearchPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchPresenter.dettachView();
        mUnbinder.unbind();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void showTweets(List<Tweet> tweets) {
        mTweetAdapter.setTweets(tweets);
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
}
