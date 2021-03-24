package com.injun.eventhandling;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimerActivity extends AppCompatActivity {
    TextView lblTime;
    Button btnStart, btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        lblTime = (TextView)findViewById(R.id.lblTime);
        btnStart = (Button)findViewById(R.id.btnStart);
        btnStop = (Button)findViewById(R.id.btnStop);

        //타이머 생성
        CountDownTimer timer = new CountDownTimer(1000000 * 100000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //현재 날짜 및 시간 가져오기
                Date date = new Date();
                //날짜 및 시간을 문자열로 변환 포맷 설정
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                //포맷에 맞춘 문자열 생성
                String time = sdf.format(date);
                //TextView에 시간 출력
                lblTime.setText(time);
            }

            @Override
            public void onFinish() {
                lblTime.setText("타이머 종료");
            }
        };

        //버튼 클랙 메서드
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.start();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                timer.onFinish();
            }
        });

    }
}