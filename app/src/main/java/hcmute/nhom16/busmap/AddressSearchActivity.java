package hcmute.nhom16.busmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import hcmute.nhom16.busmap.model.Address;
// update
public class AddressSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_address);



        Button btn_complete_1 = findViewById(R.id.btn_complete_1);
        Button btn_complete_2 = findViewById(R.id.btn_complete_2);
        btn_complete_1.setOnClickListener(v -> {
            returnResult(1);
        });
        btn_complete_2.setOnClickListener(v -> {
            returnResult(2);
        });
    }
    public void returnResult(int i) {
        Address address;
        if (i == 1) {
            address = new Address("KTX Khu B, ĐHQG TP.HCM", 10, 170);
        } else {
            address = new Address("KTX Khu A, ĐHQG TP.HCM", 10, 170);
        }
        Intent intent = new Intent();
        intent.putExtra("address", address);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}