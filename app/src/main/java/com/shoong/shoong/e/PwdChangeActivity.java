package com.shoong.shoong.e;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tsengvn.typekit.TypekitContextWrapper;

public class PwdChangeActivity extends AppCompatActivity{
    private static final String REGISTER_URL = "http://sobike.iptime.org:8080/Register.php";
    EditText new_pwd, now_pwd, new_pwd_confirm;


    @Override
    protected void attachBaseContext (Context newBase) {

        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwdchange);

        new_pwd = (EditText)findViewById(R.id.new_pwd);
        now_pwd = (EditText)findViewById(R.id.now_pwd);
        new_pwd_confirm = (EditText)findViewById(R.id.new_pwd_confirm);

        Button confirm = (Button)findViewById(R.id.change_confirm);
        TextView cancel = (TextView)findViewById(R.id.change_cancel);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwdChange();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void pwdChange() {
        final String now_password = now_pwd.getText().toString();
        final String new_password = new_pwd.getText().toString();
        final String new_passwordCnf = new_pwd_confirm.getText().toString();

        if (TextUtils.isEmpty(now_password)) {
            now_pwd.setError("현재 비밀번호를 입력하세요.");
            now_pwd.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(new_password)) {
            new_pwd.setError("새 비밀번호를 입력하세요.");
            new_pwd.requestFocus();
            return;
        }

        if (new_password.length() < 8 || new_password.length() > 20) {
            new_pwd.setError("새 비밀번호 길이가 맞지 않습니다. 다시 확인해주세요.");
            new_pwd.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(new_passwordCnf)) {
            new_pwd_confirm.setError("새 비밀번호 확인을 입력해주세요.");
            new_pwd_confirm.requestFocus();
            return;
        }

        if (!new_pwd.equals(new_pwd_confirm)) {
            new_pwd_confirm.setError("새 비밀번호와 다릅니다. 다시 확인해주세요.");
            new_pwd_confirm.requestFocus();
            return;
        }


    }
}
