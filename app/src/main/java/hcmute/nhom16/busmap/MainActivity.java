package hcmute.nhom16.busmap;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.window.SplashScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawable_layout;
    NavigationView nv_group;
    Spinner address_spinner;
    LinearLayout ll_logged;
    CircleImageView civ_avatar;
    TextView tv_name, tv_email;
    Button btn_info, btn_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initListener();
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

        address_spinner = findViewById(R.id.address_spinner);
        AddressAdapter adapter = new AddressAdapter(this, getAddresses());
        address_spinner.setAdapter(adapter);

        View header = nv_group.getHeaderView(0);
        ll_logged = header.findViewById(R.id.ll_logged);
        civ_avatar = header.findViewById(R.id.civ_avatar);
        tv_name = header.findViewById(R.id.tv_name);
        tv_email = header.findViewById(R.id.tv_email);
        btn_info = header.findViewById(R.id.btn_info);
        btn_login = header.findViewById(R.id.btn_login);
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
    }

    private void onNotificationSelected() {
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    private void onFindBusSelected() {

    }

    private void onSearchSelected() {

    }

    private void onUpdateSelected() {

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

    public List<Address> getAddresses() {
        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address("TP Hồ Chí Minh", R.drawable.vn));
        addresses.add(new Address("Đà Nẵng", R.drawable.vn));
        addresses.add(new Address("Thừa Thiên Huế", R.drawable.vn));
        addresses.add(new Address("Hà Nội", R.drawable.vn));
        return addresses;
    }
}