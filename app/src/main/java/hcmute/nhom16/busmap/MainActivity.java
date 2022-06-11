package hcmute.nhom16.busmap;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import hcmute.nhom16.busmap.data.StationDAO;
import hcmute.nhom16.busmap.feedback.FeedbackActivity;
import hcmute.nhom16.busmap.feedback.InformationGroupActivity;
import hcmute.nhom16.busmap.feedback.RateActivity;
import hcmute.nhom16.busmap.model.Address;
import hcmute.nhom16.busmap.model.Station;
import hcmute.nhom16.busmap.model.User;
import hcmute.nhom16.busmap.model.UserAccount;
import hcmute.nhom16.busmap.saved.SavedActivity;
import hcmute.nhom16.busmap.side.SideAdapter;
import hcmute.nhom16.busmap.model.Side;
import hcmute.nhom16.busmap.notification.NotificationActivity;
import hcmute.nhom16.busmap.route.RouteActivity;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    DrawerLayout drawable_layout;
    NavigationView nv_group;
    Spinner side_spinner;
    LinearLayout ll_logged, ll_not_logged;
    CircleImageView civ_avatar;
    TextView tv_name, tv_email;
    Button btn_info, btn_login, btn_search, btn_register;
    ImageButton ib_search, ib_find, ib_logout;
    Address address;
    User user;
    Bitmap icon_big, icon_small;
    private GoogleMap map;
    List<Station> stations;
    List<MarkerOptions> markerOptions;
    LatLng df = new LatLng(10.85075361772994, 106.77124465290879);
    float zoom = 15, pre_zoom = 15;

    ActivityResultLauncher<Intent> getAddress = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        address = (Address) result.getData().getSerializableExtra("address");
                        showAddress();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = UserAccount.getUser();

        initMap();
        initUI();
        initListener();
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

        stations = StationDAO.getAllStations(this);
        markerOptions = new ArrayList<>();

        for (Station station : stations) {
            markerOptions.add(new MarkerOptions()
                    .position(new LatLng(station.getAddress().getLat(), station.getAddress().getLng()))
                    .icon(BitmapDescriptorFactory.fromBitmap(icon_big)));
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fm_map);
        mapFragment.getMapAsync(this);
    }

    private void initUI() {
        drawable_layout = findViewById(R.id.drawable_layout);

        nv_group = findViewById(R.id.nv_group);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, drawable_layout,
                        toolbar, R.string.open_nav, R.string.close_nav);
        drawable_layout.addDrawerListener(toggle);
        toggle.syncState();

        side_spinner = findViewById(R.id.side_spinner);
        SideAdapter adapter = new SideAdapter(this, getSides());
        side_spinner.setAdapter(adapter);

        btn_search = findViewById(R.id.btn_search);
        ib_search = findViewById(R.id.ib_search);
        ib_find = findViewById(R.id.ib_find);

        View header = nv_group.getHeaderView(0);
        ll_logged = header.findViewById(R.id.ll_logged);
        civ_avatar = header.findViewById(R.id.civ_avatar);
        tv_name = header.findViewById(R.id.tv_name);
        tv_email = header.findViewById(R.id.tv_email);
        btn_info = header.findViewById(R.id.btn_info);
        ll_not_logged = header.findViewById(R.id.ll_not_logged);
        btn_login = header.findViewById(R.id.btn_login);
        ib_logout = header.findViewById(R.id.ib_logout);
        btn_register = header.findViewById(R.id.btn_register);

        if (user == null) {
            ll_not_logged.setVisibility(View.VISIBLE);
        } else {
            ll_logged.setVisibility(View.VISIBLE);
        }

        User user = UserAccount.getUser();
        if (user == null) {
            ll_logged.setVisibility(View.GONE);
            btn_login.setVisibility(View.VISIBLE);
        } else {
            if (user.getImage() == null) {
                civ_avatar.setImageResource(R.drawable.avatar_default);
            } else {
                byte[] image = user.getImage();
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                civ_avatar.setImageBitmap(bitmap);
            }
            tv_name.setText(user.getName());
            tv_email.setText(user.getEmail());

            ll_logged.setVisibility(View.VISIBLE);
            btn_login.setVisibility(View.GONE);
        }
    }

    private void initListener() {
        btn_info.setOnClickListener(v -> {
            Intent intent = new Intent(this, InformationActivity.class);
            startActivity(intent);
        });
        nv_group.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nv_find_address:
                        drawable_layout.closeDrawer(GravityCompat.START);
                        onSearchAddressSelected();
                        break;
                    case R.id.nv_notification:
                        drawable_layout.closeDrawer(GravityCompat.START);
                        onNotificationSelected();
                        break;
                    case R.id.nv_find_bus:
                        drawable_layout.closeDrawer(GravityCompat.START);
                        onFindBusSelected();
                        break;
                    case R.id.nv_search:
                        drawable_layout.closeDrawer(GravityCompat.START);
                        onSearchSelected();
                        break;
                    case R.id.nv_update:
                        drawable_layout.closeDrawer(GravityCompat.START);
                        onUpdateSelected();
                        break;
                    case R.id.nv_feedback:
                        drawable_layout.closeDrawer(GravityCompat.START);
                        onFeedbackSelected();
                        break;
                    case R.id.nv_rate:
                        drawable_layout.closeDrawer(GravityCompat.START);
                        onRateSelected();
                        break;
                    case R.id.nv_information:
                        drawable_layout.closeDrawer(GravityCompat.START);
                        onInformationSelected();
                        break;
                }
                return true;
            }
        });

        btn_search.setOnClickListener(v -> {
            onSearchAddressSelected();
        });

        ib_find.setOnClickListener(v -> {
            onFindBusSelected();
        });

        ib_search.setOnClickListener(v -> {
            onSearchSelected();
        });

        btn_login.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        ib_logout.setOnClickListener(v -> {
            UserAccount.logout();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        btn_register.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    public void onSearchSelected() {
        Intent intent = new Intent(this, RouteActivity.class);
        startActivity(intent);
    }

    public void onFindBusSelected() {
        Intent intent = new Intent(this, FindRoadActivity.class);
        startActivity(intent);
    }

    public void onSearchAddressSelected() {
        Intent intent = new Intent(this, AddressSearchActivity.class);
        getAddress.launch(intent);
    }

    public void onSavedSelected() {
        Intent intent = new Intent(this, SavedActivity.class);
        startActivity(intent);
    }

    private void onNotificationSelected() {
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    private void onUpdateSelected() {
        Intent intent = new Intent(this, UpdateActivity.class);
        startActivity(intent);
    }

    private void onFeedbackSelected() {
        Intent intent = new Intent(this, FeedbackActivity.class);
        startActivity(intent);
    }

    private void onRateSelected() {
        Intent intent = new Intent(this, RateActivity.class);
        startActivity(intent);
    }

    private void onInformationSelected() {
        Intent intent = new Intent(this, InformationGroupActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (drawable_layout.isDrawerOpen(GravityCompat.START)) {
            drawable_layout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_saved, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_saved:
                onSavedSelected();
                break;
        }
        return true;
    }

    private void showAddress() {
        btn_search.setText(address.getAddress());
        df = new LatLng(address.getLat(), address.getLng());
        moveCamera();
    }

    public List<Side> getSides() {
        List<Side> sides = new ArrayList<>();
        sides.add(new Side("TP Hồ Chí Minh", R.drawable.vn));
        sides.add(new Side("Đà Nẵng", R.drawable.vn));
        sides.add(new Side("Thừa Thiên Huế", R.drawable.vn));
        sides.add(new Side("Hà Nội", R.drawable.vn));
        return sides;
    }

    public void moveCamera() {
        pre_zoom = 15;
        zoom = 15;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(df, zoom));
    }

    public void drawDefault() {
        for (MarkerOptions markerOptions : markerOptions) {
            map.addMarker(markerOptions);
        }
        map.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                zoom = map.getCameraPosition().zoom;
                if (zoom >= 13 && pre_zoom < 13) {
                    setBigIcon();
                }
                if (zoom < 13 && pre_zoom >= 13) {
                    setSmallIcon();
                }
                pre_zoom = zoom;
            }
        });
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(df, zoom));
    }

    private void setBigIcon() {
        int len = markerOptions.size();
        for (int i = 0; i < len; i++) {
            markerOptions.get(i).icon(BitmapDescriptorFactory.fromBitmap(icon_big));
        }
    }

    private void setSmallIcon() {
        int len = markerOptions.size();
        for (int i = 0; i < len; i++) {
            markerOptions.get(i).icon(BitmapDescriptorFactory.fromBitmap(icon_small));
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        drawDefault();
    }
}