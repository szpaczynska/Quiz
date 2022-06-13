package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class StartGame extends AppCompatActivity {
    TextView tvTimer;
    TextView tvResult;
    ImageView ShowImage;
    HashMap<String,Integer> map = new HashMap<>();
    ArrayList<String> flagi = new ArrayList<>();
    int index;
    Button btn1,btn2,btn3,btn4;
    TextView tvPoints;
    int points;
    CountDownTimer countDownTimer;
    long millisUntilFinished;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_game);
        tvTimer = findViewById(R.id.tvTimer);
        tvResult = findViewById(R.id.tvResult);
        ShowImage = findViewById(R.id.ivShowImage);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        tvPoints = findViewById(R.id.tvPoints);
        index = 0;
        flagi.add("Albania");
        flagi.add("Bułgaria");
        flagi.add("Dominikana");
        flagi.add("Finlandia");
        flagi.add("Francja");
        flagi.add("Islandia");
        flagi.add("Katar");
        flagi.add("Matla");
        flagi.add("Maroko");
        flagi.add("Norwegia");
        flagi.add("Uzbekistan");
        flagi.add("Wenezuela");
        map.put(flagi.get(0), R.drawable.albania);
        map.put(flagi.get(1), R.drawable.bulgaria);
        map.put(flagi.get(2), R.drawable.dominikana);
        map.put(flagi.get(3), R.drawable.finladnia);
        map.put(flagi.get(4), R.drawable.francja);
        map.put(flagi.get(5), R.drawable.islandia);
        map.put(flagi.get(6), R.drawable.katar);
        map.put(flagi.get(7), R.drawable.malta);
        map.put(flagi.get(8), R.drawable.maroko);
        map.put(flagi.get(9), R.drawable.norwegia);
        map.put(flagi.get(10), R.drawable.uzbekistan);
        map.put(flagi.get(11), R.drawable.wenezuela);
        Collections.shuffle(flagi);
        millisUntilFinished = 10000;
        points = 0;
        startGame();
    }

    private void startGame() {
        millisUntilFinished = 10000;
        tvTimer.setText(""+(millisUntilFinished / 1000) + "s");
        tvPoints.setText(points + " / " + flagi.size());
        generateQuestions(index);
        countDownTimer = new CountDownTimer(millisUntilFinished,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText(""+(millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                index++;
                if (index > flagi.size() - 1){
                    ShowImage.setVisibility(View.GONE);
                    btn1.setVisibility(View.GONE);
                    btn2.setVisibility(View.GONE);
                    btn3.setVisibility(View.GONE);
                    btn4.setVisibility(View.GONE);
                    Intent intent = new Intent(StartGame.this, GameOver.class);
                    intent.putExtra("punkty", points);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    countDownTimer = null;
                    startGame();
                }
            }
        }.start();


    }

    private void generateQuestions(int index) {
        ArrayList<String> flagiPom = (ArrayList<String>) flagi.clone();
        String correctA = flagi.get(index);
        flagiPom.remove(correctA);
        Collections.shuffle(flagiPom);
        ArrayList<String> odp = new ArrayList<>();
        odp.add(flagiPom.get(0));
        odp.add(flagiPom.get(1));
        odp.add(flagiPom.get(2));
        odp.add(correctA);
        Collections.shuffle(odp);
        btn1.setText(odp.get(0));
        btn2.setText(odp.get(1));
        btn3.setText(odp.get(2));
        btn4.setText(odp.get(3));
        ShowImage.setImageResource(map.get(flagi.get(index)));

    }

    public void nextQuestion(View view) {
        countDownTimer.cancel();
        index++;
        tvResult.setText("");
        if(index > flagi.size() - 1)
        {
            ShowImage.setVisibility(View.GONE);
            btn1.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
            btn3.setVisibility(View.GONE);
            btn4.setVisibility(View.GONE);
            Intent intent = new Intent(StartGame.this, GameOver.class);
            intent.putExtra("punkty", points);
            startActivity(intent);
            finish();
        }
        else{
            countDownTimer = null;
            startGame();
        }

    }

    public void answerSelected(View view) {
        countDownTimer.cancel();
        String answer = ((Button) view).getText().toString().trim();
        String correctA = flagi.get(index);
        if(answer.equals(correctA))
        {
            points++;
            tvPoints.setText(points + " / "+ flagi.size());
            tvResult.setText("Prawidłowa odpowiedź");
        }
        else
        {
            tvResult.setText("Zła odpowiedź");
        }
    }
}
