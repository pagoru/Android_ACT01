package es.pagoru.act01.utils.article;

import android.content.Context;
import android.database.Cursor;

import java.util.List;

import es.pagoru.act01.utils.article.movement.ArticleMovementDataSource;
import es.pagoru.act01.utils.article.movement.MovementType;

/**
 * Created by Pablo on 02/02/2017.
 */

public class Article {

    private String code;
    private String description;
    private int pvp;
    private int stock;

    public Article(String code){
        this.code = code;
    }

    public Article(String code, int stock){
        this.code = code;
        this.stock = stock;
    }

    public Article(
            String code,
            String description,
            int pvp,
            int stock) {
        this.code = code;
        this.description = description;
        this.pvp = pvp;
        this.stock = stock;
    }


    public Cursor getMovements(Context context){
        return new ArticleMovementDataSource(context).getArticleMovements(code);
    }


    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public int getPvp() {
        return pvp;
    }

    public int getStock() {
        return stock;
    }


    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPvp(int pvp) {
        this.pvp = pvp;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
