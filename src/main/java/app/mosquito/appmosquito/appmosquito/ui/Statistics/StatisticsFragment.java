package app.mosquito.appmosquito.appmosquito.ui.Statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import app.mosquito.appmosquito.appmosquito.R;

public class StatisticsFragment extends Fragment {

    private StatisticsViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(StatisticsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_statistics, container, false);
        BarChart bchart = (BarChart) root.findViewById(R.id.barchart);
        BarChart bchart2 = (BarChart) root.findViewById(R.id.barchart2);
        BarChart bchart3 = (BarChart) root.findViewById(R.id.barchart3);
        BarChart bchart4 = (BarChart) root.findViewById(R.id.barchart4);
        bchart.setBorderColor(1);


        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = (int) 0; i < 12 + 1; i++) {
            float val = (float) (Math.random());
            yVals1.add(new BarEntry(i, val));
        }


        BarDataSet set1;

        set1 = new BarDataSet(yVals1, "The year 2017");



        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        set1.setColors(ColorTemplate.LIBERTY_COLORS);
        BarData data = new BarData(dataSets);

        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);

        bchart.setTouchEnabled(false);
        bchart2.setTouchEnabled(false);
        bchart3.setTouchEnabled(false);
        bchart4.setTouchEnabled(false);
        bchart.setData(data);
        bchart2.setData(data);
        bchart3.setData(data);
        bchart4.setData(data);

        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }


}
