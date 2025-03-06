package com.example.baitap04;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {
    private ListView listView;
    private EditText editText;
    private Button btnNhap, btnCapNhat, btnXoa;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    private int vitri = -1; // Lưu vị trí item đang chọn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        // Ánh xạ View
        listView = findViewById(R.id.listview1);
        editText = findViewById(R.id.editText1);
        btnNhap = findViewById(R.id.btnNhap);
        btnCapNhat = findViewById(R.id.btnCapNhat);
        btnXoa = findViewById(R.id.btnXoa);

        // Khởi tạo danh sách
        arrayList = new ArrayList<>();
        arrayList.add("Java");
        arrayList.add("C#");
        arrayList.add("PHP");
        arrayList.add("Kotlin");
        arrayList.add("Dart");

        // Tạo Adapter
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                arrayList
        );

        // Đưa dữ liệu vào ListView
        listView.setAdapter(adapter);

        // Bắt sự kiện khi nhấn vào ListView để cập nhật
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editText.setText(arrayList.get(i)); // Lấy nội dung item hiển thị lên EditText
                vitri = i; // Lưu vị trí item được chọn
            }
        });

        // Bắt sự kiện khi nhấn nút "Thêm"
        btnNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newItem = editText.getText().toString().trim();
                if (!newItem.isEmpty()) { // Kiểm tra nếu EditText không rỗng
                    arrayList.add(newItem);
                    adapter.notifyDataSetChanged();
                    editText.setText(""); // Xóa nội dung EditText sau khi thêm
                } else {
                    Toast.makeText(ListViewActivity.this, "Nhập nội dung trước khi thêm!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Bắt sự kiện khi nhấn nút "Cập nhật"
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vitri != -1) { // Kiểm tra xem đã chọn item nào chưa
                    arrayList.set(vitri, editText.getText().toString());
                    adapter.notifyDataSetChanged();
                    editText.setText(""); // Xóa EditText sau khi cập nhật
                    vitri = -1; // Reset vị trí
                } else {
                    Toast.makeText(ListViewActivity.this, "Chưa chọn mục nào để cập nhật!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Bắt sự kiện khi nhấn nút "Xóa"
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vitri != -1) { // Kiểm tra nếu đã chọn item
                    arrayList.remove(vitri);
                    adapter.notifyDataSetChanged();
                    editText.setText(""); // Xóa EditText
                    vitri = -1; // Reset vị trí
                } else {
                    Toast.makeText(ListViewActivity.this, "Chưa chọn mục nào để xóa!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
