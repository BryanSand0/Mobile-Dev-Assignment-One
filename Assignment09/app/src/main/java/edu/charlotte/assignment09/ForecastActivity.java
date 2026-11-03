/*
 * Assignment 9
 * File Name: ForecastActivity.java
 * Full name of the student: Student Name
 */
package edu.charlotte.assignment09;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ForecastActivity extends AppCompatActivity {

    public static final String TAG = "ForecastActivity";
    private static final String API_KEY = "f9233f4be97ddd960222dd500b100769";

    ListView listViewForecast;
    ArrayList<ForecastWeather> forecastList = new ArrayList<>();
    ForecastAdapter forecastAdapter;
    DataService.City city;
    TextView textViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        setTitle("Weather Forecast");

        city = (DataService.City) getIntent().getSerializableExtra("CITY");
        textViewTitle = findViewById(R.id.textViewForecastTitle);
        listViewForecast = findViewById(R.id.listViewForecast);

        if (city != null) {
            textViewTitle.setText("Weather Forecast\n" + city.getCity() + ", " + city.getCountry());
            fetchForecastData(city);
        }

        forecastAdapter = new ForecastAdapter(this, forecastList);
        listViewForecast.setAdapter(forecastAdapter);
    }

    private void fetchForecastData(DataService.City city) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.openweathermap.org/data/2.5/forecast?lat=" + city.getLatitude() +
                "&lon=" + city.getLongitude() + "&units=imperial&appid=" + API_KEY;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(ForecastActivity.this, "Failed to retrieve forecast", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonString = response.body().string();
                    try {
                        JSONObject root = new JSONObject(jsonString);
                        JSONArray list = root.getJSONArray("list");
                        forecastList.clear();

                        for (int i = 0; i < list.length(); i++) {
                            JSONObject item = list.getJSONObject(i);
                            String dtTxt = item.getString("dt_txt");
                            JSONObject main = item.getJSONObject("main");
                            double temp = main.getDouble("temp");
                            double tempMax = main.getDouble("temp_max");
                            double tempMin = main.getDouble("temp_min");
                            int humidity = main.getInt("humidity");

                            JSONArray weatherArray = item.getJSONArray("weather");
                            JSONObject weatherObj = weatherArray.getJSONObject(0);
                            String description = weatherObj.getString("description");
                            String icon = weatherObj.getString("icon");

                            JSONObject wind = item.getJSONObject("wind");
                            double windSpeed = wind.getDouble("speed");
                            double windDegree = wind.getDouble("deg");

                            JSONObject clouds = item.getJSONObject("clouds");
                            int cloudiness = clouds.getInt("all");

                            forecastList.add(new ForecastWeather(dtTxt, temp, tempMax, tempMin, description, humidity, windSpeed, windDegree, cloudiness, icon));
                        }

                        runOnUiThread(() -> forecastAdapter.notifyDataSetChanged());

                    } catch (JSONException e) {
                        runOnUiThread(() -> Toast.makeText(ForecastActivity.this, "Forecast parsing error", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(ForecastActivity.this, "Forecast error: " + response.code(), Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private static class ForecastAdapter extends ArrayAdapter<ForecastWeather> {
        public ForecastAdapter(@NonNull AppCompatActivity context, @NonNull ArrayList<ForecastWeather> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_forecast, parent, false);
            }

            ForecastWeather forecast = getItem(position);

            TextView tvDateTime = convertView.findViewById(R.id.textViewForecastDateTime);
            TextView tvDesc = convertView.findViewById(R.id.textViewForecastDescription);
            TextView tvTemp = convertView.findViewById(R.id.textViewForecastTemp);
            ImageView ivIcon = convertView.findViewById(R.id.imageViewForecastIcon);

            if (forecast != null) {
                tvDateTime.setText(forecast.getDateTime());
                String desc = forecast.getDescription();
                if (desc != null && !desc.isEmpty()) {
                    desc = desc.substring(0, 1).toUpperCase() + desc.substring(1);
                }
                tvDesc.setText(desc);
                tvTemp.setText("Temp: " + forecast.getTemp() + " F (Min: " + forecast.getTempMin() + " F, Max: " + forecast.getTempMax() + " F)");

                String iconUrl = "https://openweathermap.org/img/wn/" + forecast.getIcon() + "@2x.png";
                Picasso.get().load(iconUrl).into(ivIcon);
            }

            return convertView;
        }
    }
}
