import twitter4j.*;

import java.io.IOException;
import java.util.List;

public class TweetrApp {
    private Twitter getTwitterInstance(){
        return new TwitterFactory().getInstance();
    }

    public String publishTweet(String tweet) {
        try {
            Twitter twitter = getTwitterInstance();
            Status status = twitter.updateStatus(tweet);
            return status.getText();
        } catch (TwitterException e) {
            System.out.println("Failed to publish the tweet: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public List<Status> getUserTimeline() {
        Twitter twitter = getTwitterInstance();
        try {
            User user = twitter.verifyCredentials();
            return twitter.getUserTimeline();
        } catch (TwitterException e) {
            System.out.println("Failed to obtain the user timeline: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return  null;
    }

    public static void main(String[] args){
        try {
            List<Status> statuses = new TweetrApp().getUserTimeline();
            if(statuses == null){
                return;
            }
            for (Status status:statuses) {
                System.out.println("Text:" + status.getText());
            }
        } catch (Exception e) {
            System.out.println("Failed due to following cause:");
            e.printStackTrace();
        }


        System.out.println(new TweetrApp().publishTweet("Hello Great Morning"));
    }
}
