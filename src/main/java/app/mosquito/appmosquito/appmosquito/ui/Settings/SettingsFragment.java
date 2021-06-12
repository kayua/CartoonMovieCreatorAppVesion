package app.mosquito.appmosquito.appmosquito.ui.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import app.mosquito.appmosquito.appmosquito.R;

public class SettingsFragment extends Fragment {
    public static final String PREFS_NAME = "PersonalDatabase";
    private SettingsViewModel settingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        lowPower = root.findViewById()


        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("lowPower", "on");
        editor.putString("geoPrecision", "on");
        editor.putString("autoStart", "on");
        editor.putString("emailNotification", "on");
        editor.putString("realTimeNotification", "on");
        editor.putString("infectionNotification", "on");
        editor.putString("soundNotification", "on");
        editor.putString("advancedFilters", "on");
        editor.putString("rotationSwitch", "on");
        editor.putString("humiditySensor", "on");
        editor.putString("pressureSensor", "on");
        editor.putString("soundNotification", "on");
        editor.putString("luminositySensor", "on");
        editor.putString("temperatureSensor", "on");
        editor.putString("nightActivity", "on");
        editor.putString("dayActivity", "on");
        editor.putString("daemonActivity", "off");

        buttonname.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });



        editor.putString("username", username.getText().toString());
        editor.putString("password", password.getText().toString());

        settingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }


        return root;
    }

}