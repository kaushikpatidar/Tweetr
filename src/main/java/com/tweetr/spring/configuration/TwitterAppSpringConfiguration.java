package com.tweetr.spring.configuration;

import com.tweetr.dropwizard.configuration.TwitterConfigurationBuilder;
import com.tweetr.resource.ObtainTimelineResource;
import com.tweetr.resource.PublishTweetResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.tweetr.resource", "com.tweetr.service", "com.tweetr.dropwizard.configuration", "com.tweetr.spring.configuration"})
public class TwitterAppSpringConfiguration {

    @Bean
    public static TwitterConfigurationBuilder getTwitterConfigurationBuilder(){
        return new TwitterConfigurationBuilder();
    }

    @Bean
    public static PublishTweetResource getPublishTweetResource(){
        return new PublishTweetResource();
    }

    @Bean
    public static ObtainTimelineResource getObtainTimelineResource(){
        return new ObtainTimelineResource();
    }
}
