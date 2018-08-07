package com.shoong.shoong.e;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.Calendar;

public class ReserveActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    GoogleMap mMap;
    private Button homebtn, sharebtn, reservebtn, smartkeybtn, mypagebtn;
    TextView starttime, startdate, startweekday, endtime, enddate, endweekday, total;
    private String userId, userName;
    private BackPressCloseHandler backPressCloseHandler;
    int startMonth, startDay, startHour, startMin, endMonth, endDay, endHour, endMin;
    String startWd, endWd;

    @Override
    protected void attachBaseContext (Context newBase) {

        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        backPressCloseHandler = new BackPressCloseHandler(this);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        userName = intent.getStringExtra("userName");


        homebtn = (Button)findViewById(R.id.homebtn3);
        sharebtn = (Button)findViewById(R.id.sharebtn3);
        reservebtn = (Button)findViewById(R.id.reservebtn3);
        smartkeybtn = (Button)findViewById(R.id.smartkeybtn3);
        mypagebtn = (Button)findViewById(R.id.mypagebtn3);

        startdate = (TextView)findViewById(R.id.startDate);
        starttime = (TextView)findViewById(R.id.startTime);
        startweekday = (TextView)findViewById(R.id.startWeekday);
        enddate = (TextView)findViewById(R.id.endDate);
        endtime = (TextView)findViewById(R.id.endTime);
        endweekday = (TextView)findViewById(R.id.endWeekday);
        total = (TextView)findViewById(R.id.totalUseTime);

        homebtn.setOnClickListener(this);
        sharebtn.setOnClickListener(this);
        reservebtn.setOnClickListener(this);
        smartkeybtn.setOnClickListener(this);
        mypagebtn.setOnClickListener(this);
        startdate.setOnClickListener(this);
        starttime.setOnClickListener(this);
        startweekday.setOnClickListener(this);
        enddate.setOnClickListener(this);
        endtime.setOnClickListener(this);
        endweekday.setOnClickListener(this);

        dateSetting();

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homebtn3:
                Intent intent1 = new Intent(ReserveActivity.this, MainActivity.class);
                intent1.putExtra("userId", userId);
                intent1.putExtra("userName", userName);
                startActivity(intent1);
                finish();
                break;
            case R.id.sharebtn3:
                Intent intent2 = new Intent(ReserveActivity.this, ShareActivity.class);
                intent2.putExtra("userId", userId);
                intent2.putExtra("userName", userName);
                startActivity(intent2);
                finish();
                break;
            case R.id.reservebtn3:
                break;
            case R.id.smartkeybtn3:
                Intent intent3 = new Intent(ReserveActivity.this, SmartkeyActivity.class);
                intent3.putExtra("userId", userId);
                intent3.putExtra("userName", userName);
                startActivity(intent3);
                finish();
                break;
            case R.id.mypagebtn3:
                Intent intent4 = new Intent(ReserveActivity.this, MypageActivity.class);
                intent4.putExtra("userId", userId);
                intent4.putExtra("userName", userName);
                startActivity(intent4);
                finish();
                break;
            case R.id.startDate:
            case R.id.startTime:
            case R.id.startWeekday:
                break;
            case R.id.endDate:
            case R.id.endTime:
            case R.id.endWeekday:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng hakyeonsan = new LatLng(36.625194, 127.457302);
        LatLng joongmoon = new LatLng(36.631541, 127.457071);
        LatLng centerLibrary = new LatLng(36.628743, 127.457397);

        MarkerOptions markerOptions1 = new MarkerOptions();
        MarkerOptions markerOptions2 = new MarkerOptions();
        MarkerOptions markerOptions3 = new MarkerOptions();
        markerOptions1
                .position(hakyeonsan)
                .title("학연산");
        markerOptions2
                .position(joongmoon)
                .title("중문");
        markerOptions3
                .position(centerLibrary)
                .title("중앙도서관");

        mMap.addMarker(markerOptions1);
        mMap.addMarker(markerOptions2);
        mMap.addMarker(markerOptions3);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hakyeonsan, 16));
    }

    private void dateSetting() {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.add(end.MINUTE, + 40);
        startMonth = start.get(Calendar.MONTH) + 1;
        startDay = start.get(Calendar.DATE);
        startHour = start.get(Calendar.HOUR_OF_DAY);
        int startwday = start.get(Calendar.DAY_OF_WEEK);
        startMin = (int)(Math.ceil((double)start.get(Calendar.MINUTE) / 10.0) * 10.0);
        endMonth = end.get(Calendar.MONTH) + 1;
        endDay = end.get(Calendar.DATE);
        endHour = end.get(Calendar.HOUR_OF_DAY);
        endMin = (int)(Math.ceil((double)end.get(Calendar.MINUTE) / 10.0) * 10.0);
        int endwday = end.get(Calendar.DAY_OF_WEEK);

        if (startMin == 60) {
            startHour += 1;
            startMin = 0;
            if (startHour == 24) {
                startHour = 0;
                startDay += 1;
                startwday = (startwday + 1) % 8 == 0 ? 1 : startwday + 1;
                if (startDay > start.getActualMaximum(Calendar.DATE)) {
                    startDay = 1;
                    startMonth = (startMonth + 1) % 13 == 0 ? 1 : startMonth + 1;
                }
            }
        }

        if (endMin == 60) {
            endHour += 1;
            endMin = 0;
            if (endHour == 24) {
                endHour = 0;
                endDay += 1;
                endwday = (endwday + 1) % 8 == 0 ? 1 : endwday + 1;
                if (endDay > end.getActualMaximum(Calendar.DATE)) {
                    endDay = 1;
                    endMonth = (endMonth + 1) % 13 == 0 ? 1 : endMonth + 1;
                }
            }
        }

        switch (startwday){
            case 1: startWd = "일요일"; break;
            case 2: startWd = "월요일"; break;
            case 3: startWd = "화요일"; break;
            case 4: startWd = "수요일"; break;
            case 5: startWd = "목요일"; break;
            case 6: startWd = "금요일"; break;
            case 7: startWd = "토요일"; break;
        }
        switch (endwday){
            case 1: endWd = "일요일"; break;
            case 2: endWd = "월요일"; break;
            case 3: endWd = "화요일"; break;
            case 4: endWd = "수요일"; break;
            case 5: endWd = "목요일"; break;
            case 6: endWd = "금요일"; break;
            case 7: endWd = "토요일"; break;
        }

        starttime.setText(startHour + "시 " + startMin + "분");
        startdate.setText(startMonth + "/" + startDay);
        startweekday.setText(startWd);
        endtime.setText(endHour + "시 " + endMin + "분");
        enddate.setText(endMonth + "/" + endDay);
        endweekday.setText(endWd);
        total.setText("총 40분 이용");
    }
}