package com.shoong.shoong.e;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class AutoScrollAdapter extends PagerAdapter {

    Context context;
    int[] data;

    public AutoScrollAdapter(Context context, int[] data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPos = position % data.length;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.auto_viewpager, null);
        ImageView image_container = (ImageView)v.findViewById(R.id.image_container);
        image_container.setImageResource(data[realPos]);
        container.addView(v);
        return v;
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return data.length * 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}