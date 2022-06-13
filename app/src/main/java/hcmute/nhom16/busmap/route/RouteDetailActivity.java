package hcmute.nhom16.busmap.route;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hcmute.nhom16.busmap.data.BusStopDAO;
import hcmute.nhom16.busmap.listener.OnBusStopListener;
import hcmute.nhom16.busmap.R;
import hcmute.nhom16.busmap.Support;
import hcmute.nhom16.busmap.model.BusStop;
import hcmute.nhom16.busmap.model.Route;
//Ở Route detail, có 3 thứ chính là map, bus stops, route info
public class RouteDetailActivity extends AppCompatActivity
        implements OnMapReadyCallback, OnBusStopListener {
    ViewPager2 vp2_detail_route;
    Route route;
    TextView tv_id, tv_name;
    GoogleMap map;
    Bitmap ic_big, ic_small, ic_focus;
    List<BusStop> busStops;
    List<LocalTime> timeLines;
    List<Marker> markers;
    LatLng[] latLngs;
    int pre_position = 0;

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

        String title = getString(R.string.route_number) + " " + route.getId();
        getSupportActionBar().setTitle(title);

//        Khởi tại map
        initMap();
//        ánh xạ view
        initUI();
    }

    private void initMap() {
//      tạo ra các icon cho marker gồm ic_small, ic_big và ic_focus
        busStops = getAllBusStopFromRoute();
        timeLines = getAllBusStopTimeLinesFromRoute();
        Drawable ic = getDrawable(R.drawable.ic_station_small);
        int width = ic.getIntrinsicWidth();
        int height = ic.getIntrinsicHeight();
        ic_small = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        ic.setBounds(0, 0, width, height);
        ic.draw(new Canvas(ic_small));

        ic = getDrawable(R.drawable.ic_station_big);
        width = ic.getIntrinsicWidth();
        height = ic.getIntrinsicHeight();
        ic_big = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        ic.setBounds(0, 0, width, height);
        ic.draw(new Canvas(ic_big));

        ic = getDrawable(R.drawable.ic_station_focus);
        width = ic.getIntrinsicWidth();
        height = ic.getIntrinsicHeight();
        ic_focus = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        ic.setBounds(0, 0, width, height);
        ic.draw(new Canvas(ic_focus));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fm_map);
        mapFragment.getMapAsync(this);
    }

    private void initUI() {
        tv_id = findViewById(R.id.tv_id);
        tv_id.setText(String.valueOf(route.getId()));
        tv_name = findViewById(R.id.tv_name);
        tv_name.setText(String.valueOf(route.getName(33)));

//        2 tab là 2 fragment bus tops và route info
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
//    Hàm này được gọi từ bên trong adapter của bus stop fragment
//    khi click vào item trong adapter, vì nó có giữ listener nên có thể gọi ngược ra lại
//    Khi được gọi, vị trí bus stop sẽ được đổi màu thể hiện được focus
    @Override
    public void setOnBusStopClickListener(int position) {
//        Cập nhật icon marker
        markers.get(pre_position).setIcon(BitmapDescriptorFactory.fromBitmap(ic_big));
        markers.get(position).setIcon(BitmapDescriptorFactory.fromBitmap(ic_focus));
        pre_position = position;
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngs[position], 15));
    }

//    Lấy tất cả bus stop từ route id
    private List<BusStop> getAllBusStopFromRoute() {
        return BusStopDAO.getBusStopsByRouteId(this, route.getId());
    }

//    Lấy tất cả time line từ route
    private List<LocalTime> getAllBusStopTimeLinesFromRoute() {
        List<LocalTime> time_lines = new ArrayList<>();
        String s = route.getOperation_time().split(" -")[0];
        time_lines.add(Support.stringToLocalTime(s, "HH:mm"));
        for (int i = 1; i < route.getPer_day(); i++) {
            time_lines.add(time_lines.get(i - 1).plusMinutes(route.getRepeat_time()));
        }
        return time_lines;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        markers = new ArrayList<>();
        latLngs = new LatLng[busStops.size()];
        int i = 0;

//        Khi bản đồ được load, ta vẽ các markers ứng với vị trí của các bus stop
        for (BusStop busStop : busStops) {
            latLngs[i++] = new LatLng(busStop.getStation().getAddress().getLat(),
                    busStop.getStation().getAddress().getLng());
            markers.add(map.addMarker(new MarkerOptions()
                    .position(new LatLng(latLngs[i - 1].latitude,
                            latLngs[i - 1].longitude))
                    .icon(BitmapDescriptorFactory.fromBitmap(ic_big))));
        }

        markers.get(pre_position).setIcon(BitmapDescriptorFactory.fromBitmap(ic_focus));
//      Vẽ line đi qua các bus stop này
        googleMap.addPolyline(new PolylineOptions()
                .clickable(false)
                .add(latLngs)
                .color(getColor(R.color.orange)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngs[0], 15));
    }
}