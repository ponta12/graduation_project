package com.shoong.shoong.e;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Handler handler = new Handler();
        final Handler handler1 = new Handler();
        final LinearLayout back = (LinearLayout)findViewById(R.id.intro);
        final int[] img = {R.drawable.intro1, R.drawable.intro2, R.drawable.intro3};

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler1.post(new Runnable() {
                    @Override
                    public void run() {
                        Random ran = new Random();
                        int num = ran.nextInt(img.length);

                        back.setBackgroundResource(img[num]);
                    }
                });
            }
        }, 0, 100);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3500);
    }

}