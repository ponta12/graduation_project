package com.shoong.shoong.e;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button homebtn, sharebtn, reservebtn, smartkeybtn, mypagebtn;
    private String userId, userName;
    private BackPressCloseHandler backPressCloseHandler;

    AutoScrollViewPager autoViewPager;
    AutoScrollAdapter scrollAdapter;
    int count;
    private int mPrevPostion;

    private LinearLayout mPageMark;

    @Override
    protected void attachBaseContext (Context newBase) {

        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backPressCloseHandler = new BackPressCloseHandler(this);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        userName = intent.getStringExtra("userName");

        homebtn = (Button)findViewById(R.id.homebtn1);
        sharebtn = (Button)findViewById(R.id.sharebtn1);
        reservebtn = (Button)findViewById(R.id.reservebtn1);
        smartkeybtn = (Button)findViewById(R.id.smartkeybtn1);
        mypagebtn = (Button)findViewById(R.id.mypagebtn1);

        mPageMark = (LinearLayout)findViewById(R.id.page_mark);

        homebtn.setOnClickListener(this);
        sharebtn.setOnClickListener(this);
        reservebtn.setOnClickListener(this);
        smartkeybtn.setOnClickListener(this);
        mypagebtn.setOnClickListener(this);

        int[] data = {R.drawable.ex1, R.drawable.ex2, R.drawable.ex3, R.drawable.ex4, R.drawable.ex5};
        count = data.length;

        autoViewPager = (AutoScrollViewPager)findViewById(R.id.autoViewPager);
        scrollAdapter = new AutoScrollAdapter(this, data);
        autoViewPager.setAdapter(scrollAdapter); //Auto Viewpager에 Adapter 장착
        autoViewPager.setCurrentItem(data.length);

        autoViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float postionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position < count)
                    autoViewPager.setCurrentItem(position + count, false);
                else if (position >= count*2)
                    autoViewPager.setCurrentItem(position - count, false);
                else {
                    position -= count;
                    mPageMark.getChildAt(mPrevPostion).setBackgroundResource(R.drawable.page_not);
                    mPageMark.getChildAt(position).setBackgroundResource(R.drawable.page_select);
                    mPrevPostion = position;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        initPageMark();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homebtn1:
                break;
            case R.id.sharebtn1:
                Intent intent1 = new Intent(MainActivity.this, ShareActivity.class);
                intent1.putExtra("userId", userId);
                intent1.putExtra("userName", userName);
                startActivity(intent1);
                finish();
                break;
            case R.id.reservebtn1:
                Intent intent2 = new Intent(MainActivity.this, ReserveActivity.class);
                intent2.putExtra("userId", userId);
                intent2.putExtra("userName", userName);
                startActivity(intent2);
                finish();
                break;
            case R.id.smartkeybtn1:
                Intent intent3 = new Intent(MainActivity.this, SmartkeyActivity.class);
                intent3.putExtra("userId", userId);
                intent3.putExtra("userName", userName);
                startActivity(intent3);
                finish();
                break;
            case R.id.mypagebtn1:
                Intent intent4 = new Intent(MainActivity.this, MypageActivity.class);
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
    public void onResume() {
        super.onResume();

        scrollAdapter.notifyDataSetChanged();
        autoViewPager.setInterval(5000); // 페이지 넘어갈 시간 간격 설정
        autoViewPager.startAutoScroll(); //Auto Scroll 시작
    }

    @Override
    public void onPause() {
        super.onPause();
        autoViewPager.stopAutoScroll();
    }

    private void initPageMark() {
        for(int i=0; i<count; i++) {
            ImageView iv = new ImageView(getApplicationContext());
            iv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            if (i==0) iv.setBackgroundResource(R.drawable.page_select);
            else iv.setBackgroundResource(R.drawable.page_not);
            mPageMark.addView(iv);
        }
        mPrevPostion = 0;
    }
}
