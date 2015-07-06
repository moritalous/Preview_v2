
package forest.rice.field.k.preview.view.tweet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

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

import forest.rice.field.k.R;
import forest.rice.field.k.base.NavigationDrawerBaseInterface;
import forest.rice.field.k.preview.entity.Track;
import forest.rice.field.k.preview.entity.Tracks;
import forest.rice.field.k.preview.entity.TweetWithTrack;
import forest.rice.field.k.preview.entity.TweetsWithTrack;
import forest.rice.field.k.preview.manager.IntentManager;
import forest.rice.field.k.preview.view.base.BaseListFragment;
import forest.rice.field.k.preview.view.base.SwipeRefreshListFragment;
import forest.rice.field.k.preview.view.dialog.TrackSelectDialogFragment;
import forest.rice.field.k.preview.view.lyric.LyricActivity;
import forest.rice.field.k.preview.view.searchResultView.SearchResultAsyncTask.SearchResultAsyncTaskCallback;
import io.fabric.sdk.android.Fabric;

public class TweetFragment extends SwipeRefreshListFragment {

    private AppSession guestAppSession;
    private TweetsWithTrack tweets = new TweetsWithTrack();

    public TweetFragment() {
    }

    public static TweetFragment newInstance() {
        TweetFragment fragment = new TweetFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TwitterAuthConfig authConfig = new TwitterAuthConfig("25fC4QgQ3SPpxoF6J1b5CukQQ", "IICpWpLrWDXFMlqt091VG7iXN2ORV5jQ0rsHwuNRVuklGgOjw1");
        Fabric.with(getActivity(), new Twitter(authConfig));


        TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
            @Override
            public void success(Result<AppSession> result) {
                guestAppSession = result.data;

                TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient(guestAppSession);
                twitterApiClient.getSearchService().tweets("from:802NOWONAIR", null, null, null, "recent", 50, null, null, null, true, new Callback<Search>() {

                    @Override
                    public void success(Result<Search> result) {

                        System.out.println(result.data.tweets.size());

                        for (Tweet tweet : result.data.tweets) {
                            TweetWithTrack tweetWithTrack = new TweetWithTrack();
                            tweetWithTrack.tweet = tweet;
                            if (!tweetWithTrack.getTrackTitleAndArtist().equals("")) {
                                tweets.add(tweetWithTrack);
//                                tweetWithTrack.track();
                            }
                        }

                        TweetArrayAdapter adapter = new TweetArrayAdapter(getActivity(), tweets);
                        setListAdapter(adapter);
                    }

                    @Override
                    public void failure(TwitterException e) {
                        TweetArrayAdapter adapter = new TweetArrayAdapter(getActivity(), tweets);
                        setListAdapter(adapter);
                    }
                });
            }

            @Override
            public void failure(TwitterException e) {
                TweetArrayAdapter adapter = new TweetArrayAdapter(getActivity(), tweets);
                setListAdapter(adapter);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getActivity() instanceof NavigationDrawerBaseInterface) {
            NavigationDrawerBaseInterface activity = (NavigationDrawerBaseInterface) getActivity();
            activity.setChecked(R.id.nav_search);

            Menu menu = activity.getNavigationMenu();
            menu.findItem(R.id.nav_search).setVisible(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
