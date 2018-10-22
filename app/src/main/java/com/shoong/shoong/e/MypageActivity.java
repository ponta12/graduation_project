package com.shoong.shoong.e;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MypageActivity extends AppCompatActivity implements View.OnClickListener, ListViewmypageAdapter.ListBtnClickListener {

    private static final String INIT_URL = "http://sobike.iptime.org:8080/p_view_userinfo.php";
    private static final String CANCEL_URL = "http://sobike.iptime.org:8080/p_cancel_reservation.php";

    private Button homebtn, sharebtn, reservebtn, smartkeybtn, mypagebtn;
    private String userId, userName, userPhone;
    private BackPressCloseHandler backPressCloseHandler;

    TextView mp_id, mp_phone, mp_name;
    Button pwd_change, info_change, logout;

    SharedPreferences setting;
    SharedPreferences.Editor editor;

    ArrayList<HashMap<String, String>> marrayList;
    ListView mlistView;
    JSONArray array;

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

        //마이페이지 id 및 이름 보여지게 설정
        mp_id = (TextView)findViewById(R.id.mp_id);
        mp_id.setText("아이디 : " + userId);
        mp_phone = (TextView)findViewById(R.id.mp_phone);
        mp_name = (TextView)findViewById(R.id.mp_name);
        mp_name.setText(userName + "님,");

        //비밀번호변경, 개인정보변경, 로그아웃 버튼
        pwd_change = (Button)findViewById(R.id.pwd_change);
        info_change = (Button)findViewById(R.id.info_change);
        logout = (Button)findViewById(R.id.logoutBtn);

        //기존 메뉴 버튼
        homebtn = (Button)findViewById(R.id.homebtn5);
        reservebtn = (Button)findViewById(R.id.reservebtn5);
        smartkeybtn = (Button)findViewById(R.id.smartkeybtn5);
        mypagebtn = (Button)findViewById(R.id.mypagebtn5);

        mlistView = (ListView)findViewById(R.id.Listview_mypage);
        marrayList = new ArrayList<>();

        setting = getSharedPreferences("setting", 0);
        editor = setting.edit();

        homebtn.setOnClickListener(this);
        reservebtn.setOnClickListener(this);
        smartkeybtn.setOnClickListener(this);
        mypagebtn.setOnClickListener(this);

        pwd_change.setOnClickListener(this);
        info_change.setOnClickListener(this);
        logout.setOnClickListener(this);

        init();
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
                editor.clear();
                editor.commit();
                Intent logout = new Intent(MypageActivity.this, LoginActivity.class);
                startActivity(logout);
                finish();
                break;
            case R.id.pwd_change:
                Intent intent_pwd = new Intent(MypageActivity.this, PwdChangeActivity.class);
                intent_pwd.putExtra("userId", userId);
                intent_pwd.putExtra("userName", userName);
                startActivity(intent_pwd);
                break;
            case R.id.info_change:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    public void init() {
        StringRequest strReq = new StringRequest(Request.Method.POST, INIT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    System.err.println(s);
                    array = new JSONArray(s);
                    JSONObject jobj = array.getJSONObject(0);
                    userPhone =  jobj.getString("phone");
                    mp_phone.setText("Phone : " + userPhone);
                    set_list();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("MypageActivity", "INIT Error : " + volleyError.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", userId);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(MypageActivity.this);
        queue.add(strReq);
    }

    private void set_list() {
        ArrayList<ListViewMypageItem> items = new ArrayList<ListViewMypageItem>();

        try {
            ListViewMypageItem litem;
            JSONObject item;
            for(int i=1; i<array.length(); i++) {
                item = array.getJSONObject(i);
                litem = new ListViewMypageItem();
                litem.setZoneName(item.getString("zonename"));
                litem.setHolderId(item.getString("holderid"));
                litem.setStartTime(item.getString("starttime"));
                litem.setEndTime(item.getString("endtime"));
                items.add(litem);
            }

            ListViewmypageAdapter adapter = new ListViewmypageAdapter(this, R.layout.reserve_list, items, this);

            mlistView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onListBtnClick(int position) {
        try {
            final JSONObject jsonObject = array.getJSONObject(position+1);
            final String strtime = jsonObject.getString("starttime");
            StringRequest strReq = new StringRequest(Request.Method.POST, CANCEL_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    System.err.println(s);
                    try {
                        JSONObject returnObj = new JSONObject(s);
                        boolean success = returnObj.getBoolean("success");
                        if (success) {
                            final Intent intent = new Intent(MypageActivity.this, MypageActivity.class);
                            AlertDialog.Builder builder = new AlertDialog.Builder(MypageActivity.this);
                            builder.setMessage("취소 완료!")
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            intent.putExtra("userId", userId);
                                            intent.putExtra("userName", userName);
                                            startActivity(intent);
                                            finish();
                                        }
                                    })
                                    .create()
                                    .show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MypageActivity.this);
                            builder.setMessage("취소 실패!")
                                    .setPositiveButton("확인", null)
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
                    Log.e("MypageActivity", "CANCEL Error : " + volleyError.getMessage());
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", userId);
                    params.put("start", strtime);
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(MypageActivity.this);
            queue.add(strReq);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
