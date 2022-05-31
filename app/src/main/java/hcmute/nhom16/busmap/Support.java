package hcmute.nhom16.busmap;

import android.icu.text.SimpleDateFormat;

import java.text.ParseException;
import java.util.Date;

public class Support {
    public static String dateToString(Date date, String fm) {
        SimpleDateFormat format = new SimpleDateFormat(fm);
        return format.format(date);
    }
    public static Date stringToDate(String date, String fm) {
        SimpleDateFormat format = new SimpleDateFormat(fm);
        Date d = new Date();
        try {
            d = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }
}
