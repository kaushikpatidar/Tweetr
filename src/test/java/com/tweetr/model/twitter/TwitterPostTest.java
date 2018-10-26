package com.tweetr.model.twitter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TwitterPostTest {

    private TwitterPost mockTwitterPost;
    private TwitterUser mockTwitterUser;
    private Date date = new Date();

    @Before
    public void setUp() throws Exception {
        mockTwitterUser = mock(TwitterUser.class);
        mockTwitterPost = mock(TwitterPost.class);

        when(mockTwitterPost.getMessage()).thenReturn("Test Twitter Post");
        when(mockTwitterPost.getCreatedAt()).thenReturn(date);
        when(mockTwitterPost.getTwitterUser()).thenReturn(mockTwitterUser);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getMessage() {
        assertEquals(mockTwitterPost.getMessage(), "Test Twitter Post");
    }

    @Test
    public void setMessage() {
        mockTwitterPost.setMessage("Test Twitter Post");
        verify(mockTwitterPost, times(1)).setMessage(anyString());
    }

    @Test
    public void getTwitterUser() {
        assertEquals(mockTwitterPost.getTwitterUser(), mockTwitterUser);
    }

    @Test
    public void setTwitterUser() {
        mockTwitterPost.setTwitterUser(mockTwitterUser);
        verify(mockTwitterPost, times(1)).setTwitterUser(any(TwitterUser.class));
    }

    @Test
    public void getCreatedAt() {
        assertEquals(mockTwitterPost.getCreatedAt(), date);
    }

    @Test
    public void setCreatedAt() {
        mockTwitterPost.setCreatedAt(date);
        verify(mockTwitterPost, times(1)).setCreatedAt(any(Date.class));
    }
}