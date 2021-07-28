package app.mosquito.appmosquito.appmosquito.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import app.mosquito.appmosquito.appmosquito.MainUserInterface;
import app.mosquito.appmosquito.appmosquito.R;


public class MenuSource extends AppCompatActivity {

    public static final String PREFS_NAME = "PersonalDatabase";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_authentication_start_user);


        Button buttonAcess = (Button) findViewById(R.id.buttonAcessLogin);
        Button buttonRegister = (Button) findViewById(R.id.buttonAuthRegister);
        TextView textViewDirectAcess = findViewById(R.id.textViewAuthDirectAcess);

        buttonAcess.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                screenAcessAccount();
            }});


        buttonRegister.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                screenRegister();
            }});



        textViewDirectAcess.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                screenUser();
            }});

    }


    private void screenAcessAccount(){

        Intent i = new Intent(MenuSource.this, AcessAccount.class);
        finish();
        startActivity(i);

    }

    private void screenUser(){

        Intent i = new Intent(MenuSource.this, MainUserInterface.class);
        finish();
        startActivity(i);
    }

    private void screenRegister(){

        Intent i = new Intent(MenuSource.this, RegisterProfileFirst.class);
        finish();
        startActivity(i);

    }


}
