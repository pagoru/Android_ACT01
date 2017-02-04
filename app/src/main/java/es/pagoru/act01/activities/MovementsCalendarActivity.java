package es.pagoru.act01.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

import java.util.Calendar;

import es.pagoru.act01.R;
import es.pagoru.act01.activities.cursoradapters.MovementActivityCursorAdapter;
import es.pagoru.act01.utils.article.ArticleDataSource;
import es.pagoru.act01.utils.article.movement.ArticleMovement;
import es.pagoru.act01.utils.article.movement.ArticleMovementDataSource;

public class MovementsCalendarActivity extends ListActivity {

    private static final String[] FROM = new String[]{
            ArticleMovementDataSource._FK_ARTICLE_CODE,
            ArticleMovementDataSource._CALENDAR,
            ArticleMovementDataSource._QUANTITY,
            ArticleMovementDataSource._TYPE
    };

    private static final int[] TO = new int[]{
            R.id.row_mov_act_code,
            R.id.row_mov_act_calendar,
            R.id.row_mov_act_quantity,
            R.id.row_mov_act_type,
    };

    ArticleMovementDataSource articleMovementDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movements_calendar);

        setTitle("Moviment dels articles");

        ((CalendarView)findViewById(R.id.menu_mov_calendar)).setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.HOUR_OF_DAY, 0);

                loadList(calendar);

            }
        });

        articleMovementDataSource = new ArticleMovementDataSource(this);

        loadList(Calendar.getInstance());

    }

    private void loadList(Calendar calendar){
        Cursor data = articleMovementDataSource.getArticleMovementsFromDay(calendar);
        setListAdapter(
                new MovementActivityCursorAdapter(
                        this,
                        R.layout.row_movement_activity,
                        data,
                        FROM,
                        TO,
                        1
                )
        );
    }
}
