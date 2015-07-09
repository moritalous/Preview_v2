package forest.rice.field.k.preview.gracenote;


import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import forest.rice.field.k.gracenote.GracenoteJson;

public class Gracenote {

    public String clientId = null;
    public String userId = null;
    public String endpoint = "https://c%s.web.cddbp.net/webapi/json/1.0/";

    OkHttpClient client = new OkHttpClient();

    public Gracenote(String clientId) {
        this.clientId = clientId;
    }

    private String getBaseUrl() {
        String client = "cxxxxxxxx";
        String[] split = clientId.split("-");

        if(split.length > 0) {
            client = split[0];
        }

        return String.format(endpoint, client);

    }

    public String requestUserId() {
        String path = "%sregister?client=%s";
        String url = String.format(path, getBaseUrl(), clientId);
        String result = "";

        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getUserId() {
        if(userId == null) {
            try {
                JSONObject object = new JSONObject(requestUserId());

                userId = object.getJSONArray("RESPONSE").getJSONObject(0).getJSONArray("USER").getJSONObject(0).getString("VALUE");

                System.out.println(userId);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return userId;
    }

    public String requestRecommend(String artisitName, String trackName) {
        String path = "%sradio/recommend?";
        String url = String.format(path, getBaseUrl());

        Map<String, String> param = new HashMap<>();
        param.put("artist_name", artisitName);
        if(trackName != null) {
            param.put("track_title", trackName);
        }
        param.put("lang", "jpn");
        param.put("client", clientId);
        param.put("user", getUserId());
        param.put("return_count", "25");
        param.put("select_extended", "cover,mood,artist_image,artist_biography,link,tempo");

        StringBuilder sb = new StringBuilder(url);

        for (String key : param.keySet()) {
            sb.append(key).append("=").append(param.get(key)).append("&");
        }
        sb.delete(sb.length()-1, sb.length());

        String result = "";
        Request request = new Request.Builder()
                .url(sb.toString())
                .build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public GracenoteJson getRecommend(String artisitName, String trackName) {
        Gson gson = new Gson();
        GracenoteJson json =  gson.fromJson(requestRecommend(artisitName, trackName), GracenoteJson.class);

        return json;
    }

    public GracenoteJson getRecommend(String artisitName) {
        return getRecommend(artisitName, null);
    }

}
