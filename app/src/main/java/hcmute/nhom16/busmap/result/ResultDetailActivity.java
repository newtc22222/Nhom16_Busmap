package hcmute.nhom16.busmap.result;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import hcmute.nhom16.busmap.R;
import hcmute.nhom16.busmap.Support;
import hcmute.nhom16.busmap.model.Address;
import hcmute.nhom16.busmap.model.BusStopGuide;
import hcmute.nhom16.busmap.model.Result;
import hcmute.nhom16.busmap.model.RouteGuide;
import hcmute.nhom16.busmap.route.RouteIconAdapter;

public class ResultDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    ViewPager2 vp2_detail_route;
    Result result;
    Address from, to;
    RecyclerView rv_routes_icon;
    GoogleMap map;
    List<BusStopGuide> busStopGuides;
    List<RouteGuide> routeGuides;
    List<MarkerOptions> markerOptions;
    LatLng[] latLngs;
    Bitmap icon_big, icon_small;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(R.string.moving_guide);

        getResultRoute();
        initMap();
        initUI();
        initListener();
    }

    private void getResultRoute() {
        Intent intent = getIntent();
        result = (Result) intent.getSerializableExtra("result");
        from = (Address) intent.getSerializableExtra("from");
        to = (Address) intent.getSerializableExtra("to");
    }

    private void initListener() {
    }

    private void initMap() {

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

        busStopGuides = getBusStopGuides();
        routeGuides = getRouteGuides();
        markerOptions = new ArrayList<>();
        latLngs = new LatLng[busStopGuides.size()];
        int i = 0;

        for (BusStopGuide busStopGuide : busStopGuides) {
            markerOptions.add(new MarkerOptions().position(
                    new LatLng(busStopGuide.getAddress().getLat(),
                            busStopGuide.getAddress().getLng()))
                    .icon(BitmapDescriptorFactory.fromBitmap(icon_big)));
            latLngs[i++] = new LatLng(busStopGuide.getAddress().getLat(),
                    busStopGuide.getAddress().getLng());
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fm_map);
        mapFragment.getMapAsync(this);
    }

    private void initUI() {
        rv_routes_icon = findViewById(R.id.rv_routes_icon);
        RouteIconAdapter adapter = new RouteIconAdapter(this, result.getResult_routes());
        rv_routes_icon.setAdapter(adapter);
        rv_routes_icon.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        vp2_detail_route = findViewById(R.id.vp2_detail_route);
        ResultStateAdapter stateAdapter = new ResultStateAdapter(this, routeGuides, busStopGuides);
        vp2_detail_route.setAdapter(stateAdapter);

        new TabLayoutMediator(findViewById(R.id.tab_layout), vp2_detail_route, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText(R.string.guide_detail);
                        break;
                    case 1:
                        tab.setText(R.string.pass_bus_stop);
                        break;
                }
            }
        }).attach();
    }

    private List<RouteGuide> getRouteGuides() {
        return Support.getRouteGuides(this, result, from, to);
    }

    private List<BusStopGuide> getBusStopGuides() {
        return Support.getBusStopGuides(this, result, from, to);
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
            map.addMarker(markerOptions);
        }
        map.addPolyline(new PolylineOptions().add(latLngs).clickable(false));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngs[0], 15));
    }
}