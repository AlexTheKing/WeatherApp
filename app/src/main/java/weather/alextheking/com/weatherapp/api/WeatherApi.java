package weather.alextheking.com.weatherapp.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import weather.alextheking.com.weatherapp.model.Day;

public interface WeatherApi {

    @GET("cities")
    Call<List<String>> getCities();

    @GET("weather")
    Call<List<Day>> getWeatherByCity(@Query("city") String city);

}
