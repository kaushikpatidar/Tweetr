package com.tweetr.model.twitter;

import java.util.Objects;

public class TwitterUser {
    private String profileImageUrl;
    private String twitterHandle;
    private String name;

    public TwitterUser(String profileImageUrl, String twitterHandle, String name) {
        this.profileImageUrl = profileImageUrl;
        this.twitterHandle = twitterHandle;
        this.name = name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TwitterUser that = (TwitterUser) o;
        return Objects.equals(profileImageUrl, that.profileImageUrl) &&
                Objects.equals(twitterHandle, that.twitterHandle) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileImageUrl, twitterHandle, name);
    }

    @Override
    public String toString() {
        return "TwitterUser{" +
                "profileImageUrl='" + profileImageUrl + '\'' +
                ", twitterHandle='" + twitterHandle + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
