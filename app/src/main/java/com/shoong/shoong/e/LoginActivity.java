package com.shoong.shoong.e;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText idText, passwordText;
    private static final String LOGIN_URL = "http://sobike.iptime.org:8080/Login.php";
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void attachBaseContext (Context newBase) {

        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        backPressCloseHandler = new BackPressCloseHandler(this);

        idText = (EditText)findViewById(R.id.idText);
        passwordText = (EditText)findViewById(R.id.passwordText);
        Button loginButton = (Button)findViewById(R.id.loginBtn);
        TextView registerButton = (TextView)findViewById(R.id.registerBtn);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }

    private void userLogin() {
        final String userid = idText.getText().toString();
        final String password = passwordText.getText().toString();

        if (TextUtils.isEmpty(userid)) {
            idText.setError("아이디를 입력해주세요.");
            idText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordText.setError("비밀번호를 입력해주세요.");
            passwordText.requestFocus();
            return;
        }

        StringRequest strReq = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonResponse = new JSONObject(s);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        String userName = jsonResponse.getString("userName");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("userId", userid);
                        intent.putExtra("userName", userName);
                        LoginActivity.this.startActivity(intent);
                        finish();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("아이디, 비밀번호를 다시 확인해주세요.")
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
                Log.e("LoginActivity", "Login Error : " + volleyError.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("userid", userid);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(strReq);
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}