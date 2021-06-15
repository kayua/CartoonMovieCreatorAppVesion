package app.mosquito.appmosquito.appmosquito.Authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import app.mosquito.appmosquito.appmosquito.ActivityUserInterface;
import app.mosquito.appmosquito.appmosquito.R;

public class ActivitySplashScreen extends AppCompatActivity {

    String usernameRegistered;
    String passwordRegistered;
    public static final String PREFS_NAME = "PersonalDatabase";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                authentication();
            }}, 10000);

       }

    private void authentication(){

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

            }
        };
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        usernameRegistered = settings.getString("username", "");
        passwordRegistered = settings.getString("password", "");
        mAuth.signInWithEmailAndPassword(usernameRegistered ,
                passwordRegistered).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            screen_start();
                        }else{
                            screen_user();
                        }



                    } });
    }

    private void screen_user(){

        Intent i = new Intent(ActivitySplashScreen.this, ActivityUserInterface.class);
        finish();
        startActivity(i);
    }

    private void screen_start(){



        Intent i = new Intent(ActivitySplashScreen.this, ActivityStart.class);
        finish();
        startActivity(i);
    }

}