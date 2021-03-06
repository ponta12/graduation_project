package com.shoong.shoong.e;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.tsengvn.typekit.TypekitContextWrapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReserveTimeSetActivity extends AppCompatActivity implements View.OnClickListener{

    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;

    private TextView bt_start, bt_end;
    static int startMonth, startDay, startHour, startMin, startwday, endMonth, endDay, endHour, endMin, endwday;
    static int sval = 0, eval = 0;
    static Calendar start = Calendar.getInstance();
    static Calendar end = Calendar.getInstance();
    static TimePicker timePicker1, timePicker2;
    static NumberPicker datePicker1, datePicker2;

    @Override
    protected void attachBaseContext (Context newBase) {

        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservetimeset);

        Intent intent = getIntent();

        bt_start = (TextView)findViewById(R.id.startBtn);
        bt_end = (TextView)findViewById(R.id.endBtn);
        Button confirm = (Button)findViewById(R.id.pickerbtn);

        int number = intent.getIntExtra("number", 1);
        startMonth = intent.getIntExtra("startMonth", 1);
        startDay = intent.getIntExtra("startDay", 1);
        startHour = intent.getIntExtra("startHour", 0);
        startMin = intent.getIntExtra("startMin", 0);
        endMonth = intent.getIntExtra("endMonth", 1);
        endDay = intent.getIntExtra("endDay", 1);
        endHour = intent.getIntExtra("endHour", 0);
        endMin = intent.getIntExtra("endMin", 0);
        startwday = intent.getIntExtra("startwday", 0);
        endwday = intent.getIntExtra("endwday", 0);

        bt_start.setOnClickListener(this);
        bt_end.setOnClickListener(this);
        confirm.setOnClickListener(this);

        start.set(Calendar.MONTH, startMonth-1);
        start.set(Calendar.DAY_OF_MONTH, startDay);
        start.set(Calendar.DAY_OF_WEEK, startwday);
        end.set(Calendar.MONTH, endMonth-1);
        end.set(Calendar.DAY_OF_MONTH, endDay);
        end.set(Calendar.DAY_OF_WEEK, endwday);

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
            case R.id.pickerbtn:
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        startHour = timePicker1.getHour();
                        startMin = timePicker1.getMinute() * 10;
                    } else {
                        startHour = timePicker1.getCurrentHour();
                        startMin = timePicker1.getCurrentMinute();
                    }
                    sval = datePicker1.getValue();
                } catch(Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        endHour = timePicker2.getHour();
                        endMin = timePicker2.getMinute() * 10;
                    } else {
                        endHour = timePicker2.getCurrentHour();
                        endMin = timePicker2.getCurrentMinute();
                    }
                    eval = datePicker2.getValue();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent();
                intent.putExtra("startHour", startHour);
                intent.putExtra("startMin", startMin);
                intent.putExtra("endHour", endHour);
                intent.putExtra("endMin", endMin);

                startDay = startDay + sval;
                startwday = (startwday + sval) % 7 == 0 ? 7 : (startwday + sval) % 7;
                if (startDay > start.getActualMaximum(Calendar.DATE)) {
                    startDay = startDay - start.getActualMaximum(Calendar.DATE);
                    startMonth = (startMonth + 1) % 13 == 0 ? 1 : startMonth + 1;
                }

                endDay = endDay + eval;
                endwday = (endwday + eval) % 7 == 0 ? 7 : (endwday + eval) % 7;
                if (endDay > end.getActualMaximum(Calendar.DATE)) {
                    endDay = endDay - end.getActualMaximum(Calendar.DATE);
                    endMonth = (endMonth + 1) % 13 == 0 ? 1 : endMonth + 1;
                }

                intent.putExtra("startDay", startDay);
                intent.putExtra("startwday", startwday);
                intent.putExtra("startMonth", startMonth);
                intent.putExtra("endDay", endDay);
                intent.putExtra("endwday", endwday);
                intent.putExtra("endMonth", endMonth);

                setResult(RESULT_OK, intent);
                finish();
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

        private final int TIME_PICKER_INTERVAL = 10;
        NumberPicker minutePicker;   //10분 단위 시간설정에 사용
        List<String> displayedValues;  //10분 단위 시간설정에 사용

        public Fragment1() {
            //빈 생성자가 요구됨
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
            timePicker1 = (TimePicker) view.findViewById(R.id.timePicker1);
            datePicker1 = (NumberPicker) view.findViewById(R.id.datePicker1);
            timePicker1.setIs24HourView(true);
            datePicker1.setWrapSelectorWheel(false);
            setTimePickerInterval(timePicker1);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker1.setHour(startHour);
                timePicker1.setMinute(startMin / 10);
            } else {
                timePicker1.setCurrentHour(startHour);
                timePicker1.setCurrentMinute(startMin);
            }

            start.set(Calendar.HOUR, startHour);
            start.set(Calendar.MINUTE, startMin);

            NumberPicker.Formatter mFormatter = new NumberPicker.Formatter() {
                @Override
                public String format(int value) {
                    String startWd = "";
                    int tempMonth = startMonth;
                    int tempDay = startDay + value;
                    int tempwday = (startwday + value) % 7 == 0 ? 7 : (startwday + value) % 7;
                    if (tempDay > start.getActualMaximum(Calendar.DATE)) {
                        tempDay = tempDay - start.getActualMaximum(Calendar.DATE);
                        tempMonth = (startMonth + 1) % 13 == 0 ? 1 : startMonth + 1;
                    }

                    switch (tempwday){
                        case 1: startWd = "일요일"; break;
                        case 2: startWd = "월요일"; break;
                        case 3: startWd = "화요일"; break;
                        case 4: startWd = "수요일"; break;
                        case 5: startWd = "목요일"; break;
                        case 6: startWd = "금요일"; break;
                        case 7: startWd = "토요일"; break;
                    }

                    return tempMonth + "월 " + tempDay + "일 " + startWd;
                }
            };

            datePicker1.setMinValue(0);
            datePicker1.setMaxValue(30);
            datePicker1.setValue(sval);
            datePicker1.setFormatter(mFormatter);

            try {
                Method method = datePicker1.getClass().getDeclaredMethod("changeValueByOne", boolean.class);
                method.setAccessible(true);
                method.invoke(datePicker1, true);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            return view;
        }

        @Override
        public void onDestroyView() {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                startHour = timePicker1.getHour();
                startMin = timePicker1.getMinute() * 10;
            } else {
                startHour = timePicker1.getCurrentHour();
                startMin = timePicker1.getCurrentMinute();
            }
            sval = datePicker1.getValue();

            super.onDestroyView();
        }

        private void setTimePickerInterval(TimePicker timePicker) {
            try {
                Class<?> classForid = Class.forName("com.android.internal.R$id");

                Field field = classForid.getField("minute");
                minutePicker = (NumberPicker)timePicker.findViewById(field.getInt(null));

                minutePicker.setMinValue(0);
                minutePicker.setMaxValue(5);
                displayedValues = new ArrayList<String>();
                for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                    displayedValues.add(String.format("%02d", i));
                }

                minutePicker.setDisplayedValues(displayedValues.toArray(new String[0]));
                minutePicker.setWrapSelectorWheel(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static class Fragment2 extends Fragment {

        private final int TIME_PICKER_INTERVAL = 10;
        NumberPicker minutePicker;   //10분 단위 시간설정에 사용
        List<String> displayedValues;  //10분 단위 시간설정에 사용

        public Fragment2() {
            //빈 생성자가 요구됨
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_fragment2, container, false);
            timePicker2 = (TimePicker) view.findViewById(R.id.timePicker2);
            datePicker2 = (NumberPicker) view.findViewById(R.id.datePicker2);
            timePicker2.setIs24HourView(true);
            datePicker2.setWrapSelectorWheel(false);
            setTimePickerInterval(timePicker2);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker2.setHour(endHour);
                timePicker2.setMinute(endMin / 10);
            } else {
                timePicker2.setCurrentHour(endHour);
                timePicker2.setCurrentMinute(endMin / 10);
            }


            end.set(Calendar.HOUR, endHour);
            end.set(Calendar.MINUTE, endMin);

            NumberPicker.Formatter mFormatter = new NumberPicker.Formatter() {
                @Override
                public String format(int value) {
                    String endWd = "";
                    int tempMonth = endMonth;
                    int tempDay = endDay + value;
                    int tempwday = (endwday + value) % 7 == 0 ? 7 : (endwday + value) % 7;
                    if (tempDay > end.getActualMaximum(Calendar.DATE)) {
                        tempDay = tempDay - end.getActualMaximum(Calendar.DATE);
                        tempMonth = (endMonth + 1) % 13 == 0 ? 1 : endMonth + 1;
                    }

                    switch (tempwday){
                        case 1: endWd = "일요일"; break;
                        case 2: endWd = "월요일"; break;
                        case 3: endWd = "화요일"; break;
                        case 4: endWd = "수요일"; break;
                        case 5: endWd = "목요일"; break;
                        case 6: endWd = "금요일"; break;
                        case 7: endWd = "토요일"; break;
                    }

                    return tempMonth + "월 " + tempDay + "일 " + endWd;
                }
            };

            datePicker2.setMinValue(0);
            datePicker2.setMaxValue(30);
            datePicker2.setValue(eval);
            datePicker2.setFormatter(mFormatter);

            try {
                Method method = datePicker2.getClass().getDeclaredMethod("changeValueByOne", boolean.class);
                method.setAccessible(true);
                method.invoke(datePicker2, true);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            return view;
        }

        @Override
        public void onDestroyView() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                endHour = timePicker2.getHour();
                endMin = timePicker2.getMinute() * 10;
            } else {
                endHour = timePicker2.getCurrentHour();
                endMin = timePicker2.getCurrentMinute();
            }
            eval = datePicker2.getValue();

            super.onDestroyView();
        }

        private void setTimePickerInterval(TimePicker timePicker) {
            try {
                Class<?> classForid = Class.forName("com.android.internal.R$id");

                Field field = classForid.getField("minute");
                minutePicker = (NumberPicker)timePicker.findViewById(field.getInt(null));

                minutePicker.setMinValue(0);
                minutePicker.setMaxValue(5);
                displayedValues = new ArrayList<String>();
                for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                    displayedValues.add(String.format("%02d", i));
                }

                minutePicker.setDisplayedValues(displayedValues.toArray(new String[0]));
                minutePicker.setWrapSelectorWheel(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
