package es.pagoru.act01.activities.cursoradapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

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
    private Cursor cursor;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        currentView = view;
        cursor = (Cursor) getItem(position);

        setBackgroundColor();

        view.findViewById(R.id.row_nor_act_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addArticleStock(1);
            }
        });

        view.findViewById(R.id.row_nor_act_sub).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addArticleStock(-1);
            }
        });

        return currentView;
    }

    private void setBackgroundColor(){
        currentView.setBackgroundColor(
                getArticleStock() == 0
                        ? COLOR_RED
                        : COLOR_GRAY
        );
    }

    private String getArticleCode(){
        return ((TextView) currentView.findViewById(R.id.row_nor_act_code)).getText().toString();
    }

    private int getArticleStock(){
        return Integer.parseInt(((TextView) currentView.findViewById(R.id.row_nor_act_stock)).getText().toString());
    }

    private void setArticleStock(String amount){
        ((TextView) currentView.findViewById(R.id.row_nor_act_stock)).setText(amount);
        setBackgroundColor();
    }

    private void addArticleStock(int amount){
        ArticleDataSource articleDataSource = new ArticleDataSource(currentView.getContext());
        ArticleMovementDataSource articleMovementDataSource = new ArticleMovementDataSource(currentView.getContext());

        int a = (getArticleStock() + amount);
        if(a < 0){
            return;
        }
        setArticleStock(a + "");
        articleDataSource.updateArticleStock(
                new Article(
                        getArticleCode(),
                        a
                )
        );

        articleMovementDataSource.addArticleMovement(
                getArticleCode(),
                new ArticleMovement(
                        Calendar.getInstance(),
                        Math.abs(amount),
                        0 > amount
                                ? MovementType.OUT
                                : MovementType.IN
                )
        );
    }
}
