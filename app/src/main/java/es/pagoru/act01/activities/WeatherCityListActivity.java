package es.pagoru.act01.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import es.pagoru.act01.*;
import es.pagoru.act01.activities.cursoradapters.MainActivityCursorAdapter;
import es.pagoru.act01.activities.cursoradapters.WeatherActivityCursorAdapter;
import es.pagoru.act01.utils.article.ArticleDataSource;
import es.pagoru.act01.utils.city.CityDataSource;

public class WeatherCityListActivity extends ListActivity {

    private static final String[] FROM = new String[]{
            CityDataSource._NAME
    };

    private static final int[] TO = new int[] {
            R.id.row_weather_city_name
    };

    private CityDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_city_list);

        setTitle("Menu del temps");

        dataSource = new CityDataSource(this);

        loadList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.main_menu_add:
                startActivity(new Intent(this, NewTaskActivity.class));
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadList(){
        Cursor data = dataSource.getCities();
        setListAdapter(
                new WeatherActivityCursorAdapter(
                        this,
                        R.layout.row_weather_city_list_activity,
                        data,
                        FROM,
                        TO,
                        1
                )
        );
    }
}
