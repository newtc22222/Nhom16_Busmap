package hcmute.nhom16.busmap.saved;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

import hcmute.nhom16.busmap.Support;
import hcmute.nhom16.busmap.bus_stop.BusStopListFragment;
import hcmute.nhom16.busmap.model.BusStop;
import hcmute.nhom16.busmap.model.Route;
import hcmute.nhom16.busmap.route.RouteListFragment;

public class SavedStateAdapter extends FragmentStateAdapter {
    public SavedStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new RouteListFragment(getSavedRoutes());
            case 1:
                return new BusStopListFragment(getSavedBusStops(), false);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    private List<Route> getSavedRoutes() {
        return Support.getSavedRoutes();
    }

    private List<BusStop> getSavedBusStops() {
        return Support.getSavedBusStops();
    }
}
