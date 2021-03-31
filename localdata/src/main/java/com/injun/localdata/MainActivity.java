package com.injun.localdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.editText);
        Button btnResource = (Button)findViewById(R.id.btnResource);
        Button btnWrite = (Button)findViewById(R.id.btnWrite);
        Button btnRead = (Button)findViewById(R.id.btnRead);

        btnResource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //리소스에 저장된 파일을 읽기 위한 스트림을 생성
                    InputStream fis = getResources().openRawResource(R.raw.creed);
                    //데이터 읽기
                    byte[] data = new byte[fis.available()];
                    while (fis.read(data) != -1) {

                    }
                    //스트림 닫기
                    fis.close();
                    //출력
                    editText.setText(new String(data));
                } catch (Exception e) {
                    Log.e("예외", e.getLocalizedMessage());
                }
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileOutputStream fos = openFileOutput("test.txt", Context.MODE_PRIVATE);
                    fos.write("제목".getBytes());
                    fos.close();
                    editText.setText("기록 성공");
                } catch (Exception e) {
                    Log.e("예외", e.getLocalizedMessage());
                }
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream fis = openFileInput("test.txt");
                    //데이터 읽기
                    byte[] data = new byte[fis.available()];
                    while (fis.read(data) != -1) {

                    }
                    fis.close();
                    //출력
                    editText.setText(new String(data));
                } catch (Exception e) {
                    Log.e("예외", e.getLocalizedMessage());
                }
            }
        });

    }
}