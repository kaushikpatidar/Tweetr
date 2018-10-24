package com.tweetr.service;

import com.tweetr.dropwizard.configuration.TwitterConfigurationBuilder;
import com.tweetr.model.twitter.TwitterPost;
import com.tweetr.model.twitter.TwitterUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.TwitterException;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class TwitterServiceImpl implements TwitterService{

    @Inject
    private TwitterConfigurationBuilder twitterConfigurationBuilder;

    private Logger log = LoggerFactory.getLogger(TwitterService.class);

    @Override
    public Status postTweet(String tweet) {
        Status status = null;
        if(tweet == null){
            return status;
        }
        try{
            status = twitterConfigurationBuilder.getTwitter().updateStatus(tweet);
        }

        catch (TwitterException e) {
            log.error("Failed to publish the tweet: " + e.getMessage(), e);
        }
        return status;
    }

    @Override
    public List<TwitterPost> getTimelineTwitterPost() {

        try {
            log.info("Getting the timeline from twitter");
            log.debug("User screen name: " + twitterConfigurationBuilder.getTwitter().getScreenName());

            List<Status> timelineStatusList = twitterConfigurationBuilder.getTwitter().getHomeTimeline();

            if(timelineStatusList == null){
                return null;
            }

            List<TwitterPost> timelineTwitterPost = new ArrayList<>();

            if(timelineStatusList.size()==0){
                log.info("Didn't retrieve any  posts from timeline");
                return timelineTwitterPost;
            }

            for (Status status: timelineStatusList) {
                TwitterUser twitterUser = new TwitterUser(status.getUser().getProfileImageURL(), status.getUser().getScreenName(), status.getUser().getName());
                TwitterPost twitterPost = new TwitterPost(status.getText(), twitterUser, status.getCreatedAt());
                timelineTwitterPost.add(twitterPost);
            }

            log.info("Successfully retrieved " + timelineTwitterPost.size() + " posts from timeline");
            return timelineTwitterPost;
        } catch (TwitterException e) {
            log.error("Failed to obtain the user timeline: " + e.getMessage(), e);
        } catch (Exception e){
            log.error("Error: " + e.getMessage(), e);
        }
        return null;
    }

}

