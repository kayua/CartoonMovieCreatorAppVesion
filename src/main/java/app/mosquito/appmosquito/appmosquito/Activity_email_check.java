package app.mosquito.appmosquito.appmosquito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseUser;

public class Activity_email_check extends AppCompatActivity {

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
        setContentView(R.layout.activity_email_check);
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

        Intent i = new Intent(Activity_email_check.this, Activity_user_interface.class);
        finish();
        startActivity(i);

    }

    private void recovery_login() {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        username_registered = settings.getString("username", "");
        password_registered = settings.getString("password", "");

        }


    }
