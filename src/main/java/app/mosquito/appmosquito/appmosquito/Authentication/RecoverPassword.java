package app.mosquito.appmosquito.appmosquito.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import app.mosquito.appmosquito.appmosquito.R;


public class RecoverPassword extends AppCompatActivity {

    public static final String PREFS_NAME = "PersonalDatabase";
    private FirebaseAuth.AuthStateListener AuthenticationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.auth_recovery);
        TextView textViewMenuSource= (TextView) findViewById(R.id.textView18);

        textViewMenuSource.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), MenuSource.class);
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


}
