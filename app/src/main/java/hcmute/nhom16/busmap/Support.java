package hcmute.nhom16.busmap;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.util.Log;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hcmute.nhom16.busmap.data.BusStopDAO;
import hcmute.nhom16.busmap.data.RouteDAO;
import hcmute.nhom16.busmap.data.SavedRouteDAO;
import hcmute.nhom16.busmap.data.SavedStationDAO;
import hcmute.nhom16.busmap.data.StationDAO;
import hcmute.nhom16.busmap.data.UserDAO;
import hcmute.nhom16.busmap.model.Address;
import hcmute.nhom16.busmap.model.BusDistance;
import hcmute.nhom16.busmap.model.BusStop;
import hcmute.nhom16.busmap.model.BusStopGuide;
import hcmute.nhom16.busmap.model.BusStopRaw;
import hcmute.nhom16.busmap.model.Result;
import hcmute.nhom16.busmap.model.ResultRoute;
import hcmute.nhom16.busmap.model.Route;
import hcmute.nhom16.busmap.model.RouteGuide;
import hcmute.nhom16.busmap.model.Station;
import hcmute.nhom16.busmap.model.User;
import hcmute.nhom16.busmap.model.UserAccount;
import hcmute.nhom16.busmap.result.MoveType;

public class Support {

    public static String dateToString(Date date, String fm) {
        SimpleDateFormat format = new SimpleDateFormat(fm);
        return format.format(date);
    }

