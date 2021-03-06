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
import android.widget.ImageView;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import hcmute.nhom16.busmap.address.AddressSearchActivity;
import hcmute.nhom16.busmap.data.DatabaseHelper;
import hcmute.nhom16.busmap.data.StationDAO;
import hcmute.nhom16.busmap.feedback.FeedbackActivity;
import hcmute.nhom16.busmap.feedback.InformationGroupActivity;
import hcmute.nhom16.busmap.feedback.RateActivity;
import hcmute.nhom16.busmap.feedback.UpdateActivity;
import hcmute.nhom16.busmap.entities.Address;
import hcmute.nhom16.busmap.entities.User;
import hcmute.nhom16.busmap.entities.UserAccount;
import hcmute.nhom16.busmap.result.FindRoadActivity;
import hcmute.nhom16.busmap.saved.SavedActivity;
import hcmute.nhom16.busmap.side.SideAdapter;
import hcmute.nhom16.busmap.component.Side;
import hcmute.nhom16.busmap.notification.NotificationActivity;
import hcmute.nhom16.busmap.route.RouteActivity;
import hcmute.nhom16.busmap.user.InformationActivity;
import hcmute.nhom16.busmap.user.LoginActivity;
import hcmute.nhom16.busmap.user.RegisterActivity;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
//    ??? ????y l?? c??c bi???n s??? ??nh x??? c??c View trong main_layout
    DrawerLayout drawable_layout;
    NavigationView nv_group;
    Spinner side_spinner;
    LinearLayout ll_logged, ll_not_logged;
    ImageView iv_avatar;
    TextView tv_name, tv_email;
    Button btn_info, btn_login, btn_search, btn_register;
    ImageButton ib_search, ib_find, ib_logout;
//   User l??u gi?? tr??? c???a user, n???u b???ng null t???c l?? ch??a ????ng nh???p
//   n???u != null th?? c?? ngh??a l?? ???? ????ng nh???p
//   user s??? ???????c l???y t??? UserAccount. L???p n??y ???????c vi???t d???a tr??n Singleton Pattern
//   v?? c?? synchronized gi??p cho vi???c g???i trong nhi???u lu???ng kh??ng l??m sai singleton
    User user;
//   address l??u gi?? tr??? c???a ?????a ch??? ???????c tr??? veeff khi s??? d???ng ch???c n??ng t??m ki???m ?????a ??i???m
    Address address;
//   icon d??ng ????? l??m marker, c?? 2 lo???i icon l?? big v?? small
//   Khi m??n h??nh c?? ????? ph??ng l???n (>14) th?? ta s??? s??? d???ng big_icon
//   Ng?????c l???i th?? s??? d???ng small_icon
    Bitmap big_icon, small_icon;
//   map s??? l??u l???i google map ???????c tr??? v??? trong h??m onMapReady
    private GoogleMap map;
//   2 list small markers v?? big_markers ch???a 2 lo???i markers khi m??n h??nh c?? ????? zoom l???n v?? nh???
//   ???? th??? d??ng m???t list sau ???? khi ????? zoom thay ?????i th?? thay ?????i icon big th??nh small v?? ng?????c l???i
//   Nh??ng hi???u xu???t r???t k??m t???i v?? s??? station r???t l???n n???u c??? load ??i load l???i th?? r???t ph?? t??i nguy??n
//   Thay v??o ???? s??? d???ng 2 list v?? ch??? s??? d???ng setVisible
    List<Marker> small_markers, big_markers;
//   dest l?? marker t???i v??? tr?? m?? ta search
    Marker dest;
//   ????y l?? latLng default l??m trung t??m c???a b???n ????? (??H SPKT)
    LatLng df = new LatLng(10.85075361772994, 106.77124465290879);
//   2 bi???n zoom v?? pre_zoom gi??p cho ta c?? th??? bi???t ???????c ????? zoom tr?????c v?? sau c???a b???n ?????
//   ???????c d??ng ????? ki???m tra xem khi n??o zoom v?????t ng?????ng th?? s??? b???t t???t c??c markers
    float zoom = 15, pre_zoom = 15;

