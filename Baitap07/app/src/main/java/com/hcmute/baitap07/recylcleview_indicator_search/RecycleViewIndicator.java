package com.hcmute.baitap07.recylcleview_indicator_search;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmute.baitap07.R;
import java.util.ArrayList;
import java.util.List;

public class RecycleViewIndicator extends AppCompatActivity {
    private RecyclerView rcIcon;
    private ArrayList<IconModel> arrayList1;
    private IconAdapter iconAdapter;
    private androidx.appcompat.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recycleviewindicator);

        // Ánh xạ RecyclerView
        rcIcon = findViewById(R.id.rcIcon);

        // Khởi tạo danh sách
        arrayList1 = new ArrayList<>();
        arrayList1.add(new IconModel(R.drawable.cplusplus, "Ngôn ngữ C++"));
        arrayList1.add(new IconModel(R.drawable.csharp, "Ngôn ngữ C#"));
        arrayList1.add(new IconModel(R.drawable.golang, "Ngôn ngữ Golang"));
        arrayList1.add(new IconModel(R.drawable.java, "Ngôn ngữ Java"));
        arrayList1.add(new IconModel(R.drawable.javascript, "Ngôn ngữ Javascript"));
        arrayList1.add(new IconModel(R.drawable.python, "Ngôn ngữ Python"));
        arrayList1.add(new IconModel(R.drawable.ruby, "Ngôn ngữ Ruby"));

        // Cấu hình GridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);
        rcIcon.setLayoutManager(gridLayoutManager);

        // Cài đặt Adapter
        iconAdapter = new IconAdapter(getApplicationContext(), arrayList1);
        rcIcon.setAdapter(iconAdapter);

        // Thêm Indicator
        rcIcon.addItemDecoration(new LinePagerIndicatorDecoration());

        // Thiết lập sự kiện tìm kiếm
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterListener(newText);
                return true;
            }
        });
    }

    // Hàm lọc danh sách theo từ khóa tìm kiếm
    private void filterListener(String text) {
        List<IconModel> list = new ArrayList<>();
        for (IconModel iconModel : arrayList1) {
            if (iconModel.getDesc().toLowerCase().contains(text.toLowerCase())) {
                list.add(iconModel);
            }
        }

        if (list.isEmpty()) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            iconAdapter.setListenerList(list);
        }
    }

    class LinePagerIndicatorDecoration extends RecyclerView.ItemDecoration {
        private final float indicatorHeight = 16;
        private final float indicatorStrokeWidth = 8;
        private final float indicatorItemLength = 16;
        private final float indicatorItemPadding = 8;
        private final Paint paint = new Paint();

        public LinePagerIndicatorDecoration() {
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(indicatorStrokeWidth);
            paint.setStyle(Paint.Style.FILL);
        }

        @Override
        public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            int itemCount = parent.getAdapter().getItemCount();
            float totalLength = indicatorItemLength * itemCount;
            float paddingBetweenItems = Math.max(0, itemCount - 1) * indicatorItemPadding;
            float indicatorTotalWidth = totalLength + paddingBetweenItems;
            float indicatorStartX = (parent.getWidth() - indicatorTotalWidth) / 2f;
            float indicatorPosY = parent.getHeight() - indicatorHeight;

            drawInactiveIndicators(canvas, indicatorStartX, indicatorPosY, itemCount);
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager == null) return;

            View activeChild = parent.getChildAt(0);
            if (activeChild == null) return;

            int activePosition = parent.getChildAdapterPosition(activeChild);
            if (activePosition == RecyclerView.NO_POSITION) return;

            float progress = activeChild.getLeft() / (float) activeChild.getWidth();
            drawActiveIndicator(canvas, indicatorStartX, indicatorPosY, activePosition, progress);
        }

        private void drawInactiveIndicators(Canvas canvas, float startX, float posY, int itemCount) {
            paint.setColor(0x66FFFFFF);
            float itemWidth = indicatorItemLength + indicatorItemPadding;
            float x = startX;
            for (int i = 0; i < itemCount; i++) {
                canvas.drawRoundRect(new RectF(x, posY, x + indicatorItemLength, posY + indicatorStrokeWidth), 4, 4, paint);
                x += itemWidth;
            }
        }

        private void drawActiveIndicator(Canvas canvas, float startX, float posY, int highlightPosition, float progress) {
            paint.setColor(0xFFFFFFFF);
            float itemWidth = indicatorItemLength + indicatorItemPadding;
            float highlightStart = startX + itemWidth * highlightPosition;
            float partialLength = indicatorItemLength * progress;
            canvas.drawRoundRect(new RectF(highlightStart + partialLength, posY, highlightStart + partialLength + indicatorItemLength, posY + indicatorStrokeWidth), 4, 4, paint);
        }
    }
}
