
package edu.charlotte.assignment09;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ListView listViewCities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Cities");

        listViewCities = findViewById(R.id.listViewCities);

        ArrayAdapter<DataService.City> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                DataService.cities
        );

        listViewCities.setAdapter(adapter);

        listViewCities.setOnItemClickListener((parent, view, position, id) -> {
            DataService.City selectedCity = DataService.cities.get(position);
            Intent intent = new Intent(MainActivity.this, CurrentWeatherActivity.class);
            intent.putExtra("CITY", selectedCity);
            startActivity(intent);
        });
    }
}
