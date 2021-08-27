package app.mosquito.appmosquito.appmosquito.AR;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;

import app.mosquito.appmosquito.appmosquito.AR.Engine.SurfaceComponent;
import app.mosquito.appmosquito.appmosquito.R;

import static app.mosquito.appmosquito.appmosquito.Editor.TextEditorDialogFragment.TAG;

public class AugmentedActivity extends Activity implements SurfaceHolder.Callback {
    private Camera camera;
    private SurfaceView mSurfaceView;
    SurfaceHolder mSurfaceHolder;

    private SurfaceComponent mGLSurfaceView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.tests);

        mSurfaceView = findViewById(R.id.surfaceView);
        mSurfaceHolder = mSurfaceView.getHolder();

        mSurfaceHolder.addCallback(this);

        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        mGLSurfaceView = new SurfaceComponent(this);
        mGLSurfaceView.setAlpha(0.2f);
        addContentView(mGLSurfaceView, new WindowManager.LayoutParams(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT));

    }
    
    public void takePicture(final String fileName) {
        Log.i("TAG", "Tacking picture");
        Camera.PictureCallback callback = new Camera.PictureCallback() {

            private String mPictureFileName = fileName;

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                Log.i(TAG, "Saving a bitmap to file");
                Bitmap picture = BitmapFactory.decodeByteArray(data, 0, data.length);

            }
        };

        camera.takePicture(null, null, callback);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        Camera.Parameters p = camera.getParameters();

        p.setPreviewSize(arg2, arg3);

        try {
            camera.setPreviewDisplay(arg0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        camera.startPreview();

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        camera = Camera.open();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
    }
}
