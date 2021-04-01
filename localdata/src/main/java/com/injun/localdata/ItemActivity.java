package com.injun.localdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class ItemActivity extends AppCompatActivity {
    EditText itemid, itemname, quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        itemid = (EditText)findViewById(R.id.itemid);
        itemname = (EditText)findViewById(R.id.itemname);
        quantity = (EditText)findViewById(R.id.quantity);

        Button btnInsert = (Button)findViewById(R.id.btnInsert);
        Button btnSelect = (Button)findViewById(R.id.btnSelect);
        Button btnDelete = (Button)findViewById(R.id.btnDelete);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //db 연동 클래스 객체를 생성
                MyDBHandler handler = new MyDBHandler(ItemActivity.this);
                //삽입할 데이터 만들기
                Item item = new Item();
                item.set_itemname(itemname.getText().toString());
                item.set_quantity(Integer.parseInt(quantity.getText().toString()));

                //데이터 삽입
                handler.addItem(item);

                //입력 도구들 초기화
                itemid.setText("");
                itemname.setText("");
                quantity.setText("");
                //키보드 숨기기
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(itemid.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(itemname.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(quantity.getWindowToken(), 0);

                //메시지 출력
                Snackbar.make(v, "데이터 저장 성공", Snackbar.LENGTH_SHORT).show();
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //itemname에 입력한 내용 가져오기
                String query = itemname.getText().toString();
                //조회하기 전에 유효성 검사
                if(query == null && query.trim().length() == 0) {
                    itemid.setText("삭제할 이름을 입력해주세요.");
                    return;
                }

                MyDBHandler handler = new MyDBHandler(ItemActivity.this);
                Item item = handler.findItem(query);
                if(item == null) {
                    itemid.setText("일치하는 데이터가 없습니다.");
                } else {
                    itemid.setText(item.get_id() + "");
                    quantity.setText(item.get_quantity() + "");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //itemname에 입력한 내용 가져오기
                String query = itemname.getText().toString();
                //삭제하기 전에 유효성 검사
                if(query == null && query.trim().length() == 0) {
                    itemid.setText("삭제할 이름을 입력해주세요.");
                } else {
                    MyDBHandler handler = new MyDBHandler(ItemActivity.this);
                    handler.deleteItem(query);
                    itemid.setText("삭제 되었습니다.");
                }
            }
        });

    }
}