package com.tweetr.resource;

import com.codahale.metrics.annotation.Timed;
import com.tweetr.model.api.constants.APIResponse;
import com.tweetr.service.TwitterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import twitter4j.Status;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tweet")
@Produces(MediaType.APPLICATION_JSON)
public class PublishTweetResource {

    @Autowired
    private TwitterService twitterService;
    private Logger log = LoggerFactory.getLogger(PublishTweetResource.class);

    //@Inject
    public PublishTweetResource(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    public PublishTweetResource() {

    }

    @POST
    @Timed
    public Response publishTweet(@QueryParam("message") final String tweet) {

        try {
            log.info("Publishing the tweet:" + tweet);
            Status status = twitterService.postTweet(tweet);
            if(status != null){
                return Response.ok( APIResponse.PUBLISH_MESSAGE_SUCCESS + status.getText()).build();
            }

        } catch (Exception e){
            log.error("Error occured while publishing the tweet: " + e.getMessage(), e);

        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}