package com.hcmute.baitap07.ViewFlipper;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.hcmute.baitap07.R;

import java.util.ArrayList;
import java.util.List;

public class ViewFlipperActivity extends AppCompatActivity {

    ViewFlipper viewFlipperMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewflipper);
        viewFlipperMain = findViewById (R.id.viewFlipperMain);
        ActionViewFlipperMain();
    }

    private void ActionViewFlipperMain() {
        List<String> arrayListFlipper = new ArrayList<>();
        arrayListFlipper.add("https://www.shutterstock.com/shutterstock/photos/1505158397/display_1500/stock-vector-pepperoni-pizza-banner-ads-on-chalk-board-background-with-tomatoes-and-basil-leaves-d-illustration-1505158397.jpg");
        arrayListFlipper.add("https://classiccoffee.com.vn/upload/1tYfzBflv0QIJMeW5n5lVMDFmlTG9xma0lq.jpg");
        arrayListFlipper.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRgcXFTumF5L02mokaH67ZNeQ_lMIijXQO0qA&s");
        arrayListFlipper.add("https://wowgift.vn/wp-content/uploads/2023/06/Stt-tra-sua-hay-nhat-1024x560-2.jpg");
        for (int i = 0; i < arrayListFlipper.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(arrayListFlipper.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipperMain.addView(imageView);
        }
        viewFlipperMain.setFlipInterval(3000);
        viewFlipperMain.setAutoStart(true);
        //set animation for flipper
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipperMain.setInAnimation(slide_in);
        viewFlipperMain.setOutAnimation(slide_out);
    }
}