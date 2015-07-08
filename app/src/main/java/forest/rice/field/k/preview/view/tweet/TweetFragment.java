
package forest.rice.field.k.preview.view.tweet;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.View;

import forest.rice.field.k.R;
import forest.rice.field.k.base.NavigationDrawerBaseInterface;
import forest.rice.field.k.preview.entity.Tracks;
import forest.rice.field.k.preview.entity.TweetWithTrack;
import forest.rice.field.k.preview.entity.TweetsWithTrack;
import forest.rice.field.k.preview.request.ITunesApiSearchRequest;
import forest.rice.field.k.preview.request.Twitter802Request;
import forest.rice.field.k.preview.view.base.SwipeRefreshListFragment;

public class TweetFragment extends SwipeRefreshListFragment implements TweetAsyncTask.TweetAsyncTaskCallback {

    public TweetFragment() {
    }

    public static TweetFragment newInstance() {
        TweetFragment fragment = new TweetFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initiateRefresh();

        // BEGIN_INCLUDE (setup_refreshlistener)
        /**
         * Implement {@link SwipeRefreshLayout.OnRefreshListener}. When users do the "swipe to
         * refresh" gesture, SwipeRefreshLayout invokes
         * {@link SwipeRefreshLayout.OnRefreshListener#onRefresh onRefresh()}. In
         * {@link SwipeRefreshLayout.OnRefreshListener#onRefresh onRefresh()}, call a method that
         * refreshes the content. Call the same method in response to the Refresh action from the
         * action bar.
         */
        setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initiateRefresh();
            }
        });
        setColorSchemeResources(R.color.green, R.color.red, R.color.blue, R.color.yellow);
        // END_INCLUDE (setup_refreshlistener)
    }

    private void initiateRefresh() {
        TweetAsyncTask task = new TweetAsyncTask(getActivity(), this);
        task.execute();
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

    @Override
    public void onPostExecute(TweetsWithTrack tweetWithTracks) {
        this.tracks = tweetWithTracks.getTracks();
        TweetArrayAdapter adapter = new TweetArrayAdapter(getActivity(), tweetWithTracks);
        setListAdapter(adapter);
        setRefreshing(false);
    }
}
