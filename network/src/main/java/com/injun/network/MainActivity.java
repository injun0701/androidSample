package com.injun.network;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    //전송 받은 내용을 출력해주는 핸들러
    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            //데이터 가져오기
            String data = (String)msg.obj;
            //가져온 데이터를 텍스트 뷰에 출력하기
            TextView tfOutput = (TextView)findViewById(R.id.tfOutput);
            tfOutput.setText(data);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //뷰를 찾아오기
        TextView tfOutput = (TextView)findViewById(R.id.tfOutput);
        EditText tfInput = (EditText)findViewById(R.id.tfInput);
        Button btnSend = (Button)findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //네트워크 실행을 위한 스레드
               new Thread() {
                    public void run() {
                        try {
                            //서버와 통신할 소켓 생성
                            //host는 IP 주소이고 port는 서버의 포트 번호
                            Socket socket = new Socket("192.168.0.7", 11001);
                            //데이터 전송
                            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                            oos.writeObject(tfInput.getText().toString());
                            oos.flush();

                            //전솓된 데이터 읽기
                            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                            String msg = (String)ois.readObject();

                            //데이터 출력을 스레드가 하지말고 핸들러에게 위임해서 처리
                            Message message = new Message();
                            message.obj = msg;
                            handler.sendMessage(message);

                            //정리
                            oos.close();
                            ois.close();
                            socket.close();
                        } catch (Exception e) {
                            Log.e("예외 발생", e.getLocalizedMessage());
                        }
                    }
                }.start();
            }
        });

    }
}