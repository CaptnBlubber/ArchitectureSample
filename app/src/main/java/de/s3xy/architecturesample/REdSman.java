package de.s3xy.architecturesample;

import android.util.Log;

import javax.inject.Inject;

import de.s3xy.architecturesample.twitter.api.TwitterApi;
import de.s3xy.architecturesample.twitter.model.Tweet;

public class REdSman {

    @Inject
    TwitterApi t;

    public REdSman() {
    }

    public static void doStuff() {
        t.searchTweets("test")
                .subscribe(q -> {
                    for (Tweet s : q.getStatuses()) {
                        if (s.getText().equals("delete")) {
                            q.getStatuses().remove(s);
                        }
                    }

                    Log.d("TEST", "Loaded Stuff: " + q + ", Tweets: " + q.getStatuses());

                });
    }
}
