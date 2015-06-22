
package forest.rice.field.k.preview.view.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import forest.rice.field.k.R;
import forest.rice.field.k.base.NavigationDrawerBaseInterface;
import forest.rice.field.k.preview.entity.Track;
import forest.rice.field.k.preview.entity.Tracks;
import forest.rice.field.k.preview.manager.IntentManager;
import forest.rice.field.k.preview.mediaplayer.MediaPlayerNotificationService;
import forest.rice.field.k.preview.mediaplayer.MediaPlayerNotificationService.ServiceStatics;
import forest.rice.field.k.preview.view.dialog.TrackSelectDialogFragment;
import forest.rice.field.k.preview.view.lyric.LyricActivity;
import forest.rice.field.k.preview.view.searchResultView.SearchResultFragment;

public class BaseListFragment extends ListFragment implements SearchView.OnQueryTextListener {

    protected Tracks tracks;
    private MenuItem searchMenu;
    private SearchView searchView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    public BaseListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();

        setEmptyText(getString(R.string.track_search_no_result));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_view, menu);

        searchMenu = menu.findItem(R.id.searchView);
        searchView = (SearchView) MenuItemCompat.getActionView(searchMenu);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView l, View v, final int position, long id) {

        TrackSelectDialogFragment dialogFragment = TrackSelectDialogFragment
                .newInstance(R.array.track_select_actions);
        dialogFragment.mOnClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        // 単一プレビュー
                        play(tracks.get(position));
                        break;
                    case 1:
                        // 連続プレビュー
                        playAll(tracks, position);
                        break;
                    case 2:
                        // iTunes
                    {
                        Intent i = IntentManager.createViewInBrowserIntent(tracks.get(position)
                                .get(Track.trackViewUrl));
                        startActivity(i);
                    }
                    break;
                    case 3:
                        // 歌詞検索
                    {
                        Intent i = IntentManager.createLyricsViewInBrowserIntent(
                                tracks.get(position).get(Track.artistName), tracks.get(position)
                                        .get(Track.trackName));
                        startActivity(i);
                    }
                    break;
                    case 4:
                        // 歌詞検索β
                    {
                        Intent i = new Intent(getActivity(), LyricActivity.class);
                        i.putExtra(LyricActivity.EXTRA_ARTIST,
                                tracks.get(position).get(Track.artistName));
                        i.putExtra(LyricActivity.EXTRA_TRACK,
                                tracks.get(position).get(Track.trackName));
                        startActivity(i);
                    }
                    default:
                        break;
                }
            }
        };

        dialogFragment.show(getFragmentManager(), "TAG");

    }

    private void play(Track track) {
        Intent service = new Intent(getActivity(), MediaPlayerNotificationService.class);
        service.setAction(ServiceStatics.ACTION_TRACK_CLEAR);
        getActivity().startService(service);

        Intent service2 = new Intent(getActivity(), MediaPlayerNotificationService.class);
        service2.putExtra("TRACK", track);

        service2.setAction(ServiceStatics.ACTION_TRACK_ADD);
        getActivity().startService(service2);

        service.setAction(ServiceStatics.ACTION_PLAY);
        getActivity().startService(service);
    }

    private void playAll(Tracks tracks, int startPosition) {
        Intent service = new Intent(getActivity(), MediaPlayerNotificationService.class);
        service.setAction(ServiceStatics.ACTION_TRACK_CLEAR);
        getActivity().startService(service);

        for (int i = startPosition; i < tracks.size(); i++) {
            Track track = tracks.get(i);

            Intent service2 = new Intent(getActivity(), MediaPlayerNotificationService.class);
            service2.putExtra("TRACK", track);

            service2.setAction(ServiceStatics.ACTION_TRACK_ADD);
            getActivity().startService(service2);
        }

        service.setAction(ServiceStatics.ACTION_PLAY);
        getActivity().startService(service);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        SearchResultFragment fragment = SearchResultFragment.newInstance(query);

        searchView.clearFocus();
        MenuItemCompat.collapseActionView(searchMenu);

        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).addToBackStack(null)
                .commit();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    protected void setTitle(CharSequence title) {
        if (getActivity() instanceof NavigationDrawerBaseInterface) {
            NavigationDrawerBaseInterface activity = (NavigationDrawerBaseInterface) getActivity();
            activity.setTitle(title);
        }
    }

}