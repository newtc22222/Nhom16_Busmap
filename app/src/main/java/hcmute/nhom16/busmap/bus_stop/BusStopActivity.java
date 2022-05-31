package hcmute.nhom16.busmap.bus_stop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hcmute.nhom16.busmap.R;
import hcmute.nhom16.busmap.Support;
import hcmute.nhom16.busmap.model.BusStop;
import hcmute.nhom16.busmap.model.Route;
import hcmute.nhom16.busmap.route.RouteAdapter;

public class BusStopActivity extends AppCompatActivity {
    RecyclerView rv_routes;
    BusStop bus_stop;
    TextView tv_name, tv_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getData();

        initUI();
        initListener();
    }

    public void getData() {
        Intent intent = getIntent();
        bus_stop = (BusStop) intent.getSerializableExtra("bus_stop");
    }

    private void initListener() {
    }

    private void initUI() {
        tv_address = findViewById(R.id.tv_address);
        tv_address.setText(bus_stop.getAddress().getAddress());
        tv_name = findViewById(R.id.tv_name);
        tv_name.setText(bus_stop.getName());

        rv_routes = findViewById(R.id.rv_routes);
        RouteAdapter adapter = new RouteAdapter(this, getRoutesFromBusStop());
        rv_routes.setAdapter(adapter);
        rv_routes.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<Route> getRoutesFromBusStop() {
        return Support.getRoutesFromBusStop(bus_stop);
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
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    private void onSavedSelected() {
        Support.saveBusStop(bus_stop);
        Toast.makeText(this, R.string.saved_toast, Toast.LENGTH_SHORT).show();
    }
}