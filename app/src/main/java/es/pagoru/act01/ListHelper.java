package es.pagoru.act01;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import es.pagoru.act01.utils.article.ArticleDataSource;
import es.pagoru.act01.utils.article.movement.ArticleMovement;
import es.pagoru.act01.utils.article.movement.ArticleMovementDataSource;

/**
 * Created by Pablo on 02/02/2017.
 */

public class ListHelper extends SQLiteOpenHelper {

    private static final int db_VERSION = 2;
    private static final String db_NAME = "act01_db";

    public ListHelper(Context context){
        super(context, db_NAME, null, db_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTables(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        createTables(sqLiteDatabase);
    }

    private void createTables(SQLiteDatabase sqLiteDatabase){
        createArticleTable(sqLiteDatabase);
        createArticleMovementTable(sqLiteDatabase);
    }

    private void createArticleMovementTable(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS " + ArticleMovementDataSource.TABLE_NAME
                        + "("
                        + ArticleMovementDataSource._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + ArticleMovementDataSource._FK_ARTICLE_CODE + " TEXT, "
                        + ArticleMovementDataSource._CALENDAR + " TEXT, "
                        + ArticleMovementDataSource._QUANTITY + " INTEGER, "
                        + ArticleMovementDataSource._TYPE + " TEXT "
                        + ")"
        );
    }

    private void createArticleTable(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS " + ArticleDataSource.TABLE_NAME
                        + "("
                        + ArticleDataSource._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + ArticleDataSource._CODE + " TEXT, "
                        + ArticleDataSource._DESCRIPTION + " TEXT, "
                        + ArticleDataSource._PVP + " INTEGER, "
                        + ArticleDataSource._STOCK + " INTEGER "
                        + ")"
        );
    }
}
