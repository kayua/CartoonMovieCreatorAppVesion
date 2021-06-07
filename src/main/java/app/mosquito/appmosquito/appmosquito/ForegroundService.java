package app.mosquito.appmosquito.appmosquito;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import org.tensorflow.lite.Interpreter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import app.mosquito.appmosquito.appmosquito.ui.Audio.MFCC;
import app.mosquito.appmosquito.appmosquito.ui.Audio.WavFile;
import app.mosquito.appmosquito.appmosquito.ui.Audio.WavFileException;
import app.mosquito.appmosquito.appmosquito.ui.Audio.WavRecordFile;

import static app.mosquito.appmosquito.appmosquito.ui.Audio.WavFile.openWavFile;

public class ForegroundService extends Service {

    public static final String CHANNEL_ID = "Serviço de plano de fundo";
    private static final String mFileName = null;

    private final int RECORDER_SAMPLE_RATE = 44100;
    private final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_STEREO;
    private final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    protected Interpreter tflite;


    Runnable runnable = new Runnable(){

        public void run() {

            startRecord();

        }

    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, daemonize.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Detecção Ativada:")
                .setContentText(input)
                .setSmallIcon(R.drawable.icon_mosquito_bar)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                while (true){
                    startRecord();
                }
            }
        });
        return START_NOT_STICKY;
    }

    private void startRecord(){

        File path = Environment.getDataDirectory();
        WavRecordFile waveRecorder = new WavRecordFile(path.getPath());
        waveRecorder.startRecording();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        waveRecorder.stopRecording("test");


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            extractFeaturesAndRunEvaluation();
        } catch (IOException | WavFileException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "App mosquito",
                    NotificationManager.IMPORTANCE_LOW
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
    private MappedByteBuffer loadModelFile(String file) throws IOException
    {
        AssetFileDescriptor assetFileDescriptor = this.getAssets().openFd(file);
        FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();

        long startOffset = assetFileDescriptor.getStartOffset();
        long len = assetFileDescriptor.getLength();

        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,len);
    }

    public int doInference(float[][][][] input) throws IOException {

        Interpreter interpreter = null;
        for(int i=0; i<23; i++){
            String a = i+".tflite";
            interpreter = new Interpreter(loadModelFile(a), null);
            float output[][] = new float[1][1];

            interpreter.run(input, output);

            Log.i("************************************", String.valueOf(output[0][0]));



        }


        return 1;
    }

    public float[][][][] reshape(float[][] input_image){

        float[][][][] f = new float[1][60][60][1];
        int i;
        int j;
        for(i=0; i< 60; i++){

            for(j=0; j< 60; j++){

                f[0][i][j][0]= input_image[i][j];

            }

        }
        
    return f;

    }


    private void extractFeaturesAndRunEvaluation() throws IOException, WavFileException {


        WavFile readWavFile = openWavFile(new File("/storage/emulated/0/data/test.wav"));

        int numChannels = readWavFile.getNumChannels();

        final int BUF_SIZE = 16000;

        double[] buffer = new double[BUF_SIZE * numChannels];
        MFCC mfccConvert = new MFCC();

        readWavFile.readFrames(buffer, BUF_SIZE);
        int i = 0;
        float[][][] mfccInput = mfccConvert.processBulkSpectrograms(buffer, 60);

        for(i=0; i< mfccInput.length; i++){
            float[][] a = mfccInput[i];
            float[][][][] b = reshape(a);
            doInference(b);

        }

    }

}