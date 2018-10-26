package com.tweetr.model.api.constants;

public interface APIResponse {
    String PUBLISH_MESSAGE_SUCCESS = "Tweet Successfully Posted";
    String PUBLISH_MESSAGE_FAILED = "Tweet Posting Failed";
    String PUBLISH_NO_TWEET_MESSAGE_PROVIDED = "Please provide a tweet message";

    String OBTAIN_TIMELINE_RESPONSE_KEY = "ObtainTimelineResponse";
    String OBTAIN_TIMELINE_NO_FILTER_PROVIDED = "Please provide a filter string";
    String OBTAIN_TIMELINE_NO_TWEETS_FOUND = "No tweets found for the request";

    String ERROR_RESPONSE = "Some Error Occurred! Please try after sometime";
}
