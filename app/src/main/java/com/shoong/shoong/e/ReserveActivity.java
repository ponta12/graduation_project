package com.shoong.shoong.e;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tsengvn.typekit.TypekitContextWrapper;

public class ReserveActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    GoogleMap mMap;
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

        homebtn.setOnClickListener(this);
        sharebtn.setOnClickListener(this);
        reservebtn.setOnClickListener(this);
        smartkeybtn.setOnClickListener(this);
        mypagebtn.setOnClickListener(this);

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
}