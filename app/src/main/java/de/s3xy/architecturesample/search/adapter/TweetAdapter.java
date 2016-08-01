package de.s3xy.architecturesample.search.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.s3xy.architecturesample.R;
import de.s3xy.architecturesample.twitter.model.Tweet;

/**
 * @author Angelo Rüggeberg <s3xy4ngc@googlemail.com>
 */


public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.TweetViewHolder> {

    List<Tweet> mTweets = Collections.emptyList();

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TweetViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_tweet, parent, false));
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);
        Glide.with(holder.mTweetUserImage.getContext()).load(tweet.getUser().getProfileImageUrl()).into(holder.mTweetUserImage);
        holder.mTweetText.setText(tweet.getText());
        holder.mTweetUserName.setText(tweet.getUser().getName());
        holder.mTweetUserScreenName.setText(String.format("@%s", tweet.getUser().getScreenName()));

    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    public void setTweets(List<Tweet> tweets) {
        mTweets = tweets;
        notifyDataSetChanged();
    }

    static class TweetViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tweet_user_image)
        ImageView mTweetUserImage;
        @BindView(R.id.tweet_user_screen_name)
        TextView mTweetUserScreenName;
        @BindView(R.id.tweet_user_name)
        TextView mTweetUserName;
        @BindView(R.id.tweet_text)
        TextView mTweetText;

        public TweetViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
