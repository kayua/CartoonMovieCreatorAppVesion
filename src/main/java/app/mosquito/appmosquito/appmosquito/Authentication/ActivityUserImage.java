package app.mosquito.appmosquito.appmosquito.Authentication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import app.mosquito.appmosquito.appmosquito.ActivityUserInterface;
import app.mosquito.appmosquito.appmosquito.R;


public class ActivityUserImage extends Activity {

    String picturePath;
    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.auth_user_image);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);



        int PICK_IMAGE = 1;

        ImageView buttonLoadImage = (ImageView) findViewById(R.id.imageView23);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });


    }

    private void screen_user() {
        Intent i = new Intent(ActivityUserImage.this, ActivityUserInterface.class);
        finish();
        startActivity(i);
    }
    private static int RESULT_LOAD_IMAGE = 1;




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.imageView23);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            ImageView imageView1;
            RoundImage roundedImage;
            imageView1 = (ImageView) findViewById(R.id.imageView23);
            Bitmap bm = BitmapFactory.decodeFile(picturePath);
            roundedImage = new RoundImage(bm);
            imageView1.setImageDrawable(roundedImage);

        }


    }

    private void toast() {
        Toast.makeText(getApplicationContext(), "Não foi possível encontrar sua conta. Verificar se o preenchimento está correto.", Toast.LENGTH_SHORT).show();
    }
    @Override

    public void onStart() {
        super.onStart();

    }
}


