package forest.rice.field.k.preview.view.tweet;

import android.content.Context;
import android.os.AsyncTask;

import forest.rice.field.k.preview.entity.TweetsWithTrack;
import forest.rice.field.k.preview.request.Twitter802Request;

public class TweetAsyncTask extends AsyncTask<String, Integer, TweetsWithTrack> {

    private Context context;
    private TweetAsyncTaskCallback callback;

    public TweetAsyncTask(Context context, TweetAsyncTaskCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected TweetsWithTrack doInBackground(String... strings) {
        Twitter802Request request = new Twitter802Request(context);

        return request.requestForSync();
    }

    @Override
    protected void onPostExecute(TweetsWithTrack tweetWithTracks) {
        super.onPostExecute(tweetWithTracks);
        callback.onPostExecute(tweetWithTracks);
    }

    public interface TweetAsyncTaskCallback {
        public void onPostExecute(TweetsWithTrack tweetWithTracks);
    }
}
