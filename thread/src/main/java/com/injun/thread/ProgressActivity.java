package com.injun.thread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressActivity extends AppCompatActivity {
    ProgressBar progressBar;
    private TextView tfResult;

    //메시지를 전송해서 바로 작업을 수행시키는 핸들러
    Handler handler = new Handler(Looper.getMainLooper()) {
        //핸들러에게 메시지가 전송되면 호출되는 메서드
        @Override
        public void handleMessage(Message msg) {
            //전송받은 코드를 저장
            int what = msg.what;
            //데이터를 가져옵니다.
            int i = (Integer)msg.obj;
            //내용출력
            tfResult.setText("i=" + i);
            if(i < 100) {
               progressBar.setProgress(i);
               progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        tfResult = (TextView)findViewById(R.id.tfResult);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);
        Button btnStart = (Button)findViewById(R.id.btnStart);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //스레드를 이용해서 핸들러 호출
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            for(int i=1; i<=100; i++) {
                                //전송할 메시지 생성
                                Message msg = new Message();
                                //데이터 저장
                                msg.obj = i;
                                //다른 곳과 구분하고자 할때는 what을 이용
                                msg.what = 1;
                                //메시지 전송
                                handler.sendMessage(msg);
                                Thread.sleep(50);
                            }
                        }  catch (Exception e) {
                            Log.e("예외 발생", e.getLocalizedMessage());
                        }
                    }
                }.start();

            }
        });
    }
}