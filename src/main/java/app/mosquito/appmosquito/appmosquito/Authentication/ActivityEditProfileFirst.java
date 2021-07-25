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

import app.mosquito.appmosquito.appmosquito.R;


public class ActivityEditProfileFirst extends Activity {

    public static final String PREFS_NAME = "PersonalDatabase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.auth_profile_first);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Button next_step =  findViewById(R.id.buttonAuthAcessRegisterAccount3);

        TextView goBack =  findViewById(R.id.textViewEmailGoBackInit2);

        EditText userBirthDate =  findViewById(R.id.editTextAuthGender);

        EditText userCity =  findViewById(R.id.editTextAuthSchooling);

        EditText userCompany =  findViewById(R.id.editTextAuthFavoriteWord);

        next_step.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String birthDate = userBirthDate.getText().toString();
                String city = userCity.getText().toString();
                String company = userCompany.getText().toString();

                if(birthDate.equals("")){ birthDate = "Não informado"; }

                if(city.equals("")){ city = "Não informado"; }

                if(company.equals("")){ company = "Não informado"; }

                store_login(birthDate, city, company);
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

        Intent i = new Intent(ActivityEditProfileFirst.this,ActivityRegisterAccount.class);
        finish();
        startActivity(i);

    }

    private void nextScreen() {

        Intent i = new Intent(ActivityEditProfileFirst.this, ActivityEditProfileSecond.class);
        finish();
        startActivity(i);

    }

    private void store_login(String birthDate, String city, String company) {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("birthDate", birthDate);
        editor.putString("city",city);
        editor.putString("company", company);
        editor.apply();

    }

    @Override
    public void onStart() {
        super.onStart();

    }

}


