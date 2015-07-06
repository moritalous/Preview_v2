package forest.rice.field.k.preview.entity;

import com.twitter.sdk.android.core.models.Tweet;

import forest.rice.field.k.preview.request.ITunesApiSearchRequest;

public class TweetWithTrack {

    public Tweet tweet = null;
    private Track track = null;

    public Track track() {
        if (track != null) {
            return track;
        }

        try {
            ITunesApiSearchRequest request = new ITunesApiSearchRequest(getTrackTitleAndArtist());
            Tracks tracks = request.parseResultJson(request.getJson());
            track = tracks.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            track = getDummyTrack();
        }
        return track;
    }

    public  String getTrackTitleAndArtist() {
        String split = "";
        try {
            split= tweet.text.split("⇒")[1];
        } catch (Exception e) {

        }

        return split;
    }

    private Track getDummyTrack() {
        Track track = new Track();

        track.put(Track.artistName, getArtistFromTweet());
        track.put(Track.trackName, getTitleFromTweet());
        track.put(Track.artworkUrl100, getArtworkFromTweet());
        track.put(Track.trackId, "DUMMY");

        return track;

    }

    private String getArtistFromTweet() {
        String artist;
        try {
            String titleAndArtist = getTrackTitleAndArtist();
            artist = titleAndArtist.split("/")[1];
        } catch (Exception e) {
            artist = "";
        }
        return artist;
    }

    private String getTitleFromTweet() {
        String title;
        try {
            String titleAndArtist = getTrackTitleAndArtist();
            title = titleAndArtist.split("/")[0];
        } catch (Exception e) {
            title = "";
        }
        return title;
    }

    private String getArtworkFromTweet() {
        return tweet.user.profileImageUrl;
    }
}
