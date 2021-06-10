package app.mosquito.appmosquito.appmosquito.ui.Recognize;

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
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import app.mosquito.appmosquito.appmosquito.R;

public class RecognizeFragment extends Fragment {

    private RecognizeViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(RecognizeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recognize, container, false);
        PieChart pieChart = (PieChart) root.findViewById(R.id.pieChart_sound);

        List<PieEntry> pieEntires = new ArrayList<>();
        for( int i = 0 ; i<4;i++){
            pieEntires.add(new PieEntry(i,i));
        }
        PieDataSet dataSet = new PieDataSet(pieEntires,"");
        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        PieData data = new PieData(dataSet);

        pieChart.setData(data);
        pieChart.invalidate();
        pieChart.setCenterText("50% \n ");
        pieChart.setDrawEntryLabels(false);
        pieChart.setContentDescription("");
        pieChart.setEntryLabelTextSize(12);
        pieChart.setHoleRadius(60);
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(12);
        legend.setFormSize(20);
        legend.setFormToTextSpace(2);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }



}
