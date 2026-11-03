
package edu.charlotte.assignment09;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CurrentWeatherActivity extends AppCompatActivity {

    public static final String TAG = "CurrentWeatherActivity";
    private static final String API_KEY = "f9233f4be97ddd960222dd500b100769";

    TextView textViewTitle, textViewTemp, textViewTempMax, textViewTempMin,
            textViewDescription, textViewHumidity, textViewWindSpeed,
            textViewWindDegree, textViewCloudiness;
    ImageView imageViewIcon;
    Button buttonForecast;

    DataService.City city;
    Weather currentWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_weather);
        setTitle("Current Weather");

        city = (DataService.City) getIntent().getSerializableExtra("CITY");

        textViewTitle = findViewById(R.id.textViewCurrentWeatherTitle);
        textViewTemp = findViewById(R.id.textViewTemperature);
        textViewTempMax = findViewById(R.id.textViewTempMax);
        textViewTempMin = findViewById(R.id.textViewTempMin);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewHumidity = findViewById(R.id.textViewHumidity);
        textViewWindSpeed = findViewById(R.id.textViewWindSpeed);
        textViewWindDegree = findViewById(R.id.textViewWindDegree);
        textViewCloudiness = findViewById(R.id.textViewCloudiness);
        imageViewIcon = findViewById(R.id.imageViewWeatherIcon);
        buttonForecast = findViewById(R.id.buttonForecast);

        if (city != null) {
            textViewTitle.setText("Current Weather\n" + city.getCity() + "," + city.getCountry());
            fetchWeatherData(city);
        }

        buttonForecast.setOnClickListener(v -> {
            if (city != null) {
                Intent intent = new Intent(CurrentWeatherActivity.this, ForecastActivity.class);
                intent.putExtra("CITY", city);
                startActivity(intent);
            }
        });
    }

    private void fetchWeatherData(DataService.City city) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + city.getLatitude() +
                "&lon=" + city.getLongitude() + "&units=imperial&appid=" + API_KEY;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "API failure: " + e.getMessage(), e);
                runOnUiThread(() -> Toast.makeText(CurrentWeatherActivity.this, "Failed to retrieve weather", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonString = response.body().string();
                    try {
                        JSONObject jsonRoot = new JSONObject(jsonString);
                        JSONObject main = jsonRoot.getJSONObject("main");
                        double temp = main.getDouble("temp");
                        double tempMax = main.getDouble("temp_max");
                        double tempMin = main.getDouble("temp_min");
                        int humidity = main.getInt("humidity");

                        JSONArray weatherArray = jsonRoot.getJSONArray("weather");
                        JSONObject weatherObj = weatherArray.getJSONObject(0);
                        String description = weatherObj.getString("description");
                        String icon = weatherObj.getString("icon");

                        JSONObject wind = jsonRoot.getJSONObject("wind");
                        double windSpeed = wind.getDouble("speed");
                        double windDegree = wind.getDouble("deg");

                        JSONObject clouds = jsonRoot.getJSONObject("clouds");
                        int cloudiness = clouds.getInt("all");

                        String cityName = jsonRoot.optString("name", city.getCity());
                        JSONObject sys = jsonRoot.optJSONObject("sys");
                        String country = sys != null ? sys.optString("country", city.getCountry()) : city.getCountry();

                        currentWeather = new Weather(temp, tempMax, tempMin, description, humidity, windSpeed, windDegree, cloudiness, icon, cityName, country);

                        runOnUiThread(() -> displayWeather(currentWeather));

                    } catch (JSONException e) {
                        Log.e(TAG, "JSON parsing error", e);
                        if (city.getCity().equalsIgnoreCase("Charlotte")) {
                            fetchCltFallback();
                        } else {
                            runOnUiThread(() -> Toast.makeText(CurrentWeatherActivity.this, "Parsing error", Toast.LENGTH_SHORT).show());
                        }
                    }
                } else {
                    if (city.getCity().equalsIgnoreCase("Charlotte")) {
                        fetchCltFallback();
                    } else {
                        runOnUiThread(() -> Toast.makeText(CurrentWeatherActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show());
                    }
                }
            }
        });
    }

    private void fetchCltFallback() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/clt.json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(CurrentWeatherActivity.this, "Failed to retrieve fallback weather", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonString = response.body().string();
                    try {
                        JSONObject jsonRoot = new JSONObject(jsonString);
                        JSONObject main = jsonRoot.getJSONObject("main");
                        double temp = main.getDouble("temp");
                        double tempMax = main.getDouble("temp_max");
                        double tempMin = main.getDouble("temp_min");
                        int humidity = main.getInt("humidity");

                        JSONArray weatherArray = jsonRoot.getJSONArray("weather");
                        JSONObject weatherObj = weatherArray.getJSONObject(0);
                        String description = weatherObj.getString("description");
                        String icon = weatherObj.getString("icon");

                        JSONObject wind = jsonRoot.getJSONObject("wind");
                        double windSpeed = wind.getDouble("speed");
                        double windDegree = wind.getDouble("deg");

                        JSONObject clouds = jsonRoot.getJSONObject("clouds");
                        int cloudiness = clouds.getInt("all");

                        currentWeather = new Weather(temp, tempMax, tempMin, description, humidity, windSpeed, windDegree, cloudiness, icon, "Charlotte", "US");

                        runOnUiThread(() -> displayWeather(currentWeather));
                    } catch (JSONException e) {
                        runOnUiThread(() -> Toast.makeText(CurrentWeatherActivity.this, "Fallback parsing error", Toast.LENGTH_SHORT).show());
                    }
                }
            }
        });
    }

    private void displayWeather(Weather weather) {
        textViewTitle.setText("Current Weather\n" + weather.getCityName() + "," + weather.getCountry());
        textViewTemp.setText(weather.getTemp() + " F");
        textViewTempMax.setText(weather.getTempMax() + " F");
        textViewTempMin.setText(weather.getTempMin() + " F");
        String desc = weather.getDescription();
        if (desc != null && !desc.isEmpty()) {
            desc = desc.substring(0, 1).toUpperCase() + desc.substring(1);
        }
        textViewDescription.setText(desc);
        textViewHumidity.setText(weather.getHumidity() + "%");
        textViewWindSpeed.setText(weather.getWindSpeed() + " miles/hr");
        textViewWindDegree.setText(weather.getWindDegree() + " degrees");
        textViewCloudiness.setText(weather.getCloudiness() + "%");

        String iconUrl = "https://openweathermap.org/img/wn/" + weather.getIcon() + "@2x.png";
        Picasso.get().load(iconUrl).into(imageViewIcon);
    }
}
