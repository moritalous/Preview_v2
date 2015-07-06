
package forest.rice.field.k.preview.view.tweet;

import android.os.Bundle;
import android.view.Menu;

import forest.rice.field.k.R;
import forest.rice.field.k.base.NavigationDrawerBaseInterface;
import forest.rice.field.k.preview.entity.TweetsWithTrack;
import forest.rice.field.k.preview.request.Twitter802Request;
import forest.rice.field.k.preview.view.base.SwipeRefreshListFragment;

public class TweetFragment extends SwipeRefreshListFragment implements Twitter802Request.Twitter802RequestCallback {

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
    public void onStart() {
        super.onStart();

        if (getActivity() instanceof NavigationDrawerBaseInterface) {
            NavigationDrawerBaseInterface activity = (NavigationDrawerBaseInterface) getActivity();
            activity.setChecked(R.id.nav_search);

            Menu menu = activity.getNavigationMenu();
            menu.findItem(R.id.nav_search).setVisible(true);
        }

        Twitter802Request request = new Twitter802Request(getActivity(), this);
        request.request();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void
    callback(TweetsWithTrack tweets) {
        this.tracks = tweets.getPlayableTracks();

        TweetArrayAdapter adapter = new TweetArrayAdapter(getActivity(), tweets);
        setListAdapter(adapter);
    }
}
