package com.tweetr.dropwizard.configuration;

import com.tweetr.dropwizard.application.TweetrApp;
import com.tweetr.model.accessparameters.TwitterAccessParameters;
import org.yaml.snakeyaml.Yaml;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TwitterConfigurationBuilder {

    public Twitter getTwitter(){
        Yaml yaml = new Yaml();

        try( InputStream in = Files.newInputStream( Paths.get( TweetrApp.class.getResource("/config/access-config.yml").toURI()) ) ) {
            TwitterAccessParameters twitterAccessConfig = yaml.loadAs( in, TwitterAccessParameters.class );

            ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
            configurationBuilder.setDebugEnabled(true)
                    .setOAuthConsumerKey(twitterAccessConfig.getConsumerKey())
                    .setOAuthConsumerSecret(twitterAccessConfig.getConsumerSecret())
                    .setOAuthAccessToken(twitterAccessConfig.getAccessToken())
                    .setOAuthAccessTokenSecret(twitterAccessConfig.getAccessTokenSecret());

            return new TwitterFactory(configurationBuilder.build()).getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
