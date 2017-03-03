package es.pagoru.act01.activities;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

import es.pagoru.act01.utils.article.ArticleDataSource;

/**
 * Created by Pablo on 02/02/2017.
 */

public class MainActivityCursorAdapter extends SimpleCursorAdapter {

    private static final int COLOR_RED = Color.parseColor("#FF6961");
    private static final int COLOR_GRAY = Color.parseColor("#CFCFC4");

    public MainActivityCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        Cursor cursor = (Cursor) getItem(position);

        view.setBackgroundColor(
                (cursor.getInt(cursor.getColumnIndex(ArticleDataSource._STOCK))) == 0
                        ? COLOR_RED
                        : COLOR_GRAY
        );

        return view;
    }
}
