package hcmute.nhom16.busmap.model;

import hcmute.nhom16.busmap.Support;
import hcmute.nhom16.busmap.result.MoveType;

public class RouteGuideAdapter extends RouteGuide {
    public RouteGuideAdapter(ResultRoute resultRoute) {
        super("Tuyến số " + resultRoute.getRoute().getRouteNumId(), resultRoute.getRoute().getName(),
                MoveType.BUS, resultRoute.getTimePass(), resultRoute.getBusDistance(), resultRoute.getRoute().getMoney());
    }
    public RouteGuideAdapter(Address address, Address to) {
        super(address.getAddress(), "", MoveType.WALK,
                Support.calculateTimePass(Support.calculateDistance(address, to), 100),
                Support.toKilometerString(Support.calculateDistance(address, to)), "");
    }
}
