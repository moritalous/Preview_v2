
package forest.rice.field.k.preview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import forest.rice.field.k.R;
import forest.rice.field.k.preview.mediaplayer.MediaPlayerNitificationService;
import forest.rice.field.k.preview.view.searchResultView.SearchResultFragment;
import forest.rice.field.k.preview.view.topChart.TopChartAsyncTask;
import forest.rice.field.k.preview.view.topChart.TopChartListFragment;

public class MainActivity extends AppCompatActivity implements OnQueryTextListener {

    private SearchView searchView = null;
    private MenuItem searchMenu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, TopChartListFragment.newInstance())
                    .commit();
        }

        TopChartAsyncTask task = new TopChartAsyncTask();
        task.execute();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopService(new Intent(getApplicationContext(), MediaPlayerNitificationService.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_view, menu);
        searchMenu = menu.findItem(R.id.searchView);
        searchView = (SearchView) searchMenu.getActionView();
        searchView.setOnQueryTextListener(this);

        MenuItem actionItem = menu.add("Source");
        actionItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getTitle().equals("Source")) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/moritalous/Preview"));
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        SearchResultFragment fragment = SearchResultFragment.newInstance(query);

        searchView.clearFocus();
        searchMenu.collapseActionView();
        getSupportActionBar().setTitle(query);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).addToBackStack(null)
                .commit();
        return true;
    }
}
