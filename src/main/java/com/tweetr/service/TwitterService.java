package com.tweetr.service;

import com.tweetr.model.twitter.TwitterPost;
import twitter4j.Status;

import java.util.List;
import java.util.Optional;

public interface TwitterService {
    Optional<Status> postTweet(Optional<String> tweet) throws Exception;
    Optional<List<TwitterPost>> getTimelineTwitterPost() throws Exception;
}
