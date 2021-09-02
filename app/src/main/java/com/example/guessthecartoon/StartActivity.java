package com.example.guessthecartoon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    private Button level1, level2, level3;
    private int level;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        Intent intent1 = getIntent();
        level = intent1.getIntExtra("Level", 0);
        sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE);
        if (sharedPreferences.getInt("Level", 0)>level) {
            level = sharedPreferences.getInt("Level", 0);
        }
        level1 = findViewById(R.id.level1);
        level2 = findViewById(R.id.level2);
        level3 = findViewById(R.id.level3);
        switch (level) {
            case 0:
                level1.setText("Играть");
                level2.setText("Играть");
                level3.setText("Играть");
                level1.setEnabled(true);
                level2.setEnabled(false);
                level3.setEnabled(false);
                break;
            case 1:
                level1.setText("Пройти заново");
                level2.setText("Играть");
                level3.setText("Играть");
                level1.setEnabled(true);
                level2.setEnabled(true);
                level3.setEnabled(false);
                break;
            case 2:
                level1.setText("Пройти заново");
                level2.setText("Пройти заново");
                level3.setText("Играть");
                level1.setEnabled(true);
                level2.setEnabled(true);
                level3.setEnabled(true);
                break;
            case 3:
                level1.setText("Пройти заново");
                level2.setText("Пройти заново");
                level3.setText("Пройти заново");
                level1.setEnabled(true);
                level2.setEnabled(true);
                level3.setEnabled(true);
                break;
        }
        setOnClick();
    }

    private void setOnClick() {
        Intent intent = new Intent(StartActivity.this, CartoonActivity.class);

        level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Level", 1);
                startActivity(intent);
            }
        });
        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Level", 2);
                startActivity(intent);
            }
        });
        level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Level", 3);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Level", level);
        editor.apply();
    }
}
