package hcmute.nhom16.busmap.result;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import hcmute.nhom16.busmap.R;
import hcmute.nhom16.busmap.Support;
import hcmute.nhom16.busmap.model.Address;
import hcmute.nhom16.busmap.model.Result;

public class ResultFindRoadActivity extends AppCompatActivity {
    private RecyclerView rv_results;
    private Address from, to;
    private int route_amount;
    private TextView tv_from, tv_to;
    private ImageButton ib_swap;
    private ResultAdapter adapter;
    private List<Result> results;

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

        rv_results = findViewById(R.id.rv_results);
        results = getResultRoutes();
        adapter = new ResultAdapter(this, results, from, to);
        rv_results.setAdapter(adapter);
        rv_results.setLayoutManager(new LinearLayoutManager(this));

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
        results = getResultRoutes();
        adapter.setResult_routes(results);
    }

    public List<Result> getResultRoutes() {
        return Support.calculateResults(this, from, to, route_amount);
    }
}