package com.injun.resourceuse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.TextView;

public class PhoneRotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_rotation);
        if(savedInstanceState != null) {
            //key 라는 이름으로 저장된 데이터를 출력
            String key = savedInstanceState.getString("key");
            if (key != null) {
                Log.e("key", key);
            }
        }
    }

    //기기의 환경이 변경될 때 호출되는 메서드
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //상위 클래스의 메서드를 호출
        //안드로이드가 제공해주는 기능을 수행하고 내가 원하는 기능 추가
        super.onConfigurationChanged(newConfig);

        TextView lblPhoneRotation = (TextView)findViewById(R.id.lblPhoneRotation);
        
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            lblPhoneRotation.setText("기기 방향은 가로");
            Log.e("방향", "가로");
        } else {
            lblPhoneRotation.setText("기기 방향은 세로");
            Log.e("방향", "세로");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        //데이터 저장
        bundle.putString("key", "현재 상태 저장");
    }
}