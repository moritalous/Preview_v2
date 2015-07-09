
package forest.rice.field.k.preview.request;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import forest.rice.field.k.preview.entity.Track;
import forest.rice.field.k.preview.entity.Tracks;

public class ITunesRssRequest extends AbstractRequest {

    private String endpoint = "https://itunes.apple.com/jp/rss/topsongs/limit=100/json";

    @Override
    public String getJson() throws IOException {
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        return getStringFromInputStream(connection.getInputStream());
    }

    public Tracks parse(String jsonString) throws Exception {
        Tracks tracks = new Tracks();

        JSONObject json = new JSONObject(jsonString);

        JSONArray array = json.getJSONObject("feed").getJSONArray("entry");

        for (int i = 0; i < array.length(); i++) {
            Track track = new Track();

            JSONObject object = array.getJSONObject(i);

            String trackName = object.getJSONObject("im:name").getString("label");
            String artistName = object.getJSONObject("im:artist").getString("label");
            String artistViewUrl = object.getJSONObject("im:artist").getJSONObject("attributes").getString("href");


            JSONArray imageArray = object.getJSONArray("im:image");
            String artworkUrl = imageArray.getJSONObject(imageArray.length() - 1)
                    .getString("label");

            String trackViewUrl = object.getJSONArray("link").getJSONObject(0)
                    .getJSONObject("attributes").getString("href");

            String collectionName = object.getJSONObject("im:collection").getJSONObject("im:name")
                    .getString("label");
            String previewUrl = object.getJSONArray("link").getJSONObject(1)
                    .getJSONObject("attributes").getString("href");

            track.put(Track.trackName, trackName);
            track.put(Track.artistName, artistName);
            track.put(Track.artistViewUrl, artistViewUrl);
            track.put(Track.artworkUrl100, artworkUrl);
            track.put(Track.collectionName, collectionName);
            track.put(Track.trackViewUrl, trackViewUrl);
            track.put(Track.previewUrl, previewUrl);
            track.put(Track.artistId, getArtistId(artistViewUrl));
            track.put(Track.collectionId, getCollectionId(trackViewUrl));

            tracks.add(track);
        }

        return tracks;
    }

    private String getArtistId(String artistViewUrl) {
        return splitId(artistViewUrl);
    }

    private String getCollectionId(String trackViewUrl) {
        return splitId(trackViewUrl);
    }

    private String splitId(String url) {
        String result = "";
        String[] split = url.split("/");

        for(String str : split) {
            if(str.startsWith("id")) {
                result = str.substring(0, str.indexOf("?")).replace("id", "");
                break;
            }
        }

        return result;
    }

}
