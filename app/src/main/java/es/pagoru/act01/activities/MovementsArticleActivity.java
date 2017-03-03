package es.pagoru.act01.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Calendar;

import es.pagoru.act01.R;
import es.pagoru.act01.activities.cursoradapters.MovementActivityCursorAdapter;
import es.pagoru.act01.utils.article.movement.ArticleMovement;
import es.pagoru.act01.utils.article.movement.ArticleMovementDataSource;

public class MovementsArticleActivity extends ListActivity {

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
        setContentView(R.layout.activity_movements_article);

        setTitle("Moviment de l'article");

        articleMovementDataSource = new ArticleMovementDataSource(this);

        loadList(getExtra(ArticleMovementDataSource._FK_ARTICLE_CODE));
    }

    private String getExtra(String id){
        return getIntent().getExtras().getString(id);
    }

    private void loadList(String article_code){
        Cursor data = articleMovementDataSource.getArticleMovements(article_code);
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
