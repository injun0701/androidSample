package com.injun.eventhandling;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.LinkedList;

public class AlertDialogActivity extends AppCompatActivity {
    //선택한 데이터를 저장할 문자열 프로퍼티
    private String myDBMS;

    //대화상자 출력할 데이터 배열
    private String [] laguages;
    //하나 선택한 데이터를 저장할 문자열 프로퍼티
    private String myLaguages;
    //다중 선택한 데이터를 저장할 문자열 프로퍼티
    private LinkedList<String> myLaguageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);

        Button btnAlert = (Button)findViewById(R.id.btnAlert);
        Button btnItemDialog = (Button)findViewById(R.id.btnItemDialog);
        Button btnOneLanguage = (Button)findViewById(R.id.btnOneLanguage);
        Button btnMultiLanguage = (Button)findViewById(R.id.btnMultiLanguage);
        Button btnLogin = (Button)findViewById(R.id.btnLogin);

        btnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //얼럿 객체 생성
                AlertDialog.Builder dlg = new AlertDialog.Builder(AlertDialogActivity.this);
                dlg.setTitle("제목")
                        .setMessage("메시지")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setNegativeButton("취소", null)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(AlertDialogActivity.this, "출력", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });

        btnItemDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //얼럿 객체 생성
                AlertDialog.Builder dlg = new AlertDialog.Builder(AlertDialogActivity.this);
                dlg.setTitle("내가 사용할 데이터베이스")
                        .setIcon(android.R.drawable.ic_lock_idle_lock)
                        .setNegativeButton("취소", null)
                        .setItems(R.array.dbms, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //arrays.xml 파일에 디자인한 배열을 가져오기
                                String [] demses = getResources().getStringArray(R.array.dbms);
                                //선택한 데이터를 가져오기
                                myDBMS = demses[which];
                                //데이터 사용
                                Log.e("dbms", myDBMS);
                            }
                        })
                        .show();
            }
        });

        //배열 생성
        laguages = new String[] {
                "Pascal", "C", "C++", "Java", "Assembly", "VisualBasic", "C#", "Objective-C", "Python", "R", "Swift", "Kotlin"
        };

        btnOneLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AlertDialogActivity.this)
                        .setTitle("언어를 하나 고르세요!")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setSingleChoiceItems(laguages, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //선택한 항목을 myLaguages에 저장
                                myLaguages = laguages[which];
                                Log.e("선택한 언어", myLaguages);
                            }
                        })
                        .setNegativeButton("취소", null)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Snackbar.make(v, myLaguages, Snackbar.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });

        //다중 선택한 항목을 저장한 List 생성
        myLaguageList = new LinkedList<>();

        btnMultiLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AlertDialogActivity.this)
                        .setTitle("언어를 모두 선택해주세요!")
                        .setIcon(android.R.drawable.ic_dialog_map)
                        .setMultiChoiceItems(laguages, null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked == true) {
                                    myLaguageList.add(laguages[which]);
                                } else {
                                    myLaguageList.remove(laguages[which]);
                                }
                                Log.e("내가 선택한 언어들", myLaguageList.toString());
                            }
                        })
                        .setNegativeButton("취소", null)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Snackbar.make(v, myLaguageList.toString(), Snackbar.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //대화상자에 출력할 뷰를 생성 - login.xml 파일을 이용
                LinearLayout login = (LinearLayout)View.inflate(AlertDialogActivity.this, R.layout.login, null);

                new AlertDialog.Builder(AlertDialogActivity.this)
                        .setTitle("로그인")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setView(login)
                        .setNegativeButton("취소", null)
                        .setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //id 입력란 찾아오기
                                EditText tfId = login.findViewById(R.id.tfId);
                                //비밀번호 입력란 찾아오기
                                EditText tfPassword = login.findViewById(R.id.tfPassword);
                                //입력한 아이디와 비밀번호 로그에 출력
                                Log.e("아이디", tfId.getText().toString());
                                Log.e("비밀번호", tfPassword.getText().toString());
                            }
                        })
                        .show();
            }
        });

    }
}