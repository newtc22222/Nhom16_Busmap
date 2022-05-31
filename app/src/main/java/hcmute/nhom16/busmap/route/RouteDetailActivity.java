package hcmute.nhom16.busmap.route;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import hcmute.nhom16.busmap.R;
import hcmute.nhom16.busmap.model.Route;

public class RouteDetailActivity extends AppCompatActivity {
    ViewPager2 vp2_detail_route;
    Route route;
    TextView tv_id, tv_name;

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

        initUI();
        initListener();
    }

    private void initListener() {
    }

    private void initUI() {
        tv_id = findViewById(R.id.tv_id);
        tv_id.setText(String.valueOf(route.getId()));
        tv_name = findViewById(R.id.tv_name);
        tv_name.setText(String.valueOf(route.getName()));

        vp2_detail_route = findViewById(R.id.vp2_detail_route);
        RouteDetailStateAdapter adapter = new RouteDetailStateAdapter(this, route);
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}