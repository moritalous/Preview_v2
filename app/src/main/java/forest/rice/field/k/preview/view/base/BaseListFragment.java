
package forest.rice.field.k.preview.view.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Arrays;

import forest.rice.field.k.R;
import forest.rice.field.k.base.NavigationDrawerBaseInterface;
import forest.rice.field.k.preview.entity.Track;
import forest.rice.field.k.preview.entity.Tracks;
import forest.rice.field.k.preview.manager.IntentManager;
import forest.rice.field.k.preview.mediaplayer.MediaPlayerNotificationService;
import forest.rice.field.k.preview.mediaplayer.MediaPlayerNotificationService.ServiceStatics;
import forest.rice.field.k.preview.view.dialog.TrackSelectDialogFragment;
import forest.rice.field.k.preview.view.lyric.LyricActivity;
import forest.rice.field.k.preview.view.playing.PlayingFragment;
import forest.rice.field.k.preview.view.searchResultView.LookupByArtistFragment;
import forest.rice.field.k.preview.view.searchResultView.LookupCollectionByArtistFragment;
import forest.rice.field.k.preview.view.searchResultView.SearchResultFragment;

public class BaseListFragment extends ListFragment implements SearchView.OnQueryTextListener, AdapterView.OnItemLongClickListener {

    protected Tracks tracks;
    protected MenuItem searchMenu;
    protected SearchView searchView;

    private FirebaseAnalytics mFirebaseAnalytics;

    public BaseListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();

        getListView().setDivider(null);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getListView().setDividerHeight(16);
            getListView().setPadding(16, 16, 16, 16);
        } else {
            getListView().setPadding(4, 2, 4, 2);
        }
        getListView().setFastScrollEnabled(true);
        getListView().setDrawSelectorOnTop(true);
        getListView().setOnItemLongClickListener(this);

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

        final TrackSelectDialogFragment dialogFragment = TrackSelectDialogFragment
                .newInstance(getDialogArray());
        dialogFragment.mOnClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String[] list = getResources().getStringArray(getDialogArray());
                String selectedItem = Arrays.asList(list).get(which);

                if(selectedItem.equals(getString(R.string.track_select_action_preview))) {
                    // 単一プレビュー
                    setTracks(tracks.subTracks(position, position+1));
                    play(tracks.get(position));
                } else if(selectedItem.equals(getString(R.string.track_select_action_preview_all))) {
                    // 連続プレビュー
                    setTracks(tracks.subTracks(position, tracks.size()));
                    playAll(tracks, position);
                    moveToPlayingFragment();

                } else if(selectedItem.equals(getString(R.string.track_select_action_detail))) {
                    // iTunes
                    Intent i = IntentManager.createViewInBrowserIntent(tracks.get(position)
                            .get(Track.trackViewUrl));
                    startActivity(i);
                } else if(selectedItem.equals(getString(R.string.track_select_action_search_lyric))) {
                    // 歌詞検索
                    Intent i = IntentManager.createLyricsViewInBrowserIntent(
                            tracks.get(position).get(Track.artistName), tracks.get(position)
                                    .get(Track.trackName));
                    startActivity(i);
                } else if(selectedItem.equals(getString(R.string.track_select_action_search_lyric_beta))) {
                    // 歌詞検索β
                    Intent i = new Intent(getActivity(), LyricActivity.class);
                    i.putExtra(LyricActivity.EXTRA_ARTIST,
                            tracks.get(position).get(Track.artistName));
                    i.putExtra(LyricActivity.EXTRA_TRACK,
                            tracks.get(position).get(Track.trackName));
                    startActivity(i);
                }
            }
        };

        dialogFragment.show(getFragmentManager(), "TAG");

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
        TrackSelectDialogFragment dialogFragment = TrackSelectDialogFragment
                .newInstance(getDialogArrayForLongClick());
        dialogFragment.mOnClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Track track = tracks.get(position);

                switch (which) {
                    case 0: {
                        // アーティスト検索
                        LookupByArtistFragment fragment = LookupByArtistFragment.newInstance(track.get(Track.artistId), track.get(Track.artistName));
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment).addToBackStack(null)
                                .commit();
                    }
                    break;
                    case 1:{
                        // アルバム検索
                        LookupByArtistFragment fragment = LookupByArtistFragment.newInstance(track.get(Track.collectionId), track.get(Track.collectionName));
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment).addToBackStack(null)
                                .commit();
                    }
                    break;
                    case 2:{
                        // 他のアルバム検索
                        LookupCollectionByArtistFragment fragment = LookupCollectionByArtistFragment.newInstance(track.get(Track.artistId), track.get(Track.artistName));
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment).addToBackStack(null)
                                .commit();
                    }
                    break;
                    default:
                        break;
                }
            }
        };

        dialogFragment.show(getFragmentManager(), "TAG");
        return true;
    }

    protected  int getDialogArray() {
        return R.array.track_select_actions;
    }

    protected  int getDialogArrayForLongClick() {
        return R.array.search_type_select;
    }

    protected void setTracks(Tracks tracks) {
        if(getActivity() instanceof  NavigationDrawerBaseInterface) {
            NavigationDrawerBaseInterface activity = (NavigationDrawerBaseInterface) getActivity();
            activity.setTracks(tracks);
        }
    }

    private void play(Track track) {
        // Firabase
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "id");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "name");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Play Track");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

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

    protected void playAll(Tracks tracks, int startPosition) {
        // Firabase
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "id");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "name");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Play All Track");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

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

    protected void moveToPlayingFragment() {

        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, PlayingFragment.newInstance())
                .commit();

    }

}
