package com.example.baitap04;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainAnimationActivity extends AppCompatActivity {
    private Button btnAddItem;
    private RecyclerView rvItems;
    private CustomAnimationAdapter adapter;
    private List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_main);

        btnAddItem = findViewById(R.id.btn_add_item);
        rvItems = findViewById(R.id.rv_items);

        // Tạo danh sách dữ liệu ban đầu
        data = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            data.add("Item " + i);
        }

        adapter = new CustomAnimationAdapter(data);
        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        // Thêm hiệu ứng animation mặc định
        rvItems.setItemAnimator(new DefaultItemAnimator());

        // Xử lý sự kiện khi nhấn nút thêm item
        btnAddItem.setOnClickListener(v -> {
            adapter.addItem("New Item");
            rvItems.scrollToPosition(adapter.getItemCount() - 1);
        });
    }
}
