package es.pagoru.act01.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import es.pagoru.act01.activities.cursoradapters.MainActivityCursorAdapter;
import es.pagoru.act01.utils.article.ArticleDataSource;
import es.pagoru.act01.R;

/**
 * Created by Pablo on 02/02/2017.
 */

public class MainActivity extends ListActivity {

    /*
    0 - ASC
    1 - DESC
     */
    public static int ORDER_LIST = 0;
    public static boolean SHOW_DESCRIPTIONS = true;

    private static final String[] FROM = new String[]{
            ArticleDataSource._CODE,
            ArticleDataSource._STOCK,
            ArticleDataSource._DESCRIPTION,
            ArticleDataSource._PVP
    };

    private static final int[] TO = new int[]{
            R.id.row_nor_act_code,
            R.id.row_nor_act_stock,
            R.id.row_nor_act_description,
            R.id.row_nor_act_pvp,
    };

    private ArticleDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);

        setTitle("Menu principal");

        dataSource = new ArticleDataSource(this);

        loadList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.main_menu_add:
                return option_add();
            case R.id.main_menu_filterStock_ASC:
                return option_filterStock(0);
            case R.id.main_menu_filterStock_DESC:
                return option_filterStock(1);
            case R.id.main_menu_filterDescriptions:
                return option_filterDescription();
            case R.id.main_menu_refresh:
                return option_refresh();
            case R.id.main_menu_movements:
                return option_movements();
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadList(){
        Cursor data = dataSource.getArticles(ORDER_LIST, SHOW_DESCRIPTIONS);
        setListAdapter(
                new MainActivityCursorAdapter(
                        this,
                        R.layout.row_normal_activity,
                        data,
                        FROM,
                        TO,
                        1
                )
        );
    }

    private boolean option_movements(){
        startActivity(new Intent(this, MovementsCalendarActivity.class));
        return true;
    }

    private boolean option_filterDescription(){
        SHOW_DESCRIPTIONS = !SHOW_DESCRIPTIONS;
        loadList();
        return true;
    }

    private boolean option_add(){
        startActivity(new Intent(this, NewTaskActivity.class));
        return true;
    }

    private boolean option_filterStock(int order){
        ORDER_LIST = order;
        loadList();
        return true;
    }

    private boolean option_refresh(){
        loadList();
        return true;
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(this, ModifyTaskActivity.class);

        intent.putExtra(ArticleDataSource._CODE, ((TextView) v.findViewById(R.id.row_nor_act_code)).getText().toString());
        intent.putExtra(ArticleDataSource._DESCRIPTION, ((TextView) v.findViewById(R.id.row_nor_act_description)).getText().toString());
        intent.putExtra(ArticleDataSource._PVP, ((TextView) v.findViewById(R.id.row_nor_act_pvp)).getText().toString());
        intent.putExtra(ArticleDataSource._STOCK, ((TextView) v.findViewById(R.id.row_nor_act_stock)).getText().toString());


        startActivity(intent);
    }



    @Override
    public void onBackPressed() {
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
