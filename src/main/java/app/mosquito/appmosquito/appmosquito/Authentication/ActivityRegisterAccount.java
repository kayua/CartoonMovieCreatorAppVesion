package app.mosquito.appmosquito.appmosquito.Authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.parse.ParseUser;

import app.mosquito.appmosquito.appmosquito.R;


public class ActivityRegisterAccount extends AppCompatActivity {

    public static final String PREFS_NAME = "PersonalDatabase";
    private EditText username, password, email;
    private CheckBox license;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.auth_register);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("AUTH", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d("AUTH", "onAuthStateChanged:signed_out");
                }

            }
        };

        mAuth.signInWithEmailAndPassword("kayuaolequesp@gmail.com", "kayuaoleques").addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()) {
                    Log.w("AUTH", "Falha ao efetuar o Login: ", task.getException());
                }else{
                    Log.d("AUTH", "Login Efetuado com sucesso!!!");
                }
            }
        });
        mAuth
                .createUserWithEmailAndPassword("kayuapaim.aluno@unipampa.edu.br", "kayua1234")
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "Registration successful!",
                                    Toast.LENGTH_LONG)
                                    .show();


                        }
                        else {

                            // Registration failed
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Registration failed!!"
                                            + " Please try again later",
                                    Toast.LENGTH_LONG)
                                    .show();

                        }
                    }
                });

    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    public void createUser(View view) throws InterruptedException {


        if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()){


            Toast.makeText(this, "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show();

        }else{

            ParseUser user = new ParseUser();
            user.setUsername(username.getText().toString());

            user.setPassword(password.getText().toString());
            user.setEmail(email.getText().toString());
            user.put("phoneNumber", "99999999");


            if (license.isChecked()) {

                user.signUpInBackground(e -> {

                    if (e == null) {

                        Toast.makeText(this, "Cadastro efetuado com sucesso", Toast.LENGTH_SHORT).show();

                        store_login();
                        Intent i = new Intent(ActivityRegisterAccount.this, ActivityEmailChecking.class);
                        finish();
                        startActivity(i);

                    } else {

                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                });

            } else {

                Toast.makeText(this, "É necessário aceitar os termos de uso.", Toast.LENGTH_LONG).show();

            }

            user.getCurrentUser().logOut();

        }

    }

    private void store_login() {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("username", username.getText().toString());
        editor.putString("password", password.getText().toString());

       // User settings

        editor.putString("lowPower", "on");
        editor.putString("geoPrecision", "on");
        editor.putString("autoStart", "on");
        editor.putString("emailNotification", "on");
        editor.putString("realTimeNotification", "on");
        editor.putString("infectionNotification", "on");
        editor.putString("soundNotification", "on");
        editor.putString("advancedFilters", "on");
        editor.putString("rotationSwitch", "on");
        editor.putString("humiditySensor", "on");
        editor.putString("pressureSensor", "on");
        editor.putString("luminositySensor", "on");
        editor.putString("temperatureSensor", "on");
        editor.putString("nightActivity", "on");
        editor.putString("dayActivity", "on");
        editor.putString("daemonActivity", "off");

        editor.commit();
    }

}
