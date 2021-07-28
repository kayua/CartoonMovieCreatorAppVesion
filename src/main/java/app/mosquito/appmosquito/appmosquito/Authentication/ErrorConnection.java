package app.mosquito.appmosquito.appmosquito.Authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import app.mosquito.appmosquito.appmosquito.MainUserInterface;
import app.mosquito.appmosquito.appmosquito.R;


public class ErrorConnection extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_not_connected);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    private void screenStartUser() {
        Intent i = new Intent(ErrorConnection.this, MenuSource.class);
        finish();
        startActivity(i);
    }

    @Override

    public void onStart() {
        super.onStart();

    }
}


