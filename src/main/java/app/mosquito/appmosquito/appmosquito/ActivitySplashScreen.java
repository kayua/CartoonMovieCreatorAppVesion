package app.mosquito.appmosquito.appmosquito;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.parse.Parse;
import com.parse.ParseUser;

public class ActivitySplashScreen extends AppCompatActivity {

    String usernameRegistered;
    String passwordRegistered;
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
            }}, 10000);

    }

    private boolean recovery_login() {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        usernameRegistered = settings.getString("username", "");
        passwordRegistered = settings.getString("password", "");

        if (usernameRegistered.isEmpty()){

            return false;

        }else{

            return true;

        }

    }

    private void screen_check_email(){

        Intent s = new Intent(ActivitySplashScreen.this, ActivityEmailChecking.class);
        finish();
        startActivity(s);

    }

    private void screen_login(){

        Intent s = new Intent(ActivitySplashScreen.this, ActivityUserInterface.class);
        finish();
        startActivity(s);

    }

    private void screen_register(){

        Intent s = new Intent(ActivitySplashScreen.this, ActivityRegisterAccount.class);
        finish();
        startActivity(s);

    }

    private void access_account(){

        ParseUser.logInInBackground(usernameRegistered, passwordRegistered, (user, e) -> {

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