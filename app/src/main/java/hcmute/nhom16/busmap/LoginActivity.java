package hcmute.nhom16.busmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hcmute.nhom16.busmap.model.UserAccount;

public class LoginActivity extends AppCompatActivity {
    EditText edt_email, edt_pass;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        initListener();
    }

    private void initListener() {
        btn_login.setOnClickListener(v -> {
            String email = edt_email.getText().toString();
            String pass = edt_pass.getText().toString();
            if (email.equals("") || pass.equals("")) {
                Toast.makeText(this, R.string.fill, Toast.LENGTH_SHORT).show();
            } else {
                if (UserAccount.login(email, pass) != null) {
                    Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, R.string.login_fail, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initUI() {
        edt_email = findViewById(R.id.edt_email);
        edt_pass = findViewById(R.id.edt_pass);
        btn_login = findViewById(R.id.btn_login);
    }
}