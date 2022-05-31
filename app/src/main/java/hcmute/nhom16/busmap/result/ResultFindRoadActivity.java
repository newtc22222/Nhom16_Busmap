package hcmute.nhom16.busmap.result;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hcmute.nhom16.busmap.R;
import hcmute.nhom16.busmap.Support;
import hcmute.nhom16.busmap.model.Address;
import hcmute.nhom16.busmap.model.ResultRoute;

public class ResultFindRoadActivity extends AppCompatActivity {
    private RecyclerView rv_result_routes;
    private Address from, to;
    private int route_amount;
    private TextView tv_from, tv_to;
    private ImageButton ib_swap;
    private ResultRouteAdapter adapter;
    private List<ResultRoute> result_routes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_road_result);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(R.string.find_road_result);
        getData();

        initUI();
        initListener();
    }

    private void getData() {
        Intent intent = getIntent();
        route_amount = intent.getIntExtra("route_amount", -1);
        from = (Address) intent.getSerializableExtra("from");
        to = (Address) intent.getSerializableExtra("to");
    }

    private void swapAddress() {
        Address adr = to;
        to = from;
        from = adr;
        setAddressText();
    }

    private void setAddressText() {
        tv_from.setText(from.getAddress());
        tv_to.setText(to.getAddress());
    }

    private void initUI() {
        tv_from = findViewById(R.id.tv_from);
        tv_to = findViewById(R.id.tv_to);
        setAddressText();

        ib_swap = findViewById(R.id.ib_swap);

        rv_result_routes = findViewById(R.id.rv_result_routes);
        result_routes = getResultRoutes();
        adapter = new ResultRouteAdapter(this, result_routes, from, to);
        rv_result_routes.setAdapter(adapter);
        rv_result_routes.setLayoutManager(new LinearLayoutManager(this));

        loadResult();
    }

    private void initListener() {
        ib_swap.setOnClickListener(v -> {
            swapAddress();
            loadResult();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadResult() {
        result_routes = getResultRoutes();
        adapter.setResult_routes(result_routes);
    }

    public List<ResultRoute> getResultRoutes() {
        return Support.getResultRoutes(from, to, route_amount);
    }
}