    public static LocalTime stringToLocalTime(String time, String fm) {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern(fm));
    }

    public static Date stringToDate(String date, String fm) {
        SimpleDateFormat format = new SimpleDateFormat(fm);
        Date dt = new Date();
        try {
            dt = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static String timeToString(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }

    public static String toCurrency(int price) {
        String currency = "";
        int count = 0;
        int m = price;
        while (m != 0) {
            currency = m % 10 + currency;
            m /= 10;
            if (++count % 3 == 0 && m != 0) {
                currency = "," + currency;
            }
        }
        return currency + " VND";
    }

    private static Iterator<Integer> getLevelList() {
        List<Integer> level = new ArrayList<>();
        level.add(LevelExtend.MIN);
        level.add(LevelExtend.LOW);
        level.add(LevelExtend.NORMAL);
        level.add(LevelExtend.HIGH);
        level.add(LevelExtend.MAX);
        return level.iterator();
    }

    public static boolean checkInvalidEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z]+\\w*@gmail.com$");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public static boolean checkInvalidPassword(String password) {
        return password.length() > 8;
    }

    public static List<Route> getAllRoutes(Context context) {
        return RouteDAO.getAllRoutes(context);
    }

    public static List<BusStop> getAllBusStopFromRouteId(Context context, String route_id) {
        return BusStopDAO.getBusStopsByRouteId(context, route_id);
    }

    public static List<Route> getRoutesFromStation(Context context, Station station) {
        return getAllRoutes(context);
    }

    public static List<Station> getSavedStations(Context context) {
        User user = UserAccount.getUser();
        if (user != null) {
            return SavedStationDAO.getSavedStationsByUserId(context, user.getEmail());
        }
        return new ArrayList<>();
    }

    public static List<Route> getSavedRoutes(Context context) {
        User user = UserAccount.getUser();
        if (user != null) {
            return SavedRouteDAO.getSavedRoutesByUserID(context, user.getEmail());
        }
        return new ArrayList<>();
    }

    public static void unSaveStation(Context context, int station_id) {
        User user = UserAccount.getUser();
        if (user != null) {
            String email = user.getEmail();
            SavedStationDAO.deleteSavedStation(context, email, station_id);
        }
    }

    public static void saveStation(Context context, int station_id) {
        User user = UserAccount.getUser();
        if (user != null) {
            String email = user.getEmail();
            SavedStationDAO.insertSavedStation(context, email, station_id);
        }
    }

    public static void saveRoute(Context context, String route_id) {
        User user = UserAccount.getUser();
        if (user != null) {
            String email = user.getEmail();
            SavedRouteDAO.insertSavedRoute(context, email, route_id);
        }
    }

    public static void unSaveRoute(Context context, String route_id) {
        User user = UserAccount.getUser();
        if (user != null) {
            String email = user.getEmail();
            SavedRouteDAO.insertSavedRoute(context, email, route_id);
        }
    }

    public static int calculateDistance(Address a, Address b) {
        if (a == null || b == null) {
            return -1;
        }
        double x = Math.abs(a.getLat() - b.getLat()) * 110000;
        double y = Math.abs(a.getLng() - b.getLng()) * 110000;
        return (int) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public static String distanceToTime(int meter, MoveType type) {
        if (meter == -1) {
            return "";
        }
        int speed = 0;
        if (type == MoveType.BUS) {
            speed = Speed.BUS;
        } else {
            speed = Speed.WALK;
        }
        int result = meter / speed;
        if (meter / speed == 0) {
            result = 1;
        }
        return result + " phút";
    }

    public static boolean checkAccountExists(Context context, String email) {
        return UserDAO.checkExist(context, email);
    }

    public static User getUser(Context context, String email, String password) {
        if (checkAccountExists(context, email)) {
            return UserDAO.getUser(context, email, password);
        }
        return null;
    }

    public static void register(Context context, User user) {
        UserDAO.createUser(context, user);
    }

    public static void updateUser(Context context, User user) {
        UserDAO.updateUser(context, user);
    }

    public static String toKilometerString(int meter) {
        double k = meter / 1000.0;
        return k + "km";
    }

    public static String toMeterString(int meter) {
        return meter + " mét";
    }

    public static int calculateTimePass(int meter, int speed) {
        return meter / speed;
    }

    public static double convertMeterToDegree(int meter) {
        return meter / 111000.0;
    }

    public static int convertDegreeToMeter(double degree) {
        return (int) degree * 111000;
    }

    public static List<LocalTime> getAllBusStopTimeLinesFromRoute(Context context, Route route) {
        List<LocalTime> time_lines = new ArrayList<>();
        String s = route.getOperation_time().split(" -")[0];
        time_lines.add(stringToLocalTime(s, "HH:mm"));
        for (int i = 1; i < route.getPer_day(); i++) {
            time_lines.add(time_lines.get(i - 1).plusMinutes(route.getRepeat_time()));
        }
        return time_lines;
    }

    public static List<RouteGuide> getRouteGuides(Context context, Result result, Address from, Address to) {
        List<RouteGuide> routeGuides = new ArrayList<>();

        routeGuides.add(new RouteGuide("Đi đến " + result.getResult_routes().get(0).getBusStop_start().getStation().getName(),
                "Xuất phát từ " + from.getAddress(),
                MoveType.WALK, result.getWalk_distance_start(), ""));

        String preAddress = null;

        for (ResultRoute resultRoute : result.getResult_routes()) {
            if (preAddress != null) {
                routeGuides.add(new RouteGuide( preAddress + " - " + resultRoute.getBusStop_start().getStation().getName(),
                        "Đi xuống " + preAddress + " - " + " Đi lên " + resultRoute.getBusStop_start().getStation().getName(),
                        MoveType.WALK,0, ""));
            }

            preAddress = resultRoute.getBusStop_end().getStation().getName();

            routeGuides.add(new RouteGuide("Đi tuyến " + resultRoute.getRoute().getId() + ": " +
                resultRoute.getRoute().getName(), resultRoute.getBusStop_start().getStation().getName() + " - " +
                resultRoute.getBusStop_end().getStation().getName(), MoveType.BUS, result.getBus_distance(),
                resultRoute.getRoute().getMoney()));
        }

        routeGuides.add(new RouteGuide("Đi xuống " + result.getResult_routes().get(result.getResult_routes().size() - 1).getBusStop_end().getStation().getName(),
                "Đi đến " + to.getAddress(), MoveType.WALK,
                result.getWalk_distance_end(), ""));

        return routeGuides;
    }

    public static List<BusStopGuide> getBusStopGuides(Context context, Result result, Address from, Address to) {
        List<BusStopGuide> busStopGuides = new ArrayList<>();

        busStopGuides.add(new BusStopGuide("", from.getAddress(), MoveType.WALK, from));

        for (ResultRoute resultRoute : result.getResult_routes()) {
            int s = resultRoute.getBusStop_start().getOrder(), e = resultRoute.getBusStop_end().getOrder();
            for (BusStop busStop : BusStopDAO.getBusStopsFromRouteIdAndOrder(context, resultRoute.getRoute().getId(), s, e)) {

                busStopGuides.add(new BusStopGuide(busStop.getRoute_id(), busStop.getStation().getName(),
                        MoveType.BUS, busStop.getStation().getAddress()));

            }
        }

        busStopGuides.add(new BusStopGuide("", to.getAddress(), MoveType.WALK, to));

        return busStopGuides;
    }

    public static int compare(Result x, Result y) {
        return 0;
    }


    public static List<Result> calculateResults(Context context, Address from, Address to, int route_amount) {
        List<Result> results = new ArrayList<>();

        List<Station> starts = StationDAO.getStationsAround(context,
                from.getLat(), from.getLng(), convertMeterToDegree(2000));

        List<Station> ends = StationDAO.getStationsAround(context,
                to.getLat(), to.getLng(), convertMeterToDegree(2000));

        Dictionary<String, BusDistance> dictionary_starts = calculate(context, starts, from);
        Dictionary<String, BusDistance> dictionary_ends = calculate(context, ends, to);

        Enumeration<String> enumeration = dictionary_starts.keys();

        while (enumeration.hasMoreElements()) {
            String current = enumeration.nextElement();
            if (dictionary_ends.get(current) != null
                    && dictionary_starts.get(current).getBus_stop_raw().getOrder() < dictionary_ends.get(current).getBus_stop_raw().getOrder()) {
                List<ResultRoute> resultRoutes = new ArrayList<>();

                resultRoutes.add(new ResultRoute(RouteDAO.getRouteByID(context, current),
                        BusStopDAO.convertRawToBusStop(context, dictionary_starts.get(current).getBus_stop_raw()),
                        BusStopDAO.convertRawToBusStop(context, dictionary_ends.get(current).getBus_stop_raw())));

                results.add(new Result(resultRoutes , dictionary_starts.get(current).getDistance(),
                        dictionary_ends.get(current).getDistance()));
            }
        }
        return results;
    }

    public static Dictionary<String, BusDistance> calculate(Context context, List<Station> stations, Address address) {

        Dictionary<String, BusDistance> dictionary = new Hashtable<>();

        BusDistance busDistance;

        for (Station station : stations) {
            int distance = calculateDistance(address, station.getAddress());
            List<BusStopRaw> raws = BusStopDAO.getBusStopRawFromStationId(context, station.getId());
            for (BusStopRaw raw : raws) {
                busDistance = dictionary.get(raw.getRoute_id());
                if (busDistance == null) {
                    dictionary.put(raw.getRoute_id(), new BusDistance(raw, distance));
                } else {
                    if (busDistance.getDistance() > distance) {
                        dictionary.put(raw.getRoute_id(), new BusDistance(raw, distance));
                    }
                }
            }
        }

        return dictionary;
    }
}
