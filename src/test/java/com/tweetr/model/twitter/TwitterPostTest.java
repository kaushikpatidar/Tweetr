package com.tweetr.model.twitter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TwitterPostTest {

    private TwitterPost twitterPostMock;
    private TwitterUser twitterUserMock;
    private Date date = new Date();

    @Before
    public void setUp() throws Exception {
        twitterUserMock = mock(TwitterUser.class);
        twitterPostMock = mock(TwitterPost.class);

        when(twitterPostMock.getMessage()).thenReturn("Test Twitter Post");
        when(twitterPostMock.getCreatedAt()).thenReturn(date);
        when(twitterPostMock.getTwitterUser()).thenReturn(twitterUserMock);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getMessage() {
        assertEquals("Test Twitter Post", twitterPostMock.getMessage());
    }

    @Test
    public void setMessage() {
        twitterPostMock.setMessage("Test Twitter Post");
        verify(twitterPostMock, times(1)).setMessage(any());
    }

    @Test
    public void getTwitterUser() {
        assertEquals(twitterUserMock, twitterPostMock.getTwitterUser());
    }

    @Test
    public void setTwitterUser() {
        twitterPostMock.setTwitterUser(twitterUserMock);
        verify(twitterPostMock, times(1)).setTwitterUser(any(TwitterUser.class));
    }

    @Test
    public void getCreatedAt() {
        assertEquals(date, twitterPostMock.getCreatedAt());
    }

    @Test
    public void setCreatedAt() {
        twitterPostMock.setCreatedAt(date);
        verify(twitterPostMock, times(1)).setCreatedAt(any(Date.class));
    }
}