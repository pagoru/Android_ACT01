package es.pagoru.act01.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import es.pagoru.act01.utils.article.Article;
import es.pagoru.act01.utils.article.ArticleDataSource;
import es.pagoru.act01.R;
import es.pagoru.act01.utils.ToastUtils;

/**
 * Created by Pablo on 02/02/2017.
 */

public class NewTaskActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        setTitle("Afegir article");

        findViewById(R.id.newTask_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveActivity();
            }
        });

        findViewById(R.id.newTask_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnActivity();
            }
        });
    }

    private void saveActivity(){
        ArticleDataSource articleDataSource = new ArticleDataSource(this);

        String code = ((EditText)findViewById(R.id.newTask_code)).getText().toString();

        if(code.length() == 0){
            ToastUtils.make(this, "El codi de l'article no pot estar buit.");
            return;
        }

        if(articleDataSource.getArticle(code).getCount() != 0){
            ToastUtils.make(this, "El codi de l'article ja esta registrat.");
            return;
        }

        String description = ((EditText)findViewById(R.id.newTask_description)).getText().toString();

        /*
         Contradicció en el enunciat...
         > "Descripció, TEXT obligatori que defineix l'article."
         > "(..) que tinguin un text dins del camp DESCRIPCIO (..)"
          */
//        if(description.length() == 0){
//            ToastUtils.make(this, "La descripció de l'article no pot estar buida.");
//            return;
//        }

        String pvp = ((EditText)findViewById(R.id.newTask_pvp)).getText().toString();
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

        String stock = ((EditText)findViewById(R.id.newTask_stock)).getText().toString();
        int stock_num = 0;

        if(stock.length() != 0){
            try{
                stock_num = Integer.parseInt(stock);
            } catch (Exception e){
                ToastUtils.make(this, "El stock ha de ser un número enter.");
                return;
            }
        }

        articleDataSource.addArticle(
                new Article(code, description, pvp_num, stock_num)
        );
        returnActivity();
    }

    private void returnActivity(){
        startActivity(new Intent(this, MainActivity.class));
    }
}
