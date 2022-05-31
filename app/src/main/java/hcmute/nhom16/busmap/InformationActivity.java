package hcmute.nhom16.busmap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import hcmute.nhom16.busmap.model.User;
import hcmute.nhom16.busmap.model.UserAccount;

public class InformationActivity extends AppCompatActivity {

    EditText edt_phone, edt_name, edt_email;
    Button btn_update_info;
    Spinner spinner_gender;
    TextView tv_dob, tv_name;
    CircleImageView civ_avatar;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);
        getUserInformation();
        initUI();
        setInformationToForm();
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
        String name = edt_name.getText().toString();
        String phone = edt_phone.getText().toString();
        Date dob = Support.stringToDate(tv_dob.getText().toString(), "dd/MM/yyyy");
        boolean gender = spinner_gender.getSelectedItemPosition() == 0;

        if (name.equals("") || phone.equals("")) {
            Toast.makeText(this, R.string.fill, Toast.LENGTH_SHORT);
        } else {
            user.setDob(dob);
            user.setGender(gender);
            user.setPhone(phone);
            user.setName(name);

            UserAccount.update();

        }
    }

    public void setInformationToForm() {
        byte[] bytes = user.getImage();
        if (bytes == null) {
            civ_avatar.setImageResource(R.drawable.avatar_default);
        } else {
            civ_avatar.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, user.getImage().length));
        }
        tv_name.setText(user.getName());
        edt_name.setText(user.getName());
        edt_email.setText(user.getEmail());
        edt_phone.setText(user.getPhone());
        tv_dob.setText(Support.dateToString(user.getDob(), "dd/MM/yyyy"));
        spinner_gender.setSelection(user.isGender() ? 0 : 1);
    }

    public void getUserInformation() {
        user = UserAccount.getUser();
    }

    private List<String> getGenders() {
        List<String> genders = new ArrayList<>();
        genders.add("Nam");
        genders.add("Nữ");
        return genders;
    }
}