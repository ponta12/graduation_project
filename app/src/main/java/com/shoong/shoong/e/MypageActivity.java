package com.shoong.shoong.e;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tsengvn.typekit.TypekitContextWrapper;

public class MypageActivity extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_mypage);

        backPressCloseHandler = new BackPressCloseHandler(this);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        userName = intent.getStringExtra("userName");

        homebtn = (Button)findViewById(R.id.homebtn5);
        sharebtn = (Button)findViewById(R.id.sharebtn5);
        reservebtn = (Button)findViewById(R.id.reservebtn5);
        smartkeybtn = (Button)findViewById(R.id.smartkeybtn5);
        mypagebtn = (Button)findViewById(R.id.mypagebtn5);

        homebtn.setOnClickListener(this);
        sharebtn.setOnClickListener(this);
        reservebtn.setOnClickListener(this);
        smartkeybtn.setOnClickListener(this);
        mypagebtn.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homebtn5:
                Intent intent1 = new Intent(MypageActivity.this, MainActivity.class);
                intent1.putExtra("userId", userId);
                intent1.putExtra("userName", userName);
                startActivity(intent1);
                finish();
                break;
            case R.id.sharebtn5:
                Intent intent2 = new Intent(MypageActivity.this, ShareActivity.class);
                intent2.putExtra("userId", userId);
                intent2.putExtra("userName", userName);
                startActivity(intent2);
                finish();
                break;
            case R.id.reservebtn5:
                Intent intent3 = new Intent(MypageActivity.this, ReserveActivity.class);
                intent3.putExtra("userId", userId);
                intent3.putExtra("userName", userName);
                startActivity(intent3);
                finish();
                break;
            case R.id.smartkeybtn5:
                Intent intent4 = new Intent(MypageActivity.this, SmartkeyActivity.class);
                intent4.putExtra("userId", userId);
                intent4.putExtra("userName", userName);
                startActivity(intent4);
                finish();
                break;
            case R.id.mypagebtn5:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}
