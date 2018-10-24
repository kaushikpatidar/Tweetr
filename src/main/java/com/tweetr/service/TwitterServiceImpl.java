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

import static java.util.stream.Collectors.toList;

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
        } catch (TwitterException e) {
            log.error("Failed to publish the tweet: " + e.getMessage(), e);
        } catch (Exception e){
            log.error("Error: " + e.getMessage(), e);
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

            List<TwitterPost> timelineTwitterPostList = new ArrayList<>();

            if(timelineStatusList.size()==0){
                log.info("Didn't retrieve any  posts from timeline");
                return timelineTwitterPostList;
            }

            timelineTwitterPostList = timelineStatusList.stream()
                    .map(timelineTwitterPost -> new TwitterPost(timelineTwitterPost.getText(),
                            new TwitterUser(timelineTwitterPost.getUser().getProfileImageURL(),
                                    timelineTwitterPost.getUser().getScreenName(),
                                    timelineTwitterPost.getUser().getName()), timelineTwitterPost.getCreatedAt()))
                    .collect(toList());

            log.info("Successfully retrieved " + timelineTwitterPostList.size() + " posts from timeline");
            return timelineTwitterPostList;
        } catch (TwitterException e) {
            log.error("Failed to obtain the user timeline: " + e.getMessage(), e);
        } catch (Exception e){
            log.error("Error: " + e.getMessage(), e);
        }
        return null;
    }

}

