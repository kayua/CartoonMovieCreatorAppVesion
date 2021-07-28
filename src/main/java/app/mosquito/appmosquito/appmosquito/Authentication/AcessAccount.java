package app.mosquito.appmosquito.appmosquito.Authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import app.mosquito.appmosquito.appmosquito.ActivityUserInterface;
import app.mosquito.appmosquito.appmosquito.R;


public class AcessAccount extends Activity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.auth_acess);

        mAuth = FirebaseAuth.getInstance();
        Button buttonAcess = (Button) findViewById(R.id.buttonAuthAcessRegisterAccount3);
        EditText textBoxUsername = (EditText) findViewById(R.id.editTextAuthGender);
        EditText textBoxPassword = (EditText) findViewById(R.id.editTextAuthPassword);
        TextView recovery = (TextView ) findViewById(R.id.textView25);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        buttonAcess.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                acess(textBoxUsername.getText().toString(),textBoxPassword.getText().toString());
                Log.i(textBoxUsername.getText().toString(),textBoxPassword.getText().toString());
            }
        });
        recovery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RecoverPassword.class);
                finish();
                startActivity(i);
            }
        });

    }

    private void acess(String textBoxUsername, String textBoxPassword){

        mAuth.signInWithEmailAndPassword(textBoxUsername, textBoxPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            screen_user();

                        } else {


                            toast();

                        }
                    }
                });

    }

    private void screen_user() {
        Intent i = new Intent(AcessAccount.this, ActivityUserInterface.class);
        finish();
        startActivity(i);
    }

    private void toast() {
        Toast.makeText(getApplicationContext(), "Não foi possível encontrar sua conta. Verificar se o preenchimento está correto.", Toast.LENGTH_SHORT).show();
            }
    @Override

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }
}


