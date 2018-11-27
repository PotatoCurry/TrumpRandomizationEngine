import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ConfigurationBuilder cb = new ConfigurationBuilder(); //TODO: Env variables
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(System.getenv("TrumpRandomizationEngineConsumerKey"))
                .setOAuthConsumerSecret(System.getenv("TrumpRandomizationEngineConsumerSecret"))
                .setOAuthAccessToken(System.getenv("TrumpRandomizationEngineAccessToken"))
                .setOAuthAccessTokenSecret(System.getenv("TrumpRandomizationEngineTokenSecret"));
        Twitter twitter = new TwitterFactory(cb.build()).getInstance();
        try {
            List<Status> statuses;
            statuses = twitter.getUserTimeline("realDonaldTrump");
            int n = 0;
            for (Status status : statuses)
                n += getHash(status.getText());
            n *= Math.random();
            System.out.println(n);
        } catch (TwitterException te) {
            System.err.println("Error - Failed to fetch Trump tweets");
        }
    }

    private static int getHash(String str) {
        int[] hashArray = str.chars()
                .filter((n) -> (n != 32))
                .map((n) -> (n >= 'A' && n <= 'Z' ? (n + 32) * 31 : n * 31))
                .toArray();
        int hash = 0;
        for (int c : hashArray)
            hash = (int) Math.pow(hash + c, 2) % 139;
        return hash;
    }

}
