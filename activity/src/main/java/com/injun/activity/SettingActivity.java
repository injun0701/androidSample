package com.injun.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class SettingActivity extends AppCompatActivity {
    //메시지 받아서 스낵바와 로그를 출력하는 메서드
    private void showMessage(String msg) {
        EditText edit = (EditText)findViewById(R.id.edit);
        Snackbar.make(edit, msg, Snackbar.LENGTH_SHORT).show();
        Log.e("msg", msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        EditText edit = (EditText)findViewById(R.id.edit);
        Button btnToggle = (Button)findViewById(R.id.btnToggle);
        //키보드 토글 버튼 클릭 이벤트
        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //키보드 관리 객체 생성
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                //키보드 토글
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        });
    }

    //상태 변화가 생기면 호출되는 메서드 - 회전
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        setContentView(R.layout.activity_setting);

        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            showMessage("세로");
        } else {
            showMessage("가로");
        }
    }

    //Activity가 활성화될 때 호출되는 메서드
    @Override
    protected void onResume() {
        super.onResume();
        showMessage("액티비티 활성화");
    }

    //Activity가 비활성화될 때 호출되는 메서드
    @Override
    protected void onPause() {
        super.onPause();
        showMessage("액티비티 비활성화");
    }
}