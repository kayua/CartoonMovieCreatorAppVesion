package app.mosquito.appmosquito.appmosquito.ui.Recognize;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.tensorflow.lite.Interpreter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import app.mosquito.appmosquito.appmosquito.Audio.MelFrequency;
import app.mosquito.appmosquito.appmosquito.Audio.ReaderWav;
import app.mosquito.appmosquito.appmosquito.Audio.RecorderWav;
import app.mosquito.appmosquito.appmosquito.Audio.WavFileException;
import app.mosquito.appmosquito.appmosquito.R;

import static app.mosquito.appmosquito.appmosquito.Audio.ReaderWav.openWavFile;

public class RecognizeFragment extends Fragment {

    private RecognizeViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(RecognizeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recognize, container, false);
        PieChart pieChart = (PieChart) root.findViewById(R.id.pieChart_sound);
        final Button button = (Button) root.findViewById(R.id.recognize_button);
        List<PieEntry> pieEntires = new ArrayList<>();
        for( int i = 0 ; i<2;i++){
            pieEntires.add(new PieEntry(i,""));
        }
        PieDataSet dataSet = new PieDataSet(pieEntires,"");
        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        PieData data = new PieData(dataSet);

        pieChart.setData(data);

        pieChart.setCenterText("Inicie a gravação \n ");
        pieChart.setCenterTextSize(18);
        pieChart.setContentDescription("");
        pieChart.setHoleRadius(60);
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(10);
        legend.setFormSize(18);
        legend.setFormToTextSpace(2);
        pieChart.invalidate();
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(View v) {

                Vibrator vs = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vs.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    vs.vibrate(500);
                }
                pieChart.setCenterText("Gravando ");
                pieChart.notifyDataSetChanged();

                pieChart.invalidate();


                new Thread(new Runnable() {
                    public void run() {
                        startRecord();
                        }

                }).start();



                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vs.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    vs.vibrate(500);
                }
            }
        });



        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }

    private MappedByteBuffer loadModelFile(String file) throws IOException {
        AssetFileDescriptor assetFileDescriptor = getContext().getAssets().openFd(file);
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
        ArrayList<String> nomes = new ArrayList<String>();
        nomes.add("Aedes Aegipty");
        nomes.add("Aedes Albopictus");
        nomes.add("Aedes Mediovittatus");
        nomes.add("Aedes Sierrensis");
        nomes.add("Anopheles Albimanus");
        nomes.add("Anopheles Dongola");
        nomes.add("Anopheles Rufisque");
        nomes.add("Anopheles Atroparvus");
        nomes.add("Anopheles Dirus");
        nomes.add("Aedes10");
        nomes.add("Aedes11");
        nomes.add("Aedes12");
        nomes.add("Aedes13");
        nomes.add("Aedes14");
        nomes.add("Aedes15");
        nomes.add("Aedes16");
        nomes.add("Aedes17");
        nomes.add("Aedes18");
        nomes.add("Aedes19");
        nomes.add("Aedes20");
        nomes.add("Aedes21");
        nomes.add("Aedes22");
        nomes.add("Aedes23");

        float max = 0;
        int indice =0;
        int indice_2 =0;
        int indice_3 =0;
        int indice_4 =0;
        float max_2 = 0;
        float max_3 = 0;
        float max_4 = 0;

        for(int i=0; i<23; i++){

            if(max<output_signal_return[0][i]){

                max = output_signal_return[0][i];
                indice=i;
            }

        }
        for(int i=0; i<23; i++){

            if(max_2<output_signal_return[0][i] && indice!=i){

                max_2 = output_signal_return[0][i];
                indice_2=i;
            }

        }
        for(int i=0; i<23; i++){

            if(max_3<output_signal_return[0][i] && indice!=i && indice_2!=i){

                max_3 = output_signal_return[0][i];
                indice_3=i;
            }

        }
        for(int i=0; i<23; i++){

            if(max_4<output_signal_return[0][i]&& indice!=i && indice_2!=i && indice_3!=i){

                max_4 = output_signal_return[0][i];
                indice_4=i;
            }

        }

        Log.i("RESULTADO >>>>>>>>>>>>>>>>>>>>>: ", String.valueOf(indice));
        PieChart pieChart = (PieChart) getActivity().findViewById(R.id.pieChart_sound);
        List<PieEntry> pieEntires = new ArrayList<>();
        float percentual = output_signal_return[0][indice];
        percentual += output_signal_return[0][indice_2];
        percentual += output_signal_return[0][indice_3];


        pieEntires.add(new PieEntry((output_signal_return[0][indice]/ percentual)*100,nomes.get(indice)));
        pieEntires.add(new PieEntry((output_signal_return[0][indice_2]/ percentual)*100,nomes.get(indice_2)));
        pieEntires.add(new PieEntry((output_signal_return[0][indice_3]/ percentual)*100,nomes.get(indice_3)));
        PieDataSet dataSet = new PieDataSet(pieEntires,"");
        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        PieData data = new PieData(dataSet);


        data.setDrawValues(true);
        pieChart.setData(data);

        if(indice == 0){
            Log.i("RESULTADO: ", "*****************************");
            pieChart.setCenterText("Aedes Aegipty Detectado ");
        }else{

            pieChart.setCenterText("Não Aedes Aegipty ");

        }
        pieChart.notifyDataSetChanged();

        pieChart.invalidate();





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
    private void extractFeaturesAndRunEvaluation() throws IOException, WavFileException {


        ReaderWav readWavFile = openWavFile(new File("/storage/emulated/0/data/test.wav"));

        int numChannels = readWavFile.getNumChannels();

        final int BUF_SIZE = 16000;

        double[] buffer = new double[BUF_SIZE * numChannels];
        MelFrequency mfccConvert = new MelFrequency();

        readWavFile.readFrames(buffer, BUF_SIZE);
        int i = 0;
        float[][][] mfccInput = mfccConvert.processBulkSpectrogram(buffer, 60);

        for(i=0; i< mfccInput.length; i++){
            float[][] a = mfccInput[i];
            float[][][][] b = reshape(a);
            doInference(b);

        }

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
        } catch (IOException | WavFileException e) {
            e.printStackTrace();
        }

    }
}
