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

import app.mosquito.appmosquito.appmosquito.ActivityUserInterface;
import app.mosquito.appmosquito.appmosquito.R;


public class ActivityRegisterProfileThird extends Activity {

    public static final String PREFS_NAME = "PersonalDatabase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.auth_profile_second);
        writeFirebase();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView goBack =  findViewById(R.id.textViewEmailGoBackInit2);

        EditText userGender =  findViewById(R.id.editTextAuthGender);

        EditText userSchooling =  findViewById(R.id.editTextAuthSchooling);

        EditText userFavoriteWord =  findViewById(R.id.editTextAuthFavoriteWord);

        Button next_step =  findViewById(R.id.buttonAuthAcessRegisterAccount3);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);


        String picturePath = settings.getString("imageUser", "");

        ImageView imageView = (ImageView) findViewById(R.id.imageViedwa);
        imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        next_step.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String gender = userGender.getText().toString();
                String schooling = userSchooling.getText().toString();
                String favoriteWord = userFavoriteWord.getText().toString();

                if(gender.equals("")){ gender = "Não informado"; }

                if(schooling.equals("")){ schooling = "Não informado"; }

                if(favoriteWord.equals("")){ favoriteWord = "Não informado"; }

                storeLogin(gender, schooling, favoriteWord);
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

        Intent i = new Intent(ActivityRegisterProfileThird.this, ActivityRegisterProfileSecond.class);
        finish();
        startActivity(i);

    }

    private void nextScreen() {

        Intent i = new Intent(ActivityRegisterProfileThird.this, ActivityUserInterface.class);
        finish();
        startActivity(i);

    }

    private void storeLogin(String gender, String schooling, String favoriteWord) {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("gender", gender);
        editor.putString("schooling", schooling);
        editor.putString("favoriteWord", favoriteWord);
        editor.apply();

    }

    private void writeFirebase(){


        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        UserModel person = new UserModel();

        person.setUserName(settings.getString("username", ""));
        person.setUserCity(settings.getString("city", ""));
        person.setUserBirthDate(settings.getString("birthDate", ""));
        person.setUserSchooling(settings.getString("schooling", ""));
        person.setUserFavoriteWord(settings.getString("favoriteWord", ""));
        person.setUserGender(settings.getString("gender", ""));
        person.setPhoto(settings.getString("imageUser", ""));
        person.setUserCompany(settings.getString("company", ""));
        person.setUserEmail(settings.getString("email", ""));
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference tasksRef = rootRef.child("user_registers").push();
        tasksRef.setValue( person);
        uploadImage(settings.getString("imageUser", ""));

    }

    private void uploadImage(String filePath) {

        FirebaseStorage storage ;
        StorageReference storageReference;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Uri file = Uri.fromFile(new File(filePath));
        StorageReference riversRef = storageReference.child("images/"+file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();

    }
}


