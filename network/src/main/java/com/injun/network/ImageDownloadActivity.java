package com.injun.network;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloadActivity extends AppCompatActivity {

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            //여러게의 스레드를 사용할 때 msg의 what을 이용해서 분기

            //이미지 뷰
            ImageView imgView = (ImageView)findViewById(R.id.imgView);
            //출력할 이미지를 저장하기 위한 변수
            Bitmap bitmap = null;
            String dirPath = null;
            String filePath = null;

            switch (msg.what) {
                case 1:
                    //데이터 가져오기
                    bitmap = (Bitmap)msg.obj;
                    imgView.setImageBitmap(bitmap);
                    break;
                case 2:
                    //파일이 존재하는 경우
                    Snackbar.make(imgView, "파일이 이미 존재합니다.", Snackbar.LENGTH_SHORT).show();
                    //기존 파일 출력
                    //파일 경로 가져오기
                    dirPath = Environment.getDataDirectory().getAbsolutePath();
                    filePath = dirPath + "/data/com.injun.network/files/web.jpg";
                    imgView.setImageBitmap(BitmapFactory.decodeFile(filePath));
                    break;
                case 3:
                    //파일이 존재하지 않는 경우
                    Snackbar.make(imgView, "파일이 없어서 다운로드 받아서 출력합니다.", Snackbar.LENGTH_SHORT).show();
                    //다운로드 받은 파일 출력
                    //파일 경로 가져오기
                    dirPath = Environment.getDataDirectory().getAbsolutePath();
                    filePath = dirPath + "/data/com.injun.network/files/web.jpg";
                    imgView.setImageBitmap(BitmapFactory.decodeFile(filePath));
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_download);

        //이미지 다운로드 받아서 바로 출력하는 버튼의 클릭 이벤트 핸들러 작성
        Button btnDisplay = (Button)findViewById(R.id.btnDisplay);
        Button btnSave = (Button)findViewById(R.id.btnSave);

        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //스레드 생성
                new Thread() {
                    @Override
                    public void run() {
                        //이미지 바로 가져오기
                        try {
                            //이미지를 다운로드 받을 스트림 생성
                            InputStream is = new URL("https://dimg.donga.com/wps/NEWS/IMAGE/2020/09/11/102894068.2.jpg").openStream();
                            //이미지를 가져와서 bitmap
                            Bitmap bitmap = BitmapFactory.decodeStream(is);
                            //핸들러에게 전송할 메시지 생성
                            Message message = new Message();
                            message.what = 1;
                            message.obj = bitmap;
                            //핸들러에게 메시지 전송
                            handler.sendMessage(message);
                        } catch (Exception e) {
                            Log.e("예외 발생", e.getLocalizedMessage());
                        }
                    }
                }.start();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //현재 앱 내에 파일이 존재하는지 확인 - web.jpg

                //Data가 저장되는 Directory 경로 생성
                String dirPath = Environment.getDataDirectory().getAbsolutePath();
                //파일 경로 생성 - com.injun.network는 패키지 이름
                //web.jpg는 저장할 파일 이름
                String path = dirPath + "/data/com.injun.network/files/web.jpg";

                //파일 있는지 확인
                //File 클래스 존재 여부, 마지막 수정 날짜, 생성 날짜, 크기 가져오는 것 중요
                if(new File(path).exists()) {
                    //이미 파일이 있기 때문에 데이터를 전달하지 않음
                    //핸들러에게 전송할 메시지 생성
                    Message message = new Message();
                    message.what = 2;
                    //핸들러에게 메시지 전송
                    handler.sendMessage(message);
                } else {
                    //이미지 파일이 없는 경우 이미지 파일을 다운로드 받아서 저장
                    new Thread() {
                        @Override
                        public void run() {
                            //이미지 데이터 다운받기
                            try {
                                //다운로드 받을 URL 생성
                                URL url = new URL("https://mblogthumb-phinf.pstatic.net/MjAyMDA4MjdfNzcg/MDAxNTk4NTI3MjI0MTk2.m0kvMmRvhNW8cAbrJ-TT5xqEyTAZMGmua7nQI6x1vvwg.k-8ktA18aJaRCwdZ7znW53QQeRTHI0jZkFk-Kgy1mA8g.JPEG.minzang0302/1598527224847.jpg?type=w800");
                                //연결
                                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                                //옵션 설정
                                con.setConnectTimeout(10000);
                                con.setUseCaches(false);

                                //텍스트가 아닌 데이터를 가져오기 위한 스트림 생성
                                InputStream is = con.getInputStream();
                                //기록할 파일 스트림을 생성
                                FileOutputStream fos = openFileOutput("web.jpg", 0);

                                //데이터를 임시로 저장할 바이트 배열을 생성
                                byte [] raster = new byte[con.getContentLength()];

                                //다운로드 받아서 저장하기
                                while(true) {
                                    int read = is.read(raster);
                                    if(read <= 0) {
                                        break;
                                    }
                                    fos.write(raster, 0, read);
                                }
                                is.close();
                                fos.close();
                                con.disconnect();

                                //핸들러에게 전송할 메시지 생성
                                Message message = new Message();
                                message.what = 3;
                                //핸들러에게 메시지 전송
                                handler.sendMessage(message);
                            } catch (Exception e) {
                                Log.e("예외 발생2", e.getLocalizedMessage());
                            }
                        }
                    }.start();
                }
            }
        });
    }
}