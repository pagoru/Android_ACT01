package es.pagoru.act01.utils.article;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import es.pagoru.act01.ListHelper;

/**
 * Created by Pablo on 02/02/2017.
 */

public class ArticleDataSource {

    public static final String TABLE_NAME = "article_table";

    public static final String _ID = "_id";

    public static final String _CODE = "code";
    public static final String _DESCRIPTION = "description";
    public static final String _PVP = "pvp";
    public static final String _STOCK = "stock";

    private ListHelper db_helper;
    private SQLiteDatabase db_writer, db_reader;

    public ArticleDataSource(Context context){
        db_helper = new ListHelper(context);

        open();
    }

    private void open(){
        db_writer = db_helper.getWritableDatabase();
        db_reader = db_helper.getReadableDatabase();
    }


    protected void finalize(){
        db_reader.close();
        db_writer.close();
    }

    /*****************************************************************************************/
    /******************************************READ*******************************************/
    /*****************************************************************************************/

    public Cursor getArticles(int order, boolean descriptions){
        return db_reader.query(
                TABLE_NAME,
                new String[]{_ID, _CODE, _DESCRIPTION, _PVP, _STOCK},
                descriptions ? "" : _DESCRIPTION + " != ''",
                null,
                null, null,
                _STOCK + ((order == 0) ? " ASC" : " DESC")
        );
    }

    public Cursor getArticle(String code){
        return db_reader.query(
                TABLE_NAME,
                new String[]{_ID, _CODE, _DESCRIPTION, _PVP, _STOCK},
                _CODE + "=?",
                new String[]{code},
                null, null, null
        );
    }

    /*****************************************************************************************/
    /******************************************WRITE******************************************/
    /*****************************************************************************************/

    public long addArticle(Article article){
        ContentValues contentValues = new ContentValues();

        contentValues.put(_CODE, article.getCode());
        contentValues.put(_DESCRIPTION, article.getDescription());
        contentValues.put(_PVP, article.getPvp());
        contentValues.put(_STOCK, article.getStock());

        return db_writer.insert(TABLE_NAME, null, contentValues);
    }

    public long updateArticle(Article article){
        ContentValues contentValues = new ContentValues();

        contentValues.put(_CODE, article.getCode());
        contentValues.put(_DESCRIPTION, article.getDescription());
        contentValues.put(_PVP, article.getPvp());
        contentValues.put(_STOCK, article.getStock());

        return db_writer.update(
                TABLE_NAME,
                contentValues,
                _CODE + "=?",
                new String[]{ article.getCode() }
        );
    }

    public long updateArticleStock(Article article){
        ContentValues contentValues = new ContentValues();

        contentValues.put(_CODE, article.getCode());
        contentValues.put(_STOCK, article.getStock());

        return db_writer.update(
                TABLE_NAME,
                contentValues,
                _CODE + "=?",
                new String[]{ article.getCode() }
        );
    }

    public long deleteArticle(Article article){
        return db_writer.delete(
                TABLE_NAME,
                _CODE + "=?",
                new String[]{ article.getCode() }
        );
    }

}
