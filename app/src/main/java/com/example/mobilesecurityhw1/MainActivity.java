package com.example.mobilesecurityhw1;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraMetadata;
import android.media.AudioManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import androidx.annotation.RequiresApi;


public class MainActivity extends AppCompatActivity {
    private Button main_BTN_login;
    private EditText main_input_password;
    private boolean flatStatus = false;// false as default


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        main_BTN_login.setOnClickListener(view -> {loginAttempt();});
    }

    private void loginAttempt() {

        if(checkUserAccess()){
            Toast.makeText(getApplicationContext(), "You've successfully logged in!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Access denied!", Toast.LENGTH_SHORT).show();
        }
        main_input_password.setText("");
    }

    private boolean checkUserAccess() {
        return (checkPasswordEqMobileProvider() && checkBluetoothOn() && checkPhoneBattery() && checkIsMusicPlaying());
    }



    private boolean checkIsMusicPlaying() {

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

         return audioManager.isMusicActive();
    }

    private boolean checkPhoneBattery() {
        BatteryManager batteryManager = (BatteryManager) getSystemService(Context.BATTERY_SERVICE);
        int batteryPercentage = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        return (batteryPercentage > 50);
    }

    private boolean checkBluetoothOn() {

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return bluetoothAdapter.isEnabled();
    }

    private boolean checkPasswordEqMobileProvider() {
        return(main_input_password.getText().toString().toLowerCase().equals(getMobileCompanyService().toLowerCase()));
    }

    private String getMobileCompanyService(){
        TelephonyManager telephoneManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        return telephoneManager.getNetworkOperatorName();
    }

    private void initViews() {
        main_BTN_login = findViewById(R.id.main_BTN_login);
        main_input_password = findViewById(R.id.main_input_password);
    }
}