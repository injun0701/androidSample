package com.injun.eventhandling;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AlertDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);

        Button btnAlert = (Button)findViewById(R.id.btnAlert);

        btnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(AlertDialogActivity.this);
                //.setCancelable(false) 뒤로가기 버튼 클릭하거나 뒤에 배경 클릭해도 종료되지 않음
                dlg.setTitle("대화상자 만들기");
                dlg.setMessage("대화상자를 생성했습니다.")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setNegativeButton("취소", null)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.e("로그", "대화상자의 버튼 클릭시 작동");
                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        });
    }
}