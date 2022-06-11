package hcmute.nhom16.busmap.model;

import java.io.Serializable;

import hcmute.nhom16.busmap.Support;

public class ResultRoute implements Serializable {
    private Route route;
    private BusStop bus_stop_start, bus_stop_end;
    private final int distance;

    public ResultRoute(Route route, BusStop bus_stop_start, BusStop bus_stop_end) {
        this.route = route;
        this.bus_stop_start = bus_stop_start;
        this.bus_stop_end = bus_stop_end;
        distance = Support.calculateDistance(bus_stop_start.getStation().getAddress(),
                bus_stop_end.getStation().getAddress());
    }

    public int getDistance() {
        return distance;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public BusStop getBusStop_start() {
        return bus_stop_start;
    }

    public void setBusStop_start(BusStop bus_stop_start) {
        this.bus_stop_start = bus_stop_start;
    }

    public BusStop getBusStop_end() {
        return bus_stop_end;
    }

    public void setBusStop_end(BusStop bus_stop_end) {
        this.bus_stop_end = bus_stop_end;
    }
}
