package weather.alextheking.com.weatherapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import weather.alextheking.com.weatherapp.Constants;
import weather.alextheking.com.weatherapp.R;
import weather.alextheking.com.weatherapp.adapter.WeatherAdapter;
import weather.alextheking.com.weatherapp.api.WeatherApi;
import weather.alextheking.com.weatherapp.application.App;
import weather.alextheking.com.weatherapp.model.Day;

public class WeatherActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        final WeatherApi weatherApi = ((App) getApplication()).getWeatherApi();
        mRecyclerView = (RecyclerView) findViewById(R.id.weather_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final String city = getIntent().getStringExtra(Constants.WEATHER_FOR_CITY);
        setTitle(String.format(getString(R.string.weather), city));
        weatherApi.getWeatherByCity(city).enqueue(new Callback<List<Day>>() {
            @Override
            public void onResponse(@NonNull final Call<List<Day>> call, @NonNull final Response<List<Day>> response) {
                mRecyclerView.setAdapter(new WeatherAdapter(response.body(), WeatherActivity.this));
            }

            @Override
            public void onFailure(@NonNull final Call<List<Day>> call, @NonNull final Throwable t) {
                Toast.makeText(WeatherActivity.this, "Error loading weather for city" + city, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.action_change_city) {
            final SharedPreferences prefs = getSharedPreferences(Constants.PREFERENCES_NAME, MODE_PRIVATE);
            final SharedPreferences.Editor editor = prefs.edit();
            editor.remove(Constants.WEATHER_FOR_CITY);
            editor.apply();
            final Intent intent = new Intent(this, CitiesActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
