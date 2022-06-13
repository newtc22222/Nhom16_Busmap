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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hcmute.nhom16.busmap.R;
import hcmute.nhom16.busmap.config.MoveType;
import hcmute.nhom16.busmap.data.BusStopDAO;
import hcmute.nhom16.busmap.listener.OnBusStopListener;
import hcmute.nhom16.busmap.listener.OnRouteListener;
import hcmute.nhom16.busmap.model.Address;
import hcmute.nhom16.busmap.model.BusStop;
import hcmute.nhom16.busmap.model.BusStopGuide;
import hcmute.nhom16.busmap.model.Result;
import hcmute.nhom16.busmap.model.ResultRoute;
import hcmute.nhom16.busmap.model.RouteGuide;
import hcmute.nhom16.busmap.route.RouteIconAdapter;

public class ResultDetailActivity extends AppCompatActivity
        implements OnMapReadyCallback, OnRouteListener, OnBusStopListener {
//    Các view để ánh xạ
    ViewPager2 vp2_detail_route;
    RecyclerView rv_routes_icon;
    List<BusStopGuide> busStopGuides;
    List<RouteGuide> routeGuides;
    Result result;
    Address from, to;
//  các object cần thiết cho map
    GoogleMap map;
    List<Marker> markers;
    Bitmap ic_big, ic_focus, ic_walk, ic_walk_focus, ic_dest_focus, ic_dest;
    List<Polyline> polylines;
    int pre_marker = 0, pre_polyline = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(R.string.moving_guide);

        getData();

        initMap();
        initUI();
    }

