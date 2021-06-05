package app.mosquito.appmosquito.appmosquito;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ToggleButton;

import com.parse.Parse;
import com.parse.ParseUser;

public class Activity_splash_screen extends AppCompatActivity {

    String username_registered;
    String password_registered;
    public static final String PREFS_NAME = "PersonalDatabase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        Parse.initialize(new Parse.Configuration.Builder(this)
                        .applicationId("zaCP2VZI9fujPU1PpQMzoXYVK75Q5KvhTOmJMoM7")
                        .clientKey("FWqAGbF91QUtnDtv3h7XpKh6KQBiQCBmiDW6aG0d")
                        .server("https://parseapi.back4app.com")
                        .build());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {


                if (recovery_login()){

                    access_account();

                }else{

                    screen_register();

                }
            }}, 12000);

    }

    private boolean recovery_login() {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        username_registered = settings.getString("username", "");
        password_registered = settings.getString("password", "");

        if (username_registered.isEmpty()){

            return false;

        }else{

            return true;

        }


    }

    private void screen_check_email(){

        Intent s = new Intent(Activity_splash_screen.this, Activity_email_check.class);
        finish();
        startActivity(s);

    }

    private void screen_login(){

        Intent s = new Intent(Activity_splash_screen.this, Activity_user_interface.class);
        finish();
        startActivity(s);

    }

    private void screen_register(){

        Intent s = new Intent(Activity_splash_screen.this, Activity_register_account.class);
        finish();
        startActivity(s);

    }

    private void access_account(){

        ParseUser.logInInBackground(username_registered, password_registered, (user, e) -> {

            if (user != null) {

                boolean emailVerified = user.getBoolean("emailVerified");

                if (emailVerified == true) {

                    screen_login();

                } else {

                    screen_check_email();
                }
            } else {

                screen_register();
            }
        });
    }


}