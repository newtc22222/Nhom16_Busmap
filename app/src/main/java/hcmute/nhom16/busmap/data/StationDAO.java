package hcmute.nhom16.busmap.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hcmute.nhom16.busmap.model.Address;
import hcmute.nhom16.busmap.model.Station;

public class StationDAO {
    public static final int ID = 0;
    public static final int NAME = 1;
    public static final int ADDRESS = 2;
    public static final int LAT = 3;
    public static final int LNG = 4;

    private static List<Station> get(Context context, String condition) {
        ArrayList<Station> list = new ArrayList<>();

        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM station";
        if (condition != null) {
            sql += " WHERE " + condition;
        }
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        while (!cs.isAfterLast())
        {
            int id = cs.getInt(ID);
            String name = cs.getString(NAME);
            String address = cs.getString(ADDRESS);
            double lat = cs.getDouble(LAT);
            double lng = cs.getDouble(LNG);

            Station station = new Station(id, name, new Address(address, lat, lng));
            list.add(station);
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return list;
    }

    public static List<Station> getAllStations(Context context) {
        return get(context, null);
    }

    public static List<Station> getStationsAround(Context context, double lat, double lng, double extend) {
        String condition = String.format("lat BETWEEN %s AND %s AND lng BETWEEN %s AND %s",
                lat - extend, lat + extend, lng - extend, lng + extend);
        return get(context, condition);
    }

    public static Station getStationById(Context context, int id) {
        List<Station> stations = get(context, "id=" + id);
        return stations.size() > 0 ? stations.get(0) : null;
    }

}