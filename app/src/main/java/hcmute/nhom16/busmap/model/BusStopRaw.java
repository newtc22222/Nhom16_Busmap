package hcmute.nhom16.busmap.model;

public class BusStopRaw {
    private String route_id;
    private int station_id;
    private int order;

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public int getStation_id() {
        return station_id;
    }

    public void setStation_id(int station_id) {
        this.station_id = station_id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public BusStopRaw(String route_id, int station_id, int order) {
        this.route_id = route_id;
        this.station_id = station_id;
        this.order = order;
    }
}
