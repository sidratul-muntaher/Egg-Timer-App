package com.caffeine_to_code.timmer;

//import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView timmer;
    /**/
    int minite;
    int second;
    MediaPlayer mMediaPlayer;
    int time = 1;
    SeekBar mSeekbar;
    Button startBtn;
    Boolean isActive = false;
    CountDownTimer mCountDownTimer;
    @TargetApi(Build.VERSION_CODES.P)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timmer = (TextView) findViewById(R.id.textView);
        mMediaPlayer = MediaPlayer.create(this, R.raw.airhorn);
        final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        /*int maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        final int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        final int minimumVol = audioManager.getStreamMinVolume(AudioManager.STREAM_MUSIC);*/
        startBtn = (Button) findViewById(R.id.start);
        mSeekbar = (SeekBar) findViewById(R.id.seekBar);
        mSeekbar.setMax(600);
        mSeekbar.setMin(0);
       mSeekbar.setProgress(30);

        time = mSeekbar.getProgress();
        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                System.out.println("Progress bar = " + progress);
                /*audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);*/
                minite =(int) progress / 60;
                second = (int) (progress - (minite * 60));
                System.out.println("progress : " + minite + " : " + second);

                if(second <= 9){
                    timmer.setText( minite + " : " +"0"+ second);
                }/*else if ((second % 60 == 0)){
                    timmer.setText( minite + " : " + second+"0");
                }*/else{
                    timmer.setText( minite + " : " + second);
                }

                time = second + minite * 60;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startTimer(View view){
        if(isActive == false) {
            isActive = true;
            startBtn.setText("Stop");
            startBtn.animate().rotation(1800).translationY(350).setDuration(4000);
            startBtn.setBackgroundResource(R.drawable.stop);
            //startBtn.animate().rotationBy(300100).translationY(200).translationZ(200).translationX(-200).translationZ(200).setDuration((time * 1000)+ 1000);
            mCountDownTimer = new CountDownTimer(((time * 1000) + 1000), 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    mSeekbar.setVisibility(View.INVISIBLE);

                    int miliS = (int) millisUntilFinished / 1000;
                    int m = (int) miliS / 60;
                    int s = (int) (miliS - (m * 60));
                    if(s <= 9){
                        timmer.setText( m + " : "+ "0" + s);
                    }/*else if ((second % 10 == 0 && second == 10) || (second % 10 == 0 && second == 20) || (second % 10 == 0 && second == 30) ||(second % 10 == 0 && second == 40) || (second % 10 == 0 && second == 50)){
                        timmer.setText( minite + " : " + second+"0");
                    }*/else{
                        timmer.setText(m + " : " + s);
                    }

                    System.out.println(miliS);
                    System.out.println(m + " : " + s);
                }


                @Override
                public void onFinish() {
                    mMediaPlayer.start();
                    mSeekbar.setVisibility(View.VISIBLE);
                    isActive = false;
                    startBtn.setText("Go!");
                    timmer.setText("0 : 30");
                    mSeekbar.setProgress(30);
                    startBtn.setBackgroundResource(R.drawable.button);
                    startBtn.animate().rotation(-1440).translationY(-10).setDuration(4000);
                }
            }.start();
        }else{
            startBtn.setText("Go!");
            mSeekbar.setVisibility(View.VISIBLE);
            isActive = false;
            timmer.setText("0 : 30");
            mCountDownTimer.cancel();
            mSeekbar.setProgress(30);
            startBtn.setBackgroundResource(R.drawable.button);
            startBtn.animate().rotation(-1440).translationY(-10).setDuration(4000);
        }
    }

}
