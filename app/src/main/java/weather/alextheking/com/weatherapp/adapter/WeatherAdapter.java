package weather.alextheking.com.weatherapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import weather.alextheking.com.weatherapp.R;
import weather.alextheking.com.weatherapp.model.Day;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private final List<Day> days;
    private final Context mContext;

    public WeatherAdapter(final List<Day> days, final Context context)
    {
        this.days = days;
        this.mContext = context;
    }

    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_adapter, parent, false);
        return new WeatherAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WeatherAdapter.ViewHolder holder, final int position) {
        final Day day = days.get(position);
        holder.name.setText(day.name);
        holder.description.setText(day.description);
        holder.humidity.setText(String.format(mContext.getString(R.string.humidity), day.humidity));
        holder.temp.setText(String.format(mContext.getString(R.string.temp), day.temp));
        holder.pressurePa.setText(String.format(mContext.getString(R.string.pressure_pa), day.pressure.get(0)));
        holder.pressureMc.setText(String.format(mContext.getString(R.string.pressure_mc), day.pressure.get(1)));
        holder.wind.setText(String.format(mContext.getString(R.string.wind), day.wind));
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final TextView description;
        final TextView humidity;
        final TextView temp;
        final TextView pressurePa;
        final TextView pressureMc;
        final TextView wind;

        ViewHolder(final View view) {
            super(view);
            this.name = ((TextView) view.findViewById(R.id.text_view_name));
            this.description = ((TextView) view.findViewById(R.id.text_view_description));
            this.humidity = ((TextView) view.findViewById(R.id.text_view_humidity));
            this.temp = ((TextView) view.findViewById(R.id.text_view_temp));
            this.pressurePa = ((TextView) view.findViewById(R.id.text_view_pressure_pa));
            this.pressureMc = ((TextView) view.findViewById(R.id.text_view_pressure_mc));
            this.wind = ((TextView) view.findViewById(R.id.text_view_wind));
        }
    }
}
