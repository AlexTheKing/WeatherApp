package weather.alextheking.com.weatherapp.model;

import java.util.List;

public class Day {

    public final String name;
    public final String temp;
    public final String description;
    public final String wind;
    public final String humidity;
    public final List<String> pressure;

    public Day(final String name, final String temp, final String description, final String wind, final String humidity, final List<String> pressure) {
        this.name = name;
        this.temp = temp;
        this.description = description;
        this.wind = wind;
        this.humidity = humidity;
        this.pressure = pressure;
    }
}
