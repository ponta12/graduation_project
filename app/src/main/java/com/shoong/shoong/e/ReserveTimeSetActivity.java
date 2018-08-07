package com.shoong.shoong.e;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ReserveTimeSetActivity extends AppCompatActivity implements View.OnClickListener{

    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;

    private TextView bt_start, bt_end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservetimeset);

        bt_start = (TextView)findViewById(R.id.startBtn);
        bt_end = (TextView)findViewById(R.id.endBtn);

        bt_start.setOnClickListener(this);
        bt_end.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startBtn:
                break;
            case R.id.endBtn:
                break;
        }
    }

    private void callFragment(int fragment_no) {

        //프레그먼트 사용을 위해 등록
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (fragment_no) {
            case FRAGMENT1:
                // 1번 탭 호출
                break;
            case FRAGMENT2:
                // 2번 탭 호출
                break;
        }
    }

    public static class Fragment1 extends Fragment {

        public Fragment1() {
            //빈 생성자가 요구됨
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        }
    }
}
