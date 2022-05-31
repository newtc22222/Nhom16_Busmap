package hcmute.nhom16.busmap.model;

import hcmute.nhom16.busmap.result.MoveType;

public class BusStopGuideAdapter extends BusStopGuide {

    public BusStopGuideAdapter(BusStop busStop) {
        super(String.valueOf(busStop.getRoute_id()), busStop.getName(), MoveType.BUS,
                busStop.getAddress());
    }

    public BusStopGuideAdapter(Address address) {
        super("", address.getAddress(), MoveType.WALK, address);
    }
}
