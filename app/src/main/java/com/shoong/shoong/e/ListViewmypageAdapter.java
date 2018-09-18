package com.shoong.shoong.e;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewmypageAdapter extends ArrayAdapter implements View.OnClickListener {
    public interface ListBtnClickListener {
        void onListBtnClick(int position);
    }

    //리스트 뷰 아이디
    int resourceId;

    private ListBtnClickListener listBtnClickListener;

    ListViewmypageAdapter(Context context, int resource, ArrayList<ListViewMypageItem> list, ListBtnClickListener clickListener) {
        super(context, resource, list);

        this.resourceId = resource;
        this.listBtnClickListener = clickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // 생성자로부터 저장된 resourceId(listview_btn_item)에 해당하는 Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resourceId, parent, false);
        }

        final TextView zoneName = (TextView)convertView.findViewById(R.id.textView_list_mypage_zone_name);
        final TextView holderId = (TextView)convertView.findViewById(R.id.textView_list_mypage_holder_id);
        final TextView startTime = (TextView)convertView.findViewById(R.id.textView_list_mypage_start_time);
        final TextView endTime = (TextView)convertView.findViewById(R.id.textView_list_mypage_end_time);

        final ListViewMypageItem listViewMypageItem = (ListViewMypageItem)getItem(position);

        zoneName.setText(listViewMypageItem.getZoneName());
        holderId.setText(listViewMypageItem.getHolderId());
        startTime.setText(listViewMypageItem.getStartTime());
        endTime.setText(listViewMypageItem.getEndTime());

        Button cancelButton = (Button)convertView.findViewById(R.id.textView_list_mypage_cancel_button);
        cancelButton.setTag(position);
        cancelButton.setOnClickListener(this);

        return convertView;
    }

    public void onClick(View v) {
        if (this.listBtnClickListener != null) {
            this.listBtnClickListener.onListBtnClick((int)v.getTag());
        }
    }
}