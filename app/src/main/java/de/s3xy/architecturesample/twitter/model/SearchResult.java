package de.s3xy.architecturesample.twitter.model;

import java.util.List;

/**
 * @author Angelo Rüggeberg <s3xy4ngc@googlemail.com>
 */

public class SearchResult {
    List<Tweet> statuses;

    public List<Tweet> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Tweet> statuses) {
        this.statuses = statuses;
    }
}
