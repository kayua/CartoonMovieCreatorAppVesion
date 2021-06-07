package app.mosquito.appmosquito.appmosquito;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import app.mosquito.appmosquito.appmosquito.ui.Audio.MFCC;
import app.mosquito.appmosquito.appmosquito.ui.Audio.WavFile;
import app.mosquito.appmosquito.appmosquito.ui.Audio.WavFileException;
import app.mosquito.appmosquito.appmosquito.ui.Audio.WavRecordFile;

public class ForegroundService extends Service {

    public static final String CHANNEL_ID = "Serviço de plano de fundo";
    private static String mFileName = null;

    private int RECORDER_SAMPLE_RATE = 44100;
    private int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_STEREO;
    private int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;

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
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        waveRecorder.stopRecording("test");

        Log.i("TAG", "***************************************************");

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

    private void extractFeaturesAndRunEvaluation() throws IOException, WavFileException {

        InputStream inputstream;
        File path = Environment.getDataDirectory();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        inputstream = new FileInputStream("/storage/emulated/0/data/test.wav");
        WavFile wavFile = new WavFile();
        WavFile.openWavFile(inputstream);
        int mNumFrames = 0;
        int mChannels = 0;
        mNumFrames = (int) wavFile.getNumFrames();
        mChannels = wavFile.getNumChannels();
        Log.i("TAG***************************************", String.valueOf(mNumFrames));
        double[][] buffer = new double[2][216];
        wavFile.readFrames(buffer, 216, 0);
        MFCC mfccConvert = new MFCC();
        int i;

        for (i=0; i <buffer.length; i++) {

            float[][][] results = mfccConvert.processBulkSpectrograms(buffer[i], 40);
            int j;
            Log.i("Tamanho", String.valueOf(results.length));
            for (j=0; j<results.length; j++) {
                j++;
                j--;
            }


        }

    }


    private void predict(){




    }
}