package com.tweetr.resource;

import com.codahale.metrics.annotation.Timed;
import com.tweetr.model.api.constants.APIResponse;
import com.tweetr.model.twitter.TwitterPost;
import com.tweetr.service.TwitterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/timeline")
@Produces(MediaType.APPLICATION_JSON)
public class ObtainTimelineResource {

    @Autowired
    private TwitterService twitterService;

    private Logger log = LoggerFactory.getLogger(ObtainTimelineResource.class);

    public ObtainTimelineResource(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    public ObtainTimelineResource() {

    }

    @GET
    @Timed
    public Response getHomeTimeline() {
        try {
            log.info("Getting the timeline from twitter");

            List<TwitterPost> timelineTwitterPost = twitterService.getTimelineTwitterPost();
            if(timelineTwitterPost != null && timelineTwitterPost.size() > 0){
                log.info("Successfully retrieved " + timelineTwitterPost.size() + " posts from timeline");
                return Response.ok(timelineTwitterPost).build();
            } else if(timelineTwitterPost != null){
                log.info("There are no posts from timeline");
                return Response.ok(timelineTwitterPost).build();
            }
        } catch (Exception e){
            log.error("Error: " + e.getMessage(), e);
        }
        return  Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @GET
    @Path("/filter")
    public Response getFilteredHomeTimeline(@QueryParam("filterString") final String tweetFilter){
        try {
            log.info("Getting the filtered timeline from twitter");

            if(tweetFilter == null || tweetFilter.length() == 0){
                return Response.ok(APIResponse.OBTAIN_TIMELINE_NO_FILTER_PROVIDED).build();
            }

            List<TwitterPost> timelineTwitterPost = twitterService.getTimelineTwitterPost();
            if(timelineTwitterPost != null && timelineTwitterPost.size() > 0){
                log.info("Successfully retrieved " + timelineTwitterPost.size() + " posts from timeline");

                List<TwitterPost> filteredTimelinePosts = timelineTwitterPost.stream()
                        .filter(post -> post.getMessage().contains(tweetFilter))
                        .collect(Collectors.toList());

                return Response.ok(filteredTimelinePosts).build();
            } else if(timelineTwitterPost != null){
                log.info("There are no posts from timeline");
                return Response.ok(timelineTwitterPost).build();
            }
        } catch (Exception e){
            log.error("Error: " + e.getMessage(), e);
        }
        return  Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
