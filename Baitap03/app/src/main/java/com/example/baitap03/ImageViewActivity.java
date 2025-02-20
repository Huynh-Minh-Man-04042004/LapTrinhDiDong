package com.example.baitap03;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class ImageViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);

        // Tìm ImageView theo ID
        ImageView img1 = findViewById(R.id.imageView1);

        // Thiết lập hình ảnh từ drawable
        img1.setImageResource(R.drawable.cat_1);
    }
}