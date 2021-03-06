package com.injun.varietyview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        //웹 뷰 찾아오기
        WebView webView = (WebView)findViewById(R.id.webView);
        //URL이 리다이렉트 되는 경우에는 이코드를 추가해야만 웹 뷰로 출력함.
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.naver.com/");

        Button btnLoadHtml = (Button)findViewById(R.id.btnLoadHtml);

        btnLoadHtml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //출력하는 URL에 자바스크립트 코드가 있는 경우 WebView의 설정을 변경해야함.
                WebSettings set = webView.getSettings();
                set.setJavaScriptEnabled(true);

                webView.loadUrl("file:///android_asset/test.html");
            }
        });
    }
}