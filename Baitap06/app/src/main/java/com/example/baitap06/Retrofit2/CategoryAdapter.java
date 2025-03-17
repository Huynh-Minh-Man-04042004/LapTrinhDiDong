package com.example.baitap06.Retrofit2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.baitap06.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder>{
    private final Context context;
    private final List<Category> array;

    public CategoryAdapter(Context context, List<Category> array) {
        this.context = context;
        this.array = array;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category category = array.get(position);
        holder.tenSp.setText(category.getName());

        // Load ảnh với Glide
        Glide.with(context)
                .load(category.getImages())
                .into(holder.images);
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView images;
        public TextView tenSp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.image_cate);
            tenSp = itemView.findViewById(R.id.tvNameCategory);

            // Bắt sự kiện click cho từng item
            itemView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "You have selected a category: " + tenSp.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
