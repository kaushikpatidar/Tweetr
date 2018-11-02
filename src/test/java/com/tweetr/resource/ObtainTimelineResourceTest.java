package com.tweetr.resource;

import com.tweetr.dropwizard.application.cache.CacheConfigManager;
import com.tweetr.model.api.constants.APIResponse;
import com.tweetr.model.twitter.TwitterPost;
import com.tweetr.model.twitter.TwitterUser;
import com.tweetr.service.TwitterService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

public class ObtainTimelineResourceTest {

    @Mock
    private TwitterService twitterService;

    @Mock
    private CacheConfigManager cacheConfigManager;

    @InjectMocks
    private ObtainTimelineResource obtainTimelineResourceMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        reset(twitterService);
        reset(cacheConfigManager);
    }

    @Test
    public void getHomeTimeline() {
        Optional<List<TwitterPost>> timelineTwitterPost = Optional.of(new ArrayList<>());
        timelineTwitterPost.get().add(new TwitterPost(
                "Test Message",
                new TwitterUser("http://testprofile.jpg","Test Twitter Handle", "Test Username"),
                new Date(),
                "1"));

        when(cacheConfigManager.getObtainTimelineResponseDataFromCache(any())).thenReturn(timelineTwitterPost);

        Response responseExpected = Response.ok(timelineTwitterPost).build();
        assertEquals(responseExpected.getEntity().toString(),
                obtainTimelineResourceMock.getHomeTimeline().getEntity().toString());

        assertEquals(responseExpected.getStatus(),
                obtainTimelineResourceMock.getHomeTimeline().getStatus());
    }

    @Test
    public void getHomeTimelineWithEmptyResponse() {
        Optional<List<TwitterPost>> timelineTwitterPost = Optional.empty();

        when(cacheConfigManager.getObtainTimelineResponseDataFromCache(any())).thenReturn(timelineTwitterPost);

        Response responseExpected = Response.ok(APIResponse.OBTAIN_TIMELINE_NO_TWEETS_FOUND).build();
        assertEquals(responseExpected.getStatus(), obtainTimelineResourceMock.getHomeTimeline().getStatus());
    }

    @Test
    public void getFilteredHomeTimeline() {
        List<TwitterPost> timelineTwitterPost = (new ArrayList<>());
        timelineTwitterPost.add(new TwitterPost(
                "Test Message",
                new TwitterUser("http://testprofile.jpg","Test Twitter Handle", "Test Username"),
                new Date(),
                "1"));

        when(cacheConfigManager.getObtainTimelineResponseDataFromCache(any())).thenReturn(Optional.ofNullable(timelineTwitterPost));

        Response responseExpected = Response.ok(timelineTwitterPost).build();
        assertEquals(responseExpected.getEntity().toString(),
                obtainTimelineResourceMock.getFilteredHomeTimeline(Optional.of("Test")).getEntity().toString());

        assertEquals(responseExpected.getStatus(),
                obtainTimelineResourceMock.getFilteredHomeTimeline(Optional.of("Test")).getStatus());
    }

    @Test
    public void getFilteredHomeTimelineWithNoFilterMatchTweets() {
        List<TwitterPost> timelineTwitterPost = (new ArrayList<>());
        timelineTwitterPost.add(new TwitterPost(
                "Test Message",
                new TwitterUser("http://testprofile.jpg","Test Twitter Handle", "Test Username"),
                new Date(),
                "1"));

        when(cacheConfigManager.getObtainTimelineResponseDataFromCache(any())).thenReturn(Optional.ofNullable(timelineTwitterPost));

        Response responseExpected = Response.ok(APIResponse.OBTAIN_TIMELINE_NO_TWEETS_FOUND).build();
        assertEquals(responseExpected.getEntity().toString(),
                obtainTimelineResourceMock.getFilteredHomeTimeline(Optional.of("No Match Available")).getEntity().toString());

        assertEquals(responseExpected.getStatus(),
                obtainTimelineResourceMock.getFilteredHomeTimeline(Optional.of("No Match Available")).getStatus());
    }


    @Test
    public void getFilteredHomeTimelineWithEmptyTweetFilter() {
        Response responseExpected = Response.ok(APIResponse.OBTAIN_TIMELINE_NO_FILTER_PROVIDED).build();
        assertEquals(responseExpected.getEntity().toString(),
                obtainTimelineResourceMock.getFilteredHomeTimeline(Optional.empty()).getEntity().toString());

        assertEquals(responseExpected.getStatus(),
                obtainTimelineResourceMock.getFilteredHomeTimeline(Optional.empty()).getStatus());
    }

    @Test
    public void getFilteredHomeTimelineWithNullTweetMessage() {
        Response responseExpected = Response.ok(APIResponse.OBTAIN_TIMELINE_NO_FILTER_PROVIDED).build();
        assertEquals(responseExpected.getEntity().toString(),
                obtainTimelineResourceMock.getFilteredHomeTimeline(Optional.empty()).getEntity().toString());
        assertEquals(responseExpected.getStatus(),
                obtainTimelineResourceMock.getFilteredHomeTimeline(Optional.empty()).getStatus());
    }

    @Test
    public void getFilteredHomeTimelineWithEmptyResponse() {
        Optional<List<TwitterPost>> timelineTwitterPost = Optional.empty();

        when(cacheConfigManager.getObtainTimelineResponseDataFromCache(any())).thenReturn(timelineTwitterPost);

        Response responseExpected = Response.ok(APIResponse.OBTAIN_TIMELINE_NO_TWEETS_FOUND).build();
        assertEquals(responseExpected.getStatus(), obtainTimelineResourceMock.getFilteredHomeTimeline(Optional.of("Test")).getStatus());
    }
}