//   ????y l?? m???t launcher ???????c d??ng ????? l???y address, launcher n??y ???????c s??? d???ng thay th??? cho
//   onActivityResult
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

//        ?????u ti??n ta c???n load user ????? xem ng?????i d??ng ???? ????ng nh???p hay ch??a
//        ????? hi???n th??? navigation cho ????ng v?? gi???i h???n c??c ch???c n??ng c???n ????ng nh???p
        user = UserAccount.getUser();
//        V???i m??y ???o android 12 api 31 th?? kh??ng c???n loaddb tr?????c
//        Nh??ng c?? v???n ????? v???i android 12 api 32 th?? n?? kh??ng t??m th???y table n??n ta c???n load tr?????c
        initLoadDb();
//        Kh???i t???o b???n ?????
        initMap();
//        ??nh x??? c??c th??nh ph???n view
        initUI();
//        B???t c??c s??? ki???n tr??n c??c view
        initListener();
    }

//    Load database ????n gi???n ch??? c???n g???i h??m onCreate c???a l???p DatabaseHelper
    private void initLoadDb() {
        DatabaseHelper helper = new DatabaseHelper(this);
        helper.onCreate(helper.getWritableDatabase());
    }

//    Kh???i t???o map
    private void initMap() {
//        t???o 2 icon big v?? small cho ????? l??m icon marker
        Drawable ic = getDrawable(R.drawable.ic_station_small);
        int width = ic.getIntrinsicWidth();
        int height = ic.getIntrinsicHeight();
        small_icon = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        ic.setBounds(0, 0, width, height);
        ic.draw(new Canvas(small_icon));

        ic = getDrawable(R.drawable.ic_station_big);
        width = ic.getIntrinsicWidth();
        height = ic.getIntrinsicHeight();
        big_icon = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        ic.setBounds(0, 0, width, height);
        ic.draw(new Canvas(big_icon));

//        Kh???i t???o tr?????c 2 list markers
        big_markers = new ArrayList<>();
        small_markers = new ArrayList<>();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fm_map);
        mapFragment.getMapAsync(this);
    }

//    ??nh x??? c??c view trong layout
    private void initUI() {
//        ??nh x??? drawable_layout v?? navigation
        drawable_layout = findViewById(R.id.drawable_layout);
        nv_group = findViewById(R.id.nv_group);

//        hide title c???a toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        T???o toggle l??n icon ????ng m??? navigation
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, drawable_layout,
                        toolbar, R.string.open_nav, R.string.close_nav);
        drawable_layout.addDrawerListener(toggle);
        toggle.syncState();

//        ????? data v??o spinner ch???n c??c v??ng mi???n trong app
        side_spinner = findViewById(R.id.side_spinner);
        SideAdapter adapter = new SideAdapter(this, getSides());
        side_spinner.setAdapter(adapter);

        btn_search = findViewById(R.id.btn_search);
        ib_search = findViewById(R.id.ib_search);
        ib_find = findViewById(R.id.ib_find);

//        ??nh x??? c??c view c???a header navigation
        View header = nv_group.getHeaderView(0);
        ll_logged = header.findViewById(R.id.ll_logged);
        iv_avatar = header.findViewById(R.id.iv_avatar);
        tv_name = header.findViewById(R.id.tv_name);
        tv_email = header.findViewById(R.id.tv_email);
        btn_info = header.findViewById(R.id.btn_info);
        ll_not_logged = header.findViewById(R.id.ll_not_logged);
        btn_login = header.findViewById(R.id.btn_login);
        ib_logout = header.findViewById(R.id.ib_logout);
        btn_register = header.findViewById(R.id.btn_register);

//        Khi ng?????i d??ng ???? login th?? s??? hi???n th??? header navigation l?? th??ng tin c???a ng?????i d??ng
        if (user == null) {
            ll_not_logged.setVisibility(View.VISIBLE);
            ll_logged.setVisibility(View.GONE);
            btn_login.setVisibility(View.VISIBLE);
        } else {
            ll_logged.setVisibility(View.VISIBLE);
            if (user.getImage() == null) {
                iv_avatar.setImageResource(R.drawable.avatar_default);
            } else {
                byte[] image = user.getImage();
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                iv_avatar.setImageBitmap(bitmap);
            }
            tv_name.setText(user.getName());
            tv_email.setText(user.getEmail());

            ll_logged.setVisibility(View.VISIBLE);
            btn_login.setVisibility(View.GONE);
        }
    }

