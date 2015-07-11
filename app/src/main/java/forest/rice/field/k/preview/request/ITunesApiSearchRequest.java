
package forest.rice.field.k.preview.request;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.transform.Result;

import forest.rice.field.k.preview.entity.Artist;
import forest.rice.field.k.preview.entity.Artists;
import forest.rice.field.k.preview.entity.Collection;
import forest.rice.field.k.preview.entity.Collections;
import forest.rice.field.k.preview.entity.Results;
import forest.rice.field.k.preview.entity.Track;
import forest.rice.field.k.preview.entity.Tracks;

public class ITunesApiSearchRequest extends AbstractRequest {

    private String keyword = null;
    protected String endpoint = null;

    public ITunesApiSearchRequest(String keyword) {
        this.keyword = keyword;
        this.endpoint = "https://itunes.apple.com/search?term=%s&country=jp&media=music&entity=song&lang=ja_jp&limit=200";
    }

    @Override
    public String getJson() throws IOException {
        URL url = new URL(String.format(endpoint, URLEncoder.encode(keyword, "UTF-8")));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        return getStringFromInputStream(connection.getInputStream());
    }

    public Tracks parseJsonForTrack(String jsonString)
            throws Exception {
        return parseJsonForResult(jsonString).tracks;
    }

    public Artists parseJsonForArtist(String jsonString) throws Exception {
        return parseJsonForResult(jsonString).artists;
    }

    public Collections parseJsonForCollection(String jsonString) throws Exception {
        return parseJsonForResult(jsonString).collections;
    }

    public Results parseJsonForResult(String jsonString) throws Exception {
        JSONObject json = new JSONObject(jsonString);
        JSONArray results = json.getJSONArray("results");

        Results items = new Results();


        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);

            if(result.getString("wrapperType").equals("track")) {
                Track searchResult = new Track();

                Iterator<String> keys = result.keys();

                while (keys.hasNext()) {
                    String key = keys.next();
                    searchResult.put(key, result.getString(key));
                }

                items.tracks.add(searchResult);
            } else if(result.getString("wrapperType").equals("collection")) {
                Collection collection = new Collection();
                Iterator<String> keys = result.keys();

                while (keys.hasNext()) {
                    String key = keys.next();
                    collection.put(key, result.getString(key));
                }
                items.collections.add(collection);
            } else if(result.getString("wrapperType").equals("artist")) {
                Artist artist = new Artist();
                Iterator<String> keys = result.keys();

                while (keys.hasNext()) {
                    String key = keys.next();
                    artist.put(key, result.getString(key));
                }
                items.artists.add(artist);
            }
        }
        return items;
    }
}
