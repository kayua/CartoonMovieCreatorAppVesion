package app.mosquito.appmosquito.appmosquito.Authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.Parse;
import com.parse.ParseUser;

import app.mosquito.appmosquito.appmosquito.ActivityUserInterface;
import app.mosquito.appmosquito.appmosquito.R;

public class ActivityEmailChecking extends AppCompatActivity {

    String username_registered;
    String password_registered;
    public static final String PREFS_NAME = "PersonalDatabase";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("zaCP2VZI9fujPU1PpQMzoXYVK75Q5KvhTOmJMoM7")
                .clientKey("FWqAGbF91QUtnDtv3h7XpKh6KQBiQCBmiDW6aG0d")
                .server("https://parseapi.back4app.com")
                .build()
        );
        setContentView(R.layout.auth_email);
    }

    public void init_section_user(View view){

        recovery_login();
        Toast.makeText(this, password_registered, Toast.LENGTH_SHORT).show();

        ParseUser.logInInBackground(username_registered, password_registered, (user, e) -> {
            if (user != null) {

                boolean emailVerified = user.getBoolean("emailVerified");

                if (emailVerified) {

                    open_mosquito_app(view);

                } else {
                    Toast.makeText(this, "Seu email não foi verificado", Toast.LENGTH_SHORT).show();
                }
            } else {

                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void open_mosquito_app(View view){

        Intent i = new Intent(ActivityEmailChecking.this, ActivityUserInterface.class);
        finish();
        startActivity(i);

    }

    private void recovery_login() {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        username_registered = settings.getString("username", "");
        password_registered = settings.getString("password", "");

        }


    }
