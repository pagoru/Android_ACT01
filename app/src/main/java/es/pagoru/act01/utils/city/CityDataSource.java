package es.pagoru.act01.utils.city;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import es.pagoru.act01.ListHelper;

/**
 * Created by pablo on 21/3/17.
 */

public class CityDataSource {

    public static final String TABLE_NAME = "ciutats_table";

    public static final String _ID = "_id";

    public static final String _NAME = "name";

    private ListHelper db_helper;
    private SQLiteDatabase db_writer, db_reader;

    public CityDataSource(Context context){
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

    public Cursor getCities(){
        return db_reader.query(
                TABLE_NAME,
                new String[]{_ID, _NAME},
                null, null, null, null, null
        );
    }

    public List<City> getCityList(){
        Cursor cursor = getCities();
        List<City> list = new ArrayList<>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            list.add(new City(
                cursor.getString(cursor.getColumnIndex(_NAME))
            ));
            cursor.moveToNext();
        }
        return list;
    }

    /*****************************************************************************************/
    /******************************************WRITE******************************************/
    /*****************************************************************************************/

    public long addCity(City city){
        ContentValues contentValues = new ContentValues();

        contentValues.put(_NAME, city.getName());

        return db_writer.insert(TABLE_NAME, null, contentValues);
    }
}
