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

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

import app.mosquito.appmosquito.appmosquito.ActivityUserInterface;
import app.mosquito.appmosquito.appmosquito.R;


public class ActivityEditProfileSecond extends Activity {

    public static final String PREFS_NAME = "PersonalDatabase";

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

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

    public static int getRandomId() {

        Random random = new Random();

        return random.nextInt((100000000 - 999999999 ) + 1) + 100000000;

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

    private int getUniqueId(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user_id_list");
        DatabaseReference idReference = reference.child("user_id");
        String randomNumber = Integer.toString(getRandomId());


        String list_key = idReference.getKey();

        return 1;
    }

    private boolean getIdFirebase(String key) {

        Query query;

        if (key.equals("")){
            query = databaseReference.child("user_id_list").orderByChild("user_id");

        }else{

            query = databaseReference.child("user_id_list").orderByChild("user_id").startAt(key).endAt(key+"\uf8ff");
        }

        final boolean[] searchValue = new boolean[1];
        searchValue[0]=false;

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){

                    if (key == objSnapshot.getValue()) {

                        searchValue[0] =true;
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return searchValue[0];
    }

    private void initializeFirebase() {

        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    @Override
    public void onStart() {
        super.onStart();

    }
}


