package hcmute.nhom16.busmap.model;

import hcmute.nhom16.busmap.result.MoveType;

public class RouteGuide {
    private String title = "";
    private String description = "";
    private MoveType type;
    private int time_pass;
    private String distance;
    private String price;

    public RouteGuide(String title, String description, MoveType type, int time_pass, String distance, String price) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.time_pass = time_pass;
        this.distance = distance;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MoveType getType() {
        return type;
    }

    public void setType(MoveType type) {
        this.type = type;
    }

    public int getTime_pass() {
        return time_pass;
    }

    public String getTimePassString() {
        return time_pass + " phút";
    }

    public void setTime_pass(int time_pass) {
        this.time_pass = time_pass;
    }

    public String  getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
