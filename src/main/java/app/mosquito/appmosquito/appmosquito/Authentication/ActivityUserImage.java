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
import android.widget.Button;
import android.widget.ImageView;

import app.mosquito.appmosquito.appmosquito.ActivityUserInterface;
import app.mosquito.appmosquito.appmosquito.R;


public class ActivityUserImage extends Activity {

    private static int RESULT_LOAD_IMAGE = 1;
    String picturePath;
    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.auth_user_image);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Button buttonRegister = (Button) findViewById(R.id.buttonAuthAcessRegisterAccount3);


        int PICK_IMAGE = 1;

        ImageView buttonLoadImage = (ImageView) findViewById(R.id.imageView18);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                screenProfileEditFirst();
            }
        });

    }






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

            ImageView imageView = (ImageView) findViewById(R.id.imageView18);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            ImageView imageView1;
            RoundImage roundedImage;
            imageView1 = (ImageView) findViewById(R.id.imageView18);
            Bitmap bm = BitmapFactory.decodeFile(picturePath);
            roundedImage = new RoundImage(bm);
            imageView1.setImageDrawable(roundedImage);

        }


    }

    @Override
    public void onStart() {
        super.onStart();

    }


    private void screenProfileEditFirst(){

        Intent i = new Intent(ActivityUserImage.this, ActivityEditProfileFirst.class);
        finish();
        startActivity(i);
    }

    private void screen_user() {
        Intent i = new Intent(ActivityUserImage.this, ActivityUserInterface.class);
        finish();
        startActivity(i);
    }

}


