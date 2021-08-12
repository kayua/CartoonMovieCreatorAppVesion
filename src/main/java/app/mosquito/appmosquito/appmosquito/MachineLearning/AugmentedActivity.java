package app.mosquito.appmosquito.appmosquito.MachineLearning;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;

import app.mosquito.appmosquito.appmosquito.MachineLearning.Engine.SurfaceComponent;

public class AugmentedActivity extends Activity implements SurfaceHolder.Callback {
    private Camera camera;
    private SurfaceView mSurfaceView;
    SurfaceHolder mSurfaceHolder;

    private SurfaceComponent mGLSurfaceView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mSurfaceView = new SurfaceView(this);
        addContentView(mSurfaceView, new WindowManager.LayoutParams(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT));
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT| WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        mGLSurfaceView = new SurfaceComponent(this);
        addContentView(mGLSurfaceView, new WindowManager.LayoutParams(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT));

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
