package com.tweetr.model.accessparameters;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class TwitterAccessParametersTest {

    private TwitterAccessParameters mockTwitterAccessParameters;

    @Before
    public void setUp() throws Exception {
        mockTwitterAccessParameters = mock(TwitterAccessParameters.class);

        when(mockTwitterAccessParameters.getConsumerKey()).thenReturn("<TEST_CONSUMER_KEY>");
        when(mockTwitterAccessParameters.getConsumerSecret()).thenReturn("<TEST_CONSUMER_SECRET>");
        when(mockTwitterAccessParameters.getAccessToken()).thenReturn("<TEST_ACCESS_TOKEN>");
        when(mockTwitterAccessParameters.getAccessTokenSecret()).thenReturn("<TEST_ACCESS_TOKEN_SECRET>");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getConsumerKey() {
        assertEquals(mockTwitterAccessParameters.getConsumerKey(), "<TEST_CONSUMER_KEY>");
    }

    @Test
    public void setConsumerKey() {
        mockTwitterAccessParameters.setConsumerKey("<TEST_CONSUMER_KEY>");
        verify(mockTwitterAccessParameters, times(1)).setConsumerKey(anyString());
    }

    @Test
    public void getConsumerSecret() {
        assertEquals(mockTwitterAccessParameters.getConsumerSecret(), "<TEST_CONSUMER_SECRET>");
    }

    @Test
    public void setConsumerSecret() {
        mockTwitterAccessParameters.setConsumerSecret("<TEST_CONSUMER_SECRET>");
        verify(mockTwitterAccessParameters, times(1)).setConsumerSecret(anyString());
    }

    @Test
    public void getAccessToken() {
        assertEquals(mockTwitterAccessParameters.getAccessToken(), "<TEST_ACCESS_TOKEN>");
    }

    @Test
    public void setAccessToken() {
        mockTwitterAccessParameters.setAccessToken("<TEST_ACCESS_TOKEN>");
        verify(mockTwitterAccessParameters, times(1)).setAccessToken(anyString());
    }

    @Test
    public void getAccessTokenSecret() {
        assertEquals(mockTwitterAccessParameters.getAccessTokenSecret(), "<TEST_ACCESS_TOKEN_SECRET>");
    }

    @Test
    public void setAccessTokenSecret() {
        mockTwitterAccessParameters.setAccessTokenSecret("<TEST_ACCESS_TOKEN_SECRET>");
        verify(mockTwitterAccessParameters, times(1)).setAccessTokenSecret(anyString());
    }
}