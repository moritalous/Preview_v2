package forest.rice.field.k.preview.request;

import android.content.Context;
import android.os.AsyncTask;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.concurrent.CountDownLatch;

import forest.rice.field.k.BuildConfig;
import forest.rice.field.k.preview.entity.TweetWithTrack;
import forest.rice.field.k.preview.entity.TweetsWithTrack;
import io.fabric.sdk.android.Fabric;

public class Twitter802Request {
    private Context context;

    private AppSession guestAppSession;

    private TweetsWithTrack tweets = new TweetsWithTrack();

    public Twitter802Request(Context context) {
        this.context = context;

        TwitterAuthConfig authConfig = new TwitterAuthConfig(BuildConfig.TWITTER_KEY, BuildConfig.TWITTER_SECRET);
        Fabric.with(context, new Twitter(authConfig));
    }

    public TweetsWithTrack requestForSync() {
        final CountDownLatch latch = new CountDownLatch(1);


        TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
            @Override
            public void success(Result<AppSession> result) {
                guestAppSession = result.data;

                TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient(guestAppSession);
                twitterApiClient.getSearchService().tweets("from:802NOWONAIR", null, "ja", "ja", "recent", 20, null, null, null, true, new Callback<Search>() {

                    @Override
                    public void success(Result<Search> result) {

                        for (Tweet tweet : result.data.tweets) {
                            final TweetWithTrack tweetWithTrack = new TweetWithTrack();
                            tweetWithTrack.tweet = tweet;
                            if (!tweetWithTrack.getTrackTitleAndArtist().equals("")) {
                                tweets.add(tweetWithTrack);
                            }
                        }

                        latch.countDown();
                    }

                    @Override
                    public void failure(TwitterException e) {
                        latch.countDown();
                    }
                });
            }

            @Override
            public void failure(TwitterException e) {
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (TweetWithTrack tweet : tweets) {
            tweet.track();
        }

        return tweets;
    }
}
