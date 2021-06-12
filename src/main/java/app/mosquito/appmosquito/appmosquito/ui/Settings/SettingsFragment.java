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
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        Switch lowPower = (Switch) root.findViewById(R.id.lowPowerSwitch);
        String switchLowPower = settings.getString("lowPower", "");
        if(switchLowPower.equals("on")){
            lowPower.setChecked(true); }else{
            lowPower.setChecked(false); }

        Switch geoPrecision = (Switch) root.findViewById(R.id.geoPrecisionSwitch);
        String switchGeoPrecision = settings.getString("geoPrecision", "");
        if(switchGeoPrecision.equals("on")){
            geoPrecision.setChecked(true); }else{
            geoPrecision.setChecked(false); }

        Switch autoStart = (Switch) root.findViewById(R.id.autoStartSwitch);
        String switchAutoStart = settings.getString("autoStart", "");
        if(switchAutoStart.equals("on")){
            autoStart.setChecked(true); }else{
            autoStart.setChecked(false); }

        Switch emailNotification = (Switch) root.findViewById(R.id.emailNotificationSwitch);
        String switchEmailNotification = settings.getString("emailNotification", "");
        if(switchEmailNotification.equals("on")){
            emailNotification.setChecked(true); }else{
            emailNotification.setChecked(false); }

        Switch realTimeNotification = (Switch) root.findViewById(R.id.realTimeNotificationSwitch);
        String switchRealTimeNotification = settings.getString("realTimeNotification", "");
        if(switchRealTimeNotification.equals("on")){
            realTimeNotification.setChecked(true); }else{
            realTimeNotification.setChecked(false); }

        Switch infectionNotification = (Switch) root.findViewById(R.id.infectNotificationSwitch);
        String switchInfectionNotification = settings.getString("infectionNotification", "");
        if(switchInfectionNotification.equals("on")){
            infectionNotification.setChecked(true); }else{
            infectionNotification.setChecked(false); }

        Switch soundNotification = (Switch) root.findViewById(R.id.soundNotificationSwitch);
        String switchSoundNotification = settings.getString("soundNotification", "");
        if(switchSoundNotification.equals("on")){
            soundNotification.setChecked(true); }else{
            soundNotification.setChecked(false); }

        Switch advancedFilters = (Switch) root.findViewById(R.id.advancedNotificationSwitch);
        String switchAdvancedFilters = settings.getString("advancedFilters", "");
        if(switchAdvancedFilters.equals("on")){
            advancedFilters.setChecked(true); }else{
            advancedFilters.setChecked(false); }

        Switch rotation = (Switch) root.findViewById(R.id.rotationSwitch);
        String switchRotation = settings.getString("rotationSwitch", "");
        if(switchRotation.equals("on")){
            rotation.setChecked(true); }else{
            rotation.setChecked(false); }

        Switch humidity = (Switch) root.findViewById(R.id.sensorHumiditySwitch);
        String switchHumidity = settings.getString("humiditySensor", "");
        if(switchHumidity.equals("on")){
            humidity .setChecked(true); }else{
            humidity .setChecked(false); }

        Switch pressure = (Switch) root.findViewById(R.id.sensorPressureSwitch);
        String switchPressure= settings.getString("pressureSensor", "");
        if(switchPressure.equals("on")){
            pressure.setChecked(true); }else{
            pressure.setChecked(false); }

        Switch luminosity = (Switch) root.findViewById(R.id.sensorLuminositySwitch);
        String switchLuminosity= settings.getString("luminositySensor", "");
        if(switchLuminosity.equals("on")){
            luminosity.setChecked(true); }else{
            luminosity.setChecked(false); }

        Switch temperature = (Switch) root.findViewById(R.id.sensorTemperatureSwitch);
        String switchTemperature = settings.getString("temperatureSensor", "");
        if(switchTemperature.equals("on")){
            temperature.setChecked(true); }else{
            temperature.setChecked(false); }

        Switch dayActivity = (Switch) root.findViewById(R.id.dayActivitySwitch);
        String switchDayActivity  = settings.getString("dayActivity", "");
        if(switchDayActivity.equals("on")){
            dayActivity.setChecked(true); }else{
            dayActivity.setChecked(false); }

        Switch nightActivity = (Switch) root.findViewById(R.id.nightActivitySwitch);
        String switchNightActivity  = settings.getString("nightActivity", "");
        if(switchDayActivity.equals("on")){
            nightActivity.setChecked(true); }else{
            nightActivity.setChecked(false); }

        lowPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(lowPower.isChecked()){

                    editor.putString("lowPower", "on");

                }else{

                    editor.putString("lowPower", "off");

                }
                editor.commit();

            }});

        geoPrecision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (geoPrecision.isChecked()){

                    editor.putString("geoPrecision", "on");

                }else{

                    editor.putString("geoPrecision", "off");

                }
                editor.commit();

            }});

        autoStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(autoStart.isChecked()){

                    editor.putString("autoStart", "on");

                }else{

                    editor.putString("autoStart", "off");

                }
                editor.commit();
            }});

        emailNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(emailNotification.isChecked()){

                editor.putString("emailNotification", "on");

                }else{

                editor.putString("emailNotification", "off");
                }
                editor.commit();
        }

        });

        realTimeNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(realTimeNotification.isChecked()){

                    editor.putString("realTimeNotification", "on");

                }else{

                    editor.putString("realTimeNotification", "off");}
                editor.commit();
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
                editor.commit();
            }});

        soundNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(soundNotification.isChecked()){

                    editor.putString("soundNotification", "on");

            }else{

                editor.putString("soundNotification", "off");
                }
                editor.commit();
            }});

        advancedFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(advancedFilters.isChecked()){

                    editor.putString("advancedFilters", "on");


                }else{

                    editor.putString("advancedFilters", "off");


                }
                editor.commit();

            }});

        rotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(rotation.isChecked()){

                    editor.putString("rotationSwitch", "on");

                }else{


            editor.putString("rotationSwitch", "off");}
                editor.commit();
            }});

        humidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(humidity.isChecked()){



                    editor.putString("humiditySensor", "on");


                }else{


                editor.putString("humiditySensor", "off");}

                editor.commit();
            }
                });

        pressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(pressure.isChecked()){

                    editor.putString("pressureSensor", "on");

                }else{

                    editor.putString("pressureSensor", "off");

                }
                editor.commit();
            }});

        luminosity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(luminosity.isChecked()){

                    editor.putString("luminositySensor", "on");
                }else{


                    editor.putString("luminositySensor", "off");
                }

                editor.commit();}});

        dayActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(dayActivity.isChecked()){

                    editor.putString("dayActivity", "on");

                }else{

                    editor.putString("dayActivity", "off");

                }
                editor.commit();
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
                editor.commit();
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

                editor.commit();

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