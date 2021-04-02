package com.injun.network;

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
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ParsingActivity extends AppCompatActivity {

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {

            String data = (String)msg.obj;
            TextView result = (TextView)findViewById(R.id.result);
            result.setText(data);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parsing);

        Button btnHTML = (Button)findViewById(R.id.btnHTML);
        Button btnXML = (Button)findViewById(R.id.btnXML);
        Button btnJSON = (Button)findViewById(R.id.btnJSON);

        btnHTML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //스레드 생성
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            //문자열을 다운로드
                            URL url = new URL("https://finance.naver.com");
                            //연결
                            HttpURLConnection con = (HttpURLConnection)url.openConnection();
                            //옵션 설정
                            con.setConnectTimeout(10000);//10초
                            con.setUseCaches(false);

                            //문자열을 읽기 위한 스트림 생성
                            BufferedReader br = null;
                            String headerType = con.getContentType();
                            //인코딩 방식에 따라 스트림을 다르게 생성
                            if(headerType.toUpperCase().indexOf("EUC-KR") >= 0) {
                                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "EUC-KR"));
                            } else {
                                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                            }
                            //문자열 읽기
                            StringBuilder sb = new StringBuilder();
                            while(true) {
                                String line = br.readLine();
                                if(line == null) {
                                    break;
                                }
                                sb.append(line + "\n");
                            }

                            //다운로드 받은 문자열을 확인
                            //Log.e("다운로드 받은 문자열", sb.toString());

                            //HTML 파싱 수행
                            //HTML 파일을 메모리에 펼치기
                            Document doc = Jsoup.parse(sb.toString());
                            //찾고자 하는 선택자를 이용해서 Elements를 찾아오기
                            Elements elements = doc.select("a"); //a태그 전부 찾아오기

                            //파싱한 결과를 저장할 문자열 객체
                            //ListView에 출력할 때는 List를 이용
                            StringBuilder sbResult = new StringBuilder();
                            for(Element element : elements) {
                                //문자열 가져오기
                                String contents = element.text();
                                sbResult.append(contents + "\n");
                            }
                            //결과를 핸들러에게 전송
                            Message message = new Message();
                            message.obj = sbResult.toString();
                            //출력
                            handler.sendMessage(message);

                            br.close();
                            con.disconnect();

                        } catch (Exception e) {
                            Log.e("예외 발생", e.getLocalizedMessage());
                        }
                    }
                }.start();
            }
        });

        btnXML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //스레드 생성
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            //데이터 다운로드
                            URL url = new URL("https://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=1147051000");
                            //연결
                            HttpURLConnection con = (HttpURLConnection)url.openConnection();
                            //옵션 설정
                            con.setConnectTimeout(10000);//10초
                            con.setUseCaches(false);

                            //문자열을 읽기 위한 스트림 생성
                            BufferedReader br = null;
                            String headerType = con.getContentType();
                            //인코딩 방식에 따라 스트림을 다르게 생성
                            if(headerType.toUpperCase().indexOf("EUC-KR") >= 0) {
                                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "EUC-KR"));
                            } else {
                                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                            }
                            //문자열 읽기
                            StringBuilder sb = new StringBuilder();
                            while(true) {
                                String line = br.readLine();
                                if(line == null) {
                                    break;
                                }
                                sb.append(line + "\n");
                            }

                            //다운로드 받은 문자열을 확인
                            //Log.e("다운로드 받은 문자열", sb.toString());

                            //DOM 파싱을 위해서 Root까지 찾아가기
                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            InputStream is = new ByteArrayInputStream(sb.toString().getBytes("utf-8"));
                            org.w3c.dom.Document doc = builder.parse(is);
                            org.w3c.dom.Element root = doc.getDocumentElement();

                            //wfKor 태크의 내용을 전부 가져오기
                            NodeList wfKors = root.getElementsByTagName("wfKor");

                            NodeList hours = root.getElementsByTagName("hour");

                            //결과를 저장할 스트링 객체
                            StringBuilder sbResult = new StringBuilder();
                            //순회
                            for(int i=0; i<wfKors.getLength(); i++) {
                                //하나 가져오기
                                Node item = hours.item(i);
                                //태그 안의 문자열 가져오기
                                Node contents = item.getFirstChild();
                                String hour = contents.getNodeValue();

                                sbResult.append(hour + "시");

                                //하나 가져오기
                                item = wfKors.item(i);
                                //태그 안의 문자열 가져오기
                                contents = item.getFirstChild();
                                String wfKor = contents.getNodeValue();

                                sbResult.append(wfKor + "\n");
                            }

                            //결과를 핸들러에게 전송
                            Message message = new Message();
                            message.obj = sbResult.toString();
                            //출력
                            handler.sendMessage(message);

                            br.close();
                            con.disconnect();

                        } catch (Exception e) {
                            Log.e("예외 발생", e.getLocalizedMessage());
                        }
                    }
                }.start();
            }
        });

        btnJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //스레드 생성
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            //데이터 다운로드
                            URL url = new URL("http://cyberadam.cafe24.com/movie/list");
                            //연결
                            HttpURLConnection con = (HttpURLConnection)url.openConnection();
                            //옵션 설정
                            con.setConnectTimeout(10000);//10초
                            con.setUseCaches(false);

                            //문자열을 읽기 위한 스트림 생성
                            BufferedReader br = null;
                            String headerType = con.getContentType();
                            //인코딩 방식에 따라 스트림을 다르게 생성
                            if(headerType.toUpperCase().indexOf("EUC-KR") >= 0) {
                                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "EUC-KR"));
                            } else {
                                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                            }
                            //문자열 읽기
                            StringBuilder sb = new StringBuilder();
                            while(true) {
                                String line = br.readLine();
                                if(line == null) {
                                    break;
                                }
                                sb.append(line + "\n");
                            }

                            //다운로드 받은 문자열을 확인
                            Log.e("다운로드 받은 문자열", sb.toString());

                            //문자열을 JSON 객체로 변환
                            JSONObject json = new JSONObject(sb.toString());
                            //list 키의 데이터를 배열로 가져오기
                            JSONArray list = json.getJSONArray("list");

                            //결과를 저장할 문자열 생성
                            StringBuilder sbResult = new StringBuilder();

                            //배열 순회
                            for(int i=0; i<list.length(); i++) {
                                //배열의 데이터를 객체로 가져오기
                                JSONObject movie = list.getJSONObject(i);
                                //title 속성의 내용 가져오기
                                String title = movie.getString("title");
                                double rating = movie.getDouble("rating");
                                sbResult.append(title + " - " + rating + "\n");
                            }

                            //결과를 핸들러에게 전송
                            Message message = new Message();
                            message.obj = sbResult.toString();
                            //출력
                            handler.sendMessage(message);

                            br.close();
                            con.disconnect();

                        } catch (Exception e) {
                            Log.e("예외 발생", e.getLocalizedMessage());
                        }
                    }
                }.start();
            }
        });

    }
}