package app.mosquito.appmosquito.appmosquito;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import app.mosquito.appmosquito.appmosquito.ui.main.ActivitySettingsFragment;

public class Activity_settings extends AppCompatActivity {

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