package com.injun.varietyview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class ProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        ProgressBar rect = (ProgressBar)findViewById(R.id.progressbar);
        ProgressBar circle = (ProgressBar)findViewById(R.id.progressindicator);
        Button btnStart = (Button)findViewById(R.id.btnStart);
        Button btnStop = (Button)findViewById(R.id.btnStop);
        SeekBar seekbar = (SeekBar)findViewById(R.id.seekbar);
        TextView lblSeekValue = (TextView)findViewById(R.id.lblSeekValue);

        //시작 버튼 동작
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rect.setProgress(100);
                circle.setVisibility(View.VISIBLE);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rect.setProgress(0);
                circle.setVisibility(View.INVISIBLE);
            }
        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //값이 변경될 때 호출하는 메소드
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //현재 값을 TextView에 출력
                String msg = String.format("%d", progress);
                lblSeekValue.setText(msg);
            }

            //값을 변경하기 위해 thumb을 선택했을 때
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("시크바", "값 변경 시작");
                Snackbar.make(seekBar, "값 변경 시작", Snackbar.LENGTH_SHORT).show();
            }

            //값의 변경이 종료되고 thumb에서 손을 뗄 때
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("시크바", "값 변경 종료");
                Snackbar.make(seekBar, "값 변경 종료", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}