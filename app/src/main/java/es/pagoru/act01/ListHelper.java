package es.pagoru.act01;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import es.pagoru.act01.utils.article.ArticleDataSource;

/**
 * Created by Pablo on 02/02/2017.
 */

public class ListHelper extends SQLiteOpenHelper {

    private static final int db_VERSION = 1;
    private static final String db_NAME = "act01_db";

    public ListHelper(Context context){
        super(context, db_NAME, null, db_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + ArticleDataSource.TABLE_NAME
                        + "("
                        + ArticleDataSource._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + ArticleDataSource._CODE + " TEXT, "
                        + ArticleDataSource._DESCRIPTION + " TEXT, "
                        + ArticleDataSource._PVP + " INTEGER, "
                        + ArticleDataSource._STOCK + " INTEGER "
                        + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
