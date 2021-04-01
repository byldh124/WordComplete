package com.moondroid.wordcomplete;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView image, answerImage;
    TextView text;
    ImageView start;

    ImageView[] alphabet = new ImageView[10];

    ArrayList<String> word = new ArrayList<>();

    ArrayList<Integer> btn = new ArrayList<>();

    final int STAGE = 202;

    String answer;

    int num = 0;
    String str;

    int stageGoNum = 0;

    Animation shake;

    ArrayList<Integer> stageGo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shake = AnimationUtils.loadAnimation(this, R.anim.shakeanimation);
        answerImage = findViewById(R.id.answer);
        image = findViewById(R.id.image);
        start = findViewById(R.id.start);
        text = findViewById(R.id.text);
        for (int i = 0; i < alphabet.length; i++) {
            alphabet[i] = findViewById(R.id.alpha01 + i);
        }
        wordSet();

        for (int i = 0; i < STAGE; i++) {
            stageGo.add(i);
        }
        Collections.shuffle(stageGo);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setVisibility(View.GONE);

                image.setImageResource(R.drawable.image001 + stageGo.get(stageGoNum));

                str = word.get(stageGo.get(stageGoNum));

                for (int i = 0; i < str.length(); i++) {
                    btn.add((int) (str.charAt(i)));
                }

                Collections.shuffle(btn);

                if (str.length() <= 5) {
                    for (int i = 0; i < str.length(); i++) {
                        alphabet[i].setImageResource(R.drawable.alphabet_a + btn.get(i) - 97);
                        alphabet[i].setTag(R.drawable.alphabet_a + btn.get(i) - 97);
                    }
                } else {
                    for (int i = 0; i < str.length() / 2; i++) {
                        alphabet[i].setImageResource(R.drawable.alphabet_a + btn.get(i) - 97);
                        alphabet[i].setTag(R.drawable.alphabet_a + btn.get(i) - 97);
                    }

                    int minus = str.length() - str.length() / 2;
                    for (int i = 0; i < minus; i++) {
                        alphabet[5 + i].setImageResource(R.drawable.alphabet_a + btn.get(i + str.length() / 2) - 97);
                        alphabet[5 + i].setTag(R.drawable.alphabet_a + btn.get(i + str.length() / 2) - 97);
                    }

                }

                btn.clear();


            }
        });
    }

    public void click(View v) {
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


                        image.setImageResource(R.drawable.image001 + stageGo.get(stageGoNum));

                        str = word.get(stageGo.get(stageGoNum));

                        for (int i = 0; i < str.length(); i++) {
                            btn.add((int) (str.charAt(i)));
                        }

                        Collections.shuffle(btn);

                        if (str.length() <= 5) {
                            for (int i = 0; i < str.length(); i++) {
                                alphabet[i].setImageResource(R.drawable.alphabet_a + btn.get(i) - 97);
                                alphabet[i].setTag(R.drawable.alphabet_a + btn.get(i) - 97);
                            }
                        } else {
                            for (int i = 0; i < str.length() / 2; i++) {
                                alphabet[i].setImageResource(R.drawable.alphabet_a + btn.get(i) - 97);
                                alphabet[i].setTag(R.drawable.alphabet_a + btn.get(i) - 97);
                            }

                            int minus = str.length() - str.length() / 2;
                            for (int i = 0; i < minus; i++) {
                                alphabet[5 + i].setImageResource(R.drawable.alphabet_a + btn.get(i + str.length() / 2) - 97);
                                alphabet[5 + i].setTag(R.drawable.alphabet_a + btn.get(i + str.length() / 2) - 97);
                            }

                        }

                        btn.clear();
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

                str = word.get(stageGo.get(stageGoNum));

                for (int i = 0; i < str.length(); i++) {
                    btn.add((int) (str.charAt(i)));
                }

                Collections.shuffle(btn);

                if (str.length() <= 5) {
                    for (int i = 0; i < str.length(); i++) {
                        alphabet[i].setImageResource(R.drawable.alphabet_a + btn.get(i) - 97);
                        alphabet[i].setTag(R.drawable.alphabet_a + btn.get(i) - 97);
                    }
                } else {
                    for (int i = 0; i < str.length() / 2; i++) {
                        alphabet[i].setImageResource(R.drawable.alphabet_a + btn.get(i) - 97);
                        alphabet[i].setTag(R.drawable.alphabet_a + btn.get(i) - 97);
                    }

                    int minus = str.length() - str.length() / 2;
                    for (int i = 0; i < minus; i++) {
                        alphabet[5 + i].setImageResource(R.drawable.alphabet_a + btn.get(i + str.length() / 2) - 97);
                        alphabet[5 + i].setTag(R.drawable.alphabet_a + btn.get(i + str.length() / 2) - 97);
                    }

                }
                btn.clear();
            }
        }
    }

    public void wordSet() {
        word.add("apple");
        word.add("orange");
        word.add("card");
        word.add("sandwich");
        word.add("painter");
        word.add("doctor");
        word.add("guitar");
        word.add("television");
        word.add("music");
        word.add("clock");
        word.add("icecream");
        word.add("bubble");
        word.add("sleep");
        word.add("hamburger");
        word.add("pizza");
        word.add("spoon");
        word.add("cook");
        word.add("pilot");
        word.add("nurse");
        word.add("korea");
        word.add("china");
        word.add("japan");
        word.add("america");
        word.add("soccer");
        word.add("basketball");
        word.add("baseball");
        word.add("swimming");
        word.add("magic");
        word.add("drum");
        word.add("trumpet");
        word.add("pig");
        word.add("lion");
        word.add("tiger");
        word.add("hippo");
        word.add("hedgehog");
        word.add("prince");
        word.add("princess");
        word.add("king");
        word.add("flower");
        word.add("piano");
        word.add("dog");
        word.add("cat");
        word.add("developer");
        word.add("computer");
        word.add("door");
        word.add("deer");
        word.add("window");
        word.add("rabbit");
        word.add("sheep");
        word.add("horse");
        word.add("monkey");
        word.add("mouse");
        word.add("quiz");
        word.add("pencil");
        word.add("singer");
        word.add("scissors");
        word.add("stone");
        word.add("water");
        word.add("fire");
        word.add("green");
        word.add("red");
        word.add("blue");
        word.add("pink");
        word.add("yellow");
        word.add("cicada");
        word.add("mosquito");
        word.add("larva");
        word.add("seven");
        word.add("eight");
        word.add("butterfly");
        word.add("earth");
        word.add("candle");
        word.add("pumpkin");
        word.add("jewel");
        word.add("giraffe");
        word.add("chopstick");
        word.add("strawberry");
        word.add("peach");
        word.add("tangerine");
        word.add("bicycle");
        word.add("donut");
        word.add("bread");
        word.add("calendar");
        word.add("cheese");
        word.add("dolphin");
        word.add("grape");
        word.add("hospital");
        word.add("jungle");
        word.add("mirror");
        word.add("mountain");
        word.add("night");
        word.add("student");
        word.add("tennis");
        word.add("thunder");
        word.add("tomato");
        word.add("umbrella");
        word.add("xylophone");
        word.add("elephant");
        word.add("panda");
        word.add("turtle");
        word.add("persimmon");
        word.add("acorn");
        word.add("carrot");
        word.add("radish");
        word.add("cucumber");
        word.add("onion");
        word.add("airplane");
        word.add("airport");
        word.add("autumn");
        word.add("baby");
        word.add("bag");
        word.add("ball");
        word.add("balloon");
        word.add("banana");
        word.add("bank");
        word.add("basket");
        word.add("bath");
        word.add("bear");
        word.add("beach");
        word.add("bed");
        word.add("bell");
        word.add("bench");
        word.add("book");
        word.add("cake");
        word.add("chair");
        word.add("church");
        word.add("coffee");
        word.add("coin");
        word.add("film");
        word.add("fish");
        word.add("flag");
        word.add("glove");
        word.add("hair");
        word.add("hand");
        word.add("handle");
        word.add("heavy");
        word.add("house");
        word.add("island");
        word.add("juice");
        word.add("jump");
        word.add("key");
        word.add("knife");
        word.add("lake");
        word.add("letter");
        word.add("leaf");
        word.add("light");
        word.add("marriage");
        word.add("medal");
        word.add("melon");
        word.add("milk");
        word.add("family");
        word.add("moon");
        word.add("sun");
        word.add("money");
        word.add("movie");
        word.add("paint");
        word.add("pants");
        word.add("police");
        word.add("potato");
        word.add("rainbow");
        word.add("ribbon");
        word.add("rice");
        word.add("rocket");
        word.add("road");
        word.add("school");
        word.add("soap");
        word.add("smile");
        word.add("star");
        word.add("station");
        word.add("stove");
        word.add("table");
        word.add("swing");
        word.add("tooth");
        word.add("wind");
        word.add("blowfish");
        word.add("whale");
        word.add("shark");
        word.add("slipper");
        word.add("socks");
        word.add("hat");
        word.add("castle");
        word.add("mailbox");
        word.add("box");
        word.add("mole");
        word.add("bat");
        word.add("owl");
        word.add("squirrel");
        word.add("bottle");
        word.add("penguin");
        word.add("eggplant");
        word.add("tree");
        word.add("cloud");
        word.add("sword");
        word.add("thumb");
        word.add("heart");
        word.add("bulb");
        word.add("teacher");
        word.add("lollipop");
        word.add("ship");
        word.add("crown");
        word.add("snow");
        word.add("chick");
    }
}