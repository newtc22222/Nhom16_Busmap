package hcmute.nhom16.busmap.model;

import java.io.Serializable;

import hcmute.nhom16.busmap.Support;

public class Route implements Serializable {
    private int id;
    private BusStop start_bus_stop;
    private BusStop end_bus_stop;
    private String type;
    private String operating_time;
    private String length;
    private String time_run;
    private int price;
    private String repeat;
    private int per_day;
    private String unit;

    public Route(int id, BusStop start_bus_stop, BusStop end_bus_stop, String type,
                 String time_run, String operating_time, String length, int price, String repeat, int per_day, String unit) {
        this.id = id;
        this.start_bus_stop = start_bus_stop;
        this.end_bus_stop = end_bus_stop;
        this.type = type;
        this.operating_time = operating_time;
        this.length = length;
        this.time_run = time_run;
        this.price = price;
        this.repeat = repeat;
        this.per_day = per_day;
        this.unit = unit;
    }

    public String getOperating_time() {
        return operating_time;
    }

    public void setOperating_time(String operating_time) {
        this.operating_time = operating_time;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public int getPer_day() {
        return per_day;
    }

    public void setPer_day(int per_day) {
        this.per_day = per_day;
    }

    public String getAmountPerDay() {
        return per_day + " chuyến/ngày";
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRouteNumId() {
        return "" + id;
    }

    public String getRouteName() {
        return "Tuyến số " + id;
    }

    public String getName() {
        return start_bus_stop.getName() + " - " + end_bus_stop.getName();
    }

    public String getOperatingTime() {
        return operating_time;
    }

    public String getMoney() {
        return  Support.toCurrency(price) + "VND";
    }

    public String getTime_run() {
        return time_run;
    }

    public void setTime_run(String time_run) {
        this.time_run = time_run;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BusStop getStart_bus_stop() {
        return start_bus_stop;
    }

    public void setStart_bus_stop(BusStop start_bus_stop) {
        this.start_bus_stop = start_bus_stop;
    }

    public BusStop getEnd_bus_stop() {
        return end_bus_stop;
    }

    public void setEnd_bus_stop(BusStop end_bus_stop) {
        this.end_bus_stop = end_bus_stop;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
