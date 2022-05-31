package hcmute.nhom16.busmap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InformationActivity extends AppCompatActivity {

    EditText edt_phone, edt_name, edt_email;
    Button btn_update_info;
    Spinner spinner_gender;
    TextView tv_dob, tv_name;
    CircleImageView civ_avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);
        initUI();
        initListener();
    }

    private void initUI() {
        civ_avatar = findViewById(R.id.civ_avatar);

        tv_name = findViewById(R.id.tv_name);

        edt_phone = findViewById(R.id.edt_phone);

        edt_name = findViewById(R.id.edt_name);

        spinner_gender = findViewById(R.id.spinner_gender);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.gender_spinner, R.id.tv_gender, getGenders());
        spinner_gender.setAdapter(adapter);

        tv_dob = findViewById(R.id.tv_dob);

        edt_email = findViewById(R.id.edt_email);

        btn_update_info = findViewById(R.id.btn_update_info);

    }

    private void initListener() {
        Calendar dc = Calendar.getInstance();
        dc.set(Calendar.YEAR, 2001);
        dc.set(Calendar.MONTH, 5);
        dc.set(Calendar.DATE, 11);
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.DATE, i2);
                tv_dob.setText(Support.dateToString(calendar.getTime(), "dd/MM/yyyy"));
            }
        }, dc.get(Calendar.YEAR), dc.get(Calendar.MONTH), dc.get(Calendar.DATE));
        tv_dob.setOnClickListener(v -> {
            dialog.show();
        });
        btn_update_info.setOnClickListener(v -> {
            updateInformation();
        });
    }

    public void updateInformation() {

    }

    private List<String> getGenders() {
        List<String> genders = new ArrayList<>();
        genders.add("Nam");
        genders.add("Nữ");
        return genders;
    }
}