//    B???t s??? ki???n
    private void initListener() {
//        X??? l?? s??? ki???n khi click c??c item b??n trong navigation
//        Khi click v??o 1 item th?? ????ng th???i thu g???n navigation v?? th???c hi???n ch???c n??ng
        nv_group.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nv_find_address:
                        onSearchAddressSelected();
                        break;
                    case R.id.nv_notification:
                        onNotificationSelected();
                        break;
                    case R.id.nv_find_bus:
                        onFindBusSelected();
                        break;
                    case R.id.nv_search:
                        onSearchSelected();
                        break;
                    case R.id.nv_update:
                        onUpdateSelected();
                        break;
                    case R.id.nv_feedback:
                        onFeedbackSelected();
                        break;
                    case R.id.nv_rate:
                        onRateSelected();
                        break;
                    case R.id.nv_information:
                        onInformationSelected();
                        break;
                }
                drawable_layout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

//        M??? infomation activity c???a ng?????i d??ng
        btn_info.setOnClickListener(v -> {
            Intent intent = new Intent(this, InformationActivity.class);
            startActivity(intent);
        });

//        G???i h??m khi click v??o button search, h??m ???? ???????c ??inh ngh??a b??n d?????i
        btn_search.setOnClickListener(v -> {
            onSearchAddressSelected();
        });

//        G???i h??m khi click v??o button find road, h??m ???? ???????c ??inh ngh??a b??n d?????i
        ib_find.setOnClickListener(v -> {
            onFindBusSelected();
        });

//        G???i h??m khi click v??o button search, h??m ???? ???????c ??inh ngh??a b??n d?????i
        ib_search.setOnClickListener(v -> {
            onSearchSelected();
        });

//        chuy???n ?????n activity ????ng nh???p
        btn_login.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

//        ????ng xu???t v?? g???i l???i ?????n main activity nh??ng s??? x??a h???t c??c task activity c??
        ib_logout.setOnClickListener(v -> {
            UserAccount.logout();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

//    M??? activity RegisterActivity khi click v??o button ????ng k??
        btn_register.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }

//        launcher activity AddressSearchActivity ????? nh???n k???t qu??? tr??? v??? l?? m???t address
    public void onSearchAddressSelected() {
        Intent intent = new Intent(this, AddressSearchActivity.class);
        getAddress.launch(intent);
    }

    //    M??? activity RouteActivity
    public void onSearchSelected() {
        Intent intent = new Intent(this, RouteActivity.class);
        startActivity(intent);
    }

    //    M??? activity FindRoadActivity
    public void onFindBusSelected() {
        Intent intent = new Intent(this, FindRoadActivity.class);
        startActivity(intent);
    }

    //    M??? activity SavedActivity
    public void onSavedSelected() {
        Intent intent = new Intent(this, SavedActivity.class);
        startActivity(intent);
    }

    //    M??? activity NotificationActivity
    private void onNotificationSelected() {
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    //    M??? activity UpdateActivity
    private void onUpdateSelected() {
        Intent intent = new Intent(this, UpdateActivity.class);
        startActivity(intent);
    }

    //    M??? activity FeedbackActivity
    private void onFeedbackSelected() {
        Intent intent = new Intent(this, FeedbackActivity.class);
        startActivity(intent);
    }

    //    M??? activity RateActivity
    private void onRateSelected() {
        Intent intent = new Intent(this, RateActivity.class);
        startActivity(intent);
    }

//    M??? activity InformationGroupActivity
    private void onInformationSelected() {
        Intent intent = new Intent(this, InformationGroupActivity.class);
        startActivity(intent);
    }

//    Menu ch??? g???m 1 item l?? saved
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_saved, menu);
        return true;
    }

