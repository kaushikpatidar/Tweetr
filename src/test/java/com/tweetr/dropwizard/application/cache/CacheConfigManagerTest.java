package com.tweetr.dropwizard.application.cache;

import com.tweetr.model.twitter.TwitterPost;
import com.tweetr.model.twitter.TwitterUser;
import com.tweetr.service.TwitterService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

public class CacheConfigManagerTest {

    @Mock
    private TwitterService twitterServiceMock;

    @InjectMocks
    private CacheConfigManager cacheConfigManager;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        reset(twitterServiceMock);
    }

    @Test
    public void getObtainTimelineResponseDataFromCache() throws Exception {

        Optional<List<TwitterPost>> timelineTwitterPost = Optional.of(new ArrayList<>());
        timelineTwitterPost.get().add(new TwitterPost(
                "Test Message",
                new TwitterUser("http://testprofile.jpg","Test Twitter Handle", "Test Username"),
                new Date(),
                "1"));


        when(twitterServiceMock.getTimelineTwitterPost()).thenReturn(timelineTwitterPost);

        Optional<List<TwitterPost>> timelineTwitterPostResult = cacheConfigManager.getObtainTimelineResponseDataFromCache("Test Key");
        Optional<List<TwitterPost>> timelineTwitterPostResultCached = cacheConfigManager.getObtainTimelineResponseDataFromCache("Test Key");
        Assert.assertSame(timelineTwitterPostResult, timelineTwitterPostResultCached);
    }

    @Test
    public void getObtainTimelineResponseDataFromCacheWithExceptionFromTwitter() throws Exception {

        Optional<List<TwitterPost>> timelineTwitterPost = Optional.of(new ArrayList<>());
        timelineTwitterPost.get().add(new TwitterPost(
                "Test Message",
                new TwitterUser("http://testprofile.jpg","Test Twitter Handle", "Test Username"),
                new Date(),
                "1"));


        when(twitterServiceMock.getTimelineTwitterPost()).thenThrow(new Exception());

        Optional<List<TwitterPost>> timelineTwitterPostResult = cacheConfigManager.getObtainTimelineResponseDataFromCache("Test Key");
        Optional<List<TwitterPost>> timelineTwitterPostResultCached = cacheConfigManager.getObtainTimelineResponseDataFromCache("Test Key");
        Assert.assertSame(timelineTwitterPostResult, timelineTwitterPostResultCached);
    }

}