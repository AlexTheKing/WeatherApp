package weather.alextheking.com.weatherapp.application;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import weather.alextheking.com.weatherapp.api.WeatherApi;

import static weather.alextheking.com.weatherapp.Constants.API_URL;

public class App extends Application {

    private WeatherApi weatherApi;

    @Override
    public void onCreate() {
        super.onCreate();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherApi = retrofit.create(WeatherApi.class);
    }

    public WeatherApi getWeatherApi() {
        return weatherApi;
    }
}
