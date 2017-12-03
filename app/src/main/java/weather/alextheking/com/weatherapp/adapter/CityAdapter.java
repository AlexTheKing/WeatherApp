package weather.alextheking.com.weatherapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import weather.alextheking.com.weatherapp.Constants;
import weather.alextheking.com.weatherapp.R;
import weather.alextheking.com.weatherapp.activities.WeatherActivity;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private final List<String> cities;
    private final Context context;

    public CityAdapter(final List<String> cities, final Context context) {
        this.cities = cities;
        Collections.sort(this.cities);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String city = cities.get(position);
        holder.mTextView.setText(city);
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final SharedPreferences prefs = context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = prefs.edit();
                editor.putString(Constants.WEATHER_FOR_CITY, city);
                editor.apply();
                final Intent intent = new Intent(context, WeatherActivity.class);
                intent.putExtra(Constants.WEATHER_FOR_CITY, city);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mTextView;

        ViewHolder(final View view) {
            super(view);
            this.mTextView = ((TextView) view.findViewById(R.id.text_view));
        }
    }
}
