package app.mosquito.appmosquito.appmosquito;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import app.mosquito.appmosquito.appmosquito.Editor.EditImageActivity;
import app.mosquito.appmosquito.appmosquito.Settings.SettingsFragment;

public class MainUserInterface extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    String usernameRegistered;
    String passwordRegistered;
    protected ActionBarDrawerToggle drawerToggle;
    public static final String PREFS_NAME = "PersonalDatabase";
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sidebar_slid);
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.sky_blue_color_picker));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.argb(220,150,150,150));
        //TextView email_textview = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textView8);
        String text = new String();


        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        usernameRegistered = settings.getString("username", "");
        passwordRegistered = settings.getString("password", "");
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("temp_select_movie", "0");
        editor.putString("temp_select_filter", "0");
        editor.apply();
        text = "  Olá, "+usernameRegistered;
        //email_textview.setText(text);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_camera, R.id.nav_filters, R.id.nav_recognize,
                R.id.nav_message, R.id.nav_history, R.id.nav_image, R.id.action_settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        ImageView imageView = (ImageView ) navigationView.getHeaderView(0).findViewById(R.id.imageView18s);
        ImageView imageView_camera = (ImageView ) navigationView.getHeaderView(0).findViewById(R.id.imageViewIconSidebar1);


        ImageView imageView_editor = (ImageView ) navigationView.getHeaderView(0).findViewById(R.id.imageViewIconSidebar3);
        imageView_editor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), EditImageActivity.class);
                startActivity(i);

            }

        });


        String picturePath = settings.getString("imageUser", "");

        imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

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