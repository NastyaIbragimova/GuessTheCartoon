package com.example.guessthecartoon;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class CartoonActivity extends AppCompatActivity {

    private ArrayList<Cartoon> cartoonsArray = new ArrayList<>();
    private ArrayList<Cartoon> level1 = new ArrayList<>();
    private ArrayList<Cartoon> level2 = new ArrayList<>();
    private ArrayList<Cartoon> level3 = new ArrayList<>();
    private Dialog dialogEnd;
    private final char[] alphabet = new char[]{'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С',
            'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я'};
    private ImageView picture;
    private String answerCartoon, playerAnswer, answerTrim;
    private TextView tvNext, tvDelete;
    private int countPressedButton, countWords, level;
    private ArrayList<Character> answerArray = new ArrayList<>();
    private ArrayList<TextView> gameArray = new ArrayList<>();
    private ArrayList<TextView> pressedLetter = new ArrayList<>();
    private final int[] tvButtonArray = new int[]{R.id.let1, R.id.let2, R.id.let3, R.id.let4, R.id.let5, R.id.let6, R.id.let7, R.id.let8, R.id.let9, R.id.let10,
            R.id.let11, R.id.let12, R.id.let13, R.id.let14, R.id.let15, R.id.let16, R.id.let17, R.id.let18, R.id.let19, R.id.let20,
            R.id.let21, R.id.let22, R.id.let23, R.id.let24, R.id.let25, R.id.let26, R.id.let27, R.id.let28, R.id.let29, R.id.let30};
    private final int[] tvLetterArray = new int[]{R.id.letter1, R.id.letter2, R.id.letter3, R.id.letter4, R.id.letter5,
            R.id.letter6, R.id.letter7, R.id.letter8, R.id.letter9, R.id.letter10, R.id.letter11, R.id.letter12, R.id.letter13, R.id.letter14,
            R.id.letter15, R.id.letter16, R.id.letter17, R.id.letter18, R.id.letter19, R.id.letter20, R.id.letter21, R.id.letter22, R.id.letter23,
            R.id.letter24, R.id.letter25, R.id.letter26, R.id.letter27, R.id.letter28, R.id.letter29, R.id.letter30};
    private final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartoon);
        cartoonsArray.clear();
        Intent intent = getIntent();
        level = intent.getIntExtra("Level", 0);
        if (level == 1) {
            cartoonsArray = level1;
        } else if (level == 2) {
            cartoonsArray = level2;
        } else {
            cartoonsArray = level3;
        }
        init();
        setTask();
    }

    private void init() {
        tvNext = findViewById(R.id.tv_next);
        picture = findViewById(R.id.iv_cartoon);
        tvDelete = findViewById(R.id.tv_delete);
        picture = findViewById(R.id.iv_cartoon);
        tvNext = findViewById(R.id.tv_next);
        countWords = 0;
        dialogEnd = new Dialog(this);
        fillArrays();
    }

    private void setTask() {
        pressedLetter.clear();
        answerArray.clear();
        gameArray.clear();
        playerAnswer = "";
        answerTrim = "";
        countPressedButton = 0;
        answerCartoon = cartoonsArray.get(countWords).getName().toUpperCase();
        picture.setImageResource(cartoonsArray.get(countWords).getImage());

        for (int i = 0; i < tvLetterArray.length; i++) {
            TextView tv = findViewById(tvLetterArray[i]);
            tv.setEnabled(true);
        }
        for (int i = 0; i < 30; i++) {
            answerArray.add(alphabet[random.nextInt(32)]);
        }

        for (int i = 0; i < answerCartoon.length(); i++) {
            TextView let = findViewById(tvLetterArray[i]);
            if (answerCartoon.charAt(i) != ' ') {
                answerTrim += answerCartoon.charAt(i);
                answerArray.set(i, answerCartoon.charAt(i));
                let.setVisibility(View.VISIBLE);
                gameArray.add(let);
            }
        }

        Collections.shuffle(answerArray);
        for (int i = 0; i < tvButtonArray.length; i++) {
            TextView tvLet = findViewById(tvButtonArray[i]);
            tvLet.setText(answerArray.get(i) + "");
        }
        setOnClick();
    }

    private void setOnClick() {

        for (int i = 0; i < tvButtonArray.length; i++) {
            TextView tvLet = findViewById(tvButtonArray[i]);
            tvLet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (countPressedButton != gameArray.size()) {
                        TextView let = gameArray.get(countPressedButton);
                        for (int i = 0; i < tvLetterArray.length; i++) {
                            TextView tvLet = findViewById(tvLetterArray[i]);
                            if (tvLet.getText().toString().equals(" ")) {
                                let = tvLet;
                                break;
                            }
                        }
                        let.setText(tvLet.getText().toString());
                        tvLet.setEnabled(false);
                        tvLet.setBackgroundResource(R.drawable.button_true_cartoon);
                        if (playerAnswer.indexOf(' ') != -1) {
                            playerAnswer = playerAnswer.substring(0, playerAnswer.indexOf(' ')) + tvLet.getText().toString() + playerAnswer.substring(playerAnswer.indexOf(' ')+1);
                        } else {
                            playerAnswer += tvLet.getText().toString();
                        }
                        countPressedButton++;
                        pressedLetter.add(tvLet);
                    }
                    checkAnswer();
                }
            });
        }

        for (int i = 0; i < gameArray.size(); i++) {
            TextView tvLet = gameArray.get(i);
            int finalI = i;
            tvLet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Iterator<TextView> iterator = pressedLetter.iterator();
                    while (iterator.hasNext()) {
                        TextView let = iterator.next();
                        if (tvLet.getText().toString().equals(let.getText().toString())) {
                            Log.e("MyLog", "before   " + playerAnswer);
                            playerAnswer = playerAnswer.substring(0, finalI) + " " + playerAnswer.substring(finalI + 1);
                            Log.e("MyLog", "after   " + playerAnswer);
                            let.setBackgroundResource(R.drawable.button_cartoon);
                            tvLet.setText(" ");
                            let.setEnabled(true);
                            countPressedButton--;
                            iterator.remove();
                        }
                    }
                }
            });
        }
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearArrays();
            }
        });
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countWords < 14) {
                    countWords++;
                    setInvisibility();
                    setTask();
                    clearArrays();
                } else {
                    dialogEnd.setContentView(R.layout.dialog_end_cartoon);
                    TextView tvLevels = dialogEnd.findViewById(R.id.button_levels);
                    TextView tvExit = dialogEnd.findViewById(R.id.button_exit_cartoons);
                    Intent intent = new Intent(CartoonActivity.this, StartActivity.class);
                    tvExit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finishAffinity();
                        }
                    });
                    tvLevels.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent.putExtra("Level", level);
                            startActivity(intent);
                        }
                    });
                    dialogEnd.show();
                }
            }
        });
    }

    private void setInvisibility() {
        for (int i = 0; i < answerCartoon.length(); i++) {
            TextView let = findViewById(tvLetterArray[i]);
            let.setVisibility(View.INVISIBLE);
        }
        tvNext.setVisibility(View.INVISIBLE);
    }

    private void checkAnswer() {
        if (answerTrim.equals(playerAnswer)) {
            tvNext.setVisibility(View.VISIBLE);
            for (int i = 0; i < tvLetterArray.length; i++) {
                TextView tv = findViewById(tvLetterArray[i]);
                tv.setEnabled(false);
            }
        }
    }

    private void clearArrays() {
        countPressedButton = 0;
        playerAnswer = "";
        for (int i = 0; i < tvButtonArray.length; i++) {
            TextView tvLet = findViewById(tvButtonArray[i]);
            tvLet.setBackgroundResource(R.drawable.button_cartoon);
            tvLet.setEnabled(true);
        }
        for (int i = 0; i < tvLetterArray.length; i++) {
            TextView tvLet = findViewById(tvLetterArray[i]);
            tvLet.setText("");
        }
    }

    private void fillArrays() {
        level1.add(new Cartoon("Король лев", R.drawable.cartoon1));
        level1.add(new Cartoon("Валл-и", R.drawable.cartoon2));
        level1.add(new Cartoon("Как приручить дракона", R.drawable.cartoon3));
        level1.add(new Cartoon("Зверополис", R.drawable.cartoon4));
        level1.add(new Cartoon("Ходячий замок", R.drawable.cartoon5));
        level1.add(new Cartoon("Вверх", R.drawable.cartoon6));
        level1.add(new Cartoon("Ледниковый период", R.drawable.cartoon7));
        level1.add(new Cartoon("Тайна Коко", R.drawable.cartoon8));
        level1.add(new Cartoon("Головоломка", R.drawable.cartoon9));
        level1.add(new Cartoon("Рататуй", R.drawable.cartoon10));
        level1.add(new Cartoon("Корпорация монстров", R.drawable.cartoon11));
        level1.add(new Cartoon("Унесённые призраками", R.drawable.cartoon12));
        level1.add(new Cartoon("Шрэк", R.drawable.cartoon13));
        level1.add(new Cartoon("Холодное сердце", R.drawable.cartoon14));
        level1.add(new Cartoon("Красавица и чудовище", R.drawable.cartoon15));
        Collections.shuffle(level1);
        level2.add(new Cartoon("Аладдин", R.drawable.cartoon16));
        level2.add(new Cartoon("Вольт", R.drawable.cartoon17));
        level2.add(new Cartoon("Принцесса Мононоке", R.drawable.cartoon18));
        level2.add(new Cartoon("Кот в сапогах", R.drawable.cartoon19));
        level2.add(new Cartoon("Мегамозг", R.drawable.cartoon20));
        level2.add(new Cartoon("Миньоны", R.drawable.cartoon21));
        level2.add(new Cartoon("Хранители снов", R.drawable.cartoon22));
        level2.add(new Cartoon("Мой сосед Тоторо", R.drawable.cartoon23));
        level2.add(new Cartoon("Босс-молокосос", R.drawable.cartoon24));
        level2.add(new Cartoon("Монстры на каникулах", R.drawable.cartoon25));
        level2.add(new Cartoon("Русалочка", R.drawable.cartoon26));
        level2.add(new Cartoon("Балто", R.drawable.cartoon27));
        level2.add(new Cartoon("История игрушек", R.drawable.cartoon28));
        level2.add(new Cartoon("Город героев", R.drawable.cartoon29));
        level2.add(new Cartoon("Суперсемейка", R.drawable.cartoon30));
        Collections.shuffle(level2);
        level3.add(new Cartoon("Гадкий я", R.drawable.cartoon31));
        level3.add(new Cartoon("Тачки", R.drawable.cartoon32));
        level3.add(new Cartoon("Мадагаскар", R.drawable.cartoon33));
        level3.add(new Cartoon("В поисках Немо", R.drawable.cartoon34));
        level3.add(new Cartoon("Золушка", R.drawable.cartoon35));
        level3.add(new Cartoon("Геркулес", R.drawable.cartoon36));
        level3.add(new Cartoon("Дом", R.drawable.cartoon37));
        level3.add(new Cartoon("Рио", R.drawable.cartoon38));
        level3.add(new Cartoon("Зверопой", R.drawable.cartoon39));
        level3.add(new Cartoon("Принцесса и лягушка", R.drawable.cartoon40));
        level3.add(new Cartoon("Лило и Стич", R.drawable.cartoon41));
        level3.add(new Cartoon("Бемби", R.drawable.cartoon42));
        level3.add(new Cartoon("Покахонтас", R.drawable.cartoon43));
        level3.add(new Cartoon("Лесная братва", R.drawable.cartoon44));
        level3.add(new Cartoon("Лис и пёс", R.drawable.cartoon45));
        Collections.shuffle(level3);
    }
}