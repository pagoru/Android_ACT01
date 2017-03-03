package es.pagoru.act01.activities.cursoradapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

import java.util.Calendar;

import es.pagoru.act01.R;
import es.pagoru.act01.activities.MainActivity;
import es.pagoru.act01.utils.article.Article;
import es.pagoru.act01.utils.article.ArticleDataSource;
import es.pagoru.act01.utils.article.movement.ArticleMovement;
import es.pagoru.act01.utils.article.movement.ArticleMovementDataSource;
import es.pagoru.act01.utils.article.movement.MovementType;

/**
 * Created by Pablo on 02/02/2017.
 */

public class MainActivityCursorAdapter extends SimpleCursorAdapter {

    private static final int COLOR_RED = Color.parseColor("#FF6961");
    private static final int COLOR_GRAY = Color.parseColor("#CFCFC4");

    public MainActivityCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    private View currentView;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        currentView = view;
        Cursor cursor = ((Cursor) getItem(position));

        final String article_code = cursor.getString(cursor.getColumnIndex(ArticleDataSource._CODE));
        final int article_stock = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ArticleDataSource._STOCK)));

        setBackgroundColor(article_stock);

        view.findViewById(R.id.row_nor_act_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addArticleStock(article_code, article_stock, 1);
            }
        });

        view.findViewById(R.id.row_nor_act_sub).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addArticleStock(article_code, article_stock, -1);
            }
        });

        return view;
    }

    private void setBackgroundColor(int article_stock){
        currentView.setBackgroundColor(
                article_stock == 0
                        ? COLOR_RED
                        : COLOR_GRAY
        );
    }

    private void addArticleStock(String article_code, int article_stock, int amount){
        ArticleDataSource articleDataSource = new ArticleDataSource(currentView.getContext());
        ArticleMovementDataSource articleMovementDataSource = new ArticleMovementDataSource(currentView.getContext());

        int a = (article_stock + amount);
        if(a < 0){
            return;
        }
        articleDataSource.updateArticleStock(
                new Article(
                        article_code,
                        a
                )
        );

        articleMovementDataSource.addArticleMovement(
                article_code,
                new ArticleMovement(
                        Calendar.getInstance(),
                        Math.abs(amount),
                        0 > amount
                                ? MovementType.OUT
                                : MovementType.IN
                )
        );

        changeCursor(articleDataSource.getArticles(
                MainActivity.ORDER_LIST,
                MainActivity.SHOW_DESCRIPTIONS
        ));
        setBackgroundColor(article_stock);
    }
}
