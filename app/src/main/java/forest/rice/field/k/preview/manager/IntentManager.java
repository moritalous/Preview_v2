
package forest.rice.field.k.preview.manager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import forest.rice.field.k.preview.util.Utils;

public class IntentManager {

    private static final String baseUrl = "http://m.kget.jp/result.php?cat=0&artist=%s&title=%s&tieup=&phrase=";

    public static Intent createLyricsViewInBrowserIntent(String artist, String title) {
        String url = String.format(baseUrl, urlEncode(artist), urlEncode(title));
        return createViewInBrowserIntent(url);
    }

//    public static Intent createLyricsViewInBrowserIntent(String artist) {
//        return createLyricsViewInBrowserIntent(artist, "");
//    }

    public static Intent createViewInBrowserIntent(String url) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    }

    public static Intent createOpenPreviewIntent(Context context) {
        PackageManager manager = context.getPackageManager();
        Intent intent =  manager.getLaunchIntentForPackage("forest.rice.field.k.preview");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    private static String urlEncode(String input) {
        return Utils.urlEncode(input);
    }
}
