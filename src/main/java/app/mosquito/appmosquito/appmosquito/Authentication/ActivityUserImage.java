package app.mosquito.appmosquito.appmosquito.Authentication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import app.mosquito.appmosquito.appmosquito.ActivityUserInterface;
import app.mosquito.appmosquito.appmosquito.R;


public class ActivityUserImage extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.auth_user_image);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        ImageView imageView1;
        RoundImage roundedImage;

        imageView1 = (ImageView) findViewById(R.id.imageView23);
        Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.example2);
        roundedImage = new RoundImage(bm);
        imageView1.setImageDrawable(roundedImage);


    }

    private void screen_user() {
        Intent i = new Intent(ActivityUserImage.this, ActivityUserInterface.class);
        finish();
        startActivity(i);
    }

    private void toast() {
        Toast.makeText(getApplicationContext(), "Não foi possível encontrar sua conta. Verificar se o preenchimento está correto.", Toast.LENGTH_SHORT).show();
    }
    @Override

    public void onStart() {
        super.onStart();

    }
}


