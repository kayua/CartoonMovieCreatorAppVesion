package app.mosquito.appmosquito.appmosquito.Cartoon;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import app.mosquito.appmosquito.appmosquito.R;

public class CartoonizeFragment extends Fragment {

    private CartoonizeViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = new ViewModelProvider(this).get(CartoonizeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cartoon, container, false);
        PieChart pieChart = (PieChart) root.findViewById(R.id.pieChart_sound);
        List<PieEntry> pieEntires = new ArrayList<>();
        for( int i = 0 ; i<2;i++){
            pieEntires.add(new PieEntry(i,""));
        }
        PieDataSet dataSet = new PieDataSet(pieEntires,"");

        PieData data = new PieData(dataSet);

        pieChart.setData(data);

        pieChart.setCenterText("Inicie a gravação \n ");
        pieChart.setCenterTextSize(18);
        pieChart.setContentDescription("");
        pieChart.setHoleRadius(60);
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(10);
        legend.setFormSize(5);
        legend.setFormToTextSpace(2);
        pieChart.invalidate();


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


}
