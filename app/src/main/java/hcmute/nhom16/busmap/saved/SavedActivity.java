package hcmute.nhom16.busmap.saved;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import hcmute.nhom16.busmap.LoginActivity;
import hcmute.nhom16.busmap.R;
import hcmute.nhom16.busmap.Support;
import hcmute.nhom16.busmap.model.BusStop;
import hcmute.nhom16.busmap.model.Route;
import hcmute.nhom16.busmap.model.Station;
import hcmute.nhom16.busmap.model.User;
import hcmute.nhom16.busmap.model.UserAccount;

public class SavedActivity extends AppCompatActivity {
    ViewPager2 vp2_saved;
    TabLayout tab_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (UserAccount.getUser() == null) {
            setContentView(R.layout.need_logged);
            Button btn_login = findViewById(R.id.btn_login);
            btn_login.setOnClickListener(v -> {
                goToLogin();
            });
        } else {
            setContentView(R.layout.activity_saved);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setTitle(R.string.saved);

            initUI();
            initListener();
        }
    }

    private void initUI() {
        vp2_saved = findViewById(R.id.vp2_saved);
        SavedStateAdapter adapter = new SavedStateAdapter(this, getSavedRoutes(), getSavedStations());
        vp2_saved.setAdapter(adapter);
        tab_layout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tab_layout, vp2_saved, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText(R.string.route_tab);
                        break;
                    case 1:
                        tab.setText(R.string.route_tab_bus_stop);
                        break;
                }
            }
        }).attach();
    }

    private void initListener() {
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private List<Route> getSavedRoutes() {
        return Support.getSavedRoutes(this);
    }

    private List<Station> getSavedStations() {
        return Support.getSavedStations(this);
    }
}