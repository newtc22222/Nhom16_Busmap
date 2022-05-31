package hcmute.nhom16.busmap.route;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import hcmute.nhom16.busmap.R;
import hcmute.nhom16.busmap.Support;
import hcmute.nhom16.busmap.model.Route;

public class RouteListActivity extends AppCompatActivity {
    RecyclerView rv_routes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_route);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(R.string.pick_route);

        initUI();
        initListener();
    }

    private void initListener() {
    }

    private void initUI() {
        rv_routes = findViewById(R.id.rv_routes);
        RouteAdapter adapter = new RouteAdapter(this, getAllRoutes());
        rv_routes.setAdapter(adapter);
        rv_routes.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private List<Route> getAllRoutes() {
        return Support.getAllRoutes();
    }
}