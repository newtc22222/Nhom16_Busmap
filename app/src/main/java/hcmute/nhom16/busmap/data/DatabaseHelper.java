package hcmute.nhom16.busmap.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "busmap.sqlite";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
            "CREATE TABLE IF NOT EXISTS user ( " +
            "id INTEGER, " +
            "name TEXT, " +
            "phone TEXT, " +
            "gender INTEGER, " +
            "date_of_birth TEXT, " +
            "email TEXT, " +
            "password TEXT, " +
            "image BLOB, " +
            "PRIMARY KEY(id) " +
            ");");

        sqLiteDatabase.execSQL(
            "CREATE TABLE IF NOT EXISTS route (" +
            "id TEXT," +
            "start_station_id INTEGER," +
            "end_station_id INTEGER," +
            "price INTEGER," +
            "type TEXT," +
            "operation_time TEXT," +
            "cycle_time TEXT," +
            "unit TEXT," +
            "repeat_time INTEGER," +
            "per_day INTEGER," +
            "distance INTEGER," +
            "PRIMARY KEY(id)" +
            ");");

        sqLiteDatabase.execSQL(
            "CREATE TABLE IF NOT EXISTS station (" +
            "id INTEGER," +
            "name TEXT," +
            "address TEXT," +
            "lat REAL," +
            "lng REAL," +
            "PRIMARY KEY(id)" +
            ");");

        sqLiteDatabase.execSQL(
            "CREATE TABLE IF NOT EXISTS savedroute ( " +
            "route_id TEXT, " +
            "user_gmail TEXT, " +
            "PRIMARY KEY(route_id,user_gmail) " +
            ");");

        sqLiteDatabase.execSQL(
            "CREATE TABLE IF NOT EXISTS savedstation ( " +
            "station_id INTEGER, " +
            "user_gmail TEXT, " +
            "PRIMARY KEY(station_id,user_gmail) " +
            ");");

        sqLiteDatabase.execSQL(
            "CREATE TABLE IF NOT EXISTS busstop (" +
            "route_id TEXT," +
            "station_id INTEGER," +
            "`order` INTEGER," +
            "PRIMARY KEY(route_id, station_id)" +
            ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
