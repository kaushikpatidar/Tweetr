package com.tweetr.service;

import com.tweetr.dropwizard.application.configuration.TwitterConfigurationBuilder;
import com.tweetr.model.twitter.TwitterPost;
import com.tweetr.model.twitter.TwitterUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import twitter4j.*;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TwitterServiceImplTest {

    @Mock
    private TwitterConfigurationBuilder twitterConfigurationBuilderMock;

    @InjectMocks
    TwitterService twitterService = new TwitterServiceImpl();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void postTweet() throws Exception {
        Twitter twitterMock = mock(Twitter.class);

        Status statusMock = mock(Status.class);
        when(statusMock.getText()).thenReturn("Test Status");

        when(twitterMock.updateStatus(anyString())).thenReturn(statusMock);

        when(twitterConfigurationBuilderMock.getTwitter()).thenReturn(twitterMock);

        assertEquals(Optional.of(statusMock),
                twitterService.postTweet(Optional.of("Test Status")));
    }

    @Test
    public void postTweetWithEmptyTweet() throws Exception {
        assertEquals(Optional.empty(),
                twitterService.postTweet(Optional.empty()));
    }

    @Test (expected = Exception.class)
    public void postTweetWithTwitterException() throws Exception {
        Twitter twitterMock = mock(Twitter.class);

        Status statusMock = mock(Status.class);
        when(statusMock.getText()).thenReturn("Test Status");

        when(twitterMock.updateStatus(anyString())).thenThrow(new TwitterException(new Exception()));

        when(twitterConfigurationBuilderMock.getTwitter()).thenReturn(twitterMock);

        twitterService.postTweet(Optional.of("Test Status"));
    }

    @Test (expected = Exception.class)
    public void postTweetWithException() throws Exception {
        Twitter twitterMock = mock(Twitter.class);

        when(twitterMock.updateStatus(anyString())).thenThrow(new TwitterException(new Exception()));

        when(twitterConfigurationBuilderMock.getTwitter()).thenThrow(new Exception());

        twitterService.postTweet(Optional.of("Test Status"));
    }

    @Test
    public void getTimelineTwitterPostWithEmptyTimelineResponse() throws Exception {
        Status statusMock = mock(Status.class);
        when(statusMock.getText()).thenReturn("Test Status");

        Twitter twitterMock = mock(Twitter.class);
        when(twitterMock.getScreenName()).thenReturn("Test Screen Name");
        when(twitterMock.getHomeTimeline()).thenReturn(null);

        when(twitterConfigurationBuilderMock.getTwitter()).thenReturn(twitterMock);

        assertEquals(Optional.empty(),
                twitterService.getTimelineTwitterPost());
    }

    @Test
    public void getTimelineTwitterPost() throws Exception {

        Date testDate = new Date();

        User userMock = mock(User.class);
        when(userMock.getName()).thenReturn("Test Username");
        when(userMock.getProfileImageURL()).thenReturn("http://testprofile.jpg");
        when(userMock.getScreenName()).thenReturn("Test Twitter Handle");

        Status statusMock = mock(Status.class);
        when(statusMock.getText()).thenReturn("Test Twitter Post");
        when(statusMock.getUser()).thenReturn(userMock);
        when(statusMock.getCreatedAt()).thenReturn(testDate);
        when(statusMock.getId()).thenReturn(new Long(1));

        Twitter twitterMock = mock(Twitter.class);
        when(twitterMock.getScreenName()).thenReturn("Test Screen Name");

        TwitterUser twitterUser = new TwitterUser("http://testprofile.jpg",
                "Test Twitter Handle",
                "Test Username");

        TwitterPost twitterPost = new TwitterPost("Test Twitter Post", twitterUser, testDate, "1");

        List<TwitterPost> twitterPostList = new LinkedList<>();
        twitterPostList.add(twitterPost);

        List<Status> timelineStatusList = new LinkedList<>();
        timelineStatusList.add(statusMock);

        ResponseList<Status> timelineTwitterPostListMock = new DummyResponseList<>();
        timelineTwitterPostListMock.add(statusMock);

        when(twitterMock.getHomeTimeline()).thenReturn(timelineTwitterPostListMock);

        when(twitterConfigurationBuilderMock.getTwitter()).thenReturn(twitterMock);

        assertEquals(Optional.of(twitterPostList).toString(),
                twitterService.getTimelineTwitterPost().toString());
    }

    @Test
    public void getTimelineTwitterPostWithEmptyResult() throws Exception {

        Twitter twitterMock = mock(Twitter.class);
        when(twitterMock.getScreenName()).thenReturn("Test Screen Name");

        ResponseList<Status> timelineTwitterPostListMock = new DummyResponseList<>();

        when(twitterMock.getHomeTimeline()).thenReturn(timelineTwitterPostListMock);

        when(twitterConfigurationBuilderMock.getTwitter()).thenReturn(twitterMock);

        assertEquals(0 ,
                twitterService.getTimelineTwitterPost().get().size());
    }

    @Test (expected = Exception.class)
    public void getTimelineTwitterPostWithTwitterException() throws Exception {
        Twitter twitterMock = mock(Twitter.class);

        Status statusMock = mock(Status.class);
        when(statusMock.getText()).thenReturn("Test Status");

        when(twitterMock.getHomeTimeline()).thenThrow(new TwitterException(new Exception()));

        when(twitterConfigurationBuilderMock.getTwitter()).thenReturn(twitterMock);

        twitterService.getTimelineTwitterPost();
    }

    @Test (expected = Exception.class)
    public void getTimelineTwitterPostWithException() throws Exception {
        Twitter twitterMock = mock(Twitter.class);

        when(twitterMock.getHomeTimeline()).thenThrow(new TwitterException(new Exception()));

        when(twitterConfigurationBuilderMock.getTwitter()).thenThrow(new Exception());

        twitterService.getTimelineTwitterPost();
    }

    public class DummyResponseList<T> extends ArrayList<T>
            implements ResponseList<T>
    {

        @Override
        public RateLimitStatus getRateLimitStatus ()
        {
            return TwitterServiceImplTest.getRateLimitStatus();
        }

        @Override
        public int getAccessLevel ()
        {
            return 0;
        }
    }

    public static RateLimitStatus getRateLimitStatus ()
    {
        RateLimitStatus mockRateLimitStatus = mock(RateLimitStatus.class);
        when(mockRateLimitStatus.getLimit()).thenReturn(0);
        when(mockRateLimitStatus.getRemaining()).thenReturn(0);
        when(mockRateLimitStatus.getSecondsUntilReset()).thenReturn(0);
        return mockRateLimitStatus;
    }
}