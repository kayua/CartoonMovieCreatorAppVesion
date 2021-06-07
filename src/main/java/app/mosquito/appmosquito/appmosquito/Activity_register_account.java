package app.mosquito.appmosquito.appmosquito;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.Parse;
import com.parse.ParseUser;

public class Activity_register_account extends AppCompatActivity {

    public static final String PREFS_NAME = "PersonalDatabase";
    private EditText username, password, email, phoneNumber;
    private CheckBox license;
    private Switch active_email_notification, active_realtime_notification, active_infestations_detect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("zaCP2VZI9fujPU1PpQMzoXYVK75Q5KvhTOmJMoM7")
                .clientKey("FWqAGbF91QUtnDtv3h7XpKh6KQBiQCBmiDW6aG0d")
                .server("https://parseapi.back4app.com")
                .build()
        );
        setContentView(R.layout.activity_register_account);
    }


    public void createUser(View view) throws InterruptedException {

        username = (EditText) findViewById(R.id.username_id);
        password = (EditText) findViewById(R.id.password_id);
        email = (EditText) findViewById(R.id.email_id);

        active_email_notification = (Switch) findViewById(R.id.switch3);
        active_realtime_notification = (Switch) findViewById(R.id.switch10);
        active_infestations_detect = (Switch) findViewById(R.id.switch2);
        license = (CheckBox) findViewById(R.id.checkBox2);

        if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()){


            Toast.makeText(this, "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show();

        }else{

            ParseUser user = new ParseUser();
            user.setUsername(username.getText().toString());

            user.setPassword(password.getText().toString());
            user.setEmail(email.getText().toString());
            user.put("phoneNumber", "99999999");

            user.put("email_notification", active_email_notification.isChecked());
            user.put("realtime_notification", active_realtime_notification.isChecked());
            user.put("infection_notification", active_infestations_detect.isChecked());

            if (license.isChecked()) {

                user.signUpInBackground(e -> {

                    if (e == null) {

                        Toast.makeText(this, "Cadastro efetuado com sucesso", Toast.LENGTH_SHORT).show();

                        store_login();
                        Intent i = new Intent(Activity_register_account.this, Activity_email_check.class);
                        finish();
                        startActivity(i);

                    } else {

                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                });

            } else {

                Toast.makeText(this, "É necessário aceitar os termos de uso.", Toast.LENGTH_LONG).show();

            }

            user.getCurrentUser().logOut();

        }

    }

    private void store_login() {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("username", username.getText().toString());
        editor.putString("password", password.getText().toString());
        editor.commit();
    }

}