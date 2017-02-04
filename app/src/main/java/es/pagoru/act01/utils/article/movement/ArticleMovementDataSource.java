package es.pagoru.act01.utils.article.movement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Calendar;

import es.pagoru.act01.ListHelper;
import es.pagoru.act01.utils.article.Article;

/**
 * Created by Pablo on 04/02/2017.
 */

public class ArticleMovementDataSource {

    public static final String TABLE_NAME = "movement_article_table";

    public static final String _ID = "_id";

    public static final String _FK_ARTICLE_CODE = "fk_article_code";
    public static final String _CALENDAR = "calendar";
    public static final String _QUANTITY = "quantity";
    public static final String _TYPE = "type";

    private ListHelper db_helper;
    private SQLiteDatabase db_writer, db_reader;

    public ArticleMovementDataSource(Context context){
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

    public Cursor getArticleMovements(){
        return db_reader.query(
                TABLE_NAME,
                new String[]{_ID, _FK_ARTICLE_CODE, _CALENDAR, _QUANTITY, _TYPE},
                null, null,
                null, null,
                _ID
        );
    }

    public Cursor getArticleMovements(String article_code){
        return db_reader.query(
                TABLE_NAME,
                new String[]{_ID, _FK_ARTICLE_CODE, _CALENDAR, _QUANTITY, _TYPE},
                _FK_ARTICLE_CODE + " == ?",
                new String[]{ article_code },
                null, null,
                _ID
        );
    }

    public Cursor getArticleMovementsFromDay(Calendar calendar){
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        return db_reader.query(
                TABLE_NAME,
                new String[]{_ID, _FK_ARTICLE_CODE, _CALENDAR, _QUANTITY, _TYPE},
                _CALENDAR + " == ?",
                new String[]{ calendar.getTime().toString() },
                null, null,
                _ID
        );
    }

    /*****************************************************************************************/
    /******************************************WRITE******************************************/
    /*****************************************************************************************/

    public long addArticleMovement(String article_code, ArticleMovement articleMovement){
        ContentValues contentValues = new ContentValues();

        contentValues.put(_FK_ARTICLE_CODE, article_code);

        Calendar calendar = articleMovement.getCalendar();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        contentValues.put(_CALENDAR, calendar.getTime().toString());
        contentValues.put(_QUANTITY, articleMovement.getQuantity());
        contentValues.put(_TYPE, articleMovement.getMovementType().toString());

        return db_writer.insert(TABLE_NAME, null, contentValues);
    }

    public long deleteArticleMovements(Article article){
        return db_writer.delete(
                TABLE_NAME,
                _FK_ARTICLE_CODE + "=?",
                new String[]{ article.getCode() }
        );
    }

}
