package com.tweetr.resource;

import com.codahale.metrics.annotation.Timed;
import com.tweetr.model.TwitterPost;
import com.tweetr.model.TwitterUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/timeline")
@Produces(MediaType.APPLICATION_JSON)
public class ObtainTimelineResource {

    private Twitter twitter;
    private Logger log = LoggerFactory.getLogger(ObtainTimelineResource.class);

    public ObtainTimelineResource(Twitter twitter) {
        this.twitter = twitter;
    }

    @GET
    @Timed
    public Response getHomeTimeline() {

        try {
            log.info("Getting the timeline from twitter");
            log.debug("User screen name: " + twitter.getScreenName());

            List<Status> timelineStatusList = twitter.getHomeTimeline();

            List<TwitterPost> timelineTwitterPost = new ArrayList<>();
            for (Status status: timelineStatusList) {
                TwitterUser twitterUser = new TwitterUser(status.getUser().getProfileImageURL(), status.getUser().getScreenName(), status.getUser().getName());
                TwitterPost twitterPost = new TwitterPost(status.getText(), twitterUser, status.getCreatedAt());
                timelineTwitterPost.add(twitterPost);
            }

            log.info("Successfully retrieved " + timelineTwitterPost.size() + " posts from timeline");
            return Response.ok(timelineTwitterPost).build();
        } catch (TwitterException e) {
            log.error("Failed to obtain the user timeline: " + e.getMessage(), e);
        } catch (Exception e){
            log.error("Error: " + e.getMessage(), e);
        }
        return  Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
