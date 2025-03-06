package com.example.baitap04;

import android.os.Bundle;
import android.widget.GridView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class CustomGridViewActivity extends AppCompatActivity {
    private GridView gridView;
    private ArrayList<MonHoc> monHocList;
    private MonHocAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_gridview);

        // Ánh xạ GridView
        gridView = findViewById(R.id.gridview_custom);

        // Khởi tạo danh sách môn học
        monHocList = new ArrayList<>();
        monHocList.add(new MonHoc("Java", "Lập trình Java", R.drawable.java));
        monHocList.add(new MonHoc("C#", "Lập trình C#", R.drawable.csharp));
        monHocList.add(new MonHoc("PHP", "Lập trình Web PHP", R.drawable.php));
        monHocList.add(new MonHoc("Kotlin", "Lập trình Kotlin", R.drawable.kotlin));
        monHocList.add(new MonHoc("Ruby", "Ngôn ngữ Ruby", R.drawable.ruby));

        // Khởi tạo Adapter và gán vào GridView
        adapter = new MonHocAdapter(this, R.layout.row_monhoc_gridview, monHocList);
        gridView.setAdapter(adapter);
    }
}
