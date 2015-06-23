package forest.rice.field.k.preview.view.playing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import forest.rice.field.k.R;
import forest.rice.field.k.base.NavigationDrawerBaseInterface;
import forest.rice.field.k.preview.entity.Track;
import forest.rice.field.k.preview.view.base.BaseArrayAdapter;
import forest.rice.field.k.preview.view.base.BaseListFragment;

public class PlayingFragment extends BaseListFragment {

    private BroadcastReceiver receiver;

    public static PlayingFragment newInstance() {
        PlayingFragment fragment = new PlayingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        receiver = new MediaPlayReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("PLAYING_TRACK");
        getActivity().registerReceiver(receiver, intentFilter);
    }


    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(receiver);

        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getActivity() instanceof NavigationDrawerBaseInterface) {
            NavigationDrawerBaseInterface activity = (NavigationDrawerBaseInterface) getActivity();
            this.tracks = activity.getTracks();

            BaseArrayAdapter adapter = new BaseArrayAdapter(getActivity(), this.tracks);
            setListAdapter(adapter);
        }


        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();

        if (getActivity() instanceof NavigationDrawerBaseInterface) {
            NavigationDrawerBaseInterface activity = (NavigationDrawerBaseInterface) getActivity();
            activity.setChecked(R.id.nav_playlist);
            setTitle(getString(R.string.nav_playlist));

            Menu menu = activity.getNavigationMenu();
            menu.findItem(R.id.nav_search).setVisible(false);
        }

        setEmptyText(getString(R.string.playlist_no_track));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // 連続プレビュー
        setTracks(tracks.subTracks(position, tracks.size()));
        playAll(tracks, position);
    }

    class MediaPlayReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Track track = new Track(intent.getSerializableExtra("track"));

            int index = tracks.indexOf(track);
            if (index != -1) {
                getListView().smoothScrollToPosition(index);
                getListView().setSelection(index);
            }
        }
    }
}
