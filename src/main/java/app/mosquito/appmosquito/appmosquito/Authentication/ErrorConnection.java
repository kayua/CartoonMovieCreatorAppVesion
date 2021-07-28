package app.mosquito.appmosquito.appmosquito.Authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import app.mosquito.appmosquito.appmosquito.R;


public class ErrorConnection extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_authentication_error_connection);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button buttonRetry = (Button) findViewById(R.id.buttonAcessLogin);
        TextView textVieCloseApp = (Button) findViewById(R.id.buttonAcessLogin);



        buttonRetry.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) { screenStartUser(); }});

        textVieCloseApp.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) { closeApplication(); }});


    }

    private void screenStartUser() {

        Intent i = new Intent(ErrorConnection.this, MenuSource.class);
        finish();
        startActivity(i);

    }
    private void closeApplication(){

        this.finishAffinity();

    }



    @Override

    public void onStart() {
        super.onStart();

    }

}


