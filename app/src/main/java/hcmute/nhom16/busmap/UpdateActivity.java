package hcmute.nhom16.busmap;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class UpdateActivity extends AppCompatActivity {
    TextView tv_date, tv_percent;
    Button btn_update;
    AlertDialog.Builder builder;
    ProgressBar pgb_update;
    boolean back = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(R.string.update_data);

        initUI();
        initListener();
    }

    public void initUI() {
        tv_date = findViewById(R.id.tv_date);
        btn_update = findViewById(R.id.btn_update);
        builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        builder.setTitle(R.string.update_data)
                .setMessage(R.string.update_content)
                .setPositiveButton(R.string.download, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                update();
            }
        });
        builder.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        tv_percent = findViewById(R.id.tv_percent);
        pgb_update = findViewById(R.id.pgb_update);
    }

    public void update() {
        back = false;
        Random rd = new Random();
        new Thread(() -> {
            int process = 0, second = 0, max = pgb_update.getMax();
            while (process < max) {
                process += rd.nextInt(10);
                second += rd.nextInt(5) + 5;
                second = second < process ? process + 5 : second;
                int finalProcess = process;
                int finalSecond = second;
                runOnUiThread(() -> {
                    pgb_update.setProgress(finalProcess);
                    pgb_update.setSecondaryProgress(finalSecond);
                    tv_percent.setText(String.valueOf(finalProcess / 10) + "%");
                });
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            runOnUiThread(() -> {
                back = true;
                Toast.makeText(UpdateActivity.this, R.string.update_success, Toast.LENGTH_SHORT).show();
            });
        }).start();
    }

    public void initListener() {
        btn_update.setOnClickListener(v -> {
            builder.create().show();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (back) {
            super.onBackPressed();
        }
    }
}