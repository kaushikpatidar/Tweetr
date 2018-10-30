package com.tweetr.dropwizard.application.configuration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import twitter4j.Twitter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TwitterConfigurationBuilderTest {

    private TwitterConfigurationBuilder mockTwitterConfigurationBuilderMock;
    private Twitter mockTwitter;

    @Before
    public void setUp() throws Exception {
        mockTwitterConfigurationBuilderMock = mock(TwitterConfigurationBuilder.class);
        mockTwitter = mock(Twitter.class);

        when(mockTwitterConfigurationBuilderMock.getTwitter()).thenReturn(mockTwitter);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getTwitter() {
        assertEquals(mockTwitter, mockTwitterConfigurationBuilderMock.getTwitter());
    }
}