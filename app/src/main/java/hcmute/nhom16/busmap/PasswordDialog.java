package hcmute.nhom16.busmap;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import hcmute.nhom16.busmap.data.UserDAO;
import hcmute.nhom16.busmap.model.UserAccount;

public class PasswordDialog extends Dialog {
    EditText edt_old, edt_new, edt_re_new;
    TextView tv_ok, tv_cancel;
    public PasswordDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_dialog);

        initUI();
        initListener();
    }

    private void initUI() {
        edt_old = findViewById(R.id.edt_old);
        edt_new = findViewById(R.id.edt_new);
        edt_re_new = findViewById(R.id.edt_re_new);
        tv_ok = findViewById(R.id.tv_ok);
        tv_cancel = findViewById(R.id.tv_cancel);
    }

    private void initListener() {
        tv_cancel.setOnClickListener(v -> {
            dismiss();
        });
        tv_ok.setOnClickListener(v -> {
            String old_password = edt_old.getText().toString();
            String re_new_password = edt_re_new.getText().toString();
            String new_password = edt_new.getText().toString();
            if (old_password.equals(new_password)) {
                Toast.makeText(getContext(), R.string.duplicate_password, Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Support.checkInvalidPassword(new_password)) {
                Toast.makeText(getContext(), R.string.uninvalid_password, Toast.LENGTH_SHORT).show();
                return;
            }
            if (re_new_password.equals(new_password)) {
                if (UserDAO.changePassword(getContext(), UserAccount.getUser().getEmail(),
                        old_password, new_password)) {
                    Toast.makeText(getContext(), R.string.change_password_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.change_password_fail, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), R.string.not_match_password, Toast.LENGTH_SHORT).show();
            }
            dismiss();
        });
    }
}
