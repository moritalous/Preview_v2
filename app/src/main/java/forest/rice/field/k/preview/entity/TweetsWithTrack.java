package forest.rice.field.k.preview.entity;

import android.os.AsyncTask;

import java.util.ArrayList;

public class TweetsWithTrack extends ArrayList<TweetWithTrack> {

    private Tracks playingTracks = new Tracks();

    @Override
    public boolean add(final TweetWithTrack object) {

        new AsyncTask<TweetWithTrack, Integer, TweetWithTrack>() {

            @Override
            protected TweetWithTrack doInBackground(TweetWithTrack... tweetWithTracks) {

                Track track = tweetWithTracks[0].track();

                if(track.get(Track.trackId).equals("DUMMY")) {
                    // DUMMY
                } else {
                    playingTracks.add(track);
                }

                return null;
            }
        }.execute(object);

        return super.add(object);
    }

    public Tracks getPlayableTracks(){
        return playingTracks;
    }
}
