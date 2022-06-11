package hcmute.nhom16.busmap.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hcmute.nhom16.busmap.Support;
import hcmute.nhom16.busmap.model.Address;
import hcmute.nhom16.busmap.model.BusStop;
import hcmute.nhom16.busmap.model.BusStopRaw;
import hcmute.nhom16.busmap.model.Station;

public class BusStopDAO {
    public static final int ROUTE_ID = 0;
    public static final int STATION_ID = 1;
    public static final int ORDER = 2;

    private static List<BusStopRaw> getRaw(Context context, String condition) {
        ArrayList<BusStopRaw> list = new ArrayList<>();

        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM busstop";
        if (condition != null) {
            sql += " WHERE " + condition;
        }
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        while (!cs.isAfterLast())
        {
            String route_id = cs.getString(ROUTE_ID);
            int station_id = cs.getInt(STATION_ID);
            int order = cs.getInt(ORDER);
            BusStopRaw bus_stop_raw = new BusStopRaw(route_id, station_id, order);
            list.add(bus_stop_raw);
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return list;
    }

    private static List<BusStop> get(Context context, String condition) {
        List<BusStopRaw> bus_stop_raws = getRaw(context, condition);
        List<BusStop> bus_stops = new ArrayList<>();
        Address pre_address = null;
        for (BusStopRaw busStopRaw : bus_stop_raws) {
            Station new_station = StationDAO.getStationById(context, busStopRaw.getStation_id());
            BusStop bus_stop = new BusStop(busStopRaw.getRoute_id(),
                    new_station, busStopRaw.getOrder(),
                    Support.calculateDistance(new_station.getAddress(), pre_address));

            pre_address = new_station.getAddress();
            bus_stops.add(bus_stop);
        }
        return bus_stops;
    }

    public static BusStop getBusStopFromRouteIdAndOrder(Context context, String route_id, int order) {
        List<BusStop> busStops = get(context, "route_id='" + route_id + "' AND `order`=" + order);
        return busStops.size() > 0 ? busStops.get(0) : null;
    }

    public static List<BusStop> getBusStopsFromRouteIdAndOrder(Context context, String route_id, int order_start, int order_end) {
        List<BusStop> busStops = new ArrayList<>();
        for (int i = order_start; i <= order_end; i++) {
            busStops.add(getBusStopFromRouteIdAndOrder(context, route_id, i));
        }
        return busStops;
    }

    public static BusStop convertRawToBusStop(Context context, BusStopRaw busStopRaw) {
        return new BusStop(busStopRaw.getRoute_id(),
                StationDAO.getStationById(context, busStopRaw.getStation_id()), busStopRaw.getOrder(),
                -1);
    }

    public static List<BusStop> getBusStopFromStationId(Context context, int station_id) {
        return get(context, "station_id=" + station_id);
    }

    public static List<BusStopRaw> getBusStopRawFromStationId(Context context, int station_id) {
        return getRaw(context, "station_id=" + station_id);
    }

    public static List<BusStop> getBusStopsByRouteId(Context context, String route_id) {
        List<BusStop> busStops = get(context, "route_id='" + route_id + "' ORDER BY `order`");
        return busStops;
    }
}
