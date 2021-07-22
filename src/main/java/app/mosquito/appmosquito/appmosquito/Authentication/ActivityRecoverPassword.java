package app.mosquito.appmosquito.appmosquito.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import app.mosquito.appmosquito.appmosquito.R;


public class ActivityRecoverPassword extends AppCompatActivity {

    public static final String PREFS_NAME = "PersonalDatabase";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Switch active_email_notification, active_realtime_notification, active_infestations_detect;

    public ActivityRecoverPassword() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.auth_recovery);
        TextView goBack= (TextView) findViewById(R.id.textView18);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        goBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ActivityStart.class);
                finish();
                startActivity(i);

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


    public void createUser(View view) throws InterruptedException {


    }
}
