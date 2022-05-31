package hcmute.nhom16.busmap.result;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import hcmute.nhom16.busmap.Support;
import hcmute.nhom16.busmap.model.Address;
import hcmute.nhom16.busmap.model.BusStopGuide;
import hcmute.nhom16.busmap.model.ResultRoute;
import hcmute.nhom16.busmap.model.RouteGuide;

public class ResultStateAdapter extends FragmentStateAdapter {
    ResultRoute result_route;
    Address from, to;

    public ResultStateAdapter(@NonNull FragmentActivity fragmentActivity,
                              ResultRoute result_route, Address from, Address to) {
        super(fragmentActivity);
        this.result_route = result_route;
        this.from = from;
        this.to = to;
    }



    private List<RouteGuide> getRouteGuides() {
        return Support.getRouteGuides(result_route, from, to);
    }

    private List<BusStopGuide> getBusStopGuides() {
        return Support.getBusStopGuides(result_route, from, to);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ResultRouteGuideFragment(getRouteGuides());
            case 1:
                return new ResultBusStopsGuideFragment(getBusStopGuides());
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
