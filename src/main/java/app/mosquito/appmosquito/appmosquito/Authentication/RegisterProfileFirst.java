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


public class RegisterProfileFirst extends AppCompatActivity {

    public static final String PREFS_NAME = "PersonalDatabase";
    private FirebaseAuth processingAuth;
    private FirebaseAuth.AuthStateListener AuthenticationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(!(isOnline(getApplicationContext()))){

            screenErrorConnection();

        }

        processingAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.auth_register);

        Button buttonRegister = (Button) findViewById(R.id.buttonAuthAcessRegisterAccount3);
        EditText textBoxUsername = (EditText) findViewById(R.id.editTextAuthGender);
        EditText textBoxEmail = (EditText) findViewById(R.id.editTextAuthSchooling);
        EditText textBoxPassword = (EditText) findViewById(R.id.editTextAuthFavoriteWord);

        AuthenticationListener = new FirebaseAuth.AuthStateListener() {

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


                processingAuth.createUserWithEmailAndPassword(

                        textBoxEmail.getText().toString(), textBoxPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {

                                    String userName = textBoxUsername.getText().toString();
                                    String userEmail = textBoxEmail.getText().toString();
                                    String userPassword = textBoxEmail.getText().toString();

                                    storeUserData(userName, userEmail, userPassword);

                                    screenNext();

                                } else {

                                    Toast.makeText(getApplicationContext(), "@string/emailRegistered", Toast.LENGTH_LONG).show();

                                    textBoxEmail.setHintTextColor(Color.RED);
                                    textBoxEmail.setTextColor(Color.RED);
                                    textBoxEmail.getBackground().mutate().setColorFilter(
                                                 getResources().getColor(android.R.color.holo_red_light),
                                                 PorterDuff.Mode.SRC_ATOP);

                                }
                            }

                        });

            }

        });

    }

    public static boolean isOnline(Context context) {

        ConnectivityManager managerConnectionService = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = managerConnectionService.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnected())

            return true;

        else

            return false;

    }

    @Override
    public void onStart() {

        super.onStart();
        processingAuth.addAuthStateListener(AuthenticationListener);

    }

    @Override
    public void onStop() {

        super.onStop();

        if (AuthenticationListener != null) {

            processingAuth.removeAuthStateListener(AuthenticationListener);

        }

    }

    private void screenErrorConnection(){

        Intent i = new Intent(RegisterProfileFirst.this, ErrorConnection.class);
        finish();
        startActivity(i);

    }

    private void storeUserData(String username, String email, String password) {

        SharedPreferences userData = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editUserData = userData.edit();
        editUserData.putString("username", username);
        editUserData.putString("email", email);
        editUserData.apply();

    }

    private void screenNext(){

        Intent i = new Intent(RegisterProfileFirst.this, UserImage.class);
        finish();
        startActivity(i);
    }


}
