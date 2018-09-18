package com.shoong.shoong.e;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ReserveActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private static final String GET_DOMICILE_URL = "http://sobike.iptime.org:8080/p_search_zone.php";
    private static final String BIKE_SEARCH_URL = "http://sobike.iptime.org:8080/p_search_bike.php";
    private static final String RESERVE_BIKE_URL = "http://sobike.iptime.org:8080/p_reservation_bike.php";
    private static final String MAP_SETTING_URL = "http://sobike.iptime.org:8080/p_view_point.php";

    GoogleMap mMap;
    private Button homebtn, sharebtn, reservebtn, smartkeybtn, mypagebtn;
    Button searchbtn;
    TextView starttime, startdate, startweekday, endtime, enddate, endweekday, total;
    EditText domicile;
    private String userId, userName;
    private BackPressCloseHandler backPressCloseHandler;
    private static String startTime;
    private static String endTime;
    int startMonth, startDay, startHour, startMin, startwday, endMonth, endDay, endHour, endMin, endwday;
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

        startTime = "2018-";
        endTime = "2018-";

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

        searchbtn = (Button)findViewById(R.id.searchbtn);
        domicile = (EditText)findViewById(R.id.domicile);

        searchbtn.setOnClickListener(this);
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
                Intent stintent = new Intent(ReserveActivity.this, ReserveTimeSetActivity.class);
                stintent.putExtra("number", 1);
                //startMonth, startDay, startHour, startMin, endMonth, endDay, endHour, endMin;
                stintent.putExtra("startMonth", startMonth);
                stintent.putExtra("startDay", startDay);
                stintent.putExtra("startHour", startHour);
                stintent.putExtra("startMin", startMin);
                stintent.putExtra("endMonth", endMonth);
                stintent.putExtra("endDay", endDay);
                stintent.putExtra("endHour", endHour);
                stintent.putExtra("endMin", endMin);
                stintent.putExtra("startwday", startwday);
                stintent.putExtra("endwday", endwday);
                startActivityForResult(stintent, 4908);
                break;
            case R.id.endDate:
            case R.id.endTime:
            case R.id.endWeekday:
                Intent eintent = new Intent(ReserveActivity.this, ReserveTimeSetActivity.class);
                eintent.putExtra("number", 2);
                eintent.putExtra("startMonth", startMonth);
                eintent.putExtra("startDay", startDay);
                eintent.putExtra("startHour", startHour);
                eintent.putExtra("startMin", startMin);
                eintent.putExtra("endMonth", endMonth);
                eintent.putExtra("endDay", endDay);
                eintent.putExtra("endHour", endHour);
                eintent.putExtra("endMin", endMin);
                eintent.putExtra("startwday", startwday);
                eintent.putExtra("endwday", endwday);
                startActivityForResult(eintent, 4908);
                break;
            case R.id.searchbtn:
                domicileSearch();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case 4908:
                    redataSetting(data);
                    break;
                case 6653:
                    bikeSearch(data.getIntExtra("id", 0), data.getStringExtra("name"));
                    break;
                case 1994:
                    reserveconfirm(data.getIntExtra("zone_id", 0), data.getIntExtra("holder_id", 0), data.getStringExtra("zone_name"));
                    break;
            }
        } else {
            startTime = "2018-";
            endTime = "2018-";
        }
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        StringRequest strReq = new StringRequest(Request.Method.POST, MAP_SETTING_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONArray array = new JSONArray(s);
                    for (int i=0; i<array.length(); i++) {
                        JSONObject item = array.getJSONObject(i);

                        LatLng position = new LatLng(item.getDouble("lat"), item.getDouble("lng"));

                        MarkerOptions markerOptions = new MarkerOptions()
                                .icon(BitmapDescriptorFactory.defaultMarker(200f))
                                .title(item.getString("zonename"))
                                .snippet(item.getInt("zoneid") + "")
                                .position(position);
                        mMap.addMarker(markerOptions);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("ReserveActivity", "MAP_SETTING Error : " + volleyError.getMessage());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(ReserveActivity.this);
        queue.add(strReq);

        mMap.setOnInfoWindowClickListener(this);

        LatLng hakyeonsan = new LatLng(36.625194, 127.457302);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hakyeonsan, 16));
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        bikeSearch(Integer.parseInt(marker.getSnippet()), marker.getTitle());
    }

    private void dateSetting() {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.add(end.MINUTE, + 40);
        startMonth = start.get(Calendar.MONTH) + 1;
        startDay = start.get(Calendar.DATE);
        startHour = start.get(Calendar.HOUR_OF_DAY);
        startwday = start.get(Calendar.DAY_OF_WEEK);
        startMin = (int)(Math.ceil((double)start.get(Calendar.MINUTE) / 10.0) * 10.0);
        endMonth = end.get(Calendar.MONTH) + 1;
        endDay = end.get(Calendar.DATE);
        endHour = end.get(Calendar.HOUR_OF_DAY);
        endMin = (int)(Math.ceil((double)end.get(Calendar.MINUTE) / 10.0) * 10.0);
        endwday = end.get(Calendar.DAY_OF_WEEK);

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

    private void redataSetting(Intent data) {
        Calendar start = Calendar.getInstance();

        startMonth = data.getIntExtra("startMonth", 1);
        startDay = data.getIntExtra("startDay", 1);
        startHour = data.getIntExtra("startHour", 0);
        startMin = data.getIntExtra("startMin", 0);
        startwday = data.getIntExtra("startwday", 1);
        endMonth = data.getIntExtra("endMonth", 1);
        endDay = data.getIntExtra("endDay", 1);
        endHour = data.getIntExtra("endHour", 0);
        endMin = data.getIntExtra("endMin", 0);
        endwday = data.getIntExtra("endwday", 1);

        start.set(Calendar.MONTH, startMonth);
        start.set(Calendar.DAY_OF_MONTH, startDay);

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

        int subDay, subHour, subMin;
        if (endMonth == startMonth) subDay = endDay - startDay;
        else subDay = endDay + start.getActualMaximum(Calendar.DATE) - startDay;
        subHour = endHour - startHour;
        subMin = endMin - startMin;
        if (subHour < 0) {
            subDay -= 1;
            subHour = 24 + subHour;
        }
        if (subMin < 0) {
            subHour -= 1;
            subMin = 60 + subMin;
        }

        starttime.setText(startHour + "시 " + startMin + "분");
        startdate.setText(startMonth + "/" + startDay);
        startweekday.setText(startWd);
        endtime.setText(endHour + "시 " + endMin + "분");
        enddate.setText(endMonth + "/" + endDay);
        endweekday.setText(endWd);
        total.setText("총 " + subDay + "일 " + subHour + "시간 " + subMin + "분 이용");
    }

    private void domicileSearch() {
        final String domicileInput = domicile.getText().toString();

        if (TextUtils.isEmpty(domicileInput)) {
            domicile.setError("주소를 입력해주세요.");
            domicile.requestFocus();
            return;
        }

        StringRequest strReq = new StringRequest(Request.Method.POST, GET_DOMICILE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONArray jsonResponse = new JSONArray(s);
                    if (jsonResponse.length() != 0) {
                        Intent intent = new Intent(ReserveActivity.this, SelectShareZoneActivity.class);
                        intent.putExtra("jsonResponse", jsonResponse.toString());
                        startActivityForResult(intent, 6653);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ReserveActivity.this);
                        builder.setMessage("검색하신 주소에 대여존이 없습니다.")
                                .setNegativeButton("다시 시도", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("ReserveActivity", "GET_DOMICILE Error : " + volleyError.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("text", domicileInput);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ReserveActivity.this);
        queue.add(strReq);
    }

    private void bikeSearch(final int zone_id, final String zone_name) {

        if (startMonth < 10) startTime = startTime + "0" + startMonth  + "-";
        else startTime = startTime + startMonth  + "-";
        if (endMonth < 10) endTime = endTime + "0" + endMonth  + "-";
        else endTime = endTime + endMonth  + "-";
        if (startDay < 10) startTime = startTime + "0" + startDay + " ";
        else startTime = startTime + startDay + " ";
        if (endDay < 10) endTime = endTime + "0" + endDay + " ";
        else endTime = endTime + endDay + " ";
        if (startHour < 10) startTime = startTime + "0" + startHour + ":";
        else startTime = startTime + startHour + ":";
        if (endHour < 10) endTime = endTime + "0" + endHour + ":";
        else endTime = endTime + endHour + ":";
        if (startMin < 10) startTime = startTime + "0" + startMin + ":";
        else startTime = startTime + startMin + ":";
        if (endMin < 10) endTime = endTime + "0" + endMin + ":";
        else endTime = endTime + endMin + ":";
        startTime = startTime + "00";
        endTime = endTime + "00";

        final String mstartTime = startTime;
        final String mendTime = endTime;

        StringRequest strReq = new StringRequest(Request.Method.POST, BIKE_SEARCH_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONArray jsonResponse = new JSONArray(s);
                    if (jsonResponse.length() != 0) {
                        Intent intent = new Intent(ReserveActivity.this, SelectBikeActivity.class);
                        intent.putExtra("jsonResponse", jsonResponse.toString());
                        intent.putExtra("zonename", zone_name);
                        startActivityForResult(intent, 1994);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ReserveActivity.this);
                        builder.setMessage("해당 시간, 해당 대여존에 자전거가 없습니다.")
                                .setNegativeButton("다시 시도", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startTime = "2018-";
                                        endTime = "2018-";
                                        return;
                                    }
                                })
                                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                                    @Override
                                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                                            startTime = "2018-";
                                            endTime = "2018-";
                                            dialog.dismiss();
                                            return true;
                                        }
                                        return false;
                                    }
                                })
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("ReserveActivity", "BIKE_SEARCH Error : " + volleyError.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("zoneid", zone_id+"");
                params.put("start", mstartTime);
                params.put("end", mendTime);

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ReserveActivity.this);
        queue.add(strReq);
    }

    private void reserveconfirm(final int zone_id, final int holder_id, final String zone_name) {
        final String mstartTime = startTime;
        final String mendTime = endTime;
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(ReserveActivity.this);
        mbuilder.setMessage("시작 시간 : " + mstartTime + "\n" +
        "종료 시간 : " + mendTime + "\n" +
        "대여존 이름 : " + zone_name + "\n" +
        "거치대 번호 : " + holder_id + "\n" +
        "예약하시겠습니까?")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startTime = "2018-";
                        endTime = "2018-";
                        return;
                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringRequest strReq = new StringRequest(Request.Method.POST, RESERVE_BIKE_URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(s);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if (success) {
                                        final Intent intent = new Intent(ReserveActivity.this, MainActivity.class);
                                        intent.putExtra("userId", userId);
                                        intent.putExtra("userName", userName);
                                        ReserveActivity.this.startActivity(intent);
                                        ReserveActivity.this.finish();

                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ReserveActivity.this);
                                        builder.setMessage("예약에 실패했습니다.")
                                                .setNegativeButton("다시 시도", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        startTime = "2018-";
                                                        endTime = "2018-";
                                                        return;
                                                    }
                                                })
                                                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                                                    @Override
                                                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                                                            startTime = "2018-";
                                                            endTime = "2018-";
                                                            dialog.dismiss();
                                                            return true;
                                                        }
                                                        return false;
                                                    }
                                                })
                                                .create()
                                                .show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.e("ReserveActivity", "Reserve Error : " + volleyError.getMessage());
                                startTime = "2018-";
                                endTime = "2018-";
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                //Posting params to login url
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("id", userId);
                                params.put("zoneid", zone_id+"");
                                params.put("holderid", holder_id+"");
                                params.put("starttime", mstartTime);
                                params.put("endtime", mendTime);
                                return params;
                            }
                        };
                        RequestQueue queue = Volley.newRequestQueue(ReserveActivity.this);
                        queue.add(strReq);
                    }
                })
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            startTime = "2018-";
                            endTime = "2018-";
                            dialog.dismiss();
                            return true;
                        }
                        return false;
                    }
                })
                .create()
                .show();
    }
}