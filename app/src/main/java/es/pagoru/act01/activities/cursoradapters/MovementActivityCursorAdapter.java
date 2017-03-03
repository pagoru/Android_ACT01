package es.pagoru.act01.activities.cursoradapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import es.pagoru.act01.R;
import es.pagoru.act01.utils.article.ArticleDataSource;
import es.pagoru.act01.utils.article.movement.ArticleMovementDataSource;
import es.pagoru.act01.utils.article.movement.MovementType;

/**
 * Created by Pablo on 02/02/2017.
 */

public class MovementActivityCursorAdapter extends SimpleCursorAdapter {

    private static final int COLOR_RED = Color.parseColor("#FF6961");
    private static final int COLOR_GREEN = Color.parseColor("#61ffb8");

    public MovementActivityCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        Cursor cursor = (Cursor) getItem(position);
        TextView textView = ((TextView)view.findViewById(R.id.row_mov_act_calendar));

        textView.setText(getBeautyDateFromCalendarString(
                cursor.getString(cursor.getColumnIndex(ArticleMovementDataSource._CALENDAR))
        ));

        view.setBackgroundColor(
                (cursor.getString(cursor.getColumnIndex(ArticleMovementDataSource._TYPE)))
                        .equals(MovementType.IN.toString())
                        ? COLOR_GREEN
                        : COLOR_RED
        );

        return view;
    }

    private String getBeautyDateFromCalendarString(String calendar_string){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        try {
            calendar.setTime(sdf.parse(calendar_string));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.get(Calendar.DAY_OF_MONTH)
                + "-" + calendar.get(Calendar.MONTH)
                + "-" + calendar.get(Calendar.YEAR);

    }
}
