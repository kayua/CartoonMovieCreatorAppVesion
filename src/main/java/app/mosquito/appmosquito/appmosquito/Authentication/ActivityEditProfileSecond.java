package app.mosquito.appmosquito.appmosquito.Authentication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.Random;

import app.mosquito.appmosquito.appmosquito.ActivityUserInterface;
import app.mosquito.appmosquito.appmosquito.R;


public class ActivityEditProfileSecond extends Activity {

    public static final String PREFS_NAME = "PersonalDatabase";

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    UserModel newUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.auth_profile_second);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView goBack =  findViewById(R.id.textViewEmailGoBackInit2);

        EditText userGender =  findViewById(R.id.editTextAuthGender);

        EditText userSchooling =  findViewById(R.id.editTextAuthSchooling);

        EditText userFavoriteWord =  findViewById(R.id.editTextAuthFavoriteWord);

        Button next_step =  findViewById(R.id.buttonAuthAcessRegisterAccount3);

        next_step.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String gender = userGender.getText().toString();
                String schooling = userSchooling.getText().toString();
                String favoriteWord = userFavoriteWord.getText().toString();

                if(gender.equals("")){ gender = "Não informado"; }

                if(schooling.equals("")){ schooling = "Não informado"; }

                if(favoriteWord.equals("")){ favoriteWord = "Não informado"; }

                store_login(gender, schooling, favoriteWord);
                saveOnFirebase();
                nextScreen();

            }

        });

        goBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                previousScreen();
            }

        });



    }

    private void previousScreen() {

        Intent i = new Intent(ActivityEditProfileSecond.this,ActivityEditProfileFirst.class);
        finish();
        startActivity(i);

    }

    private void nextScreen() {

        Intent i = new Intent(ActivityEditProfileSecond.this, ActivityUserInterface.class);
        finish();
        startActivity(i);

    }

    private void store_login(String gender, String schooling, String favoriteWord) {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("gender", gender);
        editor.putString("schooling", schooling);
        editor.putString("favoriteWord", favoriteWord);
        editor.apply();

    }

    @Override
    public void onStart() {
        super.onStart();

    }
}


