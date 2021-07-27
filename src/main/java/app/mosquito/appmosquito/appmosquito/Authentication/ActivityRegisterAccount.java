package app.mosquito.appmosquito.appmosquito.Authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import app.mosquito.appmosquito.appmosquito.R;


public class ActivityRegisterAccount extends AppCompatActivity {

    public static final String PREFS_NAME = "PersonalDatabase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.auth_register);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Button buttonRegister = (Button) findViewById(R.id.buttonAuthAcessRegisterAccount3);
        EditText textBoxUsername = (EditText) findViewById(R.id.editTextAuthGender);
        EditText textBoxEmail = (EditText) findViewById(R.id.editTextAuthSchooling);
        EditText textBoxPassword = (EditText) findViewById(R.id.editTextAuthFavoriteWord);

        buttonRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                store_login(textBoxUsername.getText().toString(), textBoxEmail.getText().toString(),
                            textBoxPassword.getText().toString());
            }

        });

    }



    private void store_login(String username, String email, String password) {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("username", username);
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }

    private void screen_user(){

        Intent i = new Intent(ActivityRegisterAccount .this, ActivityAcessAccount.class);
        finish();
        startActivity(i);
    }

}
