package hcmute.nhom16.busmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        ImageView iv = findViewById(R.id.iv_load);
        iv.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }).start();
    }
}