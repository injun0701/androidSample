package com.injun.multimedia;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.File;

public class VideoRecordActivity extends AppCompatActivity implements AutoPermissionsListener {
    MediaPlayer player;
    MediaRecorder recorder;

    //저장을 위한 프로퍼티
    String filename;

    //카메라 뷰를 앱 내에 출력하기 위한 프로퍼티
    SurfaceHolder holder;

    //녹화 시작 메서드드
   public void startRecording() {
        if (recorder == null) {
            recorder = new MediaRecorder();
        }

        //옵션설정
        //소리 입력 설정
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //비디오 입력 설정
        recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        //출력 포맷 설정
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        //오디오와 비디오 코덱 설정
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        recorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);

        //출력 파일 설정
        recorder.setOutputFile(filename);

        //카메라 화면을 가져오기 위한 설정
        recorder.setPreviewDisplay(holder.getSurface());

        try {
            recorder.prepare();
            recorder.start();
        } catch (Exception e) {
            e.printStackTrace();

            recorder.release();
            recorder = null;
        }
    }

    //녹화 중지 메서드
    public void stopRecording() {
        if (recorder == null) {
            return;
        }

        //녹화 중지 후 recorder 초기화
        recorder.stop();
        recorder.reset();
        recorder.release();
        recorder = null;

        //녹화 중지를 알려주기 위한 설정
        //Map과 유사한 객체로 안드로이드에서 설정이나 SQLite에 데이터를 저장할 때 사용
        ContentValues values = new ContentValues(10);

        values.put(MediaStore.MediaColumns.TITLE, "RecordedVideo");
        values.put(MediaStore.Audio.Media.ALBUM, "Video Album");
        values.put(MediaStore.Audio.Media.ARTIST, "Mike");
        values.put(MediaStore.Audio.Media.DISPLAY_NAME, "Recorded Video");
        values.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis() / 1000);
        //파일 포맷과 파일 이름 설정
        values.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
        values.put(MediaStore.Audio.Media.DATA, filename);

        //제대로 생성되었는지 확인
        //비디오를 저장하고 그 결과를 videoUri에 저장 - 실패하면 null 리턴
        Uri videoUri = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
        if (videoUri == null) {
            Log.d("SampleVideoRecorder", "Video insert failed.");
            return;
        }

        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, videoUri));

    }

    //비디오 재생을 위한 메서드
    public void startPlay() {
        if (player == null) {
            player = new MediaPlayer();
        }
        try {
            player.setDataSource(filename);
            player.setDisplay(holder);

            player.prepare();
            player.start();
        } catch (Exception e) {
            Log.e("비디오 재생 실패", e.getLocalizedMessage());
        }
    }

    //비디오 재생 중지 메서드
    public void stopPlay() {
        if (player == null) {
            return;
        }

        player.stop();
        player.release();
        player = null;
    }

    //Permissions는 권한들의 배열
    //grantResults는 권한들의 허용 여부를 정수로 만든 배열
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //AutoPermissions에 처리를 위임
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

    //거부한 권한에 대한 정보를 알려주는 메서드 - AutoPermissions의 메서드
    @Override
    public void onDenied(int i, String[] permissions) {
        Snackbar.make(getWindow().getDecorView().getRootView(), "거부한 권한 개수: " + permissions.length, Snackbar.LENGTH_SHORT).show();
    }

    //허용한 권항에 대한 정보를 알려주는 메서드 - AutoPermissions의 메서드
    @Override
    public void onGranted(int i, String[] permissions) {
        Snackbar.make(getWindow().getDecorView().getRootView(), "허용한 권한 개수: " + permissions.length, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_record);

        //카메라 뷰 설정
        SurfaceView surface = new SurfaceView(this);
        holder = surface.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        FrameLayout frame = findViewById(R.id.container);
        frame.addView(surface);

        //비디오 녹화 시작 버튼 클릭 이벤트
        Button btnStartRecording = findViewById(R.id.btnStartRecording);
        btnStartRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecording();
            }
        });

        //비디오 녹화 중지 버튼 클릭 이벤트
        Button btnStopRecording = findViewById(R.id.btnStopRecording);
        btnStopRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();
            }
        });

        //비디오 재생 시작 버튼 클릭 이벤트
        Button btnStartPlay = findViewById(R.id.btnStartPlay);
        btnStartPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlay();
            }
        });

        //비디오 재생 중지 버튼 클릭 이벤트
        Button btnStopPlay = findViewById(R.id.btnStopPlay);
        btnStopPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlay();
            }
        });

        //파일 핸들링 - windows는 디렉토리 기호가 \ 이고 그외 운영체제는 / 이며 절대경로를 설정할 필요가 있는 경우 디렉토리 기호를 File.separator 이용하면 운영체제 상관없이 동작함
        String fileDir = getFilesDir().getAbsolutePath();
        filename = fileDir + File.separator + "video.mp4";

        //동작 권한 요청
        AutoPermissions.Companion.loadAllPermissions(this, 101);

    }
}