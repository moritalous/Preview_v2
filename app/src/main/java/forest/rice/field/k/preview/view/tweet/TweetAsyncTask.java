package forest.rice.field.k.preview.view.tweet;

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

import forest.rice.field.k.preview.entity.Tracks;
import forest.rice.field.k.preview.entity.TweetWithTrack;
import forest.rice.field.k.preview.entity.TweetsWithTrack;
import forest.rice.field.k.preview.request.ITunesApiSearchRequest;
import io.fabric.sdk.android.Fabric;

public class TweetAsyncTask extends AsyncTask<String, String, TweetsWithTrack> {
	
	public TweetAsyncTaskCallback callback;
	private Context context;

	private AppSession guestAppSession;
	private TweetsWithTrack tweets = new TweetsWithTrack();

	public TweetAsyncTask(Context context) {
		this.context = context;
	}

	@Override
	protected TweetsWithTrack doInBackground(String... params) {

		TwitterAuthConfig authConfig = new TwitterAuthConfig("25fC4QgQ3SPpxoF6J1b5CukQQ","IICpWpLrWDXFMlqt091VG7iXN2ORV5jQ0rsHwuNRVuklGgOjw1");
		Fabric.with(this.context, new Twitter(authConfig));


		TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
			@Override
			public void success(Result<AppSession> result) {
				guestAppSession = result.data;

				TwitterApiClient twitterApiClient =  TwitterCore.getInstance().getApiClient(guestAppSession);
				twitterApiClient.getSearchService().tweets("from:802NOWONAIR", null, null, null, "recent", 50, null, null, null, true, new Callback<Search>() {

					@Override
					public void success(Result<Search> result) {

						System.out.println(result.data.tweets.size());

						for(Tweet tweet:result.data.tweets)  {
							TweetWithTrack tweetWithTrack = new TweetWithTrack();
							tweetWithTrack.tweet = tweet;
							tweets.add(tweetWithTrack);
						}
					}

					@Override
					public void failure(TwitterException e) {

					}
				});
			}

			@Override
			public void failure(TwitterException e) {

			}
		});

		return tweets;
	}
	
	@Override
	protected void onPostExecute(TweetsWithTrack result) {
		super.onPostExecute(result);
		
		callback.callback(result);
	}
	
	public interface TweetAsyncTaskCallback {
		void callback(TweetsWithTrack result);
	}
}
