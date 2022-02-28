package com.moondroid.wordcomplete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private final int delay = 100;

    ImageView image, answerImage;
    TextView text;
    ImageView start;

    ImageView[] alphabet = new ImageView[10];

    ArrayList<String> word = new ArrayList<>();

    ArrayList<Integer> btn = new ArrayList<>();

    final int STAGE = 257;

    String answer;

    int num = 0;
    String str;

    int stageGoNum = 0;

    Animation shake;

    ArrayList<Integer> stageGo = new ArrayList<>();

    public static boolean sound = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("Sound", MODE_PRIVATE);
        sound = preferences.getBoolean("sound", true);
        ImageView icSound = findViewById(R.id.icSound);
        if (sound) {
            icSound.setImageResource(R.drawable.ic_baseline_music_note_24);
        } else {
            icSound.setImageResource(R.drawable.ic_baseline_music_off_24);
        }

        shake = AnimationUtils.loadAnimation(this, R.anim.shakeanimation);
        answerImage = findViewById(R.id.answer);
        image = findViewById(R.id.image);
        start = findViewById(R.id.start);
        text = findViewById(R.id.text);
        for (int i = 0; i < alphabet.length; i++) {
            alphabet[i] = findViewById(R.id.alpha01 + i);
        }
        word = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.words)));

        for (int i = 0; i < STAGE; i++) {
            stageGo.add(i);
        }
        Collections.shuffle(stageGo);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.postDelayed(() -> {
                    start.setVisibility(View.GONE);
                    btnRset();
                }, delay);
            }
        });
    }

    public void btnRset() {
        image.setImageResource(R.drawable.image001 + stageGo.get(stageGoNum));

        str = word.get(stageGo.get(stageGoNum));

        for (int i = 0; i < str.length(); i++) {
            btn.add((int) (str.charAt(i)));
        }

        Collections.shuffle(btn);

        for (ImageView view : alphabet) {
            view.setVisibility(View.GONE);
        }

        if (str.length() <= 5) {
            for (int i = 0; i < str.length(); i++) {
                alphabet[i].setVisibility(View.VISIBLE);
                alphabet[i].setImageResource(R.drawable.alphabet_a + btn.get(i) - 97);
                alphabet[i].setTag(R.drawable.alphabet_a + btn.get(i) - 97);
            }
        } else {
            for (int i = 0; i < str.length() / 2; i++) {
                alphabet[i].setVisibility(View.VISIBLE);
                alphabet[i].setImageResource(R.drawable.alphabet_a + btn.get(i) - 97);
                alphabet[i].setTag(R.drawable.alphabet_a + btn.get(i) - 97);
            }

            int minus = str.length() - str.length() / 2;
            for (int i = 0; i < minus; i++) {
                alphabet[5 + i].setVisibility(View.VISIBLE);
                alphabet[5 + i].setImageResource(R.drawable.alphabet_a + btn.get(i + str.length() / 2) - 97);
                alphabet[5 + i].setTag(R.drawable.alphabet_a + btn.get(i + str.length() / 2) - 97);
            }

        }

        btn.clear();
    }

    public void click(View v) {
        v.postDelayed(() -> {
            num++;
            v.setVisibility(View.INVISIBLE);
            char a = (char) ((int) (v.getTag()) - R.drawable.alphabet_a + 97);
            answer = text.getText().toString();
            answer += a;
            text.setText(answer);

            if (num == word.get(stageGo.get(stageGoNum)).length()) {
                num = 0;
                for (int i = 0; i < alphabet.length; i++) {
                    alphabet[i].setVisibility(View.VISIBLE);
                    alphabet[i].setImageResource(0);

                }

                if (answer.equals(word.get(stageGo.get(stageGoNum)))) {
//                Toast.makeText(MainActivity.this, "정답입니다.", Toast.LENGTH_SHORT).show();
                    answerImage.setImageResource(R.drawable.answer_o);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            text.setText("");
                            answerImage.setImageResource(0);

                            stageGoNum++;

                            if (stageGoNum >= STAGE) {
                                stageGoNum = 0;
                                Collections.shuffle(stageGo);
                            }
                            btnRset();
                        }
                    }, 300);

                } else {
                    image.startAnimation(shake); //틀렸을때 애니메이션 흔들리게 하기
                    answerImage.setImageResource(R.drawable.answer_x);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            text.setText("");
                            answerImage.setImageResource(0);
                        }
                    }, 300);

//                Toast.makeText(MainActivity.this, "틀렸습니다.", Toast.LENGTH_SHORT).show();

                    btnRset();
                }
            }
        }, delay);
    }

    public void changeSound(View view) {
        AnimImageView iv = (AnimImageView) view;
        if (sound) {
            sound = false;
            iv.setImageResource(R.drawable.ic_baseline_music_off_24);
        } else {
            sound = true;
            iv.setImageResource(R.drawable.ic_baseline_music_note_24);
        }
        SharedPreferences preferences = getSharedPreferences("Sound", MODE_PRIVATE);
        preferences.edit().putBoolean("sound", sound).commit();
    }
}