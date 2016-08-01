package de.s3xy.architecturesample.search.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.s3xy.architecturesample.R;

/**
 * @author Angelo Rüggeberg <s3xy4ngc@googlemail.com>
 */

public class TweetViewHolder extends RecyclerView.ViewHolder {
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
