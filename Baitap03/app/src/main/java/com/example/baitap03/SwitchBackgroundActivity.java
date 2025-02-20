package com.example.baitap03;

import android.os.Bundle;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.ArrayList;
import java.util.Random;

public class SwitchBackgroundActivity extends AppCompatActivity {
    private ConstraintLayout bgLayout;
    private ArrayList<Integer> backgrounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_bg);

        // Lấy ID của layout và switch
        bgLayout = findViewById(R.id.constraintLayoutSwitchBG);
        Switch switchChangeBg = findViewById(R.id.switchChangeBg);

        // Khởi tạo danh sách hình nền
        initBackgroundList();

        // Xử lý sự kiện bật/tắt switch
        switchChangeBg.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                changeBackground(); // Đổi hình nền khi bật switch
            }
        });
    }

    // Hàm khởi tạo danh sách hình nền
    private void initBackgroundList() {
        backgrounds = new ArrayList<>();
        backgrounds.add(R.drawable.cat_1);
        backgrounds.add(R.drawable.cat_2);
        backgrounds.add(R.drawable.cat_3);
        backgrounds.add(R.drawable.cat_4);
        backgrounds.add(R.drawable.cat_5);
    }

    // Hàm đổi hình nền ngẫu nhiên
    private void changeBackground() {
        Random random = new Random();
        int randomIndex = random.nextInt(backgrounds.size());
        bgLayout.setBackgroundResource(backgrounds.get(randomIndex));
    }
}
