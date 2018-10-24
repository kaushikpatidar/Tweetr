package com.tweetr.service;

import com.tweetr.model.twitter.TwitterPost;
import twitter4j.Status;

import java.util.List;

public interface TwitterService {
    Status postTweet(String tweet);
    List<TwitterPost> getTimelineTwitterPost();
}
