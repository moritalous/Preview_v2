package forest.rice.field.k.preview.entity;

import java.util.ArrayList;


public class Tracks extends ArrayList<Track> {

    /**
     *
     */
    private static final long serialVersionUID = 9219979988156321922L;

    public Tracks subTracks(int start, int end) {
        Tracks tracks = new Tracks();
        tracks.addAll(super.subList(start, end));

        return tracks;
    }
}
