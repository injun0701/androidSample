package com.injun.adapterview;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLiteListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_q_lite_list);

        //SimpleAdapter 를 사용하는 ListView 생성
        ListView simpleListView = (ListView)findViewById(R.id.simpleListView);
        //SimpleAdapter 는 Map 의 List 로 데이터를 생성해야 함
        List<Map<String, String>> simpleData = new ArrayList<>();
        //데이터베이스에서 데이터 읽어오기
        JobDBHelper helper = new JobDBHelper(SQLiteListActivity.this);
        SQLiteDatabase db = helper.getReadableDatabase();
        //데이터 읽는 SQL 실행
        Cursor cursor = db.rawQuery("select * from job_data", null);

        //SimpleAdapter
        /*

        while(cursor.moveToNext()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", cursor.getString(1));
            map.put("content", cursor.getString(2));
            simpleData.add(map);
        }

        //네번째 매개변수가 Map 에서 출력할 키의 배열임
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                SQLiteListActivity.this,
                simpleData,
                android.R.layout.simple_expandable_list_item_2,
                new String[]{"name", "content"},
                new int[]{android.R.id.text1, android.R.id.text2});

        simpleListView.setAdapter(simpleAdapter);
        */

        //CursorAdapter 사용 - Map의 리스트를 만들 필요가 없음
        CursorAdapter cursorAdapter = new SimpleCursorAdapter(
                SQLiteListActivity.this,
                android.R.layout.simple_list_item_2,
                cursor,
                new String[]{"name", "content"},
                new int[]{android.R.id.text1, android.R.id.text2});
        simpleListView.setAdapter(cursorAdapter);
    }
}