package forest.rice.field.k.preview.view.searchResultView;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import forest.rice.field.k.R;
import forest.rice.field.k.base.NavigationDrawerBaseInterface;
import forest.rice.field.k.preview.entity.Collection;
import forest.rice.field.k.preview.entity.Collections;
import forest.rice.field.k.preview.entity.Track;
import forest.rice.field.k.preview.view.base.BaseListFragment;

public class LookupCollectionByArtistFragment extends BaseListFragment implements LookupCollectionAsyncTask.LookupCollectionAsyncTaskCallbak {

    private static final String ARTIST_ID = "artistId";
    private static final String ARTIST_NAME = "artistName";

    private String artistId = null;
    private String artistName = null;

    private LookupCollectionAsyncTask task;

    private Collections collections;

    public static LookupCollectionByArtistFragment newInstance(String artistId, String artistName) {
        LookupCollectionByArtistFragment fragment = new LookupCollectionByArtistFragment();
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

        task = new LookupCollectionAsyncTask();
        task.callback = this;
        task.execute(artistId);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ロングタップなし
        getListView().setOnItemLongClickListener(null);

        if (getActivity() instanceof NavigationDrawerBaseInterface) {
            NavigationDrawerBaseInterface activity = (NavigationDrawerBaseInterface) getActivity();
            activity.setChecked(R.id.nav_search);

            Menu menu = activity.getNavigationMenu();
            menu.findItem(R.id.nav_search).setVisible(true);
        }

        setTitle(artistName + "のアルバム");
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Collection collection = collections.get(position);

        // アルバム検索
        LookupByArtistFragment fragment = LookupByArtistFragment.newInstance(collection.get(Collection.collectionId), collection.get(Collection.collectionName));
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).addToBackStack(null)
                .commit();
    }

    @Override
    public void onDestroy() {
        task.cancel(true);
        task.callback = null;
        task = null;

        super.onDestroy();
    }

    @Override
    public void callback(Collections result) {
        this.collections = result;
        LookupCollectionArrayAdapter adapter = new LookupCollectionArrayAdapter(getActivity(), result);
        setListAdapter(adapter);
    }


}
