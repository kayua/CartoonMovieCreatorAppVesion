
package app.mosquito.appmosquito.appmosquito.Camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;

import app.mosquito.appmosquito.appmosquito.R;

public class TransferredActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_camera);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String dataDir = getFilesDir().toString();
        File myDir = new File(dataDir);
        String fileName = "TransferredImage.jpg";
        File file = new File(myDir, fileName);
        Bitmap transferredBmp = BitmapFactory.decodeFile(file.getAbsolutePath());
        ImageView transferredImgView = findViewById(R.id.transferred_img);
        transferredImgView.setImageBitmap(transferredBmp);

        TextView resTextView = findViewById(R.id.transferred_str);
        resTextView.setText(TimerRecorder.getInstance().getCollectedTime());
    }

    @Override
    protected void onPause() {
        super.onPause();
        TimerRecorder.getInstance().clean();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        TimerRecorder.getInstance().clean();
        finish();
    }
}
