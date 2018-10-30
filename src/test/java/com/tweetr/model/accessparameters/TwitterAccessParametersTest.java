package com.tweetr.model.accessparameters;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TwitterAccessParametersTest {

    private TwitterAccessParameters twitterAccessParametersMock;

    @Before
    public void setUp() throws Exception {
        twitterAccessParametersMock = mock(TwitterAccessParameters.class);

        when(twitterAccessParametersMock.getConsumerKey()).thenReturn("<TEST_CONSUMER_KEY>");
        when(twitterAccessParametersMock.getConsumerSecret()).thenReturn("<TEST_CONSUMER_SECRET>");
        when(twitterAccessParametersMock.getAccessToken()).thenReturn("<TEST_ACCESS_TOKEN>");
        when(twitterAccessParametersMock.getAccessTokenSecret()).thenReturn("<TEST_ACCESS_TOKEN_SECRET>");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getConsumerKey() {
        assertEquals(twitterAccessParametersMock.getConsumerKey(), "<TEST_CONSUMER_KEY>");
    }

    @Test
    public void setConsumerKey() {
        twitterAccessParametersMock.setConsumerKey("<TEST_CONSUMER_KEY>");
        verify(twitterAccessParametersMock, times(1)).setConsumerKey(any());
    }

    @Test
    public void getConsumerSecret() {
        assertEquals("<TEST_CONSUMER_SECRET>", twitterAccessParametersMock.getConsumerSecret());
    }

    @Test
    public void setConsumerSecret() {
        twitterAccessParametersMock.setConsumerSecret("<TEST_CONSUMER_SECRET>");
        verify(twitterAccessParametersMock, times(1)).setConsumerSecret(any());
    }

    @Test
    public void getAccessToken() {
        assertEquals("<TEST_ACCESS_TOKEN>", twitterAccessParametersMock.getAccessToken());
    }

    @Test
    public void setAccessToken() {
        twitterAccessParametersMock.setAccessToken("<TEST_ACCESS_TOKEN>");
        verify(twitterAccessParametersMock, times(1)).setAccessToken(any());
    }

    @Test
    public void getAccessTokenSecret() {
        assertEquals("<TEST_ACCESS_TOKEN_SECRET>", twitterAccessParametersMock.getAccessTokenSecret());
    }

    @Test
    public void setAccessTokenSecret() {
        twitterAccessParametersMock.setAccessTokenSecret("<TEST_ACCESS_TOKEN_SECRET>");
        verify(twitterAccessParametersMock, times(1)).setAccessTokenSecret(any());
    }
}