package com.tweetr.spring.configuration;

import com.tweetr.resource.ObtainTimelineResource;
import com.tweetr.resource.PublishTweetResource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TwitterAppSpringConfigurationTest {

    private TwitterAppSpringConfiguration twitterAppSpringConfiguration;

    @Before
    public void setUp() throws Exception {
        twitterAppSpringConfiguration = new TwitterAppSpringConfiguration();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getPublishTweetResource() {
        Assert.assertNotNull(twitterAppSpringConfiguration.getPublishTweetResource());
        Assert.assertTrue(twitterAppSpringConfiguration.getPublishTweetResource() instanceof PublishTweetResource);
    }

    @Test
    public void getObtainTimelineResource() {
        Assert.assertNotNull(twitterAppSpringConfiguration.getObtainTimelineResource());
        Assert.assertTrue(twitterAppSpringConfiguration.getObtainTimelineResource() instanceof ObtainTimelineResource);
    }
}