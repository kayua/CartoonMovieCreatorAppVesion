package app.mosquito.appmosquito.appmosquito.Authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import app.mosquito.appmosquito.appmosquito.MainUserInterface;
import app.mosquito.appmosquito.appmosquito.R;

public class SplashScreen extends AppCompatActivity {

    String usernameRegistered;
    String passwordRegistered;
    public static final String PREFS_NAME = "PersonalDatabase";
    private FirebaseAuth authProcessing;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_authentication_splash_screen);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Handler delayInit = new Handler();
        delayInit.postDelayed(new Runnable() {

            @Override
            public void run() {

                authenticationProcessing();
            }}, 3500);

       }

    private void authenticationProcessing(){

        authProcessing = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser userToken = firebaseAuth.getCurrentUser();

            }
        };

        SharedPreferences userData = getSharedPreferences(PREFS_NAME, 0);

        try {

            usernameRegistered = userData.getString("email", "");
            passwordRegistered = userData.getString("password", "");
            authProcessing.signInWithEmailAndPassword(usernameRegistered ,
                    passwordRegistered).addOnCompleteListener(this,
                    new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) { screenFirstUse(); }else{ screenUser(); }

                        } });


        }catch (Exception e){

            screenFirstUse();
        }

    }

    private void screenUser(){

        Intent i = new Intent(SplashScreen.this, MainUserInterface.class);
        finish();
        startActivity(i);
    }

    private void screenFirstUse(){

        Intent i = new Intent(SplashScreen.this, MenuSource.class);
        finish();
        startActivity(i);
    }

}