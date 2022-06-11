package hcmute.nhom16.busmap;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.acl.Permission;
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
    ImageButton ib_choose_image;
    Spinner spinner_gender;
    TextView tv_dob;
    public static final int PICK_IMAGE = 10000;
    byte[] image = null;

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        InputStream inputStream;
                        try {
                            inputStream = getContentResolver().openInputStream(result.getData().getData());
                            Bitmap bm = BitmapFactory.decodeStream(inputStream);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            image = stream.toByteArray();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        civ_avatar.setImageURI(result.getData().getData());
                    }
                }
            }
    );

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
        dc.set(Calendar.MONTH, 4);
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
        ib_choose_image.setOnClickListener(v -> {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) {
                getImage();
            } else {
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_IMAGE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getImage();
        }
    }

    private void getImage() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setType("image/*");
        launcher.launch(pickIntent);
    }

    private void register() {
        String email = edt_email.getText().toString();
        if (!Support.checkInvalidEmail(email)) {
            Toast.makeText(this, R.string.uninvalid_email, Toast.LENGTH_SHORT).show();
            return;
        }
        String pass = edt_pass.getText().toString();
        if (!Support.checkInvalidPassword(pass)) {
            Toast.makeText(this, R.string.uninvalid_password, Toast.LENGTH_SHORT).show();
            return;
        }
        String phone = edt_phone.getText().toString();
        String name = edt_name.getText().toString();
        String dob_str = tv_dob.getText().toString();
        Date dob = null;
        if (phone.equals("") || name.equals("") || pass.equals("") || email.equals("") || dob_str.equals("")) {
            Toast.makeText(this, R.string.fill, Toast.LENGTH_SHORT).show();
            return;
        }

        dob = Support.stringToDate(dob_str, "dd/MM/yyyy");
        boolean gender = spinner_gender.getSelectedItemPosition() == 0;

        boolean exists = Support.checkAccountExists(this, email);
        if (exists) {
            Toast.makeText(this, R.string.account_exists, Toast.LENGTH_SHORT).show();
        } else {
            UserAccount.register(this, new User(email, pass, name, phone, gender, dob, image));
            Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initUI() {
        ib_choose_image = findViewById(R.id.ib_choose_image);
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
