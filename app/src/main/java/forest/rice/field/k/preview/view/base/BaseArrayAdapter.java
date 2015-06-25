
package forest.rice.field.k.preview.view.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import forest.rice.field.k.R;
import forest.rice.field.k.preview.entity.Track;
import forest.rice.field.k.preview.entity.Tracks;

public class BaseArrayAdapter extends ArrayAdapter<Track> {

    private LayoutInflater layoutInflater_;

    protected Tracks tracks = null;

     private Context context = null;

    public BaseArrayAdapter(Context context, Tracks tracks) {
        super(context, 0, tracks);
         this.context = context;
        layoutInflater_ = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.tracks = tracks;
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

        final Track track = tracks.get(position);

        holder.name.setText("" + (position + 1) + ". " + track.get(Track.trackName));
        holder.artist.setText(track.get(Track.artistName));
        holder.collection.setText(track.get(Track.collectionName));
        Glide.with(context).load(track.getLargestArtwork()).into(holder.image);

        return convertView;
    }

    private class ViewHolder {
        TextView name;
        TextView artist;
        TextView collection;
        ImageView image;
    }

}
