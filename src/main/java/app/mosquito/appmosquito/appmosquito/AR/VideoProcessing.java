package app.mosquito.appmosquito.appmosquito.AR;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Rational;
import android.util.Size;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraX;
import androidx.camera.core.CameraX.LensFacing;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageAnalysis.ImageReaderMode;
import androidx.camera.core.ImageAnalysisConfig;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.Preview.OnPreviewOutputUpdateListener;
import androidx.camera.core.Preview.PreviewOutput;
import androidx.camera.core.PreviewConfig;
import androidx.camera.core.PreviewConfig.Builder;
import androidx.camera.core.UseCase;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import app.mosquito.appmosquito.appmosquito.AR.Engine.SurfaceComponent;
import app.mosquito.appmosquito.appmosquito.R;

public class VideoProcessing extends AppCompatActivity {
    private LensFacing lensFacing;
    private String TAG;
    private int REQUEST_CODE_PERMISSIONS;
    private String[] REQUIRED_PERMISSIONS;
    private Tfclassifier tfLiteClassifier;
    private HashMap _$_findViewCache;
    private Executor executor = Executors.newSingleThreadExecutor();
    private SurfaceView mSurfaceView;
    SurfaceHolder mSurfaceHolder;

    private SurfaceComponent mGLSurfaceView;

    TextureView textureView;

