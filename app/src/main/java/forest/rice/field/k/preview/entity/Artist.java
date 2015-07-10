package forest.rice.field.k.preview.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Artist extends HashMap<String, String> {

    public static String wrapperType = "wrapperType";
    public static String artistType = "artistType";
    public static String artistName = "artistName";
    public static String artistLinkUrl = "artistLinkUrl";
    public static String artistId = "artistId";
    public static String amgArtistId = "amgArtistId";
    public static String primaryGenreName = "primaryGenreName";
    public static String primaryGenreId = "primaryGenreId";
    public static String radioStationUrl = "radioStationUrl";

    public Artist() {

    }
    public Artist(Serializable s) {
        super();
        try {
            if(s instanceof HashMap<?, ?>) {
                @SuppressWarnings("unchecked")
                Map<String, String> map = (HashMap<String, String>)s;
                for(Map.Entry<String, String> entry : map.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    this.put(key, value);
                }
            }
        } catch(Exception e) {
        }
    }

    @Override
    public String get(Object key) {
        String value = super.get(key);
        return (value != null) ? value : "";
    }
}
