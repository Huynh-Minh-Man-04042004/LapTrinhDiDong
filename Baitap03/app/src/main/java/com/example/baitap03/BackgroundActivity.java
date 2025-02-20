package com.example.baitap03;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.ArrayList;
import java.util.Random;

public class BackgroundActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background);

        ConstraintLayout bgLayout = findViewById(R.id.constraintLayoutBG);

        changeBackground(bgLayout);
    }

    private void changeBackground(ConstraintLayout layout) {
        ArrayList<Integer> backgrounds = new ArrayList<>();
        backgrounds.add(R.drawable.cat_1);
        backgrounds.add(R.drawable.cat_2);
        backgrounds.add(R.drawable.cat_3);
        backgrounds.add(R.drawable.cat_4);
        backgrounds.add(R.drawable.cat_5);

        Random random = new Random();
        int randomIndex = random.nextInt(backgrounds.size());

        layout.setBackgroundResource(backgrounds.get(randomIndex));
    }
}
