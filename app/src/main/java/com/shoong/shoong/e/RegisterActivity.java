package com.shoong.shoong.e;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
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

public class RegisterActivity extends AppCompatActivity{

    EditText idText, passwordText, passwordconfirm, nameText, phoneText;
    private static final String REGISTER_URL = "http://sobike.iptime.org:8080/Register.php";

    @Override
    protected void attachBaseContext (Context newBase) {

        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        idText = (EditText)findViewById(R.id.ridText);
        passwordText = (EditText)findViewById(R.id.rpasswordText);
        passwordconfirm = (EditText)findViewById(R.id.passwordConfirm);
        nameText = (EditText)findViewById(R.id.name);
        phoneText = (EditText)findViewById(R.id.phone);
        Button confirmbutton = (Button)findViewById(R.id.confirmBtn);
        TextView cancelbutton = (TextView)findViewById(R.id.cancelBtn);

        //휴대폰 '-' 처리
        phoneText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRegister();
            }
        });

        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void userRegister() {
        final String id = idText.getText().toString();
        final String password = passwordText.getText().toString();
        final String passwordCnf = passwordconfirm.getText().toString();
        final String name = nameText.getText().toString();
        final String phone = phoneText.getText().toString();

        if (TextUtils.isEmpty(id)) {
            idText.setError("아이디를 입력해주세요.");
            idText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordText.setError("비밀번호를 입력해주세요.");
            passwordText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(passwordCnf)) {
            passwordconfirm.setError("비밀번호 확인을 입력해주세요.");
            passwordconfirm.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(name)) {
            nameText.setError("이름을 입력해주세요.");
            nameText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            phoneText.setError("전화번호를 입력해주세요.");
            phoneText.requestFocus();
            return;
        }

        if (!password.equals(passwordCnf)) {
            passwordconfirm.setError("비밀번호와 다릅니다. 다시 확인해주세요.");
            passwordconfirm.requestFocus();
            return;
        }

        StringRequest strReq = new StringRequest(Request.Method.POST, REGISTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject JsonResponse = new JSONObject(s);
                    boolean success = JsonResponse.getBoolean("success");
                    int errorcode = JsonResponse.getInt("errorcode");
                    if (success) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        final Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        builder.setMessage("회원가입이 되었습니다.")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        RegisterActivity.this.startActivity(intent);
                                        RegisterActivity.this.finish();
                                    }
                                })
                                .create()
                                .show();
                    } else if (!success && errorcode == 1) {
                        idText.requestFocus();
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("존재하는 아이디입니다.")
                                .setNegativeButton("다시 시도", null)
                                .create()
                                .show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("회원가입에 실패했습니다.")
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
                Log.e("RegisterActivity", "Register Error : " + volleyError.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //Posting params to Register URL
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("password", password);
                params.put("name", name);
                params.put("phone", phone);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(strReq);
    }
}
