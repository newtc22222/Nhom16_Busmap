package hcmute.nhom16.busmap.model;

import java.io.Serializable;
import java.time.LocalTime;

public class BusStop implements Serializable {
    private int id;
    private String name;
    private int route_id;
    private int order;
    private Address address;
    private int time_pass;

    public BusStop(int id, String name, int route_id, int order, Address address, int time_pass) {
        this.id = id;
        this.name = name;
        this.route_id = route_id;
        this.order = order;
        this.time_pass = time_pass;
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoute_id() {
        return route_id;
    }

    public void setRoute_id(int route_id) {
        this.route_id = route_id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getTime_pass() {
        return time_pass;
    }

    public String getTimePassString() {
        return (time_pass == 0 ? 1 : time_pass) + " phút";
    }

    public void setTime_pass(int time_pass) {
        this.time_pass = time_pass;
    }
}
