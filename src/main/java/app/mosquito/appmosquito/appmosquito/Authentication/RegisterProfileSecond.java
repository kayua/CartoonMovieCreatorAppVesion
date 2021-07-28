package app.mosquito.appmosquito.appmosquito.Authentication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import app.mosquito.appmosquito.appmosquito.R;


public class RegisterProfileSecond extends Activity {

    public static final String PREFS_NAME = "PersonalDatabase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.layout_authentication_first_step);

        ImageView imageView = (ImageView) findViewById(R.id.imageViewFirst);
        Button buttonNext =  findViewById(R.id.buttonUserLogin);
        TextView goBack =  findViewById(R.id.textViewSecondGoBack);
        EditText userBirthDate =  findViewById(R.id.editTextSecondGender);
        EditText userCity =  findViewById(R.id.editTextSecondCity);
        EditText userCompany =  findViewById(R.id.editTextSecondCompany);

        SharedPreferences userData = getSharedPreferences(PREFS_NAME, 0);
        String picturePath = userData.getString("imageUser", "");
        imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));


        buttonNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String stringBirthDate = userBirthDate.getText().toString();
                String stringCity = userCity.getText().toString();
                String stringCompany = userCompany.getText().toString();

                if(stringBirthDate.equals("")){

                    stringBirthDate = "@string/uninformed";

                }

                if(stringCity.equals("")){

                    stringCity = "@string/uninformed";

                }

                if(stringCompany.equals("")){

                    stringCompany = "@string/uninformed";

                }

                storeUserData(stringBirthDate, stringCity, stringCompany);

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

        Intent i = new Intent(RegisterProfileSecond.this, RegisterProfileFirst.class);
        finish();
        startActivity(i);

    }

    private void nextScreen() {

        Intent i = new Intent(RegisterProfileSecond.this, RegisterProfileThird.class);
        finish();
        startActivity(i);

    }

    private void storeUserData(String birthDate, String city, String company) {

        SharedPreferences userData = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editUserData = userData.edit();
        editUserData.putString("birthDate", birthDate);
        editUserData.putString("city",city);
        editUserData.putString("company", company);
        editUserData.apply();

    }

    @Override
    public void onStart() {

        super.onStart();

    }

}


