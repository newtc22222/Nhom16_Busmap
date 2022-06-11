package hcmute.nhom16.busmap;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import hcmute.nhom16.busmap.model.User;
import hcmute.nhom16.busmap.model.UserAccount;

public class InformationActivity extends AppCompatActivity {
    public static final int PICK_IMAGE = 10000;
    EditText edt_phone, edt_name, edt_email;
    Button btn_update_info, btn_change_password;
    Spinner spinner_gender;
    TextView tv_dob, tv_name;
    CircleImageView civ_avatar;
    ImageButton ib_choose_image;
    byte[] image = null;
    User user;

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
        setContentView(R.layout.activity_infomation);
        getUserInformation();
        initUI();
        setInformationToForm();
        initListener();
    }

    private void initUI() {
        btn_change_password = findViewById(R.id.btn_change_password);

        ib_choose_image = findViewById(R.id.ib_choose_image);

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
        btn_update_info.setOnClickListener(v -> {
            updateInformation();
        });
        ib_choose_image.setOnClickListener(v -> {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                getImage();
            } else {
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_IMAGE);
            }
        });
        btn_change_password.setOnClickListener(v -> {
            PasswordDialog passwordDialog = new PasswordDialog(this);
            passwordDialog.show();
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

    public void updateInformation() {
        String name = edt_name.getText().toString();
        String phone = edt_phone.getText().toString();
        Date dob = Support.stringToDate(tv_dob.getText().toString(), "dd/MM/yyyy");
        boolean gender = spinner_gender.getSelectedItemPosition() == 0;

        if (name.equals("") || phone.equals("")) {
            Toast.makeText(this, R.string.fill, Toast.LENGTH_SHORT);
        } else {
            user.setDate_of_birth(dob);
            user.setGender(gender);
            user.setPhone(phone);
            user.setName(name);
            user.setImage(image);
            UserAccount.update(this);
            edt_phone.clearFocus();
            edt_name.clearFocus();
            Toast.makeText(this, R.string.update_success, Toast.LENGTH_SHORT).show();
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
        tv_dob.setText(Support.dateToString(user.getDate_of_birth(), "dd/MM/yyyy"));
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