package com.injun.localdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class PrefActivity extends AppCompatActivity {
    EditText tfInput;
    CheckBox ckSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);

        /*
        환경 설정 저장하기(보통 작은 양의 데이터를 저장)
         */

        tfInput = (EditText)findViewById(R.id.tfInput);
        ckSave = (CheckBox)findViewById(R.id.ckSave);
        Button btnSave = (Button)findViewById(R.id.btnSave);
        Button btnLoad = (Button)findViewById(R.id.btnLoad);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //환경 설정 객체 생성
                SharedPreferences preferences = getSharedPreferences("PrefActivity", Context.MODE_PRIVATE);
                //데이터 저장
                preferences.edit().putString("name", tfInput.getText().toString()).apply();
                preferences.edit().putBoolean("save", ckSave.isChecked()).apply();
                
                //초기화
                tfInput.setText("")
                ;ckSave.setChecked(false);
            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //환경 설정 객체 생성
                SharedPreferences preferences = getSharedPreferences("PrefActivity", Context.MODE_PRIVATE);
                //데이터 읽기
                String name = preferences.getString("name", "noname");
                boolean save = preferences.getBoolean("save", false);
                tfInput.setText(name);
                ckSave.setChecked(save);
            }
        });
    }
}