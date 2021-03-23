package com.injun.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        //순서대로 배치하는 레이아웃을 생성
        LinearLayout linear = new LinearLayout(this);
        //버튼을 생성
        Button btn = new Button(this);
        btn.setText("직접 코딩한 버튼");
        //버튼 레이아웃에 배치
        linear.addView(btn);
        setContentView(linear);

    }
}