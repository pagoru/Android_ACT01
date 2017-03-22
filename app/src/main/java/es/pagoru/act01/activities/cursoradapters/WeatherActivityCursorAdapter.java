package es.pagoru.act01.activities.cursoradapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import es.pagoru.act01.*;
import es.pagoru.act01.activities.NewTaskActivity;
import es.pagoru.act01.activities.WeatherCityActivity;

/**
 * Created by pablo on 21/3/17.
 */

public class WeatherActivityCursorAdapter extends SimpleCursorAdapter {

    public WeatherActivityCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        view.findViewById(R.id.row_weather_city_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, WeatherCityActivity.class);

                TextView textView = (TextView) view.findViewById(R.id.row_weather_city_name);

                intent.putExtra("city", textView.getText());

                context.startActivity(intent);
            }
        });

        return view;
    }

}
