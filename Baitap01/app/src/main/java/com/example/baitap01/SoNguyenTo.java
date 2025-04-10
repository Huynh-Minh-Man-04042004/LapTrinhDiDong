package com.example.baitap01;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SoNguyenTo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_so_nguyen_to);

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

            ArrayList<Integer> primeNumbers = getPrimeNumbers(numbers);

            Log.d("PrimeNumbers", "Số nguyên tố: " + primeNumbers);
            tvResult.setText("Số nguyên tố: " + primeNumbers.toString());
        });
        }
    private boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    private ArrayList<Integer> getPrimeNumbers(ArrayList<Integer> numbers) {
        ArrayList<Integer> primes = new ArrayList<>();
        for (int num : numbers) {
            if (isPrime(num)) {
                primes.add(num);
            }
        }
        return primes;
    }
}
