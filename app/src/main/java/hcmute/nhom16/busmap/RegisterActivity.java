package hcmute.nhom16.busmap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import hcmute.nhom16.busmap.model.User;
import hcmute.nhom16.busmap.model.UserAccount;

public class RegisterActivity extends AppCompatActivity {
    CircleImageView civ_avatar;
    EditText edt_name, edt_email, edt_phone, edt_pass;
    Button btn_register;
    Spinner spinner_gender;
    TextView tv_dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        initUI();
        initListener();
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
        btn_register.setOnClickListener(v -> {
            register();
        });
    }

    private void register() {
        String phone = edt_phone.getText().toString();
        String name = edt_name.getText().toString();
        String pass = edt_pass.getText().toString();
        String email = edt_email.getText().toString();
        if (phone.equals("") || name.equals("") || pass.equals("") || email.equals("")) {
            Toast.makeText(this, R.string.fill, Toast.LENGTH_SHORT).show();
            return;
        }
        boolean gender = spinner_gender.getSelectedItemPosition() == 0;
        Date dob = Support.stringToDate(tv_dob.getText().toString(), "dd/MM/yyyy");

        Bitmap bitmap = ((BitmapDrawable) civ_avatar.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] image = stream.toByteArray();
        boolean exists = Support.checkAccountExists(email);
        if (exists) {
            Toast.makeText(this, R.string.account_exists, Toast.LENGTH_SHORT).show();
        } else {
            UserAccount.register(new User(-1, name, phone, gender, dob, email, pass, image));
            Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initUI() {
        civ_avatar = findViewById(R.id.civ_avatar);
        edt_name = findViewById(R.id.edt_name);
        edt_email = findViewById(R.id.edt_email);
        edt_phone = findViewById(R.id.edt_phone);
        edt_pass = findViewById(R.id.edt_pass);
        btn_register = findViewById(R.id.btn_register);
        spinner_gender = findViewById(R.id.spinner_gender);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.gender_spinner, R.id.tv_gender, getGenders());
        spinner_gender.setAdapter(adapter);
        tv_dob = findViewById(R.id.tv_dob);
    }

    private List<String> getGenders() {
        List<String> genders = new ArrayList<>();
        genders.add("Nam");
        genders.add("Nữ");
        return genders;
    }
}