    private Tfclassifier tFliteClassifier = new Tfclassifier(VideoProcessing.this);


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.mel);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (this.allPermissionsGranted()) {
            ((TextureView) this.findViewById(R.id.textureView)).post((Runnable) (new Runnable() {
                public final void run() {
                    VideoProcessing.this.startCamera();
                }
            }));
            ((TextureView) this.findViewById(R.id.textureView)).addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                public final void onLayoutChange(View n, int nu, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
                    VideoProcessing.this.updateTransform();
                }
            });
        } else {
            ActivityCompat.requestPermissions((Activity) this, this.REQUIRED_PERMISSIONS, this.REQUEST_CODE_PERMISSIONS);
        }

        this.tfLiteClassifier.initialize().addOnSuccessListener((OnSuccessListener) null).addOnFailureListener(new OnFailureListener() {
            public final void onFailure(Exception e) {

                Log.e(VideoProcessing.this.TAG, "Error  setting up the classifier.", (Throwable) e);
            }
        });


    }

    private void startCamera() {
        DisplayMetrics displayMetrics = new DisplayMetrics();


        TextureView textureView = (TextureView) this.findViewById(R.id.textureView);

        textureView.getDisplay().getRealMetrics(displayMetrics);
        Size screenSize = new Size(displayMetrics.widthPixels, displayMetrics.heightPixels);
        Rational screenAspectRatio = new Rational(1, 1);
        Builder builder = new Builder();


        builder.setLensFacing(this.lensFacing);
        builder.setTargetResolution(screenSize);
        builder.setTargetAspectRatio(screenAspectRatio);
        WindowManager windowManager = this.getWindowManager();

        Display var25 = windowManager.getDefaultDisplay();

        builder.setTargetRotation(var25.getRotation());
        TextureView textureView1 = (TextureView) this.findViewById(R.id.textureView);

        var25 = textureView1.getDisplay();

        builder.setTargetRotation(var25.getRotation());
        PreviewConfig previewConfig = builder.build();
        Preview preview = new Preview(previewConfig);
        preview.setOnPreviewOutputUpdateListener(new OnPreviewOutputUpdateListener() {
            public final void onUpdated(PreviewOutput it) {
                TextureView textureView2 = (TextureView) VideoProcessing.this.findViewById(R.id.textureView);
                textureView2.setSurfaceTexture(it.getSurfaceTexture());
                VideoProcessing.this.updateTransform();
            }
        });
        mGLSurfaceView = new SurfaceComponent(this);
        mGLSurfaceView.setZOrderOnTop(true);
        mGLSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        addContentView(mGLSurfaceView, new WindowManager.LayoutParams(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT));

        androidx.camera.core.ImageAnalysisConfig.Builder builder1 = new androidx.camera.core.ImageAnalysisConfig.Builder();


        HandlerThread analysisThread = new HandlerThread("AnalysisThread");


        analysisThread.start();
        builder1.setCallbackHandler(new Handler(analysisThread.getLooper()));
        builder1.setImageReaderMode(ImageReaderMode.ACQUIRE_LATEST_IMAGE);
        ImageAnalysisConfig analyzerConfig = builder1.build();
        ImageAnalysis analyzerUseCase = new ImageAnalysis(analyzerConfig);
        analyzerUseCase.setAnalyzer((ImageAnalysis.Analyzer) (new ImageAnalysis.Analyzer() {
            public final void analyze(ImageProxy image, int rotationDegrees) {

                Bitmap bitmap = VideoProcessing.this.toBitmap(image);
                VideoProcessing.this.tfLiteClassifier.classifyAsync(bitmap).addOnSuccessListener(new OnSuccessListener() {
                    // $FF: synthetic method
                    // $FF: bridge method
                    public void onSuccess(Object var1) {
                        this.onSuccess((String) var1);
                    }

                    public final void onSuccess(String resultText) {
                        TextView viewById = (TextView) VideoProcessing.this.findViewById(R.id.predictedTextView);
                        if (viewById != null) {
                            viewById.setText((CharSequence) resultText);
                        }

                    }
                }).addOnFailureListener((OnFailureListener) null);
            }
        }));
        CameraX.bindToLifecycle((LifecycleOwner) this, new UseCase[]{(UseCase) preview, (UseCase) analyzerUseCase});
    }

    //convert image to bitmap
    public Bitmap toBitmap(ImageProxy imageProxy) {

        ImageProxy.PlaneProxy proxy = imageProxy.getPlanes()[0];

        ByteBuffer yBuffer = proxy.getBuffer();
        proxy = imageProxy.getPlanes()[1];

        ByteBuffer uBuffer = proxy.getBuffer();
        proxy = imageProxy.getPlanes()[2];

        ByteBuffer vBuffer = proxy.getBuffer();
        int ySize = yBuffer.remaining();
        int uSize = uBuffer.remaining();
        int vSize = vBuffer.remaining();
        byte[] nv21 = new byte[ySize + uSize + vSize];
        yBuffer.get(nv21, 0, ySize);
        vBuffer.get(nv21, ySize, vSize);
        uBuffer.get(nv21, ySize + vSize, uSize);
        YuvImage yuvImage = new YuvImage(nv21, 17, imageProxy.getWidth(), imageProxy.getHeight(), (int[]) null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, yuvImage.getWidth(), yuvImage.getHeight()), 100, (OutputStream) out);
        byte[] imageBytes = out.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        return bitmap;
    }

    private void updateTransform() {
        Matrix matrix = new Matrix();
        TextureView textureView = (TextureView) this.findViewById(R.id.textureView);

        float centerX = (float) textureView.getWidth() / 2.0F;
        textureView = (TextureView) this.findViewById(R.id.textureView);

        float centerY = (float) textureView.getHeight() / 2.0F;
        textureView = (TextureView) this.findViewById(R.id.textureView);

        Display display = textureView.getDisplay();

        short i;
        switch (display.getRotation()) {
            case 0:
                i = 0;
                break;
            case 1:
                i = 90;
                break;
            case 2:
                i = 180;
                break;
            case 3:
                i = 270;
                break;
            default:
                return;
        }

        int rotationDegrees = i;
        matrix.postRotate(-((float) rotationDegrees), centerX, centerY);
        ((TextureView) this.findViewById(R.id.textureView)).setTransform(matrix);
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == this.REQUEST_CODE_PERMISSIONS) {
            if (this.allPermissionsGranted()) {
                this.startCamera();
            } else {
                Toast.makeText((Context) this, (CharSequence) "Permissions not granted by the user.", 0).show();
                this.finish();
            }
        }

    }

    private final boolean allPermissionsGranted() {
        String[] required_permissions = this.REQUIRED_PERMISSIONS;
        int length = required_permissions.length;

        for (int i = 0; i < length; ++i) {
            String permission = required_permissions[i];
            if (ContextCompat.checkSelfPermission((Context) this, permission) != 0) {
                return false;
            }
        }

        return true;
    }

    protected void onDestroy() {
        this.tfLiteClassifier.close();
        super.onDestroy();
    }

    public VideoProcessing() {
        this.lensFacing = LensFacing.BACK;
        this.TAG = "MainActivity";
        this.REQUEST_CODE_PERMISSIONS = 101;
        this.REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA"};
        this.tfLiteClassifier = new Tfclassifier((Context) this);
    }


}