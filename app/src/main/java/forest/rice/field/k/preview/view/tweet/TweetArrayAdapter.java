
package forest.rice.field.k.preview.view.tweet;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.twitter.sdk.android.core.models.Tweet;

import forest.rice.field.k.R;
import forest.rice.field.k.preview.entity.Track;
import forest.rice.field.k.preview.entity.Tracks;
import forest.rice.field.k.preview.entity.TweetWithTrack;
import forest.rice.field.k.preview.entity.TweetsWithTrack;
import forest.rice.field.k.preview.view.base.BaseArrayAdapter;

public class TweetArrayAdapter extends ArrayAdapter<TweetWithTrack> {

    private LayoutInflater layoutInflater_;
    private Context context = null;

    private TweetsWithTrack tweetsWithTrack;

    public TweetArrayAdapter(Context context, TweetsWithTrack tweetsWithTrack) {
        super(context, 0, tweetsWithTrack);
        this.context = context;
        layoutInflater_ = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.tweetsWithTrack = tweetsWithTrack;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater_.inflate(
                    R.layout.fragment_track_list, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView
                    .findViewById(R.id.track_name);
            holder.artist = (TextView) convertView
                    .findViewById(R.id.track_artist);
            holder.collection = (TextView) convertView
                    .findViewById(R.id.track_collection);
            holder.image = (ImageView) convertView
                    .findViewById(R.id.track_image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }

    private class ViewHolder {
        TextView name;
        TextView artist;
        TextView collection;
        ImageView image;
    }

    private class TrackAsyncTask extends AsyncTask<TweetWithTrack, Integer, Track> {

        private ViewHolder holder;
        int position = 0;

        TrackAsyncTask(ViewHolder holder, int position) {
            super();
            this.holder = holder;
            this.position = position;
        }

        @Override
        protected Track doInBackground(TweetWithTrack... tweetWithTracks) {

            return tweetWithTracks[0].track();
        }

        @Override
        protected void onPostExecute(Track track) {
            holder.name.setText("" + (position + 1) + ". " + track.get(Track.trackName));
            holder.artist.setText(track.get(Track.artistName));
            holder.collection.setText(track.get(Track.collectionName));
            Glide.with(context).load(track.getLargestArtwork()).into(holder.image);
        }
    }
}
