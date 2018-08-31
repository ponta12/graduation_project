package com.shoong.shoong.e;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectShareZoneActivity extends AppCompatActivity {


    private static final String TAG_ID = "zoneid";
    private static final String TAG_NAME = "zonename";
    private static final String TAG_ADDRESS = "address";

    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView;
    JSONArray array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_share_zone);

        mlistView = (ListView)findViewById(R.id.listView_share_zone);
        mArrayList = new ArrayList<>();

        Intent intent = getIntent();
        String jsonArray = intent.getStringExtra("jsonResponse");
        try {
            array = new JSONArray(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        showResult();
    }

    private void showResult() {
        try {
            for(int i=0; i<array.length(); i++) {
                JSONObject item = array.getJSONObject(i);

                String name = item.getString(TAG_NAME);
                String address = item.getString(TAG_ADDRESS);

                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(TAG_NAME, name);
                hashMap.put(TAG_ADDRESS, address);

                mArrayList.add(hashMap);
            }

            ListAdapter adapter = new SimpleAdapter(
                    SelectShareZoneActivity.this, mArrayList, R.layout.share_zone_list,
                    new String[]{TAG_NAME, TAG_ADDRESS},
                    new int[]{R.id.textView_list_zone_name, R.id.textView_list_zone_address}
            );

            mlistView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int zone_id = 0;
                String zone_name = "";

                try {
                    JSONObject item = array.getJSONObject(position);
                    zone_id = item.getInt(TAG_ID);
                    zone_name = item.getString(TAG_NAME);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent();
                intent.putExtra("id", zone_id);
                intent.putExtra("name", zone_name);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
