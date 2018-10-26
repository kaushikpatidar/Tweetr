package com.tweetr.resource;

import com.codahale.metrics.annotation.Timed;
import com.tweetr.dropwizard.application.cache.CacheConfigManager;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/timeline")
@Produces(MediaType.APPLICATION_JSON)
public class ObtainTimelineResource {

    @Autowired
    private TwitterService twitterService;

    @Autowired
    private CacheConfigManager cacheConfigManager;

    private Logger log = LoggerFactory.getLogger(ObtainTimelineResource.class);

    public ObtainTimelineResource() {

    }

    @GET
    @Timed
    public Response getHomeTimeline() {
        try {
            log.info("Getting the timeline from twitter");

            Optional<List<TwitterPost>> timelineTwitterPost =
                    cacheConfigManager.getObtainTimelineResponseDataFromCache(APIResponse.OBTAIN_TIMELINE_RESPONSE_KEY);

            if(timelineTwitterPost.isPresent() && timelineTwitterPost.get().size() > 0){
                log.info("Successfully retrieved " + timelineTwitterPost.get().size() + " posts from timeline");
                return Response.ok(timelineTwitterPost).build();
            } else if(timelineTwitterPost.isPresent()){
                log.info("There are no posts from timeline");
                return Response.ok(timelineTwitterPost).build();
            }
        } catch (Exception e){
            log.error("Error: " + e.getMessage(), e);
        }
        return  Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @GET
    @Timed
    @Path("/filter")
    public Response getFilteredHomeTimeline(@QueryParam("filterString") final Optional<String> tweetFilter){
        try {
            log.info("Getting the filtered timeline from twitter");

            if(!tweetFilter.isPresent() || tweetFilter.get().length() == 0){
                return Response.ok(APIResponse.OBTAIN_TIMELINE_NO_FILTER_PROVIDED).build();
            }

            Optional<List<TwitterPost>> timelineTwitterPost = cacheConfigManager.getObtainTimelineResponseDataFromCache(APIResponse.OBTAIN_TIMELINE_RESPONSE_KEY);
            if(timelineTwitterPost.isPresent() && timelineTwitterPost.get().size() > 0){
                log.info("Successfully retrieved " + timelineTwitterPost.get().size() + " posts from service");

                List<TwitterPost> filteredTimelinePosts = timelineTwitterPost.get().stream()
                        .filter(post -> post.getMessage().contains(tweetFilter.get()))
                        .collect(Collectors.toList());

                if(filteredTimelinePosts.size() == 0){
                    log.info("No posts from timeline after filtering with string:" + tweetFilter.get());
                    return Response.ok(APIResponse.OBTAIN_TIMELINE_NO_TWEETS_FOUND).build();
                }

                log.info("After filtering, retrieved  " + timelineTwitterPost.get().size() + " posts from timeline");
                return Response.ok(filteredTimelinePosts).build();
            } else if(timelineTwitterPost.isPresent()){
                log.info("There are no posts from timeline");
                return Response.ok(timelineTwitterPost).build();
            }
        } catch (Exception e){
            log.error("Error: " + e.getMessage(), e);
        }
        return  Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
