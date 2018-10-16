package com.tweetr.resource;

import com.codahale.metrics.annotation.Timed;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

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

    public PublishTweetResource(Twitter twitter) {
        this.twitter = twitter;
    }

    @POST
    @Timed
    public Response publishTweet(@QueryParam("message") String tweet) {

        try {
            Status status = twitter.updateStatus(tweet);
            return Response.ok(status.getText()).build();
        } catch (TwitterException e) {
            System.out.println("Failed to publish the tweet: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}