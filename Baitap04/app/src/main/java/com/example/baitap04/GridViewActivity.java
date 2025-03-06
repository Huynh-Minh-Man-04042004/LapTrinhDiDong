package com.example.baitap04;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class GridViewActivity extends AppCompatActivity {

    GridView gridView;
    EditText editText1;
    Button btnNhap, btnCapNhat, btnXoa;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    int selectedIndex = -1; // Lưu vị trí phần tử đang chọn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);

        // Ánh xạ các view
        gridView = findViewById(R.id.gridview1);
        editText1 = findViewById(R.id.editText1);
        btnNhap = findViewById(R.id.btnNhap);
        btnCapNhat = findViewById(R.id.btnCapNhat);
        btnXoa = findViewById(R.id.btnXoa);

        // Tạo danh sách dữ liệu
        arrayList = new ArrayList<>();
        arrayList.add("Java");
        arrayList.add("C#");
        arrayList.add("PHP");
        arrayList.add("Kotlin");
        arrayList.add("Dart");
        arrayList.add("Python");
        arrayList.add("Swift");
        arrayList.add("Ruby");
        arrayList.add("Go");

        // Tạo Adapter
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                arrayList
        );

        // Gán Adapter vào GridView
        gridView.setAdapter(adapter);

        // Xử lý sự kiện click để chọn phần tử
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedIndex = i; // Lưu vị trí được chọn
                editText1.setText(arrayList.get(i)); // Hiển thị nội dung vào EditText
            }
        });

        // Xử lý sự kiện nhấn nút Thêm
        btnNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText1.getText().toString().trim();
                if (!name.isEmpty()) {
                    arrayList.add(name);
                    adapter.notifyDataSetChanged();
                    editText1.setText(""); // Xóa nội dung nhập
                } else {
                    Toast.makeText(GridViewActivity.this, "Vui lòng nhập tên!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Xử lý sự kiện nhấn nút Cập Nhật
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedIndex >= 0) {
                    String newName = editText1.getText().toString().trim();
                    if (!newName.isEmpty()) {
                        arrayList.set(selectedIndex, newName); // Cập nhật giá trị
                        adapter.notifyDataSetChanged();
                        editText1.setText(""); // Xóa nội dung nhập
                        selectedIndex = -1; // Đặt lại vị trí
                    } else {
                        Toast.makeText(GridViewActivity.this, "Vui lòng nhập tên mới!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GridViewActivity.this, "Chọn một mục để cập nhật!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Xử lý sự kiện nhấn nút Xóa
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedIndex >= 0) {
                    arrayList.remove(selectedIndex); // Xóa phần tử
                    adapter.notifyDataSetChanged();
                    editText1.setText(""); // Xóa nội dung nhập
                    selectedIndex = -1; // Đặt lại vị trí
                } else {
                    Toast.makeText(GridViewActivity.this, "Chọn một mục để xóa!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
