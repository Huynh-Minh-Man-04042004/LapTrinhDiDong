package com.example.baitap06.Thread_Handler;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baitap06.Thread_Handler.LoginActivity;
import com.example.baitap06.R;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        new Thread(() -> {
            int n = 0;
            try {
                do {
                    if (n >= 2000) {
                        runOnUiThread(() -> {
                            Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        });
                        return;
                    }
                    Thread.sleep(100);
                    n += 100;
                } while (true);
            } catch (InterruptedException e) {
                runOnUiThread(() -> {
                    Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                });
            } catch (Throwable e) { // Tách Throwable ra riêng
                runOnUiThread(() -> {
                    Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                });
                throw new RuntimeException(e);
            }
        }).start();
    }
}
