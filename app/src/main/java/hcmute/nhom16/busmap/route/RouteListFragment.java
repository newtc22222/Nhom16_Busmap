package hcmute.nhom16.busmap.route;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hcmute.nhom16.busmap.R;
import hcmute.nhom16.busmap.model.Route;

public class RouteListFragment extends Fragment {
    List<Route> routes;

    public RouteListFragment() {

    }

    public RouteListFragment(List<Route> routes) {
        this.routes = routes;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_linear, container, false);
        if (routes != null && routes.size() > 0) {
            RecyclerView rv_linear = view.findViewById(R.id.rv_linear);
            RouteAdapter adapter = new RouteAdapter(getContext(), routes);
            rv_linear.setAdapter(adapter);
            rv_linear.setLayoutManager(new LinearLayoutManager(getContext()));
        };
        return view;
    }
}
