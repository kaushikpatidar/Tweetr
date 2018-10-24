package com.tweetr.resource;

import com.codahale.metrics.annotation.Timed;
import com.tweetr.model.twitter.TwitterPost;
import com.tweetr.service.TwitterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/timeline")
@Produces(MediaType.APPLICATION_JSON)
public class ObtainTimelineResource {

    @Autowired
    private TwitterService twitterService;

    private Logger log = LoggerFactory.getLogger(ObtainTimelineResource.class);

    //@Inject
    public ObtainTimelineResource(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    public ObtainTimelineResource() {

    }

    @GET
    @Timed
    public Response getHomeTimeline() {
        log.info("Getting the timeline from twitter");

        List<TwitterPost> timelineTwitterPost = twitterService.getTimelineTwitterPost();
        if(timelineTwitterPost != null && timelineTwitterPost.size() > 0){
            log.info("Successfully retrieved " + timelineTwitterPost.size() + " posts from timeline");
            return Response.ok(timelineTwitterPost).build();
        }

        return  Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
