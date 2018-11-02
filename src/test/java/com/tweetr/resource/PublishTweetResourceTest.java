package com.tweetr.resource;

import com.tweetr.model.api.constants.APIResponse;
import com.tweetr.service.TwitterService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import twitter4j.Status;

import javax.ws.rs.core.Response;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PublishTweetResourceTest {

    @Mock
    private TwitterService twitterServiceMock;

    @InjectMocks
    private PublishTweetResource publishTweetResourceMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        reset(twitterServiceMock);
    }

    @Test
    public void publishTweet() throws Exception {
        Status statusMock = mock(Status.class);
        when(statusMock.getText()).thenReturn("Test Status Text");

        when(twitterServiceMock.postTweet(any())).thenReturn(Optional.of(statusMock));

        Response responseExpected = Response.ok(APIResponse.PUBLISH_MESSAGE_SUCCESS + " " + statusMock.getText()).build();
        assertEquals(responseExpected.getEntity().toString(),
                publishTweetResourceMock.publishTweet(Optional.of("Test Status Text")).getEntity().toString());

        assertEquals(responseExpected.getStatus(),
                publishTweetResourceMock.publishTweet(Optional.of("Test Status Text")).getStatus());

    }

    @Test
    public void publishTweetWithEmptyTweet() {
        Response responseExpected = Response.ok(APIResponse.PUBLISH_NO_TWEET_MESSAGE_PROVIDED).build();
        assertEquals(responseExpected.getEntity().toString(),
                publishTweetResourceMock.publishTweet(Optional.empty()).getEntity().toString());
        assertEquals(responseExpected.getStatus(),
                publishTweetResourceMock.publishTweet(Optional.empty()).getStatus());
    }

    @Test
    public void publishTweetWithNullTweetMessage() {
        Response responseExpected = Response.ok(APIResponse.PUBLISH_NO_TWEET_MESSAGE_PROVIDED).build();
        assertEquals(responseExpected.getEntity().toString(),
                publishTweetResourceMock.publishTweet(Optional.ofNullable(null)).getEntity().toString());
        assertEquals(responseExpected.getStatus(),
                publishTweetResourceMock.publishTweet(Optional.empty()).getStatus());
    }

    @Test
    public void publishTweetWithExceptionFromTwitterService() throws Exception{
        when(twitterServiceMock.postTweet(any())).thenReturn(Optional.empty());

        Response responseExpected = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        assertEquals(responseExpected.getStatus(),
                publishTweetResourceMock.publishTweet(Optional.ofNullable("Test Status Text")).getStatus());
    }
}