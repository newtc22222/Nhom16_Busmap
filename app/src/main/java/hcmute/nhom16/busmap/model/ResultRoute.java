package hcmute.nhom16.busmap.model;

import java.io.Serializable;

import hcmute.nhom16.busmap.Support;

public class ResultRoute implements Serializable {
    private int order_start, order_end;
    private Address address_start, address_end;
    private Route route;

    public ResultRoute(Route route, int order_start, int order_end, Address address_start, Address address_end) {
        this.order_start = order_start;
        this.order_end = order_end;
        this.address_start = address_start;
        this.address_end = address_end;
        this.route = route;
    }

    public int getTimePass() {
        return Support.calculateTimePass(
                Support.calculateDistance(address_start,
                        route.getStart_bus_stop().getAddress()), 400);
    }

    public String getWalkDistance() {
        String m = Support.toMeterString(Support.calculateDistance(address_start, route.getStart_bus_stop().getAddress())
                + Support.calculateDistance(address_end, route.getEnd_bus_stop().getAddress()));
        return m;
    }

    public String getBusDistance() {
        String m = Support.toKilometerString(
                Support.calculateDistance(route.getEnd_bus_stop().getAddress(), route.getStart_bus_stop().getAddress()));
        return m;
    }

    public int getOrder_start() {
        return order_start;
    }

    public void setOrder_start(int order_start) {
        this.order_start = order_start;
    }

    public int getOrder_end() {
        return order_end;
    }

    public void setOrder_end(int order_end) {
        this.order_end = order_end;
    }

    public Address getAddress_start() {
        return address_start;
    }

    public void setAddress_start(Address address_start) {
        this.address_start = address_start;
    }

    public Address getAddress_end() {
        return address_end;
    }

    public void setAddress_end(Address address_end) {
        this.address_end = address_end;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
