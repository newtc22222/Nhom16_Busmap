package hcmute.nhom16.busmap.route;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import hcmute.nhom16.busmap.OnBusStopListener;
import hcmute.nhom16.busmap.Support;
import hcmute.nhom16.busmap.bus_stop.BusStopListFragment;
import hcmute.nhom16.busmap.model.BusStop;
import hcmute.nhom16.busmap.model.Route;

public class RouteDetailStateAdapter extends FragmentStateAdapter {
    Route route;
    List<BusStop> busStops;
    List<LocalTime> localTimes;
    OnBusStopListener listener;
    public RouteDetailStateAdapter(@NonNull FragmentActivity fragmentActivity, Route route
    , List<BusStop> busStops, List<LocalTime> localTimes, OnBusStopListener listener) {
        super(fragmentActivity);
        this.route = route;
        this.busStops = busStops;
        this.localTimes = localTimes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new BusStopListFragment(busStops, localTimes, true, listener);
            case 1:
                return new RouteInfoFragment(route);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
