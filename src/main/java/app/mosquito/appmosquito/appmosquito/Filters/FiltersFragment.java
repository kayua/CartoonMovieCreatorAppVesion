package app.mosquito.appmosquito.appmosquito.Filters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import app.mosquito.appmosquito.appmosquito.R;

public class FiltersFragment extends Fragment {

    public static final String PREFS_NAME = "PersonalDatabase";
    private FiltersViewModel settingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel = new ViewModelProvider(this).get(FiltersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_filters, container, false);

        return root;
        };

}