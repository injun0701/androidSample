package com.injun.multimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.google.android.material.snackbar.Snackbar;

public class VideoPlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        //VideoView 가져오기
        VideoView videoView = (VideoView)findViewById(R.id.videoView);
        //재생할 비디오 설정
        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);

        //비디오 파일 경로
        String path = "android.resource://" + getPackageName() + "/" + R.raw.trailer;
        videoView.setVideoURI(Uri.parse(path));
        videoView.requestFocus();

        //재생 버튼 이벤트 처리
        Button startBtn = (Button)findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.seekTo(0);
                videoView.start();
            }
        });

        //비디오 재생 준비 되면
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Snackbar.make(getWindow().getDecorView().getRootView(), "재생 준비 완료", Snackbar.LENGTH_SHORT).show();
            }
        });

        //비디오 재생 완료 되면
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer player) {
                Snackbar.make(getWindow().getDecorView().getRootView(), "동영상 재생이 완료되었습니다.", Snackbar.LENGTH_LONG).show();
            }
        });

        //볼륨을 최대로 설정
        Button volumeBtn = (Button)findViewById(R.id.volumeBtn);
        volumeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AudioManager mAudioManager = (AudioManager)
                        getSystemService(AUDIO_SERVICE);
                int maxVolume =
                        mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume,
                        AudioManager.FLAG_SHOW_UI);
            }
        });
    }
}