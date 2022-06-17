package hcmute.nhom16.busmap.result;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import hcmute.nhom16.busmap.listener.OnBusStopListener;
import hcmute.nhom16.busmap.listener.OnRouteListener;
import hcmute.nhom16.busmap.component.BusStopGuide;
import hcmute.nhom16.busmap.component.RouteGuide;
//ResultStateAdapter là adapter cho viewpager2
//2 tab là routes guide và bus stops guide
public class ResultStateAdapter extends FragmentStateAdapter {
    List<RouteGuide> routeGuides;
    List<BusStopGuide> busStopGuides;
    OnBusStopListener busStopListener;
    OnRouteListener routeListener;

    public ResultStateAdapter(@NonNull FragmentActivity fragmentActivity,
                              List<RouteGuide> routeGuides, List<BusStopGuide> busStopGuides,
                              OnBusStopListener busStopListener, OnRouteListener routeListener) {
        super(fragmentActivity);
        this.routeGuides = routeGuides;
        this.busStopGuides = busStopGuides;
        this.busStopListener = busStopListener;
        this.routeListener = routeListener;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ResultRouteGuideFragment(routeGuides, routeListener);
            case 1:
                return new ResultBusStopsGuideFragment(busStopGuides, busStopListener);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
