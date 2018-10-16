package com.tweetr.application;

import com.tweetr.configuration.TweetrAppConfiguration;
import com.tweetr.configuration.TwitterAccessConfiguration;
import com.tweetr.resource.ObtainTimelineResource;
import com.tweetr.resource.PublishTweetResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.yaml.snakeyaml.Yaml;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TweetrApp extends Application<TweetrAppConfiguration> {
    public static void main(String[] args) throws Exception {
        new TweetrApp().run(args);
    }

    @Override
    public String getName() {
        return "twitter";
    }

    @Override
    public void initialize(Bootstrap<TweetrAppConfiguration> bootstrap) {

    }

    @Override
    public void run(TweetrAppConfiguration configuration,
                    Environment environment) {

        Yaml yaml = new Yaml();

        try( InputStream in = Files.newInputStream( Paths.get( TweetrApp.class.getResource("/config/access-config.yml").toURI()) ) ) {
            TwitterAccessConfiguration  twitterAccessConfig = yaml.loadAs( in, TwitterAccessConfiguration.class );

            ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
            configurationBuilder.setDebugEnabled(true)
                    .setOAuthConsumerKey(twitterAccessConfig.getConsumerKey())
                    .setOAuthConsumerSecret(twitterAccessConfig.getConsumerSecret())
                    .setOAuthAccessToken(twitterAccessConfig.getAccessToken())
                    .setOAuthAccessTokenSecret(twitterAccessConfig.getAccessTokenSecret());

            Twitter twitter = new TwitterFactory(configurationBuilder.build()).getInstance();

            environment.jersey().register(new PublishTweetResource(twitter));
            environment.jersey().register(new ObtainTimelineResource(twitter));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}