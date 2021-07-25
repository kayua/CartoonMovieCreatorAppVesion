package app.mosquito.appmosquito.appmosquito.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import app.mosquito.appmosquito.appmosquito.ActivityUserInterface;
import app.mosquito.appmosquito.appmosquito.R;


public class ActivityStart extends AppCompatActivity {

    public static final String PREFS_NAME = "PersonalDatabase";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.auth_start);
        Button buttonAcess = (Button) findViewById(R.id.buttonAuthAcessRegisterAccount3);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        buttonAcess.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                screen_acess_account();
            }});

        Button buttonRegister = (Button) findViewById(R.id.buttonAuthRegister);
        buttonRegister.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                screen_register();
            }});

        TextView DirectAcess = findViewById(R.id.textViewAuthDirectAcess);
        DirectAcess.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                screen_user();
            }});
    }


    private void screen_acess_account(){

        Intent i = new Intent(ActivityStart.this, ActivityAcessAccount.class);
        finish();
        startActivity(i);
    }

    private void screen_user(){

        Intent i = new Intent(ActivityStart.this, ActivityUserInterface.class);
        finish();
        startActivity(i);
    }

    private void screen_register(){


        Intent i = new Intent(ActivityStart.this, ActivityRegisterAccount.class);
        finish();
        startActivity(i);

    }

}
