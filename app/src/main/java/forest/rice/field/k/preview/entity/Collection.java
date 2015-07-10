package forest.rice.field.k.preview.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Collection extends HashMap<String, String> {

    public static String wrapperType = "wrapperType";
    public static String collectionType = "collectionType";
    public static String artistId = "artistId";
    public static String collectionId = "collectionId";
    public static String amgArtistId = "amgArtistId";
    public static String artistName = "artistName";
    public static String collectionName = "collectionName";
    public static String collectionCensoredName = "collectionCensoredName";
    public static String artistViewUrl = "artistViewUrl";
    public static String collectionViewUrl = "collectionViewUrl";
    public static String artworkUrl60 = "artworkUrl60";
    public static String artworkUrl100 = "artworkUrl100";
    public static String collectionPrice = "collectionPrice";
    public static String collectionExplicitness = "collectionExplicitness";
    public static String trackCount = "trackCount";
    public static String copyright = "copyright";
    public static String country = "country";
    public static String currency = "currency";
    public static String releaseDate = "releaseDate";

    public Collection() {
    }

    public Collection(Serializable s) {
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
