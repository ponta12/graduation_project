package com.shoong.shoong.e;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

public class ReserveTimeSetActivity extends AppCompatActivity implements View.OnClickListener{

    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;

    private TextView bt_start, bt_end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservetimeset);

        Intent intent = getIntent();

        bt_start = (TextView)findViewById(R.id.startBtn);
        bt_end = (TextView)findViewById(R.id.endBtn);

        int number = intent.getIntExtra("number", 1);

        bt_start.setOnClickListener(this);
        bt_end.setOnClickListener(this);

        if (number == 1) {
            bt_start.setTextColor(Color.rgb(255, 255, 255));
            bt_start.setBackgroundColor(Color.rgb(11, 194, 252));
            callFragment(FRAGMENT1);
        }
        else {
            bt_end.setTextColor(Color.rgb(255, 255, 255));
            bt_end.setBackgroundColor(Color.rgb(11, 194, 252));
            callFragment(FRAGMENT2);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startBtn:
                bt_start.setTextColor(Color.rgb(255, 255, 255));
                bt_start.setBackgroundColor(Color.rgb(11, 194, 252));
                bt_end.setTextColor(Color.BLACK);
                bt_end.setBackgroundColor(Color.WHITE);
                callFragment(FRAGMENT1);
                break;
            case R.id.endBtn:
                bt_end.setTextColor(Color.rgb(255, 255, 255));
                bt_end.setBackgroundColor(Color.rgb(11, 194, 252));
                bt_start.setTextColor(Color.BLACK);
                bt_start.setBackgroundColor(Color.WHITE);
                callFragment(FRAGMENT2);
                break;
        }
    }

    private void callFragment(int fragment_no) {

        //프레그먼트 사용을 위해 등록
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (fragment_no) {
            case FRAGMENT1:
                // 1번 탭 호출
                transaction.replace(R.id.fragment_container, new Fragment1());
                transaction.commit();
                break;
            case FRAGMENT2:
                // 2번 탭 호출
                transaction.replace(R.id.fragment_container, new Fragment2());
                transaction.commit();
                break;
        }
    }

    public static class Fragment1 extends Fragment {

        public Fragment1() {
            //빈 생성자가 요구됨
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
            TimePicker timePicker = (TimePicker) view.findViewById(R.id.timePicker1);
            timePicker.setIs24HourView(true);

            return view;
        }
    }

    public static class Fragment2 extends Fragment {

        public Fragment2() {
            //빈 생성자가 요구됨
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_fragment2, container, false);
            TimePicker timePicker = (TimePicker) view.findViewById(R.id.timePicker2);
            timePicker.setIs24HourView(true);



            return view;
        }
    }
}
