package hcmute.nhom16.busmap.result;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import hcmute.nhom16.busmap.R;
import hcmute.nhom16.busmap.model.Address;
import hcmute.nhom16.busmap.model.ResultRoute;

public class ResultRouteDetailActivity extends AppCompatActivity {
    ViewPager2 vp2_detail_route;
    ResultRoute result_route;
    Address from, to;
    TextView tv_route_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_route_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(R.string.moving_guide);

        getResultRoute();

        initUI();
        initListener();
    }

    private void getResultRoute() {
        Intent intent = getIntent();
        result_route = (ResultRoute) intent.getSerializableExtra("result_route");
        from = (Address) intent.getSerializableExtra("from");
        to = (Address) intent.getSerializableExtra("to");
    }

    private void initListener() {
    }

    private void initUI() {
        tv_route_id = findViewById(R.id.tv_route_id);
        tv_route_id.setText(result_route.getRoute().getRouteNumId());
        vp2_detail_route = findViewById(R.id.vp2_detail_route);
        ResultStateAdapter adapter = new ResultStateAdapter(this, result_route, from, to);
        vp2_detail_route.setAdapter(adapter);

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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}