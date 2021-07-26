package app.mosquito.appmosquito.appmosquito.Editor.CropImage;

import android.app.Application;

@SuppressWarnings("unused") public class AppController extends Application {
  private static final String TAG = com.example.simplecropviewsample.AppController.class.getSimpleName();
  private static com.example.simplecropviewsample.AppController instance;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    // load custom font
    com.example.simplecropviewsample.FontUtils.loadFont(getApplicationContext(), "Roboto-Light.ttf");
  }

  public static com.example.simplecropviewsample.AppController getInstance() {
    return instance;
  }
}