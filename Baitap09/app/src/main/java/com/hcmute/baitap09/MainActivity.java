package com.hcmute.baitap09;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private ImageView imgAvatar;
    private TextView tvId, tvUsername, tvFullName, tvEmail, tvGender;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ view
        imgAvatar = findViewById(R.id.imgAvatar);
        tvId = findViewById(R.id.tvId);
        tvUsername = findViewById(R.id.tvUsername);
        tvFullName = findViewById(R.id.tvFullName);
        tvEmail = findViewById(R.id.tvEmail);
        tvGender = findViewById(R.id.tvGender);
        btnLogout = findViewById(R.id.btnLogout);

        // Set dữ liệu giả lập (Bạn có thể lấy từ API)
        tvId.setText("1");
        tvUsername.setText("minhman");
        tvFullName.setText("Huỳnh Minh Mẫn");
        tvEmail.setText("nsndman0404@gmail.com");
        tvGender.setText("Male");

        // Sự kiện click vào avatar
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UploadImagesActivity.class);
                startActivity(intent);
            }
        });

        // Sự kiện đăng xuất (bạn có thể thêm logic đăng xuất tại đây)
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}