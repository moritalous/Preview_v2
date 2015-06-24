
package forest.rice.field.k.preview.view.topChart;

import android.os.Bundle;
import android.view.Menu;

import forest.rice.field.k.R;
import forest.rice.field.k.base.NavigationDrawerBaseInterface;
import forest.rice.field.k.preview.entity.Tracks;
import forest.rice.field.k.preview.view.base.BaseListFragment;
import forest.rice.field.k.preview.view.topChart.TopChartAsyncTask.TopChartAsyncTaskCallback;

public class TopChartListFragment extends BaseListFragment implements TopChartAsyncTaskCallback {

    TopChartAsyncTask task;

    public static TopChartListFragment newInstance() {
        TopChartListFragment fragment = new TopChartListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        task = new TopChartAsyncTask();
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
    }
}
