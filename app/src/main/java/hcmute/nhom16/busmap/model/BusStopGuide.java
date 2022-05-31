package hcmute.nhom16.busmap.model;

import hcmute.nhom16.busmap.result.MoveType;

public abstract class BusStopGuide {
    private String route = "";
//    Tên của bus_stop với tên của address khác nhau
    private String name = "";
    private MoveType type;
    private Address address;

    public BusStopGuide(String route, String name, MoveType type, Address address) {
        this.route = route;
        this.name = name;
        this.type = type;
        this.address = address;
    }

    public MoveType getType() {
        return type;
    }

    public void setType(MoveType type) {
        this.type = type;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
