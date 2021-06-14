package app.mosquito.appmosquito.appmosquito;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.parse.ParseObject;

import org.tensorflow.lite.Interpreter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import app.mosquito.appmosquito.appmosquito.Audio.MelFrequency;
import app.mosquito.appmosquito.appmosquito.Audio.ReaderWav;
import app.mosquito.appmosquito.appmosquito.Audio.RecorderWav;

import static app.mosquito.appmosquito.appmosquito.Audio.ReaderWav.openWavFile;

public class ForegroundService extends Service {

    public static final String CHANNEL_ID = "Serviço de plano de fundo";
    protected Interpreter tflite;
    SharedPreferences settings;


    Runnable runnable = new Runnable(){

        public void run() {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                startRecord();
            }

        }

    };

    @Override
    public void onCreate() {
        super.onCreate();
        settings = getSharedPreferences("PersonalDatabase", 0);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, DaemonService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Detecção Ativada:")
                .setContentText(input)
                .setSmallIcon(R.drawable.icon_mosquito_app_bar)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                while (true){

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                        String night_on = settings.getString("nightActivity", "");
                        String day_on = settings.getString("dayActivity", "");
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        String s = sdf.format(new Date()).substring(0, 2);


                        if(night_on.equals("on") && (Integer.parseInt(s)>18) || (Integer.parseInt(s)<6)){

                            Log.i("*Night mode on: ", s );
                            startRecord();

                        }else{

                            if(day_on.equals("on") && (Integer.parseInt(s)<18) && (Integer.parseInt(s)>6)){

                                Log.i("*Day mode on: ", s );
                                startRecord();

                            }else{

                                try {
                                    Thread.sleep(5000);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        });
        return START_NOT_STICKY;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void startRecord(){

        File path = Environment.getDataDirectory();
        RecorderWav waveRecorder = new RecorderWav(path.getPath());
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
        } catch (IOException e) {
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

    public void doInference(float[][][][] input) throws IOException {

        Interpreter interpreter = null;
        float[] output_signal = new float[23];

        for(int i=0; i<23; i++){

            float output[][] = new float[1][1];
            String a = i+".tflite";
            interpreter = new Interpreter(loadModelFile(a), null);


            interpreter.run(input, output);
            output_signal[i] = output[0][0];

        }

        interpreter = new Interpreter(loadModelFile("clustering.tflite"), null);

        float[][] output_signal_return = new float[1][23];


        interpreter.run(output_signal, output_signal_return);

        float max = 0;
        int indice =0;

        for(int i=0; i<23; i++){

            if(max<output_signal_return[0][i]){

                max = output_signal_return[0][i];
                indice=i;
            }

        }

        if(indice == 0){
            Log.i("RESULTADO: ", "*****************************");
            registerDetection();
            //registerDetection();
        }


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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void extractFeaturesAndRunEvaluation() throws IOException{


        ReaderWav readWavFile = openWavFile(new File("/storage/emulated/0/data/test.wav"));

        int numChannels = readWavFile.getNumChannels();

        final int BUF_SIZE = 16000;

        double[] buffer = new double[BUF_SIZE * numChannels];
        MelFrequency mfccConvert = new MelFrequency();

        readWavFile.readFrames(buffer, BUF_SIZE);
        int i = 0;
        float[][][] mfccInput = mfccConvert.processingSpectrogram(buffer, 60);

        for(i=0; i< mfccInput.length; i++){

            float[][] a = mfccInput[i];
            float[][][][] b = reshape(a);
            doInference(b);

        }

    }

    private void registerDetection(){

        ParseObject entity = new ParseObject("Detections");
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());


    }
}