//    N???u item_saved ???????c click th?? s??? g???i h??m onSavesSelected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_saved:
                onSavedSelected();
                break;
        }
        return true;
    }

//    Show ?????a ch??? tr??? v??? l??n thanh t??m ki???m v?? move camera t???i ?????a ch??? v???a tr??? v???
    private void showAddress() {
        btn_search.setText(address.getAddress());
        df = new LatLng(address.getLat(), address.getLng());
        moveCamera();
    }

//    T???o c??c side m???c ?????nh
    public List<Side> getSides() {
        List<Side> sides = new ArrayList<>();
        sides.add(new Side("TP H??? Ch?? Minh", R.drawable.vn));
        sides.add(new Side("???? N???ng", R.drawable.vn));
        sides.add(new Side("Th???a Thi??n Hu???", R.drawable.vn));
        sides.add(new Side("H?? N???i", R.drawable.vn));
        return sides;
    }

//    di chuy???n camera ?????n v??? tr?? c???a df v???i ????? zoom ???????c thi???t l???p l???i l?? 15
//    marker dest s??? ???????c hi???n th??? l??n tr??n c??ng
    public void moveCamera() {
        pre_zoom = 15;
        zoom = 15;
        if (dest != null) {
            dest.setPosition(df);
        } else {
            dest = map.addMarker(new MarkerOptions().position(df).zIndex(1f));
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(df, zoom));
    }

//    V??? m???c ?????nh c??c markers
    public void drawDefault() {
//        L???y t???t c??? latlng c???a station
        List<LatLng> latLngs = StationDAO.getLatLngOfStations(this);
        int len = latLngs.size();
//        V???i c??c latlng c???a c??c stations, m???i latlng s??? v??? 2 marker v?? ???n marker c???a icon small ??i
        for (int i = 0; i < len; i++) {
            big_markers.add(map.addMarker(new MarkerOptions().position(latLngs.get(i)).icon(BitmapDescriptorFactory.fromBitmap(big_icon))));
            small_markers.add(map.addMarker(new MarkerOptions().position(latLngs.get(i)).icon(BitmapDescriptorFactory.fromBitmap(small_icon))));
            small_markers.get(i).setVisible(false);
        }
//        B???t s??? ki???n move camera: n???u camera t??? ????? zoom t??? < 14 l??n > 14 th?? s??? show big marker
//        N???u camera t??? ????? zoom > 14 xu???ng < 14 th?? s??? show small marker
        map.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                zoom = map.getCameraPosition().zoom;
                if (zoom >= 14 && pre_zoom < 14) {
                    setBigIcon();
                }
                if (zoom < 14 && pre_zoom >= 14) {
                    setSmallIcon();
                }
                pre_zoom = zoom;
            }
        });
        moveCamera();
    }

//    H??m n??y ???n t???t c??? c??c small_markers v?? show t???t c??? c??c big_markers
    private void setBigIcon() {
        int len = big_markers.size();
        for (int i = 0; i < len; i++) {
//            big_markers.get(i).setIcon(BitmapDescriptorFactory.fromBitmap(big_icon));
            big_markers.get(i).setVisible(true);
            small_markers.get(i).setVisible(false);
        }
    }

//    H??m n??y ???n t???t c??? c??c big_markers v?? show t???t c??? c??c small_markers
    private void setSmallIcon() {
//        int len = big_markers.size();
        int len = small_markers.size();
        for (int i = 0; i < len; i++) {
//            big_markers.get(i).setIcon(BitmapDescriptorFactory.fromBitmap(big_icon));
            big_markers.get(i).setVisible(false);
            small_markers.get(i).setVisible(true);
        }
    }

//    khi map ???? load xong th?? g??n googleMap v?? map sau ???? v??? c??c marker
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        drawDefault();
    }

//    Khi b???m n??t back, n???u drawable_layout ??ang m??? th?? c???n ????ng n?? l???i tr?????c
    @Override
    public void onBackPressed() {
        if (drawable_layout.isDrawerOpen(GravityCompat.START)) {
            drawable_layout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
    }
}