// get result route sẽ lấy data từ activity trước truyền tới gồm result và điểm đi và  điểm đến
    private void getData() {
        Intent intent = getIntent();
        result = (Result) intent.getSerializableExtra("result");
        from = (Address) intent.getSerializableExtra("from");
        to = (Address) intent.getSerializableExtra("to");
    }

    private void initMap() {

        Drawable ic = getDrawable(R.drawable.ic_station_big);
        int width = ic.getIntrinsicWidth();
        int height = ic.getIntrinsicHeight();
        ic_big = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        ic.setBounds(0, 0, width, height);
        ic.draw(new Canvas(ic_big));

        ic = getDrawable(R.drawable.ic_walk_map);
        width = ic.getIntrinsicWidth();
        height = ic.getIntrinsicHeight();
        ic_walk = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        ic.setBounds(0, 0, width, height);
        ic.draw(new Canvas(ic_walk));

        ic = getDrawable(R.drawable.ic_walk_map_focus);
        width = ic.getIntrinsicWidth();
        height = ic.getIntrinsicHeight();
        ic_walk_focus = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        ic.setBounds(0, 0, width, height);
        ic.draw(new Canvas(ic_walk_focus));

        ic = getDrawable(R.drawable.ic_destination);
        width = ic.getIntrinsicWidth();
        height = ic.getIntrinsicHeight();
        ic_dest = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        ic.setBounds(0, 0, width, height);
        ic.draw(new Canvas(ic_dest));

        ic = getDrawable(R.drawable.ic_destination_focus);
        width = ic.getIntrinsicWidth();
        height = ic.getIntrinsicHeight();
        ic_dest_focus = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        ic.setBounds(0, 0, width, height);
        ic.draw(new Canvas(ic_dest_focus));

        ic = getDrawable(R.drawable.ic_station_focus);
        width = ic.getIntrinsicWidth();
        height = ic.getIntrinsicHeight();
        ic_focus = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        ic.setBounds(0, 0, width, height);
        ic.draw(new Canvas(ic_focus));

        busStopGuides = getBusStopGuides();
        routeGuides = getRouteGuides();

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
        ResultStateAdapter stateAdapter = new ResultStateAdapter(this, routeGuides, busStopGuides, this, this);
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

//    từ result tính toán được các routes guide cho người dùng
//    xác định là từ điểm đi đến trạm đầu tiêu là một route
//    từ trạm xuống đến điểm kết thúc cũng là một route
//    Ở giữa lộ trình đi có thể ngồi một hoặc nhiều tuyến xe nữa
    private List<RouteGuide> getRouteGuides() {
        List<RouteGuide> routeGuides = new ArrayList<>();

//        Route từ điểm bắt đầu đến trạm gần nhất
        routeGuides.add(new RouteGuide("Đi đến " + result.getResult_routes().get(0).getBusStop_start().getStation().getName(),
                "Xuất phát từ " + from.getAddress(),
                MoveType.WALK, result.getWalk_distance_start(), ""));

        String preAddress = null;

//        Lặp các result route để thêm các hướng dẫn về các tuyến xe sẽ lên và xuống
        for (ResultRoute resultRoute : result.getResult_routes()) {
            if (preAddress != null) {
                routeGuides.add(new RouteGuide( preAddress + " - " + resultRoute.getBusStop_start().getStation().getName(),
                        "Đi xuống " + preAddress + " - " + " Đi lên " + resultRoute.getBusStop_start().getStation().getName(),
                        MoveType.WALK,0, ""));
            }

            preAddress = resultRoute.getBusStop_end().getStation().getName();

            routeGuides.add(new RouteGuide("Đi tuyến " + resultRoute.getRoute().getId() + ": " +
                    resultRoute.getRoute().getName(), resultRoute.getBusStop_start().getStation().getName() + " - " +
                    resultRoute.getBusStop_end().getStation().getName(), MoveType.BUS, result.getBus_distance(),
                    resultRoute.getRoute().getMoney()));
        }

//        Route từ trạm dừng chân đến điểm đến
        routeGuides.add(new RouteGuide("Đi xuống " + result.getResult_routes().get(result.getResult_routes().size() - 1).getBusStop_end().getStation().getName(),
                "Đi đến " + to.getAddress(), MoveType.WALK,
                result.getWalk_distance_end(), ""));

        return routeGuides;
    }

//    tương tự routes guide thì bus stop guide cũng tương tự như vậy
//    điểm bắt đầu cũng được coi như một trạm cho nên di chuyển từ điểm bắt đầu
//    đến trạm gần nhất cũng coi như là một hành trình từ trạm này qua trạm khác

//    trạm xuống đến điểm đến cũng sẽ được coi như hành trình từ trạm này qua trạm khác
    private List<BusStopGuide> getBusStopGuides() {
        List<BusStopGuide> busStopGuides = new ArrayList<>();

        busStopGuides.add(new BusStopGuide("", from.getAddress(), MoveType.WALK, from));

        for (ResultRoute resultRoute : result.getResult_routes()) {
            int s = resultRoute.getBusStop_start().getOrder(), e = resultRoute.getBusStop_end().getOrder();
            for (BusStop busStop : BusStopDAO.getBusStopsFromRouteIdAndOrder(this, resultRoute.getRoute().getId(), s, e)) {

                busStopGuides.add(new BusStopGuide(busStop.getRoute_id(), busStop.getStation().getName(),
                        MoveType.BUS, busStop.getStation().getAddress()));

            }
        }

        busStopGuides.add(new BusStopGuide("", to.getAddress(), MoveType.WALK, to));

        return busStopGuides;
    }

    @Override
    public void setOnBusStopClickListener(int position) {
        int len = markers.size();
        if (position == 0) {
            markers.get(position).setIcon(BitmapDescriptorFactory.fromBitmap(ic_walk_focus));
        }
        if (position == len - 1) {
            markers.get(position).setIcon(BitmapDescriptorFactory.fromBitmap(ic_dest_focus));
        }
        if (position > 0 && position < len - 1) {
            markers.get(position).setIcon(BitmapDescriptorFactory.fromBitmap(ic_focus));
        }
        if (pre_marker == 0) {
            markers.get(pre_marker).setIcon(BitmapDescriptorFactory.fromBitmap(ic_walk));
        }
        if (pre_marker == len - 1) {
            markers.get(pre_marker).setIcon(BitmapDescriptorFactory.fromBitmap(ic_dest));
        }
        if (pre_marker > 0 && pre_marker < len - 1) {
            markers.get(pre_marker).setIcon(BitmapDescriptorFactory.fromBitmap(ic_big));
        }
        pre_marker = position;
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(markers.get(position).getPosition(), 15));
    }

    @Override
    public void setOnRouteClickListener(int position) {
        if (pre_polyline != position) {
            if (position == 0 || position == polylines.size() - 1) {
                polylines.get(pre_polyline).setColor(getColor(R.color.primary_600));
            } else {
                polylines.get(pre_polyline).setColor(getColor(R.color.orange));
            }
            polylines.get(position).setColor(getColor(R.color.red));
        }
        pre_polyline = position;
    }

//    Từ các routes guide và bus stops guide có được ta sẽ vẽ các markers và polylines
//    để biểu thị đường đi
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        markers = new ArrayList<>();
        int len = busStopGuides.size();
        polylines = new ArrayList<>();
//        l1, l2 lần lượt là 2 điểm đầu của hành trình
//        l1 là điểm bắt đầu
        LatLng l1 = new LatLng(busStopGuides.get(0).getAddress().getLat(),
                busStopGuides.get(0).getAddress().getLng());
