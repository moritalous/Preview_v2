package forest.rice.field.k.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import forest.rice.field.k.R;
import forest.rice.field.k.preview.entity.Tracks;
import forest.rice.field.k.preview.mediaplayer.MediaPlayerNotificationService;
import forest.rice.field.k.preview.view.playing.PlayingFragment;
import forest.rice.field.k.preview.view.topChart.TopChartListFragment;


public class NavigationDrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, NavigationDrawerBaseInterface {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private ActionBarHelper mActionBar;
    private ActionBarDrawerToggle mDrawerToggle;

    private Toolbar mToolbar;

    private Fragment topChartFragment;

    private Tracks tracks;

    private FloatingActionButton floatingActionButton;

    private BroadcastReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_bar_drawer_layout);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(new DrawerListener());

        // The drawer title must be set in order to announce state changes when
        // accessibility is turned on. This is typically a simple description,
        // e.g. "Navigation".
        mDrawerLayout.setDrawerTitle(GravityCompat.START, getString(R.string.drawer_title));

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mActionBar = createActionBarHelper();
        mActionBar.init();

        // ActionBarDrawerToggle provides convenient helpers for tying together the
        // prescribed interactions between a top-level sliding drawer and the action bar.
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close);

        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        mNavigationView.setNavigationItemSelectedListener(this);

        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String action = (String)view.getTag();

                Intent service = new Intent(getBaseContext(), MediaPlayerNotificationService.class);
                service.setAction(action);
                startService(service);
            }
        });

        if (topChartFragment == null) {
            topChartFragment = TopChartListFragment.newInstance();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, topChartFragment)
                .commit();

        receiver = new MyBroadcastReceiver();
        LocalBroadcastManager.getInstance(getBaseContext()).registerReceiver(receiver, new IntentFilter("PLAYING_TRACK"));
        LocalBroadcastManager.getInstance(getBaseContext()).registerReceiver(receiver, new IntentFilter("STOP_TRACK"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopService(new Intent(getApplicationContext(), MediaPlayerNotificationService.class));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
         * The action bar home/up action should open or close the drawer.
         * mDrawerToggle will take care of this.
         */
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        mToolbar.setTitle(menuItem.getTitle());
        menuItem.setChecked(true);
        mDrawerLayout.closeDrawers();

        switch (menuItem.getItemId()) {
            case R.id.nav_source:
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://github.com/moritalous/Preview_v2"));
                startActivity(intent);
                break;
            case R.id.nav_ranking: {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, topChartFragment)
                        .commit();
            }
            break;
            case R.id.nav_playlist: {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, PlayingFragment.newInstance())
                        .commit();
            }
            default:
                break;
        }

        return true;
    }

    @Override
    public void setChecked(int id) {
        mNavigationView.getMenu().findItem(id).setChecked(true);
    }

    @Override
    public Menu getNavigationMenu() {
        return mNavigationView.getMenu();
    }

    @Override
    public void setTitle(CharSequence title) {
        mActionBar.setTitle(title);
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    }

    @Override
    public void clearTracks() {
        this.tracks.clear();
    }

    @Override
    public Tracks getTracks() {
        if (this.tracks == null) {
            this.tracks = new Tracks();
        }
        return this.tracks;
    }

    private class DrawerListener implements DrawerLayout.DrawerListener {

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            mDrawerToggle.onDrawerOpened(drawerView);
            mActionBar.onDrawerOpened();
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            mDrawerToggle.onDrawerClosed(drawerView);
            mActionBar.onDrawerClosed();
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            mDrawerToggle.onDrawerStateChanged(newState);
        }
    }

    /**
     * Create a compatible helper that will manipulate the action bar if available.
     */
    private ActionBarHelper createActionBarHelper() {
        return new ActionBarHelper();
    }

    /**
     * Action bar helper for use on ICS and newer devices.
     */
    private class ActionBarHelper {
        private final ActionBar mActionBar;
        private CharSequence mDrawerTitle;
        private CharSequence mTitle;

        ActionBarHelper() {
            mActionBar = getSupportActionBar();
        }

        public void init() {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowHomeEnabled(false);
            mTitle = mDrawerTitle = getTitle();
        }

        /**
         * When the drawer is closed we restore the action bar state reflecting
         * the specific contents in view.
         */
        public void onDrawerClosed() {
            mActionBar.setTitle(mTitle);
        }

        /**
         * When the drawer is open we set the action bar to a generic title.
         * The action bar should only contain data relevant at the top level of
         * the nav hierarchy represented by the drawer, as the rest of your content
         * will be dimmed down and non-interactive.
         */
        public void onDrawerOpened() {
            mActionBar.setTitle(mDrawerTitle);
        }

        public void setTitle(CharSequence title) {
            mTitle = title;
        }
    }

    class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            switch (action) {
                case "PLAYING_TRACK": {

                    floatingActionButton.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_media_pause));
                    floatingActionButton.setVisibility(View.VISIBLE);
                    floatingActionButton.setTag(MediaPlayerNotificationService.ServiceStatics.ACTION_PAUSE);
                }
                break;
                case "STOP_TRACK": {
                    floatingActionButton.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_media_play));
                    floatingActionButton.setVisibility(View.VISIBLE);
                    floatingActionButton.setTag(MediaPlayerNotificationService.ServiceStatics.ACTION_RESUME);
                }
                break;
            }
        }
    }
}
