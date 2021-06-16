package app.mosquito.appmosquito.appmosquito.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import app.mosquito.appmosquito.appmosquito.ActivityUserInterface;
import app.mosquito.appmosquito.appmosquito.R;

public class ActivityEmailChecking extends AppCompatActivity {

    public static final String PREFS_NAME = "PersonalDatabase";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.auth_email);
        TextView textViewGoBackInit = findViewById(R.id.textViewEmailGoBackInit);
        Button buttonGoUserInterface = findViewById(R.id.buttonAuthAcessEmail);

        textViewGoBackInit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                screen_start();
            }

        });

        buttonGoUserInterface.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                screen_start();
            }

        });

    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void screen_user() {
        Intent i = new Intent(ActivityEmailChecking.this, ActivityUserInterface.class);
        finish();
        startActivity(i);
    }

    private void screen_start() {
        Intent i = new Intent(ActivityEmailChecking.this, ActivityStart.class);
        finish();
        startActivity(i);
    }
}