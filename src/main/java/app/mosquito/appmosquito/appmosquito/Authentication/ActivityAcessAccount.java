package app.mosquito.appmosquito.appmosquito.Authentication;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import app.mosquito.appmosquito.appmosquito.R;


public class ActivityAcessAccount extends AppCompatActivity {

    public static final String PREFS_NAME = "PersonalDatabase";
    private EditText username, password, email;
    private CheckBox license;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.auth_acess);


    }
    private void authentication(){

        Button buttonAcess = (Button) findViewById(R.id.buttonAuthAcessAccount);

        buttonAcess.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                mAuthListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();

                    }
                };

                EditText textBoxUsername = (EditText) findViewById(R.id.editTextAuthUser);
                EditText textBoxPassword = (EditText) findViewById(R.id.editTextAuthPassword);
                Editable username = textBoxUsername.getText();
                Editable password = textBoxPassword.getText();

                mAuth.signInWithEmailAndPassword(username.toString(),password.toString()).addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {
                                    screen_start();
                                }else{
                                    screen_user();
                                }

            }});

                    } });

    }










}
