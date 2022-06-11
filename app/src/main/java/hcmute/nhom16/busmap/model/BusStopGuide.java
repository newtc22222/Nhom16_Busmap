package hcmute.nhom16.busmap.model;

import hcmute.nhom16.busmap.result.MoveType;

public class BusStopGuide {
    private String route_id = "";
//    Tên của bus_stop với tên của address khác nhau
    private String name = "";
    private MoveType type;
    private Address address;

    public BusStopGuide(String route_id, String name, MoveType type, Address address) {
        this.route_id = route_id;
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

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
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
