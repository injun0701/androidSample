package com.injun.eventhandling;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    //클릭 이벤트 처리를 위한 클래스
   class ClickClass implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Snackbar.make(v, "버튼의 클릭 이벤트 처리", Snackbar.LENGTH_SHORT).show();
        }
    }

    Button btnToast, btnSnackbar, btnDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnToast = (Button)findViewById(R.id.btnToast);
        btnSnackbar = (Button)findViewById(R.id.btnSnackbar);
        btnDelegate = (Button)findViewById(R.id.btnDelegate);

        btnToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "토스트 메시지", Toast.LENGTH_LONG).show();
            }
        });

        btnSnackbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "스낵바를 출력합니다.", Snackbar.LENGTH_LONG).setAction("확인", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("태그", "로그를 출력합니다.");
                    }
                }).show();
            }
        });

        //btnDelegate.setOnClickListener(new ClickClass());

        btnDelegate.setOnClickListener((View v) -> {
            Snackbar.make(v, "람다식을 이용한 이벤트 처리", Snackbar.LENGTH_SHORT).show();
        });
    }

    //터치 이벤트가 발생했을 때 호출되는 메서드
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Toast.makeText(getApplicationContext(), "터치 이벤트 처리 - Hierachy Model", Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //누른 키 값을 확인 - 백 버튼을 눌렀으면
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            //현재 Activity 종료
            finish();
        }
        return true;
    }
}