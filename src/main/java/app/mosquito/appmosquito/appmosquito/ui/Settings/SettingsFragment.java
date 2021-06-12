package app.mosquito.appmosquito.appmosquito.ui.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
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

        Switch lowPower = (Switch) root.findViewById(R.id.lowPowerSwitch);
        Switch geoPrecision = (Switch) root.findViewById(R.id.geoPrecisionSwitch);
        Switch autoStart = (Switch) root.findViewById(R.id.autoStartSwitch);
        Switch emailNotification = (Switch) root.findViewById(R.id.emailNotificationSwitch);
        Switch realTimeNotification = (Switch) root.findViewById(R.id.realTimeNotificationSwitch);
        Switch infectionNotification = (Switch) root.findViewById(R.id.infectNotificationSwitch);
        Switch soundNotification = (Switch) root.findViewById(R.id.soundNotificationSwitch);
        Switch advancedFilters = (Switch) root.findViewById(R.id.advancedNotificationSwitch);
        Switch rotation = (Switch) root.findViewById(R.id.rotationSwitch);
        Switch humidity = (Switch) root.findViewById(R.id.sensorHumiditySwitch);
        Switch pressure = (Switch) root.findViewById(R.id.sensorPressureSwitch);
        Switch luminosity = (Switch) root.findViewById(R.id.sensorLuminositySwitch);
        Switch temperature = (Switch) root.findViewById(R.id.sensorTemperatureSwitch);
        Switch dayActivity = (Switch) root.findViewById(R.id.dayActivitySwitch);
        Switch nightActivity = (Switch) root.findViewById(R.id.nightActivitySwitch);

        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        lowPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(lowPower.isChecked()){

                    editor.putString("lowPower", "on");

                }else{

                    editor.putString("lowPower", "off");

                }

            }});

        geoPrecision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (geoPrecision.isChecked()){

                    editor.putString("geoPrecision", "on");

                }else{

                    editor.putString("lowPower", "off");

                }


            }});

        autoStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(autoStart.isChecked()){

                    editor.putString("autoStart", "on");

                }else{

                    editor.putString("autoStart", "off");

                }

            }});

        emailNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(emailNotification.isChecked()){

                editor.putString("emailNotification", "on");

                }else{

                editor.putString("emailNotification", "off");
                }

        }

        });

        realTimeNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(realTimeNotification.isChecked()){

                    editor.putString("realTimeNotification", "on");

                }else{

                    editor.putString("realTimeNotification", "off");}

            }

            });

        infectionNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(infectionNotification.isChecked()){

                    editor.putString("infectionNotification", "on");

                }else{

                    editor.putString("infectionNotification", "off");

                }

            }});

        soundNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(soundNotification.isChecked()){

                    editor.putString("soundNotification", "on");

            }else{

                editor.putString("soundNotification", "off");


        }}});

        advancedFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(advancedFilters.isChecked()){

                    editor.putString("advancedFilters", "on");


                }else{

                    editor.putString("advancedFilters", "off");


                }

            }});

        rotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(rotation.isChecked()){

                    editor.putString("rotationSwitch", "on");

                }else{


            editor.putString("rotationSwitch", "off");}}});

        humidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(humidity.isChecked()){



                    editor.putString("humiditySensor", "on");


                }else{


                editor.putString("humiditySensor", "off");}}


                });

        pressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(pressure.isChecked()){

                    editor.putString("pressureSensor", "on");

                }else{

                    editor.putString("pressureSensor", "off");

                }

            }});

        luminosity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(luminosity.isChecked()){

                    editor.putString("luminositySensor", "on");
                }else{


                    editor.putString("luminositySensor", "off");
                }}});

        dayActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(dayActivity.isChecked()){

                    editor.putString("dayActivity", "on");

                }else{

                    editor.putString("dayActivity", "off");

                }
            }
        });

        nightActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(nightActivity.isChecked()){

                    editor.putString("nightActivity", "on");

                }else{

                    editor.putString("nightActivity", "off");

                }
            }
        });

        temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(temperature.isChecked()){


                    editor.putString("temperatureSensor", "on");


                }else{

                    editor.putString("temperatureSensor", "off");


                }

            }});

        settingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
            });
        return root;
        };

}