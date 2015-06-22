
package forest.rice.field.k.preview.view.topChart;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import forest.rice.field.k.R;
import forest.rice.field.k.base.NavigationDrawerBaseActivity;
import forest.rice.field.k.base.NavigationDrawerBaseInterface;
import forest.rice.field.k.preview.entity.Tracks;
import forest.rice.field.k.preview.view.base.BaseListFragment;
import forest.rice.field.k.preview.view.topChart.TopChartAsyncTask.TopChartAsyncTaskCallback;

public class TopChartListFragment extends BaseListFragment implements TopChartAsyncTaskCallback {

    public static TopChartListFragment newInstance() {
        TopChartListFragment fragment = new TopChartListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TopChartAsyncTask task = new TopChartAsyncTask();
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

        setTitle(getString(R.string.nav_topchart));
    }

    ;

    @Override
    public void callback(Tracks tracks) {
        this.tracks = tracks;
        TopChartArrayAdapter adapter = new TopChartArrayAdapter(getActivity(), tracks);
        setListAdapter(adapter);
    }
}
