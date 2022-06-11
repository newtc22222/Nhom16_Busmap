package hcmute.nhom16.busmap.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hcmute.nhom16.busmap.model.Route;

public class SavedRouteDAO {
    public static final int ROUTE_ID = 0;
    public static final int USER_EMAIL = 1;

    public static List<Route> getSavedRoutesByUserID(Context context , String user_gmail){

        ArrayList<Route> list = new ArrayList<>();

        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM savedroute WHERE user_gmail='" + user_gmail + "'";
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        while (!cs.isAfterLast())
        {
            String route_id = cs.getString(ROUTE_ID);

            Route route = RouteDAO.getRouteByID(context, route_id);
            list.add(route);
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return list;
    }

    public static void insertSavedRoute(Context context, String user_gmail, String route_id) {
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_gmail", user_gmail);
        contentValues.put("route_id", route_id);
        db.insert("savedroute", null, contentValues);
        db.close();
    }

    public static void deleteSavedRoute(Context context, String user_gmail, String route_id) {
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("DELETE FROM savedroute WHERE user_gmail='" + user_gmail + "' AND route_id='" + route_id + "'");
        db.close();
    }
}