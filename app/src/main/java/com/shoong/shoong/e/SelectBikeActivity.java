package com.shoong.shoong.e;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectBikeActivity extends AppCompatActivity {


    private static final String TAG_BIKE_ID = "bikeid";
    private static final String TAG_ZONE_ID = "zoneid";
    private static final String TAG_HOLDER_ID = "holderid";
    private static final String TAG_ZONE_NAME = "zonename";
    static String zone_name = "";

    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView;
    JSONArray array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bike);

        mlistView = (ListView)findViewById(R.id.listView_bike);
        mArrayList = new ArrayList<>();

        Intent intent = getIntent();
        zone_name = intent.getStringExtra(TAG_ZONE_NAME);
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

                String bike_id = item.getString(TAG_BIKE_ID);
                String holder_id = item.getString(TAG_HOLDER_ID);

                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(TAG_BIKE_ID, bike_id);
                hashMap.put(TAG_HOLDER_ID, holder_id);
                hashMap.put(TAG_ZONE_NAME, zone_name);

                mArrayList.add(hashMap);
            }

            ListAdapter adapter = new SimpleAdapter(
                    SelectBikeActivity.this, mArrayList, R.layout.bike_list,
                    new String[]{TAG_BIKE_ID, TAG_HOLDER_ID, TAG_ZONE_NAME},
                    new int[]{R.id.textView_list_bike_id, R.id.textView_list_holder_id, R.id.textView_list_bike_zone_name}
            );

            mlistView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int zone_id = 0;
                int holder_id = 0;

                try {
                    JSONObject item = array.getJSONObject(position);
                    zone_id = item.getInt(TAG_ZONE_ID);
                    holder_id = item.getInt(TAG_HOLDER_ID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent();
                intent.putExtra("zone_id", zone_id);
                intent.putExtra("holder_id", holder_id);
                intent.putExtra("zone_name", zone_name);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
