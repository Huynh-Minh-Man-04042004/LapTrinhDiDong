package com.example.baitap04;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAnimationAdapter extends RecyclerView.Adapter<CustomAnimationAdapter.ViewHolder> {
    private List<String> mDatas;

    public CustomAnimationAdapter(List<String> data) {
        this.mDatas = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.row_animation, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = mDatas.get(position);
        holder.tvItem.setText(item);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    // Thêm item vào danh sách và cập nhật RecyclerView
    public void addItem(String item) {
        mDatas.add(item);
        notifyItemInserted(mDatas.size() - 1);
    }

    // Xóa item theo vị trí
    public void removeItem(int position) {
        if (position >= 0 && position < mDatas.size()) {
            mDatas.remove(position);
            notifyItemRemoved(position);
        }
    }

    // Thay đổi nội dung item
    public void replaceItem(int position, String newItem) {
        if (position >= 0 && position < mDatas.size()) {
            mDatas.set(position, newItem);
            notifyItemChanged(position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItem;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);

            // Xóa item khi nhấn giữ
            itemView.setOnLongClickListener(v -> {
                removeItem(getAdapterPosition());
                Toast.makeText(itemView.getContext(), "Item Removed", Toast.LENGTH_SHORT).show();
                return true;
            });

            // Thay đổi item khi click
            itemView.setOnClickListener(v -> {
                replaceItem(getAdapterPosition(), "Item Changed");
                Toast.makeText(itemView.getContext(), "Item Updated", Toast.LENGTH_SHORT).show();
            });
        }
    }
}

