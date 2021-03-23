package com.injun.userinterface;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TextActivity extends AppCompatActivity {
    //xml 파일에 디자인한 뷰의 참조를 저장하기 위한 변수
    TextView txtIdResult; //Anonymous Class의 코드에서는 자신이 속한 메서드의 지역변수를 사용할 수 없어서 인스턴스 변수로 생성
    EditText txtId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        /*
        //TextView가져오기
        TextView textView = (TextView)findViewById(R.id.txtView);
        //문자열 설정
        textView.setText("다른 문자열");
        //문자열 가져오기
        //toString()을 이용해서 문자열로 변환해야 String에 대입 가능
        //EditText 나 TextView의 입력된 문자열의 자료형은 Editable 이나 CharSequence 라서 String에 바로 대입이 불가능
        //반대로 설정할 때는 String이 Editable 이나 CharSequence를 implements 했기 때문에 대입 가능
        String content = textView.getText().toString();
         */

        txtIdResult= (TextView)findViewById(R.id.txtIdResult);
        txtId= (EditText)findViewById(R.id.txtId);

        txtId.addTextChangedListener(new TextWatcher() {
            //EditText의 내용이 변경되기 직전에 호출되는 메서드
            //s가 입력된 문자열
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            //EditText의 내용이 변경된 후에 호출되는 메서드
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //EditText의 내용을 TextView에 출력
                txtIdResult.setText(txtId.getText());
            }
            //EditText의 내용이 변경된 후에 호출되는 메서드
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}