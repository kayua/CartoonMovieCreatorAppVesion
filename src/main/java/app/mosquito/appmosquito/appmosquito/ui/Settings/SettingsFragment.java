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
            public void onClick(View view) {editor.putString("lowPower", "on");}});

        geoPrecision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {editor.putString("geoPrecision", "on");}});

        autoStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {editor.putString("autoStart", "on");}});

        emailNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {editor.putString("emailNotification", "on");}});

        realTimeNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {editor.putString("realTimeNotification", "on");}});

        infectionNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {editor.putString("infectionNotification", "on");}});

        soundNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {editor.putString("soundNotification", "on");}});

        advancedFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {editor.putString("advancedFilters", "on");}});

        rotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {editor.putString("rotationSwitch", "on");}});

        humidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {editor.putString("humiditySensor", "on");}});

        pressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {editor.putString("pressureSensor", "on");}});

        luminosity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {editor.putString("luminositySensor", "on");}});
        dayActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {editor.putString("dayActivity", "on");}});

        temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {editor.putString("temperatureSensor", "on");}});


        settingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
            });
        return root;
        };

}