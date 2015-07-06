package forest.rice.field.k.preview.request;

import android.content.Context;

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

import forest.rice.field.k.preview.entity.TweetWithTrack;
import forest.rice.field.k.preview.entity.TweetsWithTrack;
import io.fabric.sdk.android.Fabric;

public class Twitter802Request {
    private Context context;

    private AppSession guestAppSession;

    private TweetsWithTrack tweets = new TweetsWithTrack();

    private Twitter802RequestCallback callback;

    public Twitter802Request(Context context, Twitter802RequestCallback callback) {
        this.context = context;
        this.callback = callback;

        TwitterAuthConfig authConfig = new TwitterAuthConfig("25fC4QgQ3SPpxoF6J1b5CukQQ", "IICpWpLrWDXFMlqt091VG7iXN2ORV5jQ0rsHwuNRVuklGgOjw1");
        Fabric.with(context, new Twitter(authConfig));
    }

    public void request() {
        TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
            @Override
            public void success(Result<AppSession> result) {
                guestAppSession = result.data;

                TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient(guestAppSession);
                twitterApiClient.getSearchService().tweets("from:802NOWONAIR", null, null, null, "recent", 50, null, null, null, true, new Callback<Search>() {

                    @Override
                    public void success(Result<Search> result) {

                        for (Tweet tweet : result.data.tweets) {
                            TweetWithTrack tweetWithTrack = new TweetWithTrack();
                            tweetWithTrack.tweet = tweet;
                            if (!tweetWithTrack.getTrackTitleAndArtist().equals("")) {
                                tweets.add(tweetWithTrack);
                            }
                        }

                        callback.callback(tweets);
                    }

                    @Override
                    public void failure(TwitterException e) {
                        callback.callback(tweets);
                    }
                });
            }

            @Override
            public void failure(TwitterException e) {
                callback.callback(tweets);
            }
        });
    }

    public interface Twitter802RequestCallback {
        public void callback(TweetsWithTrack tweets);
    }
}
