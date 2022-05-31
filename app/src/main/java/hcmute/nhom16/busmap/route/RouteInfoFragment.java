package hcmute.nhom16.busmap.route;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import hcmute.nhom16.busmap.R;
import hcmute.nhom16.busmap.model.Route;

public class RouteInfoFragment extends Fragment {
    Route route;

    public RouteInfoFragment() {

    }

    public RouteInfoFragment(Route route) {
        this.route = route;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        if (route == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.not_found_layout, container, false);
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.info_route, container, false);
            ((TextView) view.findViewById(R.id.tv_id)).setText(route.getRouteNumId());
            ((TextView) view.findViewById(R.id.tv_name)).setText(route.getName());
            ((TextView) view.findViewById(R.id.tv_operating)).setText(route.getOperatingTime());
            ((TextView) view.findViewById(R.id.tv_price)).setText(route.getMoney());
            ((TextView) view.findViewById(R.id.tv_type)).setText(route.getType());
            ((TextView) view.findViewById(R.id.tv_time_run)).setText(route.getTime_run());
            ((TextView) view.findViewById(R.id.tv_repeat)).setText(route.getRepeat());
            ((TextView) view.findViewById(R.id.tv_amount)).setText(route.getAmountPerDay());
            ((TextView) view.findViewById(R.id.tv_unit)).setText(route.getUnit());
        }
        return view;
    }
}
