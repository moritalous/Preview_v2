
package forest.rice.field.k.preview.view.lyric;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import forest.rice.field.k.R;
import forest.rice.field.k.base.NavigationDrawerBaseActivity;
import forest.rice.field.k.preview.entity.Lyric;
import forest.rice.field.k.preview.entity.Lyrics;
import forest.rice.field.k.preview.request.LyricRequest;
import forest.rice.field.k.preview.view.dialog.LyricSelectDialogFragment;
import forest.rice.field.k.preview.view.dialog.SimpleAlertDialog;

public class LyricActivity extends AppCompatActivity {

    public static final String EXTRA_ARTIST = "artist";
    public static final String EXTRA_TRACK = "track";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyric);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home: {
                Intent intent = new Intent(getBaseContext(), NavigationDrawerBaseActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
            break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private WebView webview;
        private ProgressBar progressBar;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_lyric, container, false);

            webview = (WebView) rootView.findViewById(R.id.lyric_webview);
            progressBar = (ProgressBar) rootView.findViewById(R.id.lyric_progress);

            progressBar.setVisibility(View.VISIBLE);

            FragmentActivity activity = getActivity();

            String artist = activity.getIntent().getStringExtra(EXTRA_ARTIST);
            String track = activity.getIntent().getStringExtra(EXTRA_TRACK);

            if (activity instanceof AppCompatActivity) {
                ((AppCompatActivity) activity).getSupportActionBar().setTitle(track + " / " + artist);
            }

            new LyricsSearchTask().execute(artist, track);

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            // TODO Auto-generated method stub
            super.onAttach(activity);

        }

        class LyricsSearchTask extends AsyncTask<String, Integer, Lyrics> {

            @Override
            protected Lyrics doInBackground(String... params) {
                LyricRequest search = new LyricRequest();

                String artist = params[0];
                String track = params[1];

                Lyrics lyrics = search.getLyrics(artist, track);
                return lyrics;
            }

            @Override
            protected void onPostExecute(final Lyrics result) {
                switch (result.size()) {
                    case 0:
                        // なし
                    {
                        SimpleAlertDialog dialog = SimpleAlertDialog
                                .newInstance(getString(R.string.lyric_search_no_result));
                        dialog.mOnClickListener = new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finish();
                            }
                        };
                        dialog.show(getFragmentManager(), "tag");

                        progressBar.setVisibility(View.GONE);
                    }
                    break;
                    case 1:
                        // Webビューに表示
                        new LyricsGetTask().execute(result.get(0));
                        break;
                    default:
                        // ダイアログ表示
                    {
                        String[] items = new String[result.size()];
                        for (int i = 0; i < result.size(); i++) {
                            items[i] = result.get(i).getFormatedTitleWithArtist();
                        }

                        LyricSelectDialogFragment dialogFragment = LyricSelectDialogFragment
                                .newInstance(items);

                        dialogFragment.mOnClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new LyricsGetTask().execute(result.get(which));
                            }
                        };
                        dialogFragment.show(getFragmentManager(), "TAG");
                    }
                    break;
                }
            }
        }

        class LyricsGetTask extends AsyncTask<Lyric, Integer, Lyric> {
            @Override
            protected Lyric doInBackground(Lyric... params) {
                LyricRequest search = new LyricRequest();
                return search.getLyric(params[0]);
            }

            @Override
            protected void onPostExecute(Lyric result) {
                progressBar.setVisibility(View.GONE);
                webview.loadDataWithBaseURL(null, result.get(Lyric.LYRICS), "text/html", "UTF-8",
                        null);

            }
        }
    }
}
