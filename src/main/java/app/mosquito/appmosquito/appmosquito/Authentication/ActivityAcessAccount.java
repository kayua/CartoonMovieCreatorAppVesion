package app.mosquito.appmosquito.appmosquito.Authentication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import app.mosquito.appmosquito.appmosquito.R;


public class ActivityAcessAccount extends AppCompatActivity {

    public static final String PREFS_NAME = "PersonalDatabase";
    private EditText username, password, email;
    private CheckBox license;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.auth_acess);


    }
    private void authentication(){

        Button buttonAcess = (Button) findViewById(R.id.buttonAuthAcessAccount);
        EditText textBoxUsername = (EditText) findViewById(R.id.editTextAuthUser);
        buttonAcess.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                screen_user();
            }});

    }







}
