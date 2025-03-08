package com.example.baitap05;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseHandler databaseHandler;
    ListView listView;
    ArrayList<NotesModel> arrayList;
    NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ ListView và khởi tạo adapter
        listView = findViewById(R.id.listView1);
        arrayList = new ArrayList<>();
        adapter = new NotesAdapter(this, R.layout.row_notes, arrayList);
        listView.setAdapter(adapter);

        // Khởi tạo database và load dữ liệu từ SQLite
        InitDatabaseSQLite();
        createDatabaseSQLite();
        databaseSQLite();
    }

    // Khởi tạo menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Xử lý khi chọn menu "Thêm Notes"
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAddNotes) {
            DialogThem();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Hàm mở Dialog thêm Notes
    private void DialogThem() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_notes);

        EditText editText = dialog.findViewById(R.id.editTextName);
        Button buttonAdd = dialog.findViewById(R.id.buttonThem);
        Button buttonHuy = dialog.findViewById(R.id.buttonHuy);

        // Xử lý nút "Thêm"
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString().trim();
                if (name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên Notes", Toast.LENGTH_SHORT).show();
                } else {
                    databaseHandler.QueryData("INSERT INTO Notes VALUES (null, '" + name + "')");
                    Toast.makeText(MainActivity.this, "Đã thêm Notes", Toast.LENGTH_SHORT).show();

                    // Cập nhật lại danh sách
                    databaseSQLite();
                    dialog.dismiss();
                }
            }
        });

        // Xử lý nút "Hủy"
        buttonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void DialogCapNhatNotes(String name, int id) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_notes);

        // Ánh xạ các view trong dialog
        EditText editText = dialog.findViewById(R.id.editTextName);
        Button buttonEdit = dialog.findViewById(R.id.buttonEdit);
        Button buttonHuy = dialog.findViewById(R.id.buttonHuy);

        // Gán tên hiện tại của Notes vào EditText
        editText.setText(name);

        // Xử lý sự kiện khi nhấn "Cập nhật"
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = editText.getText().toString().trim();
                if (newName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên Notes", Toast.LENGTH_SHORT).show();
                } else {
                    databaseHandler.QueryData("UPDATE Notes SET NameNotes = '" + newName + "' WHERE Id = '" + id + "'");
                    Toast.makeText(MainActivity.this, "Notes cập nhật thành công", Toast.LENGTH_SHORT).show();

                    dialog.dismiss();
                    databaseSQLite(); // Load lại dữ liệu
                }
            }
        });

        // Xử lý sự kiện khi nhấn "Hủy"
        buttonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void DialogDelete(String name, final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xóa ghi chú \"" + name + "\" không?");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xóa ghi chú khỏi database
                databaseHandler.QueryData("DELETE FROM Notes WHERE Id = '" + id + "'");

                // Hiển thị thông báo
                Toast.makeText(MainActivity.this, "Đã xóa ghi chú: " + name, Toast.LENGTH_SHORT).show();

                // Cập nhật lại danh sách
                databaseSQLite();
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Đóng dialog khi người dùng chọn "Không"
                dialog.dismiss();
            }
        });

        builder.show();
    }

    // Khởi tạo database
    private void InitDatabaseSQLite() {
        databaseHandler = new DatabaseHandler(this, "notes.sqlite", null, 1);
    }

    // Chèn dữ liệu mẫu vào database nếu chưa có
    private void createDatabaseSQLite() {
        Cursor cursor = databaseHandler.GetData("SELECT COUNT(*) FROM Notes");
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();

            if (count == 0) {
                databaseHandler.QueryData("INSERT INTO Notes VALUES (null, 'Ví dụ SQLite 1')");
                databaseHandler.QueryData("INSERT INTO Notes VALUES (null, 'Ví dụ SQLite 2')");
            }
        }
    }

    // Load dữ liệu từ SQLite và cập nhật ListView
    private void databaseSQLite() {
        arrayList.clear(); // Xóa dữ liệu cũ tránh trùng lặp
        Cursor cursor = databaseHandler.GetData("SELECT * FROM Notes");

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            arrayList.add(new NotesModel(id, name));
        }

        cursor.close();
        adapter.notifyDataSetChanged();
    }
}
