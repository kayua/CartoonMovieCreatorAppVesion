package app.mosquito.appmosquito.appmosquito.Services;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import app.mosquito.appmosquito.appmosquito.R;
import app.mosquito.appmosquito.appmosquito.ui.Main.ActivitySettingsFragment;

public class ActivitySettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ActivitySettingsFragment.newInstance())
                    .commitNow();
        }
    }
}