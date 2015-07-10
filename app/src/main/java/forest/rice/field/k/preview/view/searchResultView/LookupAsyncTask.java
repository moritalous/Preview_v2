package forest.rice.field.k.preview.view.searchResultView;

import android.os.AsyncTask;

import forest.rice.field.k.preview.entity.Tracks;
import forest.rice.field.k.preview.request.ITunesAPILookupRequest;

public class LookupAsyncTask extends AsyncTask<String, String, Tracks> {

    public LookupAsyncTaskCallback callback;

    @Override
    protected Tracks doInBackground(String... params) {
        ITunesAPILookupRequest request = new ITunesAPILookupRequest(params[0]);

        Tracks result  = new Tracks();
        try {
            result = request.parseJsonForTrack(request.getJson());
        } catch (Exception e) {
        }

        return result;
    }

    @Override
    protected void onPostExecute(Tracks result) {
        super.onPostExecute(result);

        callback.callback(result);
    }

    public interface LookupAsyncTaskCallback {
        void callback(Tracks result);
    }
}
