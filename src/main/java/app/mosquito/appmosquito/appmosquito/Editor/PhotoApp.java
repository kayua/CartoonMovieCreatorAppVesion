package app.mosquito.appmosquito.appmosquito.Editor;

import android.app.Application;
import android.content.Context;

/**
 * Created by Burhanuddin Rashid on 1/23/2018.
 */

public class PhotoApp extends Application {
    private static com.burhanrashid52.photoediting.PhotoApp sPhotoApp;
    private static final String TAG = com.burhanrashid52.photoediting.PhotoApp.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        sPhotoApp = this;
     /*   FontRequest fontRequest = new FontRequest(
                "com.google.android.gms.fonts",
                "com.google.android.gms",
                "Noto Color Emoji Compat",
                R.array.com_google_android_gms_fonts_certs);

        EmojiCompat.Config config = new FontRequestEmojiCompatConfig(this, fontRequest)
                .setReplaceAll(true)
                //    .setEmojiSpanIndicatorEnabled(true)
                //     .setEmojiSpanIndicatorColor(Color.GREEN)
                .registerInitCallback(new EmojiCompat.InitCallback() {
                    @Override
                    public void onInitialized() {
                        super.onInitialized();
                        Log.e(TAG, "Success");
                    }

                    @Override
                    public void onFailed(@Nullable Throwable throwable) {
                        super.onFailed(throwable);
                        Log.e(TAG, "onFailed: " + throwable.getMessage());
                    }
                });

     //   BundledEmojiCompatConfig bundledEmojiCompatConfig = new BundledEmojiCompatConfig(this);
        EmojiCompat.init(config);*/
    }

    public static com.burhanrashid52.photoediting.PhotoApp getPhotoApp() {
        return sPhotoApp;
    }

    public Context getContext() {
        return sPhotoApp.getContext();
    }
}
