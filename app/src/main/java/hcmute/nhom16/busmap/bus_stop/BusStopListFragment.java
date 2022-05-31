package hcmute.nhom16.busmap.bus_stop;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import hcmute.nhom16.busmap.R;
import hcmute.nhom16.busmap.model.BusStop;

public class BusStopListFragment extends Fragment {
    private boolean order;
    private List<BusStop> bus_stops;
    private List<LocalTime> time_lines;

    public BusStopListFragment() {
        order = false;
    }

    public BusStopListFragment(List<BusStop> bus_stops, boolean order) {
        this.bus_stops = bus_stops;
        this.order = order;
    }

    public BusStopListFragment(List<BusStop> bus_stops, List<LocalTime> time_lines, boolean order) {
        this.bus_stops = bus_stops;
        this.time_lines = time_lines;
        this.order = order;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2_linear, container, false);
        if (bus_stops != null && bus_stops.size() > 0) {

            RecyclerView rv_linear_2 = view.findViewById(R.id.rv_linear_2);
            BusStopAdapter adapter_2 = new BusStopAdapter(getContext(), bus_stops, order);
            rv_linear_2.setAdapter(adapter_2);
            rv_linear_2.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        if (time_lines != null && time_lines.size() > 0) {
            RecyclerView rv_linear_1 = view.findViewById(R.id.rv_linear_1);
            BusStopTimeLineAdapter adapter_1 = new BusStopTimeLineAdapter(getContext(), time_lines);
            rv_linear_1.setAdapter(adapter_1);
            rv_linear_1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        }
        return view;
    }
}
