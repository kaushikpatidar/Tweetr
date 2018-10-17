package com.tweetr.resource;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tweet")
@Produces(MediaType.APPLICATION_JSON)
public class PublishTweetResource {

    private Twitter twitter;
    private Logger log = LoggerFactory.getLogger(PublishTweetResource.class);

    public PublishTweetResource(Twitter twitter) {
        this.twitter = twitter;
    }

    @POST
    @Timed
    public Response publishTweet(@QueryParam("message") final String tweet) {

        try {
            log.info("Publishing the tweet:" + tweet);
            Status status = twitter.updateStatus(tweet);
            return Response.ok(status.getText()).build();
        } catch (TwitterException e) {
            log.error("Failed to publish the tweet: " + e.getMessage(), e);
        } catch (Exception e){
            log.error("Error occured while publishing the tweet: " + e.getMessage(), e);

        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}