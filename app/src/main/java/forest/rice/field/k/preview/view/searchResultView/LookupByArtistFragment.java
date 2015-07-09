package forest.rice.field.k.preview.view.searchResultView;


import android.os.Bundle;
import android.view.Menu;

import forest.rice.field.k.R;
import forest.rice.field.k.base.NavigationDrawerBaseInterface;
import forest.rice.field.k.preview.entity.Tracks;
import forest.rice.field.k.preview.view.base.BaseListFragment;

public class LookupByArtistFragment extends BaseListFragment implements LookupAsyncTask.LookupAsyncTaskCallback {

    private static final String ARTIST_ID = "artistId";
    private static final String ARTIST_NAME = "artistName";

    private String artistId = null;
    private String artistName = null;

    private LookupAsyncTask task = null;


    public static LookupByArtistFragment newInstance(String artistId, String artistName) {
        LookupByArtistFragment fragment = new LookupByArtistFragment();
        Bundle args = new Bundle();
        args.putString(ARTIST_ID, artistId);
        args.putString(ARTIST_NAME, artistName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        artistId = "";
        artistName = "";
        if(getArguments() != null) {
            artistId = getArguments().getString(ARTIST_ID);
            artistName = getArguments().getString(ARTIST_NAME);
        }

        task = new LookupAsyncTask();
        task.callback = this;
        task.execute(artistId);

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

        setTitle(artistName);
    }

    @Override
    public void onDestroy() {
        task.cancel(true);
        task.callback = null;
        task = null;

        super.onDestroy();
    }

    @Override
    public void callback(Tracks result) {
        this.tracks = result;
        LookupArrayAdapter adapter = new LookupArrayAdapter(getActivity(), result);
        setListAdapter(adapter);
    }
}
