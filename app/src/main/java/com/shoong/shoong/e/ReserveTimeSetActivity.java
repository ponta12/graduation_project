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
    Fragment1 fragment1 = new Fragment1();
    Fragment2 fragment2 = new Fragment2();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservetimeset);

        bt_start = (TextView)findViewById(R.id.startBtn);
        bt_end = (TextView)findViewById(R.id.endBtn);

        bt_start.setOnClickListener(this);
        bt_end.setOnClickListener(this);

        callFragment(FRAGMENT1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startBtn:
                callFragment(FRAGMENT1);
                break;
            case R.id.endBtn:
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
                transaction.replace(R.id.fragment_container, fragment1);
                transaction.commit();
                break;
            case FRAGMENT2:
                // 2번 탭 호출
                transaction.replace(R.id.fragment_container, fragment2);
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
            return inflater.inflate(R.layout.fragment_fragment1, container, false);
        }
    }

    public static class Fragment2 extends Fragment {

        public Fragment2() {
            //빈 생성자가 요구됨
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_fragment2, container, false);
        }
    }
}
