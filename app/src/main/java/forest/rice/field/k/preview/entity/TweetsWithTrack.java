package forest.rice.field.k.preview.entity;

import java.util.ArrayList;

public class TweetsWithTrack extends ArrayList<TweetWithTrack> {

    public Tracks getTracks() {
        Tracks tracks = new Tracks();
        for(TweetWithTrack tweet : this) {
            tracks.add(tweet.track());
        }
        return tracks;
    }
}
