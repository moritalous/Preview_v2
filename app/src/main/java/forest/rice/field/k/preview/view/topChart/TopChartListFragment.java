
package forest.rice.field.k.preview.view.topChart;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.View;

import forest.rice.field.k.R;
import forest.rice.field.k.base.NavigationDrawerBaseInterface;
import forest.rice.field.k.preview.entity.Tracks;
import forest.rice.field.k.preview.view.base.SwipeRefreshListFragment;
import forest.rice.field.k.preview.view.topChart.TopChartAsyncTask.TopChartAsyncTaskCallback;

public class TopChartListFragment extends SwipeRefreshListFragment implements TopChartAsyncTaskCallback {

    TopChartAsyncTask task;

    public static TopChartListFragment newInstance() {
        TopChartListFragment fragment = new TopChartListFragment();
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
        setColorSchemeResources(R.color.green, R.color.yellow, R.color.red, R.color.blue);
        // END_INCLUDE (setup_refreshlistener)
    }

    private void initiateRefresh() {
        task = new TopChartAsyncTask();
        task.context = getActivity();
        task.callback = this;
        task.execute();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getActivity() instanceof NavigationDrawerBaseInterface) {
            NavigationDrawerBaseInterface activity = (NavigationDrawerBaseInterface) getActivity();
            activity.setChecked(R.id.nav_ranking);

            Menu menu = activity.getNavigationMenu();
            menu.findItem(R.id.nav_search).setVisible(false);
        }

        setTitle(getString(R.string.nav_top_chart));
    }

    @Override
    public void onDestroy() {
        task.cancel(true);
        task.callback = null;
        task = null;

        super.onDestroy();
    }

    @Override
    public void callback(Tracks tracks) {
        this.tracks = tracks;
        TopChartArrayAdapter adapter = new TopChartArrayAdapter(getActivity(), tracks);
        setListAdapter(adapter);
        setRefreshing(false);
    }
}
