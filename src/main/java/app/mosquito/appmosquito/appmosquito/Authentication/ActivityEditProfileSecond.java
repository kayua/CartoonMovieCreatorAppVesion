package app.mosquito.appmosquito.appmosquito.Authentication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import app.mosquito.appmosquito.appmosquito.ActivityUserInterface;
import app.mosquito.appmosquito.appmosquito.R;


public class ActivityEditProfileSecond extends Activity {

    public static final String PREFS_NAME = "PersonalDatabase";
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

    private void saveOnFirebase(){

        String birthDate = "";
        String city = "";
        String company = "";
        String gender = "";
        String schooling = "";
        String favoriteWord = "";


        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        try {

            birthDate = settings.getString("email", "");
            city = settings.getString("password", "");
            company = settings.getString("email", "");
            gender = settings.getString("password", "");
            schooling = settings.getString("email", "");
            favoriteWord = settings.getString("password", "");

        }catch (Exception e){

        }

        newUser = new UserModel(birthDate, city, company, gender, schooling, favoriteWord);




    }
    @Override
    public void onStart() {
        super.onStart();

    }
}


