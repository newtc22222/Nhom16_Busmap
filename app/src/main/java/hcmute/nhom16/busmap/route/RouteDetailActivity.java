package hcmute.nhom16.busmap.route;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import hcmute.nhom16.busmap.OnBusStopListener;
import hcmute.nhom16.busmap.R;
import hcmute.nhom16.busmap.Support;
import hcmute.nhom16.busmap.data.StationDAO;
import hcmute.nhom16.busmap.model.BusStop;
import hcmute.nhom16.busmap.model.Route;
import hcmute.nhom16.busmap.model.Station;

public class RouteDetailActivity extends AppCompatActivity
        implements OnMapReadyCallback, OnBusStopListener {
    ViewPager2 vp2_detail_route;
    Route route;
    TextView tv_id, tv_name;
    GoogleMap map;
    Bitmap icon_big, icon_small;
    List<BusStop> busStops;
    List<LocalTime> timeLines;
    List<MarkerOptions> markerOptions;
    LatLng[] latLngs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_detail);
        Intent intent = getIntent();
        route = (Route) intent.getSerializableExtra("route");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        String title = getString(R.string.route_id_label) + " " + route.getId();
        getSupportActionBar().setTitle(title);

        initMap();
        initUI();
        initListener();
    }

    private void initMap() {

        busStops = getAllBusStopFromRoute();
        timeLines = getAllBusStopTimeLinesFromRoute();
        Drawable ic = getDrawable(R.drawable.ic_station_small);
        int width = ic.getIntrinsicWidth();
        int height = ic.getIntrinsicHeight();
        icon_small = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        ic.setBounds(0, 0, width, height);
        ic.draw(new Canvas(icon_small));

        ic = getDrawable(R.drawable.ic_station_big);
        width = ic.getIntrinsicWidth();
        height = ic.getIntrinsicHeight();
        icon_big = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        ic.setBounds(0, 0, width, height);
        ic.draw(new Canvas(icon_big));

        markerOptions = new ArrayList<>();
        latLngs = new LatLng[busStops.size()];
        int i = 0;

        for (BusStop busStop : busStops) {
            markerOptions.add(new MarkerOptions()
                    .position(new LatLng(busStop.getStation().getAddress().getLat(),
                            busStop.getStation().getAddress().getLng()))
                    .icon(BitmapDescriptorFactory.fromBitmap(icon_big)));
            latLngs[i++] = new LatLng(busStop.getStation().getAddress().getLat(),
                    busStop.getStation().getAddress().getLng());
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fm_map);
        mapFragment.getMapAsync(this);
    }

    private void initListener() {
    }

    private void initUI() {
        tv_id = findViewById(R.id.tv_id);
        tv_id.setText(String.valueOf(route.getId()));
        tv_name = findViewById(R.id.tv_name);
        tv_name.setText(String.valueOf(route.getName()));

        vp2_detail_route = findViewById(R.id.vp2_detail_route);
        RouteDetailStateAdapter adapter = new RouteDetailStateAdapter(this,
                route, busStops, timeLines, this);
        vp2_detail_route.setAdapter(adapter);

        new TabLayoutMediator(findViewById(R.id.tab_layout), vp2_detail_route, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText(R.string.route_tab_bus_stop);
                        break;
                    case 1:
                        tab.setText(R.string.information_text);
                        break;
                }
            }
        }).attach();
    }

    @Override
    public void setOnBusStopClickListener(int position) {
//        Cập nhật icon marker
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngs[position], 15));
    }

    private List<BusStop> getAllBusStopFromRoute() {
        return Support.getAllBusStopFromRouteId(this, route.getId());
    }

    private List<LocalTime> getAllBusStopTimeLinesFromRoute() {
        return Support.getAllBusStopTimeLinesFromRoute(this, route);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        for (MarkerOptions markerOptions : markerOptions) {
            googleMap.addMarker(markerOptions);
        }
        Polyline polyline = googleMap.addPolyline(new PolylineOptions()
                .clickable(false)
                .add(latLngs));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngs[0], 15));
    }
}