package com.shoong.shoong.e;

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
        Calendar start = Calendar.getInstance();

        public Fragment1() {
            //빈 생성자가 요구됨
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
            TimePicker timePicker = (TimePicker) view.findViewById(R.id.timePicker1);
            NumberPicker datePicker = (NumberPicker) view.findViewById(R.id.datePicker1);
            timePicker.setIs24HourView(true);
            datePicker.setWrapSelectorWheel(false);
            setTimePickerInterval(timePicker);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker.setHour(startHour);
                timePicker.setMinute(startMin / 10);
                System.out.println(startMin);
            } else {
                timePicker.setCurrentHour(startHour);
                timePicker.setCurrentMinute(startMin);
            }

            start.set(Calendar.HOUR, startHour);
            start.set(Calendar.MINUTE, startMin);
            start.set(Calendar.MONTH, startMonth-1);
            start.set(Calendar.DAY_OF_MONTH, startDay);
            start.set(Calendar.DAY_OF_WEEK, startwday);

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

            datePicker.setMinValue(0);
            datePicker.setMaxValue(30);
            datePicker.setValue(sval);
            datePicker.setFormatter(mFormatter);

            try {
                Method method = datePicker.getClass().getDeclaredMethod("changeValueByOne", boolean.class);
                method.setAccessible(true);
                method.invoke(datePicker, true);
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
            TimePicker timePicker = (TimePicker)getView().findViewById(R.id.timePicker1);
            NumberPicker datePicker = (NumberPicker)getView().findViewById(R.id.datePicker1);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                startHour = timePicker.getHour();
                startMin = timePicker.getMinute() * 10;
            } else {
                startHour = timePicker.getCurrentHour();
                startMin = timePicker.getCurrentMinute();
            }
            sval = datePicker.getValue();

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
        Calendar end = Calendar.getInstance();

        public Fragment2() {
            //빈 생성자가 요구됨
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_fragment2, container, false);
            TimePicker timePicker = (TimePicker) view.findViewById(R.id.timePicker2);
            NumberPicker datePicker = (NumberPicker) view.findViewById(R.id.datePicker2);
            timePicker.setIs24HourView(true);
            datePicker.setWrapSelectorWheel(false);
            setTimePickerInterval(timePicker);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker.setHour(endHour);
                timePicker.setMinute(endMin / 10);
            } else {
                timePicker.setCurrentHour(endHour);
                timePicker.setCurrentMinute(endMin / 10);
            }


            end.set(Calendar.HOUR, endHour);
            end.set(Calendar.MINUTE, endMin);
            end.set(Calendar.MONTH, endMonth-1);
            end.set(Calendar.DAY_OF_MONTH, endDay);
            end.set(Calendar.DAY_OF_WEEK, endwday);

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

            datePicker.setMinValue(0);
            datePicker.setMaxValue(30);
            datePicker.setValue(eval);
            datePicker.setFormatter(mFormatter);

            try {
                Method method = datePicker.getClass().getDeclaredMethod("changeValueByOne", boolean.class);
                method.setAccessible(true);
                method.invoke(datePicker, true);
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
            TimePicker timePicker = (TimePicker)getView().findViewById(R.id.timePicker2);
            NumberPicker datePicker = (NumberPicker)getView().findViewById(R.id.datePicker2);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                endHour = timePicker.getHour();
                endMin = timePicker.getMinute() * 10;
            } else {
                endHour = timePicker.getCurrentHour();
                endMin = timePicker.getCurrentMinute();
            }
            eval = datePicker.getValue();

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
