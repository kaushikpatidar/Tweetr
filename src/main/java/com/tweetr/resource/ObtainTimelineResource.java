package com.tweetr.resource;

import com.codahale.metrics.annotation.Timed;
import twitter4j.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/timeline")
@Produces(MediaType.APPLICATION_JSON)
public class ObtainTimelineResource {

    private Twitter twitter;

    public ObtainTimelineResource(Twitter twitter) {
        this.twitter = twitter;
    }

    @GET
    @Timed
    public Response getHomeTimeline() {

        try {
            User user = twitter.verifyCredentials();
            List<Status> timelineStatusList = twitter.getHomeTimeline();
            List<String> timelineStatusText = new ArrayList<>();
            for (Status status: timelineStatusList) {
                timelineStatusText.add(status.getText());
            }
            return Response.ok(timelineStatusText).build();
        } catch (TwitterException e) {
            System.out.println("Failed to obtain the user timeline: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return  Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}