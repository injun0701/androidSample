package com.injun.adapterview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.io.InputStream;
import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {
    //ListView 출력을 위한 프로퍼티
    ListView listView;
    //데이터 모임과 Adapter 의 제너릭은 항상 일치
    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        //ListView 찾아오기
        listView = (ListView)findViewById(R.id.listView);
        //데이터 생성
        list = new ArrayList<>();
        list.add("IoC");
        list.add("DI");
        list.add("AOP");

        //android.R 로 시작하면 안드로이드에서 제공하는 리소스
        //R 로 시작하면 사용자가 삽입한 리소스

        //기본 모양으로 설정
//        adapter = new ArrayAdapter<>(ListViewActivity.this, android.R.layout.simple_list_item_1, list);

        //체크 박스 버튼을 옆에 배치하는 모양으로 설정
        adapter = new ArrayAdapter<>(ListViewActivity.this, android.R.layout.simple_list_item_checked, list);

        //ListView 에 adapter 설정
        listView.setAdapter(adapter);

        //구분선 설정
        listView.setDivider(new ColorDrawable(Color.CYAN));
        listView.setDividerHeight(3);
        //listView 의 선택모드 설정
        //listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE); //하나 선택
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); //다중 선택
        
        //ListView 에서 항목을 클릭했을 때 처리
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //첫번째 매개변수는 listview
            //두번째 매개변수는 클릭한 항목 뷰
            //세번째 매개변수는 누른 항목의 인덱스
            //네번째 매개변수는 항목 뷰의 아이디
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = list.get(position);
                Snackbar.make(getWindow().getDecorView().getRootView(), item, Snackbar.LENGTH_SHORT).show();
            }
        });

        //데이터 삽입 버튼을 눌렀을 때 처리
        Button btnInsert = (Button)findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //입력한 내용 가져오기
                EditText newItem = (EditText)findViewById(R.id.newItem);
                String item = newItem.getText().toString().trim();

                //전송하기 전에 유효성 검사를 수행 - null 체크, 중복 검사 등
                if(item.length() <= 0) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "아이템은 필수 입력입니다.", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                //list의 데이터를 순회하면서 모든 대문자로 변경하여 중복 비교
                for(String spring:list) {
                    if(spring.toUpperCase().equals(item. toUpperCase())) {
                        Snackbar.make(getWindow().getDecorView().getRootView(), "아이템이 이미 존재합니다.", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                }

                //list 에 삽입
                list.add(item);
                //listView 업데이트
                adapter.notifyDataSetChanged();

                newItem.setText("");
                Snackbar.make(getWindow().getDecorView().getRootView(), "아이템이 정상적으로 추가되었습니다.", Snackbar.LENGTH_SHORT).show();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(newItem.getWindowToken(), 0);
            }
        });

        //데이터 삽입 버튼을 눌렀을 때 처리
        Button btnDelete = (Button)findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //하나를 선택할 수 있을 때 삭제 처리
                /*
                //작업 수행할 데이터 유효성 검사
                //선택된 항목 인덱스를 찾아오기
                int pos = listView.getCheckedItemPosition();
                //인덱스는 데이터 범위 내의 인덱스여야 함.
                if(pos < 0 || pos >= list.size()) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "아이템이 선택되지 않아서 삭제할 수 없습니다.", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                //작업을 수행
                list.remove(pos);
                adapter.notifyDataSetChanged(); //테이블 리로드
                listView.clearChoices();
                */

                //다중 선택할 수 있을 때 삭제 처리
                //선택 항목 관련 정봅를 가져옴
                SparseBooleanArray sb = listView.getCheckedItemPositions();
                //유효성 검사
                if(sb.size() <= 0) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "리스트 뷰에 데이터가 없습니다.", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                //배열 순회
                //배열을 순회하면서 true이면 삭제
                //앞에서 부터 진행하면 안되고 뒤에서 부터 진행해야함
                for(int i = listView.getCount() - 1; i >= 0; i--) {
                    if(sb.get(i) == true) {
                        list.remove(i);
                    }
                }
                adapter.notifyDataSetChanged();
                listView.clearChoices();

                //수행 결과를 출력
                Snackbar.make(getWindow().getDecorView().getRootView(), "아이템이 정상적으로 삭제되었습니다.", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}