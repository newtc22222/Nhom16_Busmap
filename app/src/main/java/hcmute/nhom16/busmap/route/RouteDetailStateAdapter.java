package hcmute.nhom16.busmap.route;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.time.LocalTime;
import java.util.List;

import hcmute.nhom16.busmap.listener.OnBusStopListener;
import hcmute.nhom16.busmap.bus_stop.BusStopListFragment;
import hcmute.nhom16.busmap.entities.BusStop;
import hcmute.nhom16.busmap.entities.Route;

//Route detail state là adapter có 2 fragment là list bus stop và route info
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
//                Với bus stop list gồm 2 list là bus stop và timeline
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
