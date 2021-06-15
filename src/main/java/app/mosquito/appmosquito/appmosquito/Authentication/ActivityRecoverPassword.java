package app.mosquito.appmosquito.appmosquito.Authentication;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import app.mosquito.appmosquito.appmosquito.R;


public class ActivityRecoverPassword extends AppCompatActivity {

    public static final String PREFS_NAME = "PersonalDatabase";
    private EditText username, password, email;
    private CheckBox license;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Switch active_email_notification, active_realtime_notification, active_infestations_detect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.auth_recovery);

    }
    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }


    public void createUser(View view) throws InterruptedException {


    }
}
