package app.mosquito.appmosquito.appmosquito.Editor.CropImage;

import android.app.Application;

@SuppressWarnings("unused") public class AppController extends Application {
  private static final String TAG = AppController.class.getSimpleName();
  private static AppController instance;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;

    FontUtils.loadFont(getApplicationContext(), "Roboto-Light.ttf");
  }

  public static AppController getInstance() {
    return instance;
  }
}