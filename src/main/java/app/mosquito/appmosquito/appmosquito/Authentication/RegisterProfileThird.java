package app.mosquito.appmosquito.appmosquito.Authentication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import app.mosquito.appmosquito.appmosquito.MainUserInterface;
import app.mosquito.appmosquito.appmosquito.R;


public class RegisterProfileThird extends Activity {

    public static final String PREFS_NAME = "PersonalDatabase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SharedPreferences userData = getSharedPreferences(PREFS_NAME, 0);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_authentication_second_step);
        writeFirebase();

        TextView textViewGoBack =  findViewById(R.id.textViewEmailGoBackInit2);
        EditText editTextUserGender =  findViewById(R.id.editTextAuthGender);
        EditText editTextUserSchooling =  findViewById(R.id.editTextAuthSchooling);
        EditText userFavoriteWord =  findViewById(R.id.editTextAuthFavoriteWord);

        Button buttonNextStep =  findViewById(R.id.buttonAuthAcessRegisterAccount3);

        String picturePath = userData.getString("imageUser", "");

        ImageView imageView = (ImageView) findViewById(R.id.imageViedwa);
        imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        buttonNextStep.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String stringGender = editTextUserGender.getText().toString();
                String stringSchooling = editTextUserSchooling.getText().toString();
                String stringFavoriteWord = userFavoriteWord.getText().toString();

                if(stringGender.equals("")){ stringGender = "@String/uninformed"; }

                if(stringSchooling.equals("")){ stringSchooling = "@String/uninformed"; }

                if(stringFavoriteWord.equals("")){ stringFavoriteWord = "@String/uninformed"; }

                storeLogin(stringGender, stringSchooling, stringFavoriteWord);
                nextScreen();

            }

        });

        textViewGoBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                previousScreen();
            }

        });

    }

    private void previousScreen() {

        Intent i = new Intent(RegisterProfileThird.this, RegisterProfileSecond.class);
        finish();
        startActivity(i);

    }

    private void nextScreen() {

        Intent i = new Intent(RegisterProfileThird.this, MainUserInterface.class);
        finish();
        startActivity(i);

    }

    private void storeLogin(String gender, String schooling, String favoriteWord) {

        SharedPreferences userData = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editUserData = userData.edit();
        editUserData.putString("gender", gender);
        editUserData.putString("schooling", schooling);
        editUserData.putString("favoriteWord", favoriteWord);
        editUserData.apply();

    }

    private void writeFirebase(){


        SharedPreferences userData = getSharedPreferences(PREFS_NAME, 0);

        UserModel userClass = new UserModel();

        userClass.setUserName(userData.getString("username", ""));
        userClass.setUserCity(userData.getString("city", ""));
        userClass.setUserBirthDate(userData.getString("birthDate", ""));
        userClass.setUserSchooling(userData.getString("schooling", ""));
        userClass.setUserFavoriteWord(userData.getString("favoriteWord", ""));
        userClass.setUserGender(userData.getString("gender", ""));
        userClass.setPhoto(userData.getString("imageUser", ""));
        userClass.setUserCompany(userData.getString("company", ""));
        userClass.setUserEmail(userData.getString("email", ""));
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference tasksRef = rootRef.child("user_registers").push();
        tasksRef.setValue( userClass);
        uploadImage(userData.getString("imageUser", ""), userData.getString("email", ""));

    }

    private void uploadImage(String filePath, String userProfile) {

        FirebaseStorage storage;
        StorageReference storageReference;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Uri file = Uri.fromFile(new File(filePath));
        StorageReference riversRef = storageReference.child("user_"+userProfile+"/"+file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception exception) {}

        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {}

        });
    }

    @Override
    public void onStart() {

        super.onStart();

    }
}


