package com.example.andriy.weatherforecast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.LinkedHashSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherForecastActivity extends AppCompatActivity {
    String TAG = "WEATHER";

    WeatherAPI.ApiInterface api;

    public static String nameCity;
    public static Double lat;
    public static Double lng;
    private String units = "metric";
    private String key = WeatherAPI.KEY;

    ImageView iv_Weather_0;
    ImageView iv_Weather_1;
    ImageView iv_Weather_2;
    ImageView iv_Weather_3;
    ImageView iv_Weather_4;
    ImageView iv_Weather_5;

    TextView tv_Day_0;
    TextView tv_Day_1;
    TextView tv_Day_2;
    TextView tv_Day_3;
    TextView tv_Day_4;
    TextView tv_Day_5;

    TextView tv_Temp_Min_1;
    TextView tv_Temp_Min_2;
    TextView tv_Temp_Min_3;
    TextView tv_Temp_Min_4;
    TextView tv_Temp_Min_5;

    TextView tv_Temp_Max_1;
    TextView tv_Temp_Max_2;
    TextView tv_Temp_Max_3;
    TextView tv_Temp_Max_4;
    TextView tv_Temp_Max_5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        tv_Day_0 = findViewById(R.id.tv_Day_0);
        tv_Day_1 = findViewById(R.id.tv_Day_1);
        tv_Day_2 = findViewById(R.id.tv_Day_2);
        tv_Day_3 = findViewById(R.id.tv_Day_3);
        tv_Day_4 = findViewById(R.id.tv_Day_4);
        tv_Day_5 = findViewById(R.id.tv_Day_5);

        iv_Weather_0 = findViewById(R.id.iv_Weather_0);
        iv_Weather_1 = findViewById(R.id.iv_Weather_1);
        iv_Weather_2 = findViewById(R.id.iv_Weather_2);
        iv_Weather_3 = findViewById(R.id.iv_Weather_3);
        iv_Weather_4 = findViewById(R.id.iv_Weather_4);
        iv_Weather_5 = findViewById(R.id.iv_Weather_5);

        tv_Temp_Min_1 = findViewById(R.id.tv_Temp_Min_1);
        tv_Temp_Min_2 = findViewById(R.id.tv_Temp_Min_2);
        tv_Temp_Min_3 = findViewById(R.id.tv_Temp_Min_3);
        tv_Temp_Min_4 = findViewById(R.id.tv_Temp_Min_4);
        tv_Temp_Min_5 = findViewById(R.id.tv_Temp_Min_5);


        tv_Temp_Max_1 = findViewById(R.id.tv_Temp_Max_1);
        tv_Temp_Max_2 = findViewById(R.id.tv_Temp_Max_2);
        tv_Temp_Max_3 = findViewById(R.id.tv_Temp_Max_3);
        tv_Temp_Max_4 = findViewById(R.id.tv_Temp_Max_4);
        tv_Temp_Max_5 = findViewById(R.id.tv_Temp_Max_5);

        api = WeatherAPI.getClient().create(WeatherAPI.ApiInterface.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Call<WeatherDay> callToday = null;
        if (nameCity != null) {
            callToday = api.getTodayNameCity(nameCity, units, key);
        } else if (lat != null && lng != null) {
            callToday = api.getToday(lat, lng, units, key);
        }

        callToday.enqueue(new Callback<WeatherDay>() {
            @Override
            public void onResponse(Call<WeatherDay> call, Response<WeatherDay> response) {
                Log.e(TAG, "onResponse");
                WeatherDay data = response.body();

                if (response.isSuccessful()) {
                    tv_Day_0.setText(data.getCity() +" " +data.getTempWithDegree());
                    Glide.with(WeatherForecastActivity.this).load(data.getIconUrl()).into(iv_Weather_0);
                }
            }

            @Override
            public void onFailure(Call<WeatherDay> call, Throwable t) {
                Log.e(TAG, "onFailure");
                Log.e(TAG, t.toString());
            }
        });


        Call<WeatherForecast> callForecast = null;
        if (nameCity != null) {
            callForecast = api.getForecastNameCity(nameCity, units, key);
        } else if (lat != null && lng != null) {
            callForecast = api.getForecast(lat, lng, units, key);
        }

        callForecast.enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                Log.e(TAG, "onResponse");
                WeatherForecast data = response.body();

                LinkedHashSet<String> days = new LinkedHashSet<>();
                LinkedHashSet<Integer> daysNumber = new LinkedHashSet<>();
                for (WeatherDay day : data.getItems()) {
                    if (day.getDate().getTime().getDay() == 0) {
                        days.add("Sunday\n" +day.getDate().getTime().getDate());
                    } else if (day.getDate().getTime().getDay() == 1) {
                        days.add("Monday\n" +day.getDate().getTime().getDate());
                        daysNumber.add(day.getDate().getTime().getDate());
                    } else if (day.getDate().getTime().getDay() == 2) {
                        days.add("Tuesday\n" +day.getDate().getTime().getDate());
                        daysNumber.add(day.getDate().getTime().getDate());
                    } else if (day.getDate().getTime().getDay() == 3) {
                        days.add("Wednesday\n" +day.getDate().getTime().getDate());
                        daysNumber.add(day.getDate().getTime().getDate());
                    } else if (day.getDate().getTime().getDay() == 4) {
                        days.add("Thursday\n" +day.getDate().getTime().getDate());
                        daysNumber.add(day.getDate().getTime().getDate());
                    } else if (day.getDate().getTime().getDay() == 5) {
                        days.add("Friday\n" +day.getDate().getTime().getDate());
                        daysNumber.add(day.getDate().getTime().getDate());
                    } else if (day.getDate().getTime().getDay() == 6) {
                        days.add("Saturday\n" +day.getDate().getTime().getDate());
                        daysNumber.add(day.getDate().getTime().getDate());
                    }
                }

                int indexDay = 0;
                for (String d : days) {
                    indexDay++;

                    if (indexDay == 1) tv_Day_1.setText(d);
                    if (indexDay == 2) tv_Day_2.setText(d);
                    if (indexDay == 3) tv_Day_3.setText(d);
                    if (indexDay == 4) tv_Day_4.setText(d);
                    if (indexDay == 5) tv_Day_5.setText(d);
                }

                int daysNumberIndex = 0;
                for (int dn : daysNumber) {
                    daysNumberIndex++;
                    int min = 30;
                    int max = -30;
                    for (WeatherDay day : data.getItems()) {
                        if (dn == day.getDate().getTime().getDate()) {
                            if (daysNumberIndex == 1) {
                                Glide.with(WeatherForecastActivity.this).load(day.getIconUrl()).into(iv_Weather_1);

                                if (min > Integer.parseInt(day.getTempInteger())) min = Integer.parseInt(day.getTempInteger());
                                if (max < Integer.parseInt(day.getTempInteger())) max = Integer.parseInt(day.getTempInteger());
                                tv_Temp_Min_1.setText("Min.\n" +min +"\u00B0");
                                tv_Temp_Max_1.setText("Max.\n" +max +"\u00B0");
                            }
                            if (daysNumberIndex == 2) {
                                Glide.with(WeatherForecastActivity.this).load(day.getIconUrl()).into(iv_Weather_2);

                                if (min > Integer.parseInt(day.getTempInteger())) min = Integer.parseInt(day.getTempInteger());
                                if (max < Integer.parseInt(day.getTempInteger())) max = Integer.parseInt(day.getTempInteger());
                                tv_Temp_Min_2.setText("Min.\n" +min +"\u00B0");
                                tv_Temp_Max_2.setText("Max.\n" +max +"\u00B0");
                            }
                            if (daysNumberIndex == 3) {
                                Glide.with(WeatherForecastActivity.this).load(day.getIconUrl()).into(iv_Weather_3);

                                if (min > Integer.parseInt(day.getTempInteger())) min = Integer.parseInt(day.getTempInteger());
                                if (max < Integer.parseInt(day.getTempInteger())) max = Integer.parseInt(day.getTempInteger());
                                tv_Temp_Min_3.setText("Min.\n" +min +"\u00B0");
                                tv_Temp_Max_3.setText("Max.\n" +max +"\u00B0");
                            }
                            if (daysNumberIndex == 4) {
                                Glide.with(WeatherForecastActivity.this).load(day.getIconUrl()).into(iv_Weather_4);

                                if (min > Integer.parseInt(day.getTempInteger())) min = Integer.parseInt(day.getTempInteger());
                                if (max < Integer.parseInt(day.getTempInteger())) max = Integer.parseInt(day.getTempInteger());
                                tv_Temp_Min_4.setText("Min.\n" +min +"\u00B0");
                                tv_Temp_Max_4.setText("Max.\n" +max +"\u00B0");
                            }
                            if (daysNumberIndex == 5) {
                                Glide.with(WeatherForecastActivity.this).load(day.getIconUrl()).into(iv_Weather_5);

                                if (min > Integer.parseInt(day.getTempInteger())) min = Integer.parseInt(day.getTempInteger());
                                if (max < Integer.parseInt(day.getTempInteger())) max = Integer.parseInt(day.getTempInteger());
                                tv_Temp_Min_5.setText("Min.\n" +min +"\u00B0");
                                tv_Temp_Max_5.setText("Max.\n" +max +"\u00B0");
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<WeatherForecast> call, Throwable t) {
                Log.e(TAG, "onFailure");
                Log.e(TAG, t.toString());
            }
        });
    }
}
