package app.mosquito.appmosquito.appmosquito.Chat;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Agosto, 02 2019
 *
 * @author suporte@moonjava.com.br (Tiago Aguiar).
 */
public class ChatApplication extends Application implements
  Application.ActivityLifecycleCallbacks {

  private void setOnline(boolean enabled) {
    String uid = FirebaseAuth.getInstance().getUid();
    if (uid != null) {
      FirebaseFirestore.getInstance().collection("users")
              .document(uid)
              .update("online", enabled);
    }
  }

  @Override
  public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

  }

  @Override
  public void onActivityStarted(Activity activity) {

  }

  @Override
  public void onActivityResumed(Activity activity) {
    setOnline(true);
  }

  @Override
  public void onActivityPaused(Activity activity) {
    setOnline(false);
  }

  @Override
  public void onActivityStopped(Activity activity) {

  }

  @Override
  public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

  }

  @Override
  public void onActivityDestroyed(Activity activity) {

  }
}
