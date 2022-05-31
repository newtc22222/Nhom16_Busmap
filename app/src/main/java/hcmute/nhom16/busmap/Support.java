package hcmute.nhom16.busmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import hcmute.nhom16.busmap.model.Address;
import hcmute.nhom16.busmap.model.BusStop;
import hcmute.nhom16.busmap.model.BusStopGuide;
import hcmute.nhom16.busmap.model.BusStopGuideAdapter;
import hcmute.nhom16.busmap.model.ResultRoute;
import hcmute.nhom16.busmap.model.Route;
import hcmute.nhom16.busmap.model.RouteGuide;
import hcmute.nhom16.busmap.model.RouteGuideAdapter;
import hcmute.nhom16.busmap.model.User;
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
        Date d = new Date();
        try {
            d = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
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
                currency += ",";
            }
        }
        return currency;
    }

    public static List<Address> getAllAddresses() {
        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address("204 Đường Quốc Lộ 13 Cũ, Hiệp Bình Phước, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("204 Đường Quốc Lộ 13 Cũ, Hiệp Bình Phước, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("90/4A Đường Số 21, Hiệp Bình Phước, Thủ Đức, TP. HCM 700000", 10, 170));
        addresses.add(new Address("37 QL1A, Hiệp Bình Phước, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("190C QL1A, Tam Binh, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("348 QL1A, Bình Chiểu, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("19 Xuyên Á, An Bình, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("533 Khu phố, Bình Đường 4, An Bình, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("48 Đ. Số 11, Linh Xuân, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("87/4 Tô Vĩnh Diện, Đông Hoà, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("5a Nguyễn Bỉnh Khiêm, Đông Hoà, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("204 Đường Quốc Lộ 13 Cũ, Hiệp Bình Phước, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("90/4A Đường Số 21, Hiệp Bình Phước, Thủ Đức, TP. HCM 700000", 10, 170));
        addresses.add(new Address("37 QL1A, Hiệp Bình Phước, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("190C QL1A, Tam Binh, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("348 QL1A, Bình Chiểu, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("19 Xuyên Á, An Bình, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("533 Khu phố, Bình Đường 4, An Bình, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("48 Đ. Số 11, Linh Xuân, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("87/4 Tô Vĩnh Diện, Đông Hoà, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("5a Nguyễn Bỉnh Khiêm, Đông Hoà, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("204 Đường Quốc Lộ 13 Cũ, Hiệp Bình Phước, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("90/4A Đường Số 21, Hiệp Bình Phước, Thủ Đức, TP. HCM 700000", 10, 170));
        addresses.add(new Address("37 QL1A, Hiệp Bình Phước, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("190C QL1A, Tam Binh, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("348 QL1A, Bình Chiểu, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("19 Xuyên Á, An Bình, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("533 Khu phố, Bình Đường 4, An Bình, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("48 Đ. Số 11, Linh Xuân, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("87/4 Tô Vĩnh Diện, Đông Hoà, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("5a Nguyễn Bỉnh Khiêm, Đông Hoà, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("204 Đường Quốc Lộ 13 Cũ, Hiệp Bình Phước, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("90/4A Đường Số 21, Hiệp Bình Phước, Thủ Đức, TP. HCM 700000", 10, 170));
        addresses.add(new Address("37 QL1A, Hiệp Bình Phước, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("190C QL1A, Tam Binh, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("348 QL1A, Bình Chiểu, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("19 Xuyên Á, An Bình, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("533 Khu phố, Bình Đường 4, An Bình, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("48 Đ. Số 11, Linh Xuân, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("87/4 Tô Vĩnh Diện, Đông Hoà, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("5a Nguyễn Bỉnh Khiêm, Đông Hoà, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("204 Đường Quốc Lộ 13 Cũ, Hiệp Bình Phước, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("90/4A Đường Số 21, Hiệp Bình Phước, Thủ Đức, TP. HCM 700000", 10, 170));
        addresses.add(new Address("37 QL1A, Hiệp Bình Phước, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("190C QL1A, Tam Binh, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("348 QL1A, Bình Chiểu, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("19 Xuyên Á, An Bình, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("533 Khu phố, Bình Đường 4, An Bình, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("48 Đ. Số 11, Linh Xuân, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("87/4 Tô Vĩnh Diện, Đông Hoà, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("5a Nguyễn Bỉnh Khiêm, Đông Hoà, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("204 Đường Quốc Lộ 13 Cũ, Hiệp Bình Phước, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("90/4A Đường Số 21, Hiệp Bình Phước, Thủ Đức, TP. HCM 700000", 10, 170));
        addresses.add(new Address("37 QL1A, Hiệp Bình Phước, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("190C QL1A, Tam Binh, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("348 QL1A, Bình Chiểu, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("19 Xuyên Á, An Bình, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("533 Khu phố, Bình Đường 4, An Bình, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("48 Đ. Số 11, Linh Xuân, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("87/4 Tô Vĩnh Diện, Đông Hoà, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("5a Nguyễn Bỉnh Khiêm, Đông Hoà, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("204 Đường Quốc Lộ 13 Cũ, Hiệp Bình Phước, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("90/4A Đường Số 21, Hiệp Bình Phước, Thủ Đức, TP. HCM 700000", 10, 170));
        addresses.add(new Address("37 QL1A, Hiệp Bình Phước, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("190C QL1A, Tam Binh, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("348 QL1A, Bình Chiểu, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("19 Xuyên Á, An Bình, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("533 Khu phố, Bình Đường 4, An Bình, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("48 Đ. Số 11, Linh Xuân, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("87/4 Tô Vĩnh Diện, Đông Hoà, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("5a Nguyễn Bỉnh Khiêm, Đông Hoà, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("204 Đường Quốc Lộ 13 Cũ, Hiệp Bình Phước, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("90/4A Đường Số 21, Hiệp Bình Phước, Thủ Đức, TP. HCM 700000", 10, 170));
        addresses.add(new Address("37 QL1A, Hiệp Bình Phước, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("190C QL1A, Tam Binh, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("348 QL1A, Bình Chiểu, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("19 Xuyên Á, An Bình, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("533 Khu phố, Bình Đường 4, An Bình, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("48 Đ. Số 11, Linh Xuân, Thủ Đức, TP. HCM", 10, 170));
        addresses.add(new Address("87/4 Tô Vĩnh Diện, Đông Hoà, Dĩ An, Bình Dương", 10, 170));
        addresses.add(new Address("5a Nguyễn Bỉnh Khiêm, Đông Hoà, Dĩ An, Bình Dương", 10, 170));
        return addresses;
    }

    public static List<BusStop> createBusStops() {
        List<BusStop> busStops = new ArrayList<>();
        List<Address> addresses = getAllAddresses();
        busStops.add(new BusStop(1, "Bến xe buýt Quận 8", 8, 1, addresses.get(1), 1));
        busStops.add(new BusStop(2, "Bùi Minh Trực", 8, 2, addresses.get(2), 0));
        busStops.add(new BusStop(3, "Cầu Nhị Thiên Đường", 8, 3, addresses.get(3), 1));
        busStops.add(new BusStop(4, "Tùng Thiện Vương", 8, 4, addresses.get(4), 2));
        busStops.add(new BusStop(5, "Chợ Xóm Củi", 8, 5, addresses.get(5), 3));
        busStops.add(new BusStop(6, "Bưu điện Quận 5", 8, 6, addresses.get(6), 2));
        busStops.add(new BusStop(7, "Rạp Đại Quang", 8, 7, addresses.get(7), 1));
        busStops.add(new BusStop(8, "Bệnh viện Chợ Rẫy", 8, 8, addresses.get(8), 0));
        busStops.add(new BusStop(9, "Bệnh viện Hùng Vương", 8, 9, addresses.get(9), 1));
        busStops.add(new BusStop(10, "CoopMart Lý Thường Kiệt", 8, 10, addresses.get(10), 1));
        busStops.add(new BusStop(11, "Điện lực Phú Thọ", 8, 8, addresses.get(8), 3));
        busStops.add(new BusStop(12, "Nhà thi đấu Phú Thọ", 8, 12, addresses.get(12), 4));
        busStops.add(new BusStop(13, "Bệnh viện Trưng Vương", 8, 13, addresses.get(13), 1));
        busStops.add(new BusStop(14, "Đại học Bách Khoa", 8, 14, addresses.get(14), 2));
        busStops.add(new BusStop(15, "Bưu Điện Phú Thọ", 8, 15, addresses.get(15), 5));
        busStops.add(new BusStop(16, "Ngã ba Thành Thái", 8, 16, addresses.get(16), 1));
        busStops.add(new BusStop(17, "Siêu thị Nguyễn Kim - CMC Tân Bình", 8, 17, addresses.get(17), 1));
        busStops.add(new BusStop(18, "Cây xăng Đôi", 8, 18, addresses.get(18), 1));
        busStops.add(new BusStop(19, "Chợ Tân Bình", 8, 19, addresses.get(19), 2));
        busStops.add(new BusStop(20, "Bệnh viện chỉnh hình và Phục hồi chức năng", 8, 20, addresses.get(20), 2));
        busStops.add(new BusStop(21, "Bệnh viện Thống Nhất", 8, 21, addresses.get(21), 3));
        busStops.add(new BusStop(22, "Bệnh viện Quận Tân Bình", 8, 22, addresses.get(22), 2));
        busStops.add(new BusStop(23, "Nhà hàng Đông Phương", 8, 23, addresses.get(23), 0));
        busStops.add(new BusStop(24, "Công viên Hoàng Văn Thụ", 8, 24, addresses.get(24), 1));
        busStops.add(new BusStop(25, "Bảo Tàng Miền Đông", 8, 25, addresses.get(25), 0));
        busStops.add(new BusStop(26, "Siêu Thị Big C", 8, 26, addresses.get(26), 1));
        busStops.add(new BusStop(27, "Khách Sạn Tân Sơn Nhất", 8, 27, addresses.get(27), 2));
        busStops.add(new BusStop(28, "Công an Phú Nhuận", 8, 28, addresses.get(28), 1));
        busStops.add(new BusStop(29, "Ngã tư Phú Nhuận", 8, 29, addresses.get(29), 1));
        busStops.add(new BusStop(30, "Ngã tư Phan Xích Long", 8, 30, addresses.get(30), 1));
        busStops.add(new BusStop(31, "Ngã Tư Thích Quảng Đức", 8, 31, addresses.get(31), 1));
        busStops.add(new BusStop(32, "Trường Đại học Văn Hiến", 8, 32, addresses.get(32), 1));
        busStops.add(new BusStop(33, "Bệnh Viện Phước An", 8, 33, addresses.get(33), 0));
        busStops.add(new BusStop(34, "Công viên Văn hóa Phú Nhuận", 8, 34, addresses.get(34), 2));
        busStops.add(new BusStop(35, "UBND Quận Bình Thạnh", 8, 35, addresses.get(35), 1));
        busStops.add(new BusStop(36, "Lăng Ông Bà Chiểu", 8, 36, addresses.get(36), 1));
        busStops.add(new BusStop(37, "Chợ Bà Chiểu", 8, 37, addresses.get(37), 1));
        busStops.add(new BusStop(38, "Tòa Án nhân dân Quận Bình Thạnh", 8, 38, addresses.get(38), 1));
        busStops.add(new BusStop(39, "Chùa Bồ Đề", 8, 39, addresses.get(39), 1));
        busStops.add(new BusStop(40, "Nhà thờ Hàng Xanh", 8, 40, addresses.get(40), 1));
        busStops.add(new BusStop(41, "Chợ Hàng Xanh", 8, 41, addresses.get(41), 1));
        busStops.add(new BusStop(42, "Ngã Ba Hàng Xanh", 8, 42, addresses.get(42), 0));
        busStops.add(new BusStop(43, "Đài Liệt sĩ", 8, 43, addresses.get(43), 0));
        busStops.add(new BusStop(44, "Siêu thị Coop Mart", 8, 44, addresses.get(44), 1));
        busStops.add(new BusStop(45, "Cổng ra - Bến xe Miền Đông 3", 8, 45, addresses.get(45), 1));
        busStops.add(new BusStop(46, "Ngã tư Bình Triệu", 8, 46, addresses.get(46), 0));
        busStops.add(new BusStop(47, "Đường số 20", 8, 47, addresses.get(47), 1));
        busStops.add(new BusStop(48, "Chùa Ưu Đàm", 8, 48, addresses.get(48), 1));
        busStops.add(new BusStop(49, "Cá sấu Hoa cà", 8, 49, addresses.get(49), 1));
        busStops.add(new BusStop(50, "THCS Ngô Chí Quốc", 8, 50, addresses.get(50), 1));
        busStops.add(new BusStop(51, "Chùa An Lạc", 8, 51, addresses.get(51), 0));
        busStops.add(new BusStop(52, "Cầu Gò Dưa", 8, 52, addresses.get(52), 0));
        busStops.add(new BusStop(53, "Trường Cao đẳng Vinatex", 8, 53, addresses.get(53), 1));
        busStops.add(new BusStop(54, "Trạm xăng 27-7", 8, 54, addresses.get(54), 1));
        busStops.add(new BusStop(55, "Công an phường Linh Đông", 8, 55, addresses.get(55), 2));
        busStops.add(new BusStop(56, "Trạm Cầu Ngang", 8, 56, addresses.get(56), 1));
        busStops.add(new BusStop(57, "Chợ Thủ Đức", 8, 57, addresses.get(57), 1));
        busStops.add(new BusStop(58, "Ngã ba Chương Dương", 8, 58, addresses.get(58), 1));
        busStops.add(new BusStop(59, "Cao đẳng xây dựng 2", 8, 59, addresses.get(59), 1));
        busStops.add(new BusStop(60, "Siêu thị Nguyễn Kim", 8, 60, addresses.get(60), 2));
        busStops.add(new BusStop(61, "Trường ĐHSP Kỹ Thuật", 8, 61, addresses.get(61), 1));
        busStops.add(new BusStop(62, "Công an Quận 9", 8, 62, addresses.get(62), 2));
        busStops.add(new BusStop(63, "Chợ chiều", 8, 63, addresses.get(63), 1));
        busStops.add(new BusStop(64, "Trạm Hutech - Khu Công nghệ cao", 8, 64, addresses.get(64), 0));
        busStops.add(new BusStop(65, "Khu Công nghệ cao quận 9", 8, 65, addresses.get(65), 1));
        busStops.add(new BusStop(66, "Cầu Vượt Trạm 2", 8, 66, addresses.get(66), 0));
        busStops.add(new BusStop(67, "Suối Tiên", 8, 67, addresses.get(67), 1));
        busStops.add(new BusStop(68, "Nghĩa trang liệt sĩ TP.HCM", 8, 68, addresses.get(68), 1));
        busStops.add(new BusStop(69, "Vành đại ĐHQG TPHCM", 8, 69, addresses.get(69), 0));
        busStops.add(new BusStop(70, "KTX Khu A ĐH Quốc Gia TPHCM", 8, 70, addresses.get(70), 0));
        busStops.add(new BusStop(71, "Đại học Quốc tế", 8, 71, addresses.get(71), 0));
        busStops.add(new BusStop(72, "Đại học Quốc gia", 8, 72, addresses.get(72), 1));
        return busStops;
    }

    public static List<Route> getAllRoutes() {
        List<Route> routes = new ArrayList<>();
        List<Address> addresses = getAllAddresses();
        routes.add(new Route(8, new BusStop(72, "Bến Xe Buýt Quận 8", 8, 1, addresses.get(1), 1),
                new BusStop(1, "Đại học Quốc Gia", 8, 72, addresses.get(72), 1), "1", "80 phút", "04:40 - 20:30", "32 km", 7000, "15 phút - 20 phút", 130, "Hợp tác xã vận tải xe buýt Quyết Thắng, ĐT: (028)38.642.712"));
        routes.add(new Route(1, new BusStop(13, "Bến Thành", 1, 1, addresses.get(3), 1),
                new BusStop(12, "Bến Xe buýt Chợ Lớn", 1, 50, addresses.get(50), 1), "1", "35 phút", "05:00 - 19:00", "8 km", 5000, "15 phút - 20 phút", 54, "Cty TNHH Du lịch, Dịch vụ Xây dựng Bảo Yến, ĐT: 028.3776.3777"));
        routes.add(new Route(3, new BusStop(14, "Bến Xe Buýt Sài Gòn", 3, 1, addresses.get(5), 1),
                new BusStop(3, "Bến Xe Buýt Thạnh Lộc", 3, 53, addresses.get(53), 1), "1", "60 phút", "04:00 - 20:45", "20 km", 6000, "15 phút - 20 phút", 60, "Hợp tác xã vận tải 19/5, ĐT: 18001557"));
        routes.add(new Route(4, new BusStop(15, "Bến Xe Buýt Sài Gòn", 4, 1, addresses.get(9), 1),
                new BusStop(4, "Cộng Hòa - Bến Xe An Sương", 4, 61, addresses.get(6), 1), "1", "50 - 55 phút", "05:00 - 19:00", "16 km", 6000, "15 phút - 20 phút", 60, "Công ty Cổ phần Xe khách Sài Gòn, ĐT: (028)39505505"));
        routes.add(new Route(5, new BusStop(16, "Bến Xe Buýt Chợ Lớn", 5, 1, addresses.get(12), 1),
                new BusStop(5, "Biên Hòa", 5, 57, addresses.get(57), 1), "1", "100 phút", "05:00 - 17:40", "38 km",15000, "15 phút - 20 phút", 60, "Hợp tác xã vận tải xe buýt Quyết Thắng, ĐT: (028)38.642.712"));
        routes.add(new Route(9, new BusStop(17, "Bến Xe Buýt Chợ Lớn", 9, 1, addresses.get(22), 1),
                new BusStop(6, "Bình Chánh - Hưng Long", 9, 67, addresses.get(67), 1), "1", "70 phút", "03:45 - 19:10", "25 km", 7000, "15 phút - 20 phút", 54, "Liên hiệp hợp tác xã vận tải thành phố, ĐT: (028)39.716.720"));
        routes.add(new Route(6, new BusStop(18, "Bến Xe Buýt Chợ Lớn", 6, 1, addresses.get(62), 1),
                new BusStop(7, "Đại học Nông Lâm", 6, 69, addresses.get(69), 1), "1", "70 phút", "04:55 - 20:30", "26 km", 7000, "15 phút - 20 phút", 70, "Hợp tác xã vận tải xe buýt Quyết Thắng, ĐT: (028)38.642.712"));
        routes.add(new Route(15, new BusStop(19, "Chợ Phú Định", 15, 1, addresses.get(63), 1),
                new BusStop(8, "Đầm Sen", 15, 71, addresses.get(34), 1), "1", "50 phút", "05:00 - 19:00", "17 km", 6000, "15 phút - 20 phút", 54, "Hợp tác xã vận tải số 28, ĐT: (028)38.758.875"));
        routes.add(new Route(14, new BusStop(20, "Bến Xe Buýt Miền Đông", 14, 1, addresses.get(11), 1),
                new BusStop(9, "3 tháng 2 - Bến Xe Buýt Miền Tây", 14, 62, addresses.get(62), 1), "1", "60 phút", "04:00 - 20:00", "16 km", 6000, "15 phút - 20 phút", 60, "Liên hiệp hợp tác xã vận tải thành phố, ĐT: (028)39.716.720"));
        routes.add(new Route(10, new BusStop(21, "Đại học Quốc Gia", 10, 1, addresses.get(56), 1),
                new BusStop(10, "Bến xe Miền Tây", 10, 63, addresses.get(63), 1), "1", "80 phút", "05:00 - 18:45", "31 km", 7000, "15 phút - 20 phút", 70, "Hợp tác xã vận tải xe buýt Quyết Thắng, ĐT: (028)38.642.712"));
        routes.add(new Route(7, new BusStop(22, "Bến Xe Buýt Chợ Lớn", 7, 66, addresses.get(66), 1),
                new BusStop(11, "Gò vấp", 7, 70, addresses.get(70), 1), "1", "50 - 55 phút", "05:00 - 19:30", "15 km", 6000, "15 phút - 20 phút", 60, "Công ty Cổ phần Xe khách Sài Gòn, ĐT: (028)39505505"));
        return routes;
    }

    public static List<BusStop> getAllBusStopFromRoute(Route route) {
        return createBusStops();
    }

    public static void saveRoute(Route route) {

    }

    public static List<Route> getRoutesFromBusStop(BusStop busStop) {
        return getAllRoutes();
    }

    public static void saveBusStop(BusStop bus_stop) {
    }

    public static List<Route> getSavedRoutes() {
        return getAllRoutes();
    }

    public static List<BusStop> getSavedBusStops() {
        return createBusStops();
    }

    public static List<ResultRoute> getResultRoutes(Address from, Address to, int route_amount) {
        Random rd = new Random();
        int a = rd.nextInt(4);
        int b = a + 2 + rd.nextInt(5);
        List<ResultRoute> resultRoutes = new ArrayList<>();
        int start, end;
        for (Route route : getAllRoutes().subList(a, b)) {
            start = rd.nextInt(10);
            end = start + 3 + rd.nextInt(8);
            resultRoutes.add(new ResultRoute(route, start, end, from, to));
        }
        return resultRoutes;
    }

    public static List<RouteGuide> getRouteGuides(ResultRoute result_route, Address from, Address to) {
        List<RouteGuide> routeGuides = new ArrayList<>();
        routeGuides.add(new RouteGuide("Đi đến trạm " + result_route.getAddress_start().getAddress(),
                "Xuất phát từ " + from.getAddress(),
                MoveType.WALK, Support.calculateTimePass(Support.calculateDistance(from, result_route.getAddress_start()), 100),
                Support.toKilometerString(Support.calculateDistance(from, result_route.getAddress_start())), ""));
        routeGuides.add(new RouteGuide("Đi tuyến " + result_route.getRoute().getRouteNumId() + ": " +
                result_route.getRoute().getName(), result_route.getAddress_start().getAddress() + " - " +
                result_route.getAddress_end().getAddress(), MoveType.BUS, result_route.getTimePass(),
                result_route.getBusDistance(), result_route.getRoute().getMoney()));
        routeGuides.add(new RouteGuide("Đi xuống trạm " + result_route.getAddress_end().getAddress(),
                "Đi đến " + to.getAddress(),
                MoveType.WALK, Support.calculateTimePass(Support.calculateDistance(to, result_route.getAddress_end()), 100),
                Support.toKilometerString(Support.calculateDistance(to, result_route.getAddress_end())), ""));
        return routeGuides;
    }

    public static int calculateDistance(Address a, Address b) {
        return 1300;
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

    public static List<BusStopGuide> getBusStopGuides(ResultRoute result_route, Address from, Address to) {
        List<BusStopGuide> busStopGuides = new ArrayList<>();
        busStopGuides.add(new BusStopGuideAdapter(from));
        for (BusStop busStop : getAllBusStopFromRoute(result_route.getRoute())
                .subList(result_route.getOrder_start(), result_route.getOrder_end())) {
            busStopGuides.add(new BusStopGuideAdapter(busStop));
        }
        busStopGuides.add(new BusStopGuideAdapter(to));
        return busStopGuides;
    }

    public static boolean checkAccount(String email, String password) {
        return true;
    }

    public static boolean checkAccountExists(String email) {
        return false;
    }

    public static User getUser(String email, String password) {
        if (checkAccount(email, password)) {
            Date date = stringToDate("11/05/2001", "dd/MM/yyyy");
            return new User(1, "Phan Trung Tín", "0878754872", true, date, "phantrungtin011@gmail.com", null, null);
        }
        return null;
    }

    public static void register(User user) {
    }

    public static void updateUser(User user) {
    }

    public static List<LocalTime> getAllBusStopTimeLinesFromRoute(Route route) {
        List<LocalTime> time_lines = new ArrayList<>();
        time_lines.add(stringToLocalTime("04:45", "HH:mm"));
        time_lines.add(stringToLocalTime("05:00", "HH:mm"));
        time_lines.add(stringToLocalTime("05:15", "HH:mm"));
        time_lines.add(stringToLocalTime("05:30", "HH:mm"));
        time_lines.add(stringToLocalTime("05:45", "HH:mm"));
        time_lines.add(stringToLocalTime("06:00", "HH:mm"));
        time_lines.add(stringToLocalTime("06:15", "HH:mm"));
        time_lines.add(stringToLocalTime("06:30", "HH:mm"));
        time_lines.add(stringToLocalTime("06:45", "HH:mm"));
        time_lines.add(stringToLocalTime("07:00", "HH:mm"));
        time_lines.add(stringToLocalTime("07:15", "HH:mm"));
        time_lines.add(stringToLocalTime("07:30", "HH:mm"));
        time_lines.add(stringToLocalTime("07:45", "HH:mm"));
        time_lines.add(stringToLocalTime("08:00", "HH:mm"));
        time_lines.add(stringToLocalTime("08:15", "HH:mm"));
        time_lines.add(stringToLocalTime("08:30", "HH:mm"));
        time_lines.add(stringToLocalTime("08:30", "HH:mm"));
        time_lines.add(stringToLocalTime("08:45", "HH:mm"));
        time_lines.add(stringToLocalTime("09:00", "HH:mm"));
        time_lines.add(stringToLocalTime("09:15", "HH:mm"));
        time_lines.add(stringToLocalTime("09:30", "HH:mm"));
        time_lines.add(stringToLocalTime("09:45", "HH:mm"));
        time_lines.add(stringToLocalTime("10:00", "HH:mm"));
        time_lines.add(stringToLocalTime("10:15", "HH:mm"));
        time_lines.add(stringToLocalTime("10:30", "HH:mm"));
        time_lines.add(stringToLocalTime("10:45", "HH:mm"));
        time_lines.add(stringToLocalTime("11:00", "HH:mm"));
        time_lines.add(stringToLocalTime("13:15", "HH:mm"));
        time_lines.add(stringToLocalTime("13:30", "HH:mm"));
        time_lines.add(stringToLocalTime("13:45", "HH:mm"));
        time_lines.add(stringToLocalTime("14:00", "HH:mm"));
        time_lines.add(stringToLocalTime("14:15", "HH:mm"));
        time_lines.add(stringToLocalTime("14:30", "HH:mm"));
        time_lines.add(stringToLocalTime("14:45", "HH:mm"));
        time_lines.add(stringToLocalTime("15:00", "HH:mm"));
        time_lines.add(stringToLocalTime("15:15", "HH:mm"));
        time_lines.add(stringToLocalTime("15:30", "HH:mm"));
        time_lines.add(stringToLocalTime("15:45", "HH:mm"));
        time_lines.add(stringToLocalTime("16:00", "HH:mm"));
        time_lines.add(stringToLocalTime("16:15", "HH:mm"));
        time_lines.add(stringToLocalTime("16:30", "HH:mm"));
        time_lines.add(stringToLocalTime("16:45", "HH:mm"));
        time_lines.add(stringToLocalTime("17:00", "HH:mm"));
        time_lines.add(stringToLocalTime("17:15", "HH:mm"));
        time_lines.add(stringToLocalTime("17:30", "HH:mm"));
        time_lines.add(stringToLocalTime("17:45", "HH:mm"));
        time_lines.add(stringToLocalTime("18:00", "HH:mm"));
        time_lines.add(stringToLocalTime("18:15", "HH:mm"));
        time_lines.add(stringToLocalTime("18:30", "HH:mm"));
        time_lines.add(stringToLocalTime("18:45", "HH:mm"));
        time_lines.add(stringToLocalTime("19:00", "HH:mm"));
        time_lines.add(stringToLocalTime("20:15", "HH:mm"));
        time_lines.add(stringToLocalTime("20:30", "HH:mm"));
        time_lines.add(stringToLocalTime("20:45", "HH:mm"));
        time_lines.add(stringToLocalTime("20:00", "HH:mm"));
        time_lines.add(stringToLocalTime("20:15", "HH:mm"));
        time_lines.add(stringToLocalTime("20:30", "HH:mm"));
        time_lines.add(stringToLocalTime("20:45", "HH:mm"));
        time_lines.add(stringToLocalTime("21:00", "HH:mm"));
        return time_lines;
    }
}
