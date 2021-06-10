package app.mosquito.appmosquito.appmosquito;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import app.mosquito.appmosquito.appmosquito.ui.Settings.SettingsFragment;

public class ActivityUserInterface extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    String usernameRegistered;
    String passwordRegistered;
    public static final String PREFS_NAME = "PersonalDatabase";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interface);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        TextView email_textview = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textView8);
        String text = new String();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        usernameRegistered = settings.getString("username", "");
        passwordRegistered = settings.getString("password", "");

        text = "  Olá, "+usernameRegistered;
        email_textview.setText(text);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_maps, R.id.nav_statistics, R.id.nav_recognize,
                R.id.nav_message, R.id.nav_history, R.id.nav_information, R.id.action_settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.user_interface, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void screenIntent(View view) {

        Intent intent = new Intent(this, DaemonService.class);
        startActivity(intent);
    }

    public void screenView(MenuItem item) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        SettingsFragment frag1 = new SettingsFragment();
        ft.add(R.id.nav_host_fragment, frag1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Configurações");
        ft.commit();
    }


}