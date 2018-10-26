package com.tweetr.model.twitter;

import java.util.Date;
import java.util.Objects;

public class TwitterPost {
    private String message;
    private TwitterUser twitterUser;
    private Date createdAt;

    public TwitterPost(String message, TwitterUser twitterUser, Date createdAt) {
        this.message = message;
        this.twitterUser = twitterUser;
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TwitterUser getTwitterUser() {
        return twitterUser;
    }

    public void setTwitterUser(TwitterUser twitterUser) {
        this.twitterUser = twitterUser;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TwitterPost that = (TwitterPost) o;
        return Objects.equals(message, that.message) &&
                Objects.equals(twitterUser, that.twitterUser) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, twitterUser, createdAt);
    }

    @Override
    public String toString() {
        return "TwitterPost{" +
                "message='" + message + '\'' +
                ", twitterUser=" + twitterUser +
                ", createdAt=" + createdAt +
                '}';
    }
}
