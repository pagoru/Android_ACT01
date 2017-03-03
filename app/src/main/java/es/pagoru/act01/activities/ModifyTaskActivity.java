package es.pagoru.act01.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

import es.pagoru.act01.R;
import es.pagoru.act01.utils.ToastUtils;
import es.pagoru.act01.utils.article.Article;
import es.pagoru.act01.utils.article.ArticleDataSource;
import es.pagoru.act01.utils.article.movement.ArticleMovement;
import es.pagoru.act01.utils.article.movement.ArticleMovementDataSource;
import es.pagoru.act01.utils.article.movement.MovementType;

/**
 * Created by Pablo on 02/02/2017.
 */

public class ModifyTaskActivity extends Activity {

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_task);

        setTitle("Modificar article");

        findViewById(R.id.modTask_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnActivity();
            }
        });

        findViewById(R.id.modTask_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveActivity();
            }
        });

        findViewById(R.id.modTask_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteActivity();
            }
        });

        findViewById(R.id.modTask_stock_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementStock(1);
            }
        });

        findViewById(R.id.modTask_stock_sub).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getStockInput() > 0)
                incrementStock(-1);
            }
        });

        setData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movements_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.modify_menu_movements:
                return option_openMovements();
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean option_openMovements(){
        Intent intent = new Intent(this, MovementsArticleActivity.class);

        intent.putExtra(ArticleMovementDataSource._FK_ARTICLE_CODE,
                ((EditText) findViewById(R.id.modTask_code)).getText().toString());
        startActivity(intent);
        return true;
    }

    private void incrementStock(int num){
        ((EditText)findViewById(R.id.modTask_stock)).setText((getStockInput() + num) + "");
    }

    private int getStockInput(){
        return Integer.parseInt(((EditText)findViewById(R.id.modTask_stock))
                .getText().toString());
    }

    private void saveActivity(){
        ArticleDataSource articleDataSource
                = new ArticleDataSource(this);
        ArticleMovementDataSource articleMovementDataSource
                = new ArticleMovementDataSource(this);

        String description = ((EditText)findViewById(R.id.modTask_description))
                .getText().toString();

        /*
         Contradicció en el enunciat...
         > "Descripció, TEXT obligatori que defineix l'article."
         > "(..) que tinguin un text dins del camp DESCRIPCIO (..)"
          */
//        if(description.length() == 0){
//            ToastUtils.make(this, "La descripció de l'article no pot estar buida.");
//            return;
//        }

        String pvp = ((EditText)findViewById(R.id.modTask_pvp)).getText().toString();
        int pvp_num;

        if(pvp.length() == 0){
            ToastUtils.make(this, "El pvp de l'article no pot estar buit.");
            return;
        }
        try{
            pvp_num = Integer.parseInt(pvp);
        } catch (Exception e){
            ToastUtils.make(this, "El pvp ha de ser un número enter.");
            return;
        }

        String stock = ((EditText)findViewById(R.id.modTask_stock)).getText().toString();
        int stock_num = 0;

        if(stock.length() != 0){
            try{
                stock_num = Integer.parseInt(stock);
            } catch (Exception e){
                ToastUtils.make(this, "El stock ha de ser un número enter.");
                return;
            }
        }

        String code = ((EditText)findViewById(R.id.modTask_code)).getText().toString();

        Cursor cursor = articleDataSource.getArticle(code);
        cursor.moveToPosition(0);
        int old_stock = cursor.getInt(cursor.getColumnIndex(ArticleDataSource._STOCK));

        if(old_stock != stock_num){
            articleMovementDataSource.addArticleMovement(
                    code,
                    new ArticleMovement(
                            Calendar.getInstance(),
                            Math.abs(old_stock - stock_num),
                            old_stock > stock_num
                                    ? MovementType.OUT
                                    : MovementType.IN
                    )
            );
        }

        articleDataSource.updateArticle(
                new Article(code, description, pvp_num, stock_num)
        );
        returnActivity();
    }

    private void deleteActivity(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Estas segur que vols eliminar l'article?");

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ArticleDataSource articleDataSource = new ArticleDataSource(context);
                ArticleMovementDataSource articleMovementDataSource
                        = new ArticleMovementDataSource(context);

                Article article = new Article(
                        ((EditText)findViewById(R.id.modTask_code)).getText().toString()
                );

                articleMovementDataSource.deleteArticleMovements(article);
                articleDataSource.deleteArticle(article);

                returnActivity();
            }
        });

        builder.setNegativeButton("No", null);

        builder.show();
    }


    private String getExtra(String id){
        return getIntent().getExtras().getString(id);
    }

    private void setTextToId(int id, String id_intent){
        ((TextView)findViewById(id)).setText(getExtra(id_intent));
    }

    private void setData(){
        setTextToId(R.id.modTask_code, ArticleDataSource._CODE);
        setTextToId(R.id.modTask_description, ArticleDataSource._DESCRIPTION);
        setTextToId(R.id.modTask_pvp, ArticleDataSource._PVP);
        setTextToId(R.id.modTask_stock, ArticleDataSource._STOCK);
    }

    private void returnActivity(){
        startActivity(new Intent(this, MainActivity.class));
    }

}
