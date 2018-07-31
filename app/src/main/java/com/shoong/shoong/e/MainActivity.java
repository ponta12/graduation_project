package com.shoong.shoong.e;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button homebtn, sharebtn, reservebtn, smartkeybtn, mypagebtn;
    private String userId, userName;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void attachBaseContext (Context newBase) {

        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backPressCloseHandler = new BackPressCloseHandler(this);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        userName = intent.getStringExtra("userName");

        homebtn = (Button)findViewById(R.id.homebtn1);
        sharebtn = (Button)findViewById(R.id.sharebtn1);
        reservebtn = (Button)findViewById(R.id.reservebtn1);
        smartkeybtn = (Button)findViewById(R.id.smartkeybtn1);
        mypagebtn = (Button)findViewById(R.id.mypagebtn1);

        homebtn.setOnClickListener(this);
        sharebtn.setOnClickListener(this);
        reservebtn.setOnClickListener(this);
        smartkeybtn.setOnClickListener(this);
        mypagebtn.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homebtn1:
                break;
            case R.id.sharebtn1:
                Intent intent1 = new Intent(MainActivity.this, ShareActivity.class);
                intent1.putExtra("userId", userId);
                intent1.putExtra("userName", userName);
                startActivity(intent1);
                finish();
                break;
            case R.id.reservebtn1:
                Intent intent2 = new Intent(MainActivity.this, ReserveActivity.class);
                intent2.putExtra("userId", userId);
                intent2.putExtra("userName", userName);
                startActivity(intent2);
                finish();
                break;
            case R.id.smartkeybtn1:
                Intent intent3 = new Intent(MainActivity.this, SmartkeyActivity.class);
                intent3.putExtra("userId", userId);
                intent3.putExtra("userName", userName);
                startActivity(intent3);
                finish();
                break;
            case R.id.mypagebtn1:
                Intent intent4 = new Intent(MainActivity.this, MypageActivity.class);
                intent4.putExtra("userId", userId);
                intent4.putExtra("userName", userName);
                startActivity(intent4);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

}
