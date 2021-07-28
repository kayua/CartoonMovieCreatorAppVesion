package app.mosquito.appmosquito.appmosquito.Authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import app.mosquito.appmosquito.appmosquito.R;


public class ActivityRegisterProfileFirst extends AppCompatActivity {

    public static final String PREFS_NAME = "PersonalDatabase";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if(!(isOnline(getApplicationContext()))){

            Intent i = new Intent(ActivityRegisterProfileFirst.this, ActivityNotConnected.class);
            finish();
            startActivity(i);
        }

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.auth_register);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Button buttonRegister = (Button) findViewById(R.id.buttonAuthAcessRegisterAccount3);
        EditText textBoxUsername = (EditText) findViewById(R.id.editTextAuthGender);
        EditText textBoxEmail = (EditText) findViewById(R.id.editTextAuthSchooling);
        EditText textBoxPassword = (EditText) findViewById(R.id.editTextAuthFavoriteWord);

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

        buttonRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                mAuth.createUserWithEmailAndPassword(

                        textBoxEmail.getText().toString(), textBoxPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {

                                    store_login(textBoxUsername.getText().toString(),
                                            textBoxEmail.getText().toString(),
                                            textBoxPassword.getText().toString());

                                    screenRegisterProfileSecond();

                                } else {

                                    Toast.makeText(
                                            getApplicationContext(),
                                            "Esse email já foi registrado",
                                            Toast.LENGTH_LONG)
                                            .show();

                                    textBoxEmail.setHintTextColor(Color.RED);
                                    textBoxEmail.setTextColor(Color.RED);
                                    textBoxEmail.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);


                                }
                            }
                        });

            }

        });

    }

    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnected())

            return true;

        else

            return false;

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

    private void store_login(String username, String email, String password) {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("username", username);
        editor.putString("email", email);

        editor.apply();
    }

    private void screenRegisterProfileSecond(){

        Intent i = new Intent(ActivityRegisterProfileFirst.this, ActivityRegisterProfileSecond.class);
        finish();
        startActivity(i);
    }

    private void screen_user(){

        Intent i = new Intent(ActivityRegisterProfileFirst .this, ActivityAcessAccount.class);
        finish();
        startActivity(i);
    }

}
