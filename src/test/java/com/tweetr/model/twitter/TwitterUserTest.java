package com.tweetr.model.twitter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class TwitterUserTest {

    private TwitterUser twitterUserMock;

    @Before
    public void setUp() throws Exception {
        twitterUserMock = mock(TwitterUser.class);

        when(twitterUserMock.getProfileImageUrl()).thenReturn("http://testprofile.jpg");
        when(twitterUserMock.getTwitterHandle()).thenReturn("Test Twitter Handle");
        when(twitterUserMock.getName()).thenReturn("Test Username");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getProfileImageUrl() {
        assertEquals("http://testprofile.jpg", twitterUserMock.getProfileImageUrl());
    }

    @Test
    public void setProfileImageUrl() {
        twitterUserMock.setProfileImageUrl("http://testprofile.jpg");
        verify(twitterUserMock, times(1)).setProfileImageUrl(any());
    }

    @Test
    public void getTwitterHandle() {
        assertEquals("Test Twitter Handle", twitterUserMock.getTwitterHandle());
    }

    @Test
    public void setTwitterHandle() {
        twitterUserMock.setTwitterHandle("Test Twitter Handle");
        verify(twitterUserMock, times(1)).setTwitterHandle(any());
    }

    @Test
    public void getName() {
        assertEquals("Test Username", twitterUserMock.getName());
    }

    @Test
    public void setName() {
        twitterUserMock.setTwitterHandle("Test Username");
        verify(twitterUserMock, times(1)).setTwitterHandle(any());
    }
}