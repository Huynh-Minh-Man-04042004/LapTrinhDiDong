package com.example.baitap01;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
public class SoChinhPhuong extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_so_chinh_phuong);

        EditText edtNumbers = findViewById(R.id.edtNumbers);
        Button btnCheck = findViewById(R.id.btnCheck);
        TextView tvResult = findViewById(R.id.tvResult);

        btnCheck.setOnClickListener(v -> {
            String input = edtNumbers.getText().toString().trim();
            if (input.isEmpty()) {
                tvResult.setText("Vui lòng nhập số!");
                return;
            }

            String[] numberStrings = input.split(",");
            ArrayList<Integer> numbers = new ArrayList<>();
            try {
                for (String numStr : numberStrings) {
                    numbers.add(Integer.parseInt(numStr.trim()));
                }
            } catch (NumberFormatException e) {
                tvResult.setText("Vui lòng nhập đúng định dạng số nguyên, cách nhau bằng dấu phẩy.");
                return;
            }

            ArrayList<Integer> perfectSquares = getPerfectSquares(numbers);

            // Hiển thị kết quả
            tvResult.setText("Số chính phương: " + perfectSquares.toString());
            Toast.makeText(this, "Số chính phương: " + perfectSquares, Toast.LENGTH_SHORT).show();
        });
    }

    private ArrayList<Integer> getPerfectSquares(ArrayList<Integer> numbers) {
        ArrayList<Integer> perfectSquares = new ArrayList<>();
        for (int num : numbers) {
            if (isPerfectSquare(num)) {
                perfectSquares.add(num);
            }
        }
        return perfectSquares;
    }

    private boolean isPerfectSquare(int n) {
        if (n < 0) return false;
        int sqrt = (int) Math.sqrt(n);
        return sqrt * sqrt == n;
    }
}
