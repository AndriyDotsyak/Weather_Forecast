package com.example.andriy.weatherforecast;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText et_Input_City;
    Button btn_See_Weather;
    Button btn_See_Weather_GPS;

    LocationManager locationManager;

    private double Latitude;
    private double Longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_Input_City = findViewById(R.id.et_Input_City);
        btn_See_Weather = findViewById(R.id.btn_See_Weather);
        btn_See_Weather_GPS = findViewById(R.id.btn_See_Weather_GPS);

        btn_See_Weather.setOnClickListener(this);
        btn_See_Weather_GPS.setOnClickListener(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, locationListener);
    }

    @Override
    protected void onPause() {
        super.onPause();

        locationManager.removeUpdates(locationListener);
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Latitude = location.getLatitude();
            Longitude = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };



    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.btn_See_Weather:
                WeatherForecastActivity.nameCity = et_Input_City.getText().toString() +",us";

                WeatherForecastActivity.lat = null;
                WeatherForecastActivity.lng = null;

                intent = new Intent("android.intent.action.WeatherForecast");
                startActivity(intent);
                break;
            case R.id.btn_See_Weather_GPS:
                WeatherForecastActivity.lat = Latitude;
                WeatherForecastActivity.lng = Longitude;

                WeatherForecastActivity.nameCity = null;

                intent = new Intent("android.intent.action.WeatherForecast");
                startActivity(intent);
                break;
        }
    }
}
