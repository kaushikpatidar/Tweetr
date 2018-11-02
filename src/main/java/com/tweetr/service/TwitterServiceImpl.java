package com.tweetr.service;

import com.tweetr.dropwizard.application.configuration.TwitterConfigurationBuilder;
import com.tweetr.model.twitter.TwitterPost;
import com.tweetr.model.twitter.TwitterUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class TwitterServiceImpl implements TwitterService{

    @Autowired
    private TwitterConfigurationBuilder twitterConfigurationBuilder;

    private Logger log = LoggerFactory.getLogger(TwitterService.class);

    @Override
    public Optional<Status> postTweet(Optional<String> tweet) {
        Optional<Status> status = Optional.empty();
        if(!tweet.isPresent()){
            return status;
        }
        try{
            status = Optional.ofNullable(twitterConfigurationBuilder.getTwitter().updateStatus(tweet.get()));
        } catch (TwitterException e) {
            log.error("Failed to publish the tweet: " + e.getMessage(), e);
        } catch (Exception e){
            log.error("Error: " + e.getMessage(), e);
        }
        return status;
    }

    @Override
    public Optional<List<TwitterPost>> getTimelineTwitterPost() {

        try {
            log.info("Getting the timeline from twitter");
            log.debug("User screen name: " + twitterConfigurationBuilder.getTwitter().getScreenName());

            Optional<List<Status>> timelineStatusList = Optional.ofNullable(twitterConfigurationBuilder.getTwitter().getHomeTimeline());

            if(!timelineStatusList.isPresent()){
                return Optional.empty();
            }

            Optional<List<TwitterPost>> timelineTwitterPostList = Optional.ofNullable(new ArrayList<>());

            if(timelineStatusList.get().size() == 0){
                log.info("Didn't retrieve any  posts from timeline");
                return timelineTwitterPostList;
            }

            timelineTwitterPostList = Optional.ofNullable(timelineStatusList.get().stream()
                    .map(timelineTwitterPost -> new TwitterPost(timelineTwitterPost.getText(),
                            new TwitterUser(timelineTwitterPost.getUser().getProfileImageURL(),
                                    timelineTwitterPost.getUser().getScreenName(),
                                    timelineTwitterPost.getUser().getName()), timelineTwitterPost.getCreatedAt(),
                                    Long.toString(timelineTwitterPost.getId())))
                    .collect(toList()));

            log.info("Successfully retrieved " + timelineTwitterPostList.get().size() + " posts from timeline");
            return timelineTwitterPostList;
        } catch (TwitterException e) {
            log.error("Failed to obtain the user timeline: " + e.getMessage(), e);
        } catch (Exception e){
            log.error("Error: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

}

