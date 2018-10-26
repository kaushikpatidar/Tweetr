package com.tweetr.model.twitter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class TwitterUserTest {

    private TwitterUser mockTwitterUser;

    @Before
    public void setUp() throws Exception {
        mockTwitterUser = mock(TwitterUser.class);

        when(mockTwitterUser.getName()).thenReturn("Test Username");
        when(mockTwitterUser.getProfileImageUrl()).thenReturn("http://testprofile.jpg");
        when(mockTwitterUser.getTwitterHandle()).thenReturn("Test Twitter Handle");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getProfileImageUrl() {
        assertEquals(mockTwitterUser.getProfileImageUrl(), "http://testprofile.jpg");
    }

    @Test
    public void setProfileImageUrl() {
        mockTwitterUser.setProfileImageUrl("http://testprofile.jpg");
        verify(mockTwitterUser, times(1)).setProfileImageUrl(anyString());
    }

    @Test
    public void getTwitterHandle() {
        assertEquals(mockTwitterUser.getTwitterHandle(), "Test Twitter Handle");
    }

    @Test
    public void setTwitterHandle() {
        mockTwitterUser.setTwitterHandle("Test Twitter Handle");
        verify(mockTwitterUser, times(1)).setTwitterHandle(anyString());
    }

    @Test
    public void getName() {
        assertEquals(mockTwitterUser.getName(), "Test Username");
    }

    @Test
    public void setName() {
        mockTwitterUser.setTwitterHandle("Test Username");
        verify(mockTwitterUser, times(1)).setTwitterHandle(anyString());
    }
}