//        l2 là trạm đầu tiên
        LatLng l2 = new LatLng(busStopGuides.get(1).getAddress().getLat(),
                busStopGuides.get(1).getAddress().getLng());

//        vẽ polyline từ điểm bắt đầu đến trạm đầu tiên tức là từ l1 đến l2
        polylines.add(map.addPolyline(new PolylineOptions()
                .add(l1, l2).clickable(false).color(getColor(R.color.red))
                .pattern(Arrays.asList(new Dash(30), new Gap(20)))));
//      vẽ marker cho điểm bắt đầu
        markers.add(map.addMarker(new MarkerOptions().position(l1)
                        .zIndex(1f)
                .icon(BitmapDescriptorFactory.fromBitmap(ic_walk_focus))));

//      duyệt tất cả các busStopGuide để đánh marker cho toàn bộ các trạm

//        Còn đây là phần quan trọng nhất. Về các route thì nếu mà chúng ta có nhiều route thì
//        cần phải vẽ các polyline cho các route đó. Nếu 2 route gối đầu nhau tức là
//        bus stop kết thúc của route này đồng thời là bus stop bắt đầu của route kia thì có thể vẽ 1 polyline
//        Nhưng nếu bus stop kết thúc của route trước không phải là bus stop bắt đầu của route kia
//        như vậy thì ta phải vẽ line cho đoạn đường đi giữa 2 bus stop đó

        String route_id = busStopGuides.get(1).getRoute_id();
        List<LatLng> latLngs = new ArrayList<>();
        for (BusStopGuide busStopGuide : busStopGuides.subList(1, len - 1)) {
            if (!route_id.equals(busStopGuide.getRoute_id())) {
                LatLng[] lls = new LatLng[latLngs.size()];
                latLngs.toArray(lls);

//                Vẽ line cho route phía trước
                polylines.add(map.addPolyline(new PolylineOptions()
                        .add(lls).clickable(false).color(getColor(R.color.orange))));

//                vẽ line cho 2 bus stop đó, nếu 2 bus stop đó là một thì lien sẽ không được nhìn thấy
                map.addPolyline(new PolylineOptions()
                        .add(latLngs.get(latLngs.size() - 1),
                                new LatLng(busStopGuide.getAddress().getLat(),
                                        busStopGuide.getAddress().getLng()))
                        .clickable(false).color(getColor(R.color.primary_600))
                        .pattern(Arrays.asList(new Dash(30), new Gap(20))));
                latLngs = new ArrayList<>();
                route_id = busStopGuide.getRoute_id();
            }

//            Chuyển tọa độ của bus stop và latLngs
            latLngs.add(new LatLng(busStopGuide.getAddress().getLat(),
                    busStopGuide.getAddress().getLng()));
//              Từ latLngs có thể đánh marker cho các bus stop
            markers.add(map.addMarker(new MarkerOptions().position(
                            latLngs.get(latLngs.size() - 1))
                    .icon(BitmapDescriptorFactory.fromBitmap(ic_big))));
        }
        LatLng[] lls = new LatLng[latLngs.size()];
        latLngs.toArray(lls);
//        Vẽ line cho route cuối cùng
        polylines.add(map.addPolyline(new PolylineOptions()
                .add(lls).clickable(false).color(getColor(R.color.orange))));

//        l1, l2 lần lượt là 2 điểm cuối cùng của hành trình
//        l1 là trạm cuối cùng
        l1 = new LatLng(busStopGuides.get(len - 2).getAddress().getLat(),
                busStopGuides.get(len - 2).getAddress().getLng());
//        l2 là đích đến
        l2 = new LatLng(busStopGuides.get(len - 1).getAddress().getLat(),
                busStopGuides.get(len - 1).getAddress().getLng());

//        Vẽ marker là điểm đến với icon là ic_dest

        markers.add(map.addMarker(new MarkerOptions().position(l2)
                        .zIndex(1f)
                .icon(BitmapDescriptorFactory.fromBitmap(ic_dest))));

//        vẽ polyline từ trạm cuối cùng đến điểm đến là từ l1 đến l2
        polylines.add(map.addPolyline(new PolylineOptions()
                .add(l1, l2).clickable(false).color(getColor(R.color.primary_600))
                .pattern(Arrays.asList(new Dash(30), new Gap(20)))));

//        Di chuyển camera đến điểm bắt đầu
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(busStopGuides.get(0).getAddress().getLat(),
                        busStopGuides.get(0).getAddress().getLng()), 15));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}