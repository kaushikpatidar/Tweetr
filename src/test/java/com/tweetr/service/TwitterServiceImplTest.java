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
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.User;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TwitterServiceImplTest {

    @Mock
    private TwitterConfigurationBuilder twitterConfigurationBuilderMock;

    @InjectMocks
    TwitterService twitterServiceMock = new TwitterServiceImpl();

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
                twitterServiceMock.postTweet(Optional.of("Test Status")));
    }

    @Test
    public void postTweetWithEmptyTweet() throws Exception{
        assertEquals(Optional.empty(),
                twitterServiceMock.postTweet(Optional.empty()));
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
                twitterServiceMock.getTimelineTwitterPost());
    }

    //@Test
    public void getTimelineTwitterPost() throws Exception {
        User userMock = mock(User.class);
        when(userMock.getName()).thenReturn("Test Username");
        when(userMock.getProfileImageURL()).thenReturn("http://testprofile.jpg");
        when(userMock.getScreenName()).thenReturn("Test Twitter Handle");

        Status statusMock = mock(Status.class);
        when(statusMock.getText()).thenReturn("Test Status");
        when(statusMock.getUser()).thenReturn(userMock);

        Twitter twitterMock = mock(Twitter.class);
        when(twitterMock.getScreenName()).thenReturn("Test Screen Name");

        TwitterUser twitterUser = new TwitterUser("http://testprofile.jpg",
                "Test Twitter Handle",
                "Test Username");

        TwitterPost twitterPost = new TwitterPost("Test Twitter Post", twitterUser, new Date(), "1");

        List<TwitterPost> twitterPostList = new LinkedList<>();
        twitterPostList.add(twitterPost);

        List<Status> timelineStatusList = new LinkedList<>();
        timelineStatusList.add(statusMock);

        ResponseList<Status> statusResponseList = mock(ResponseList.class);


        when(twitterMock.getHomeTimeline()).thenReturn(statusResponseList);

        when(twitterConfigurationBuilderMock.getTwitter()).thenReturn(twitterMock);

        assertEquals(Optional.of(twitterPostList),
                twitterServiceMock.getTimelineTwitterPost());
    }
}