package com.shoong.shoong.e;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

import java.util.HashMap;
import java.util.Map;

public class SmartkeyActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String RENT_URL = "http://sobike.iptime.org:8080/p_renting.php";
    private static final String RETURN_URL = "http://sobike.iptime.org:8080/p_return.php";


    private Button homebtn, sharebtn, reservebtn, smartkeybtn, mypagebtn;
    private String userId, userName;
    private BackPressCloseHandler backPressCloseHandler;
    private Button rentbtn, returnbtn;

    @Override
    protected void attachBaseContext (Context newBase) {

        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_key);

        backPressCloseHandler = new BackPressCloseHandler(this);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        userName = intent.getStringExtra("userName");

        homebtn = (Button)findViewById(R.id.homebtn4);
        sharebtn = (Button)findViewById(R.id.sharebtn4);
        reservebtn = (Button)findViewById(R.id.reservebtn4);
        smartkeybtn = (Button)findViewById(R.id.smartkeybtn4);
        mypagebtn = (Button)findViewById(R.id.mypagebtn4);

        rentbtn = (Button)findViewById(R.id.rentbtn);
        returnbtn = (Button)findViewById(R.id.returnbtn);

        homebtn.setOnClickListener(this);
        sharebtn.setOnClickListener(this);
        reservebtn.setOnClickListener(this);
        smartkeybtn.setOnClickListener(this);
        mypagebtn.setOnClickListener(this);

        rentbtn.setOnClickListener(this);
        returnbtn.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homebtn4:
                Intent intent1 = new Intent(SmartkeyActivity.this, MainActivity.class);
                intent1.putExtra("userId", userId);
                intent1.putExtra("userName", userName);
                startActivity(intent1);
                finish();
                break;
            case R.id.sharebtn4:
                Intent intent2 = new Intent(SmartkeyActivity.this, ShareActivity.class);
                intent2.putExtra("userId", userId);
                intent2.putExtra("userName", userName);
                startActivity(intent2);
                finish();
                break;
            case R.id.reservebtn4:
                Intent intent3 = new Intent(SmartkeyActivity.this, ReserveActivity.class);
                intent3.putExtra("userId", userId);
                intent3.putExtra("userName", userName);
                startActivity(intent3);
                finish();
                break;
            case R.id.smartkeybtn4:
                break;
            case R.id.mypagebtn4:
                Intent intent4 = new Intent(SmartkeyActivity.this, MypageActivity.class);
                intent4.putExtra("userId", userId);
                intent4.putExtra("userName", userName);
                startActivity(intent4);
                finish();
                break;
            case R.id.rentbtn:
                rentreturn(1);
                break;
            case R.id.returnbtn:
                rentreturn(0);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    private void rentreturn(final int flag) {
        if (flag == 1) {
            System.out.println("flag = 1");
            StringRequest strReq = new StringRequest(Request.Method.POST, RENT_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    try {
                        System.out.println(s);
                        JSONObject jsonResponse = new JSONObject(s);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SmartkeyActivity.this);
                            builder.setMessage("대여!!!")
                                    .setPositiveButton("확인", null)
                                    .create()
                                    .show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SmartkeyActivity.this);
                            builder.setMessage("실패!!!")
                                    .setNegativeButton("확인", null)
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
                    Log.e("SmartKeyActivity", "RENT Error : " + volleyError.getMessage());
                }
            }) {
              @Override
              protected Map<String, String> getParams() {
                  Map<String, String> params = new HashMap<String, String>();
                  params.put("id", "1");
                  params.put("flag", flag+"");
                  return params;
              }
            };
            RequestQueue queue = Volley.newRequestQueue(SmartkeyActivity.this);
            queue.add(strReq);
        } else {
            System.out.println("flag = 0");
            StringRequest strReq = new StringRequest(Request.Method.POST, RETURN_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    try {
                        System.out.println(s);
                        JSONObject jsonResponse = new JSONObject(s);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SmartkeyActivity.this);
                            builder.setMessage("반납!!!")
                                    .setPositiveButton("확인", null)
                                    .create()
                                    .show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SmartkeyActivity.this);
                            builder.setMessage("실패!!!")
                                    .setNegativeButton("확인", null)
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
                    Log.e("SmartKeyActivity", "RENT Error : " + volleyError.getMessage());
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", "1");
                    params.put("flag", flag+"");
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(SmartkeyActivity.this);
            queue.add(strReq);
        }
    }
}
