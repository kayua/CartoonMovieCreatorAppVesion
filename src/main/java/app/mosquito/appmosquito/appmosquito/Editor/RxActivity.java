package app.mosquito.appmosquito.appmosquito.Editor;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class RxActivity extends AppCompatActivity {
  private static final String TAG = com.example.simplecropviewsample.RxActivity.class.getSimpleName();

  public static Intent createIntent(Activity activity) {
    return new Intent(activity, com.example.simplecropviewsample.RxActivity.class);
  }

  // Lifecycle Method ////////////////////////////////////////////////////////////////////////////

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_basic);

    if(savedInstanceState == null){
      getSupportFragmentManager().beginTransaction().add(R.id.container, com.example.simplecropviewsample.RxFragment.newInstance()).commit();
    }

    // apply custom font
    com.example.simplecropviewsample.FontUtils.setFont(findViewById(R.id.root_layout));

    initToolbar();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
  }

  @Override
  public boolean onSupportNavigateUp() {
    onBackPressed();
    return super.onSupportNavigateUp();
  }

  private void initToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    com.example.simplecropviewsample.FontUtils.setTitle(actionBar, "Rx Sample");
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeButtonEnabled(true);
  }

  public void startResultActivity(Uri uri) {
    if (isFinishing()) return;
    // Start ResultActivity
    startActivity(com.example.simplecropviewsample.ResultActivity.createIntent(this, uri));
  }
}
