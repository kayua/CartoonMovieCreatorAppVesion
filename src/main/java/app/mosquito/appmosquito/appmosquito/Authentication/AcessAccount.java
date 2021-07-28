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
import app.mosquito.appmosquito.appmosquito.MainUserInterface;
import app.mosquito.appmosquito.appmosquito.R;


public class AcessAccount extends Activity {

    private FirebaseAuth processingAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_authentication_acess);

        processingAuth = FirebaseAuth.getInstance();
        Button buttonAcess = (Button) findViewById(R.id.buttonUserLogin);
        EditText textBoxUsername = (EditText) findViewById(R.id.editTextSecondGender);
        EditText textBoxPassword = (EditText) findViewById(R.id.editTextAcessPassword);
        TextView recovery = (TextView ) findViewById(R.id.textViewAcessForget);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        buttonAcess.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                acessProcessing(textBoxUsername.getText().toString(),textBoxPassword.getText().toString());
                Log.i(textBoxUsername.getText().toString(),textBoxPassword.getText().toString()); }});

        recovery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), RecoverPassword.class);
                finish();
                startActivity(i); }});}

    private void acessProcessing(String textBoxUsername, String textBoxPassword){

        processingAuth.signInWithEmailAndPassword(textBoxUsername, textBoxPassword)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser userToken = processingAuth.getCurrentUser();
                            screenUser();

                        } else {

                            errorAcess();

                        }

                    }

                });

        }

    private void screenUser() {

        Intent i = new Intent(AcessAccount.this, MainUserInterface.class);
        finish();
        startActivity(i);

    }

    private void errorAcess() {

        Toast.makeText(getApplicationContext(), "@string/errorAcessAccount", Toast.LENGTH_SHORT).show();

            }
    @Override

    public void onStart() {

        super.onStart();
        FirebaseUser currentUser = processingAuth.getCurrentUser();

    }

}


