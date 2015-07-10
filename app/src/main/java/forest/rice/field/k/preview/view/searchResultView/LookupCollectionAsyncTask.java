package forest.rice.field.k.preview.view.searchResultView;

import android.os.AsyncTask;

import forest.rice.field.k.preview.entity.Collections;
import forest.rice.field.k.preview.request.ITunesAPILookupCollectionRequest;

public class LookupCollectionAsyncTask extends AsyncTask<String, String, Collections> {

    public LookupCollectionAsyncTaskCallbak callback;

    @Override
    protected Collections doInBackground(String... params) {
        ITunesAPILookupCollectionRequest request = new ITunesAPILookupCollectionRequest(params[0]);

        Collections result = new Collections();
        try {
            result = request.parseJsonForCollection(request.getJson());
        } catch (Exception e) {

        }

        return result;
    }

    @Override
    protected void onPostExecute(Collections result) {
        super.onPostExecute(result);

        callback.callback(result);
    }

    public interface LookupCollectionAsyncTaskCallbak {
        void callback(Collections result);
    }
}
