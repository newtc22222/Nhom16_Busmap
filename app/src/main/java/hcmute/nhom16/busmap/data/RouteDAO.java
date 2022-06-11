package hcmute.nhom16.busmap.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hcmute.nhom16.busmap.Support;
import hcmute.nhom16.busmap.model.Route;

public class RouteDAO {
    public static final int ID = 0;
    public static final int START_STATION_ID = 1;
    public static final int END_STATION_ID = 2;
    public static final int PRICE = 3;
    public static final int TYPE = 4;
    public static final int OPERATION_TIME = 5;
    public static final int CYCLE_TIME = 6;
    public static final int UNIT = 7;
    public static final int REPEAT_TIME = 8;
    public static final int PER_DAY = 9;
    public static final int DISTANCE = 10;

    private static List<Route> get(Context context, String condition) {
        ArrayList<Route> list = new ArrayList<>();

        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM route";
        if (condition != null) {
            sql += " WHERE " + condition;
        }
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        while (!cs.isAfterLast())
        {
            String id = cs.getString(ID);
            int start_station_id = cs.getInt(START_STATION_ID);
            int end_station_id = cs.getInt(END_STATION_ID);
            int price = cs.getInt(PRICE);
            String type = cs.getString(TYPE);
            String operation_time = cs.getString(OPERATION_TIME);
            String cycle_time  = cs.getString(CYCLE_TIME);
            String unit = cs.getString(UNIT);
            int repeat_time = cs.getInt(REPEAT_TIME);
            int per_day = cs.getInt(PER_DAY);
            int distance = cs.getInt(DISTANCE);

            Route route = new Route(id, StationDAO.getStationById(context, start_station_id),
                    StationDAO.getStationById(context, end_station_id), price, type, operation_time,
                    cycle_time, unit, repeat_time, per_day, distance);
            list.add(route);
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return list;
    }

    public static List<Route> getAllRoutes(Context context) {
        return get(context, null);
    }

    public static Route getRouteByID(Context context, String id) {
        List<Route> routes = get(context, "id='" + id + "'");
        return routes.size() > 0 ? routes.get(0) : null;
    }
}