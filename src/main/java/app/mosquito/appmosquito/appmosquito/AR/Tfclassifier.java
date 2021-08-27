package app.mosquito.appmosquito.appmosquito.AR;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import org.tensorflow.lite.Delegate;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.GpuDelegate;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Tfclassifier {

    //constructor
    public Tfclassifier(Context context) {
        super();
        this.con = context;
        this.labels = new ArrayList();
        ExecutorService executorService = Executors.newCachedThreadPool();

        this.executorService = executorService;
    }

    private Context con;
    private Interpreter interpreter = null;
    private boolean isIntiliased = false;
    private GpuDelegate gpuDelegate = null;
    ArrayList<String> labels = new ArrayList<String>();
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private int inputImageWidth = 0;
    private int inputImageHeight = 0;
    private int modelInputSize = 0;

    public final Task initialize() {
        Task taskinit = Tasks.call((Executor) this.executorService, (Callable) (new Callable() {

            public final Void call() throws Exception {
                Tfclassifier.this.initializeInterpreter();
                return null;
            }
        }));

        return taskinit;
    }

    //exw na ftiaxw ton intepreter kai na kanv load to montelo
    private void initializeInterpreter() {
        AssetManager assetManager = this.con.getAssets();
        ByteBuffer model = null;
        try {
            model = this.loadModelFile(assetManager, "model.tflite");
            this.labels = this.loadLines(this.con, "labels.txt");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("----------dasdasdajsknsabiuavsfiavsfihyavsfuavsi","sfbsiudfbsidf");
        Interpreter.Options options = new Interpreter.Options();
        options.setUseXNNPACK(true);
        options.setAllowBufferHandleOutput(true);
        options.setAllowFp16PrecisionForFp32(true);

        Interpreter interpreter = new Interpreter(model, options);
        int[] inputShape = interpreter.getInputTensor(0).shape();
        this.inputImageWidth = inputShape[1];
        this.inputImageHeight = inputShape[2];
        this.modelInputSize = 4 * this.inputImageWidth * this.inputImageHeight * 3;
        this.interpreter = interpreter;
        this.isIntiliased = true;
    }

    //methodos gia na kanw load to montelo
    private ByteBuffer loadModelFile(AssetManager assetManager, String filename) throws IOException {

        AssetFileDescriptor assetFileDescriptor = assetManager.openFd(filename);

        AssetFileDescriptor fileDescriptor = assetFileDescriptor;
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);

        return (ByteBuffer) mappedByteBuffer;
    }

    //methodos gia na pairnw sto arraylist ta labels
    public ArrayList loadLines(Context context, String filename) throws IOException {

        Scanner s = new Scanner((Readable) (new InputStreamReader(context.getAssets().open(filename))));
        ArrayList labels = new ArrayList();

        while (s.hasNextLine()) {
            labels.add(s.nextLine());
        }

        s.close();
        return labels;
    }

    //pairnw tyo result gia to label
    private int getMaxResult(float[] result) {

        float probability = result[0];
        int index = 0;
        for (int i = 0; i < result.length; i++) {

            if (probability < result[i]) {
                probability = result[i];
                index = i;
            }
        }

        return index;
    }

    //kanw to clasify
    private String classify(Bitmap bitmap) {
        boolean isIntiliased = this.isIntiliased;

        if (!isIntiliased) {

            String eroor = "TF Lite Interpreter is not initialized yet.";
            System.out.println(eroor);
            Toast.makeText(con.getApplicationContext(), eroor, Toast.LENGTH_SHORT).show();
            return null;
        } else {
            Bitmap resizedImage = Bitmap.createScaledBitmap(bitmap, this.inputImageWidth, this.inputImageHeight, true);

            ByteBuffer byteBuffer = this.convertBitmapToByteBuffer(resizedImage);
            byte index1 = 1;
            float[][] out = new float[index1][];

            for (int i = 0; i < index1; ++i) {

                float[] out1 = new float[this.labels.size()];
                out[i] = out1;
            }

            float[][] output = (float[][]) out;
            long startTime = SystemClock.uptimeMillis();
            Interpreter inter = this.interpreter;
            if (inter != null) {
                inter.run(byteBuffer, output);
            }

            long endTime = SystemClock.uptimeMillis();
            long inferenceTime = endTime - startTime;
            int index = this.getMaxResult(output[0]);
            String result = "Prediction is " + (String) this.labels.get(index) + "\nInference Time " + inferenceTime + " ms";
            return result;
        }
    }

    //methodos gia t callback ths asyxronis clasify
    public Task classifyAsync(final Bitmap bitmap) {

        Task taskcla = Tasks.call((Executor) this.executorService, (Callable) (new Callable() {


            public final String call() {
                return Tfclassifier.this.classify(bitmap);
            }
        }));

        return taskcla;
    }

    //methodos pou kleinw to intepreter
    public void close() {
        Tasks.call((Executor) this.executorService, (Callable) (new Callable() {


            public final Void call() {
                Interpreter interpreter = Tfclassifier.this.interpreter;
                if (interpreter != null) {
                    interpreter.close();
                }

                if (Tfclassifier.this.gpuDelegate != null) {
                    GpuDelegate gpu = Tfclassifier.this.gpuDelegate;
                    if (gpu == null) {

                    }

                    gpu.close();
                    Tfclassifier.this.gpuDelegate = (GpuDelegate) null;
                }

                Log.d("TfliteClassifier", "Closed TFLite interpreter.");
                return null;
            }
        }));
    }


    private ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(modelInputSize);
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] pixels = new int[inputImageWidth * inputImageHeight];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int pixel = 0;
        for (int i = 0; i < inputImageWidth; i++) {
            for (int j = 0; j < inputImageHeight; j++) {
                int pixelval = pixels[pixel++];
                byteBuffer.putFloat(((pixelval >> 16) & 0xFF) - IMAGE_MEAN / IMAGE_STD);
                byteBuffer.putFloat(((pixelval >> 8) & 0xFF) - IMAGE_MEAN / IMAGE_STD);
                byteBuffer.putFloat((pixelval & 0xFF) - IMAGE_MEAN / IMAGE_STD);

            }


        }
        bitmap.recycle();
        return byteBuffer;


    }

    private String TAG = "TfliteClassifier";
    private int FLOAT_TYPE_SIZE = 4;
    private int CHANNEL_SIZE = 3;
    private float IMAGE_MEAN = 127.5f;
    private float IMAGE_STD = 127.5f;
}






