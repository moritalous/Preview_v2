
package forest.rice.field.k.preview.view.topChart;

import android.os.AsyncTask;

import java.util.List;

import forest.rice.field.k.gracenote.ALBUM;
import forest.rice.field.k.gracenote.GracenoteJson;
import forest.rice.field.k.preview.entity.Tracks;
import forest.rice.field.k.preview.gracenote.Gracenote;
import forest.rice.field.k.preview.request.ITunesRssRequest;

public class TopChartAsyncTask extends AsyncTask<String, String, Tracks> {

    public TopChartAsyncTaskCallback callback;

    @Override
    protected Tracks doInBackground(String... arg0) {

        Tracks tracks = new Tracks();
        try {

            Gracenote gracenote = new Gracenote("6179840-535C87BBB4117D196F68D6E51D32BB22");
            GracenoteJson json = gracenote.getRecommend("GLAY", "百花繚乱");

            List<ALBUM> albums = json.getRESPONSE().get(0).getALBUM();

            for(ALBUM album : albums) {
                String artist = album.getARTIST().get(0).getVALUE();
                String title = album.getTITLE().get(0).getVALUE();
                String gnid = album.getGNID();

                String result = String.format("%s %s %s", artist, title, gnid);

                System.out.println(result);

            }


            ITunesRssRequest request = new ITunesRssRequest();
            tracks = request.parse(request.getJson());
        } catch (Exception e) {
        }

        return tracks;
    }

    @Override
    protected void onPostExecute(Tracks tracks) {
        super.onPostExecute(tracks);

        if (callback != null) {
            callback.callback(tracks);
        }
    }

    public interface TopChartAsyncTaskCallback {
        void callback(Tracks tracks);
    }

}
