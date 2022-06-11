package hcmute.nhom16.busmap.result;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import hcmute.nhom16.busmap.model.BusStopGuide;
import hcmute.nhom16.busmap.model.RouteGuide;

public class ResultStateAdapter extends FragmentStateAdapter {
    List<RouteGuide> routeGuides;
    List<BusStopGuide> busStopGuides;

    public ResultStateAdapter(@NonNull FragmentActivity fragmentActivity,
                              List<RouteGuide> routeGuides, List<BusStopGuide> busStopGuides) {
        super(fragmentActivity);
        this.routeGuides = routeGuides;
        this.busStopGuides = busStopGuides;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ResultRouteGuideFragment(routeGuides);
            case 1:
                return new ResultBusStopsGuideFragment(busStopGuides);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
