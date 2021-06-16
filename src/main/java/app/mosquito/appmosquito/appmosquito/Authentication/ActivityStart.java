package app.mosquito.appmosquito.appmosquito.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import app.mosquito.appmosquito.appmosquito.R;


public class ActivityStart extends AppCompatActivity {

    public static final String PREFS_NAME = "PersonalDatabase";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.auth_start);
        Button buttonAcess = (Button) findViewById(R.id.buttonAuthAcessRegisterAccount);
        buttonAcess.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                screen_user();
            }});

        Button buttonRegister = (Button) findViewById(R.id.buttonAuthRegister);
        buttonRegister.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                screen_register();
            }});

    }


        private void screen_user(){

            Intent i = new Intent(ActivityStart.this, ActivityAcessAccount.class);
            finish();
            startActivity(i);
        }

        private void screen_register(){


            Intent i = new Intent(ActivityStart.this, ActivityRegisterAccount.class);
            finish();
            startActivity(i);
        }

}
