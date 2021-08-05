package #PACK_NAME;

import org.apache.cordova.DroidGap;
import android.os.Bundle;

public class MobAppActivity extends DroidGap {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.loadUrl("file:///android_asset/www/finhtml/#WELCOME_FILE");
    }
}
