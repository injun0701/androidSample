package com.injun.userinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class BttonActivity extends AppCompatActivity {
    //뷰를 참조할 변수
    TextView lblDisplay;
    Button btnDisplay;
    CheckBox bigfont;
    RadioGroup colorgroup;
    ToggleButton btntoggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btton);

        //뷰 참조
        lblDisplay = (TextView)findViewById(R.id.lblDisplay);
        btnDisplay = (Button)findViewById(R.id.btnDisplay);
        bigfont = (CheckBox)findViewById(R.id.bigfont);
        colorgroup = (RadioGroup)findViewById(R.id.colorgroup);
        btntoggle = (ToggleButton)findViewById(R.id.btntoggle);

        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lblDisplay.setText("버튼을 클릭");
            }
        });

        //체크박스 클릭 처리
        bigfont.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 if (isChecked == true) {
                     btntoggle.setTextSize(30);
                } else {
                     btntoggle.setTextSize(15);
                 }
            }
        });

        //라디오 버튼은 라디오 그룹을 처리해서 작업
        colorgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.green:
                        btntoggle.setTextColor(Color.GREEN);
                        break;
                    case R.id.red:
                        btntoggle.setTextColor(Color.RED);
                        break;
                    case R.id.blue:
                        btntoggle.setTextColor(Color.BLUE);
                        break;
                }
            }
        });

    }
}