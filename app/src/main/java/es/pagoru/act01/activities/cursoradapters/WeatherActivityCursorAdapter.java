package es.pagoru.act01.activities.cursoradapters;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

/**
 * Created by pablo on 21/3/17.
 */

public class WeatherActivityCursorAdapter extends SimpleCursorAdapter {
    public WeatherActivityCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }
}
