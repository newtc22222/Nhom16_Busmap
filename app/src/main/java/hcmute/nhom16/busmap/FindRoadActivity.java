package hcmute.nhom16.busmap;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import hcmute.nhom16.busmap.model.Address;
import hcmute.nhom16.busmap.result.ResultFindRoadActivity;

public class FindRoadActivity extends AppCompatActivity {
    Spinner spinner_route_amount;
    LinearLayout ll_to, ll_from;
    ImageButton ib_swap;
    Button btn_find_road;
    TextView tv_from, tv_to;
    Address from, to;

    ActivityResultLauncher<Intent> launcherFrom = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        from = (Address) result.getData()
                                .getSerializableExtra("address");
                        handleAddress(AddressResultType.FROM);
                    }
                }
            }
    );

    ActivityResultLauncher<Intent> launcherTo = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        to = (Address) result.getData()
                                .getSerializableExtra("address");
                        handleAddress(AddressResultType.TO);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_road);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(R.string.find_road);

        initUI();
        initListener();
    }

    private void initUI() {
        btn_find_road = findViewById(R.id.btn_find_road);
        ib_swap = findViewById(R.id.ib_swap);
        ll_from = findViewById(R.id.ll_from);
        ll_to = findViewById(R.id.ll_to);
        tv_from = findViewById(R.id.tv_from);
        tv_to = findViewById(R.id.tv_to);
        spinner_route_amount = findViewById(R.id.spinner_route_amount);
        List<String> amount_route = Arrays.asList(getResources().getStringArray(R.array.amount_route));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.route_amount_item, R.id.tv_item, amount_route);
        spinner_route_amount.setAdapter(adapter);
    }

    private void initListener() {
        ib_swap.setOnClickListener(v -> {
            swapAddress();
        });
        ll_from.setOnClickListener(v -> {
            getAddress(AddressResultType.FROM);
        });
        ll_to.setOnClickListener(v -> {
            getAddress(AddressResultType.TO);
        });
        btn_find_road.setOnClickListener(v -> {
            if (null != from && null != to) {
                Intent intent = new Intent(this, ResultFindRoadActivity.class);
                intent.putExtra("from", from);
                intent.putExtra("to", to);
//                intent.putExtra("route_amount", spinner_route_amount.getSelectedItemPosition());
                intent.putExtra("route_amount", 1);
                startActivity(intent);
            }
        });
    }

    private void getAddress(AddressResultType type) {
        Intent intent = new Intent(this, AddressSearchActivity.class);
        if (AddressResultType.FROM == type) {
            launcherFrom.launch(intent);
        } else {
            launcherTo.launch(intent);
        }
    }

    private void handleAddress(AddressResultType type) {
        if (type == AddressResultType.FROM) {
            tv_from.setText(from.getAddress());
        } else {
            tv_to.setText(to.getAddress());
        }
    }

    private void swapAddress() {
        Address adr = to;
        to = from;
        from = adr;
        setAddress();
    }

    private void setAddress() {
        if (from != null) {
            tv_from.setText(from.getAddress());
        } else {
            tv_from.setText("");
        }
        if (to != null) {
            tv_to.setText(to.getAddress());
        } else {
            tv_to.setText("");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}