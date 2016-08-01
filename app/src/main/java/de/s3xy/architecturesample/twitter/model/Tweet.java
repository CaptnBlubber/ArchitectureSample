package de.s3xy.architecturesample.twitter.model;

/**
 * @author Angelo Rüggeberg <s3xy4ngc@googlemail.com>
 */

public class Tweet {

    String text;
    boolean truncated;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isTruncated() {
        return truncated;
    }

    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }
}
