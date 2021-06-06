package app.mosquito.appmosquito.appmosquito;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import app.mosquito.appmosquito.appmosquito.ui.Audio.MFCC;
import app.mosquito.appmosquito.appmosquito.ui.Audio.WavAudioRecorder;
import app.mosquito.appmosquito.appmosquito.ui.Audio.WavFile;
import app.mosquito.appmosquito.appmosquito.ui.Audio.WavFileException;

public class ForegroundService extends Service {

    public static final String CHANNEL_ID = "Serviço de plano de fundo";
    private static String mFileName = null;
    private WavAudioRecorder mRecorder;
    private static final String mRcordFilePath = Environment.getExternalStorageDirectory() + "/testwave.wav";

    Runnable runnable = new Runnable(){

        public void run() {

            try {

                startRecord();

            } catch (IOException e) {

                e.printStackTrace();

            } catch (WavFileException e) {

                e.printStackTrace();

            }

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
                    try {
                        startRecord();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (WavFileException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return START_NOT_STICKY;
    }

    private void startRecord() throws IOException, WavFileException {

        mRecorder = WavAudioRecorder.getInstanse();
        mRecorder.setOutputFile(mRcordFilePath);
        mRecorder.prepare();
        mRecorder.start();
        mRecorder.stop();
        extractFeaturesAndRunEvaluation();


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
        inputstream = new FileInputStream(mRcordFilePath);

        WavFile wavFile = new WavFile();
        WavFile.openWavFile(inputstream);
        int mNumFrames = 0;
        int mChannels = 0;
        mNumFrames = (int) wavFile.getNumFrames();
        mChannels = wavFile.getNumChannels();

        double[][] buffer = new double[mChannels][mNumFrames];
        wavFile.readFrames(buffer, mNumFrames, 0);
        MFCC mfccConvert = new MFCC();
        int i;
        for (i = 0; i < buffer.length; i++) {

            float[][][] results = mfccConvert.processBulkSpectrograms(buffer[i], 40);
            int j;

            for (j=0; j< results.length; j++) {

                val flattenedSpec = flattenSpectrogram(element)
                predictedResult =
                        max(loadModelAndMakePredictions(flattenedSpec), predictedResult)
            }


        }

    }
}