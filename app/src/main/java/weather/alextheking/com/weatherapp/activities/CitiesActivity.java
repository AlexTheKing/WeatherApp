package weather.alextheking.com.weatherapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import weather.alextheking.com.weatherapp.Constants;
import weather.alextheking.com.weatherapp.R;
import weather.alextheking.com.weatherapp.adapter.CityAdapter;
import weather.alextheking.com.weatherapp.api.WeatherApi;
import weather.alextheking.com.weatherapp.application.App;

public class CitiesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        final SharedPreferences prefs = getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (prefs.contains(Constants.WEATHER_FOR_CITY)) {
            final String city = prefs.getString(Constants.WEATHER_FOR_CITY, null);
            final Intent intent = new Intent(this, WeatherActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constants.WEATHER_FOR_CITY, city);
            startActivity(intent);
        } else {
            final WeatherApi weatherApi = ((App) getApplication()).getWeatherApi();
            mRecyclerView = (RecyclerView) findViewById(R.id.cities_recycler_view);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            weatherApi.getCities().enqueue(new Callback<List<String>>() {

                @Override
                public void onResponse(@NonNull final Call<List<String>> call, @NonNull final Response<List<String>> response) {
                    mRecyclerView.setAdapter(new CityAdapter(response.body(), CitiesActivity.this));
                }

                @Override
                public void onFailure(@NonNull final Call<List<String>> call, @NonNull final Throwable t) {
                    Toast.makeText(CitiesActivity.this, "Error loading cities", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
