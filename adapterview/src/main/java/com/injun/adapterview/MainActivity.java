package com.injun.adapterview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //ListView 출력을 위한 프로퍼티
    ListView listView;
    //데이터 모임과 Adapter 의 제너릭은 항상 일치
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    //ArrayAdapter<CharSequence> adapter; //어댑터 생성 - xml을 이용할 경우(가변 불가능)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ListView 찾아오기
        listView = (ListView)findViewById(R.id.listview);
        //데이터 생성
        list = new ArrayList<>();
        list.add("C");
        list.add("C++");
        list.add("Java");
        list.add("Python");
        list.add("JavaScript");
        list.add("C#");
        list.add("Ruby");
        list.add("Scala");
        list.add("Kotlin");
        list.add("Swift");
        list.add("R");
        list.add("Dart");

        //어댑터 생성
        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, list);

        //어댑터 생성 - xml을 이용할 경우(가변 불가능)
        //adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.oop, android.R.layout.simple_list_item_1);

        //ListVIew 와 ArrayAdpater 연결
        listView.setAdapter(adapter);
    }
}