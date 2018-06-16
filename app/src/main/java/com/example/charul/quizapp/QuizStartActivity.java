package com.example.charul.quizapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Locale;

public class QuizStartActivity extends AppCompatActivity {

    private static final long COUNTDOWN_IN_MILLIS=30000;


    TextView score,quesCount,time,tvQues;
    RadioGroup radioGroup;
    RadioButton btn1,btn2,btn3,btn4;
    Button next;

    private int quesCounter;
    private int quesCountTotal=3;
    private Questions currentQuestion;

    private int Score;
    private boolean answered;

    private ColorStateList textColorDefaultedCd;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_start);
        Button btnNext=(Button)findViewById(R.id.btnNext);

        score=(TextView)findViewById(R.id.Score);
        quesCount=(TextView)findViewById(R.id.QuesCount);
        time=(TextView)findViewById(R.id.time);
        tvQues=(TextView)findViewById(R.id.tvQues);

        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        btn1=(RadioButton)findViewById(R.id.btn1);
        btn2=(RadioButton)findViewById(R.id.btn2);
        btn3=(RadioButton)findViewById(R.id.btn3);
        btn4=(RadioButton)findViewById(R.id.btn4);

        next=(Button)findViewById(R.id.btnNext);

        textColorDefaultedCd=time.getTextColors();

        showNextQuestion();


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizStartActivity.this,MainActivity.class));
            }
        });
    }


        private void showNextQuestion() {

        timeLeftInMillis=COUNTDOWN_IN_MILLIS;
        startCountDown();
    }

    private void startCountDown() {
        countDownTimer=new CountDownTimer(timeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis=millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis=0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }

    private void checkAnswer() {
        countDownTimer.cancel();
    }

    private void updateCountDownText() {
        int minutes=(int)(timeLeftInMillis/1000)/60;
        int seconds=(int)(timeLeftInMillis/1000)%60;

        String timeFormatted=String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        time.setText(timeFormatted);

        if(timeLeftInMillis<10000){
            time.setTextColor(Color.RED);
        }else{
            time.setTextColor(textColorDefaultedCd);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
    }
}
