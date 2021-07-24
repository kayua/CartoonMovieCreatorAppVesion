package app.mosquito.appmosquito.appmosquito.Authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import app.mosquito.appmosquito.appmosquito.ActivityUserInterface;
import app.mosquito.appmosquito.appmosquito.R;


public class ActivityEditPerfil extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.auth_perfil);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }
    private void acess(String textBoxUsername, String textBoxPassword){



    }
    private void screen_user() {
        Intent i = new Intent(ActivityEditPerfil.this, ActivityUserInterface.class);
        finish();
        startActivity(i);
    }

    private void toast() {
        Toast.makeText(getApplicationContext(), "Não foi possível encontrar sua conta. Verificar se o preenchimento está correto.", Toast.LENGTH_SHORT).show();
            }
    @Override

    public void onStart() {
        super.onStart();

    }
}


