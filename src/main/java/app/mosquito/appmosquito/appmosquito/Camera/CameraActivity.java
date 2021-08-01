package app.mosquito.appmosquito.appmosquito.Camera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import app.mosquito.appmosquito.appmosquito.R;

import static android.content.ContentValues.TAG;

public class CameraActivity extends AppCompatActivity {

    private CameraViewModel homeViewModel;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private Context context;
    MediaRecorder mediaRecorder;
    CameraPreview mPreview;
    Camera mCamera;


    View myView;
    boolean isUp;
    boolean isRecording = false;

    public static final String PREFS_NAME = "PersonalDatabase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_camera_main);

        homeViewModel = new ViewModelProvider(this).get(CameraViewModel.class);

        mCamera = getCameraInstance();
        myView = findViewById(R.id.mydd_view);
        mPreview = new CameraPreview(getApplicationContext(), mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        ImageView captureButton = findViewById(R.id.button_record);


        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (isRecording) {

                            mediaRecorder.stop();
                            releaseMediaRecorder();
                            mCamera.lock();

                            isRecording = false;
                            castToFrames();
                        } else {

                            if (prepareVideoRecorder()) {

                                mediaRecorder.start();

                                isRecording = true;
                            } else {
                                releaseMediaRecorder();
                            }
                        }
                    }
                });
        ImageView myButton_a = findViewById(R.id.imwgShare);
        ImageView myButton_b = findViewById(R.id.imageVieaaw25);

        myButton_b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                onSlideViewButtonClick(view);

            }

        });
        myButton_a.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                onSlideViewButtonClick(view);

            }

        });

    }
    private void castToFrames(){

        File videoFile = new File("/storage/emulated/0/Movies/MyCameraApp/VID_20210619_160213.mp4");

        Uri videoFileUri = Uri.parse(videoFile.toString());

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(videoFile.getAbsolutePath());
        ArrayList<Bitmap> rev = new ArrayList<Bitmap>();

        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), videoFileUri);
        int millis = mp.getDuration();
        for (int i = 0; i < millis; i += 100) {
            Bitmap bitmap = retriever.getFrameAtTime(i, MediaMetadataRetriever.OPTION_CLOSEST);
            rev.add(bitmap);

            try {
                saveFrames(rev);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }


    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {

            return true;
        } else {

            return false;
        }
    }

    public static Camera getCameraInstance() {
        Camera c = null;

        try {
            c = Camera.open();
            c.setDisplayOrientation(90);
        } catch (Exception e) {

        }
        return c;
    }

    private boolean prepareVideoRecorder() {

        mCamera = getCameraInstance();
        mediaRecorder = new MediaRecorder();
        mCamera.unlock();
        mediaRecorder.setCamera(mCamera);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
        mediaRecorder.setOutputFile(getOutputMediaFile(MEDIA_TYPE_VIDEO).toString());
        mediaRecorder.setPreviewDisplay(mPreview.getHolder().getSurface());

        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException e) {
            Log.d(TAG, "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();

            return false;
        } catch (IOException e) {
            Log.d(TAG, "IOException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();

            return false;
        }
        return true;
    }

    private void releaseMediaRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
            mCamera.lock();
        }
    }

    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MOVIES), "MyCameraApp");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");

        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
            Log.i("------>", mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    public void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,
                0,
                view.getHeight(),
                0);
        animate.setDuration(600);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.bringToFront();
    }

    public void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(300);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setEnabled(false);
        view.setFocusableInTouchMode(false);
        view.setFocusable(false);

    }

    public void onSlideViewButtonClick(View view) {
        if (isUp) {
            slideDown(myView);

        } else {
            slideUp(myView);

        }
        isUp = !isUp;
    }

    public void saveFrames(ArrayList<Bitmap> saveBitmapList) throws IOException {

        String folder = Environment.getExternalStorageDirectory().toString();
        File saveFolder = new File(folder + "/Movies/new /");
        if (!saveFolder.exists()) {
            saveFolder.mkdirs();
        }

        int i = 1;

        for (Bitmap b : saveBitmapList) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

            File f = new File(saveFolder, ("frame" + i + ".jpg"));

            f.createNewFile();

            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());

            fo.flush();
            fo.close();

            i++;
        }


    }



}


