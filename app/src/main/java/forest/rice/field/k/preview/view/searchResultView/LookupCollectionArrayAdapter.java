package forest.rice.field.k.preview.view.searchResultView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import forest.rice.field.k.R;
import forest.rice.field.k.preview.entity.Collection;
import forest.rice.field.k.preview.entity.Collections;
import forest.rice.field.k.preview.entity.Track;
import forest.rice.field.k.preview.entity.Tracks;

public class LookupCollectionArrayAdapter extends ArrayAdapter<Collection> {

    private LayoutInflater layoutInflater_;

    protected Collections collections = null;

    private Context context = null;

    public LookupCollectionArrayAdapter(Context context, Collections collections) {
        super(context, 0, collections);
        this.context = context;
        layoutInflater_ = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.collections = collections;
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

        final Collection collection = collections.get(position);

        holder.name.setText("" + (position + 1) + ". " + collection.get(Collection.collectionName));
        holder.artist.setText(collection.get(Collection.artistName));
        holder.collection.setText("");
        Glide.with(context).load(collection.get(Collection.artworkUrl100)).into(holder.image);

        return convertView;
    }

    private class ViewHolder {
        TextView name;
        TextView artist;
        TextView collection;
        ImageView image;
    }
}
