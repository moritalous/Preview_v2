package forest.rice.field.k.preview.view.playing;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import forest.rice.field.k.preview.entity.Track;
import forest.rice.field.k.preview.entity.Tracks;
import forest.rice.field.k.preview.view.base.BaseArrayAdapter;

public class PlayingArrayAdapter extends BaseArrayAdapter {

    private Track playingTrack;

    public PlayingArrayAdapter(Context context, Tracks tracks) {
        super(context, tracks);
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        View view = super.getView(position, convertView, parent);
//
//        Track track = tracks.get(position);
//
//        if(playingTrack != null && playingTrack.get(Track.previewUrl).equals(track.get(Track.previewUrl))) {
//
//        }
//
//        return view;
//
//    }

    public void setPlayingTrack(Track track) {
        this.playingTrack = track;
    }
}
