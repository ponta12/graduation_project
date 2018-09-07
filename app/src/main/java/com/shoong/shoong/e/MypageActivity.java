package com.shoong.shoong.e;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tsengvn.typekit.TypekitContextWrapper;

public class MypageActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String INIT_URL = "http://sobike.iptime.org:8080/p_view_userinfo.php";

    private Button homebtn, sharebtn, reservebtn, smartkeybtn, mypagebtn;
    private String userId, userName;
    private BackPressCloseHandler backPressCloseHandler;

    TextView mp_id, mp_phone, mp_name;
    Button pwd_change, info_change, logout;

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

        mp_id = (TextView)findViewById(R.id.mp_id);
        mp_id.setText("아이디 : " + userId);
        mp_phone = (TextView)findViewById(R.id.mp_phone);
        mp_name = (TextView)findViewById(R.id.mp_name);
        mp_name.setText(userName + "님,");

        pwd_change = (Button)findViewById(R.id.pwd_change);
        info_change = (Button)findViewById(R.id.info_change);
        logout = (Button)findViewById(R.id.logoutBtn);

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

        pwd_change.setOnClickListener(this);
        info_change.setOnClickListener(this);
        logout.setOnClickListener(this);

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
            case R.id.logoutBtn:

                break;
            case R.id.pwd_change:
                break;
            case R.id.info_change:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}
