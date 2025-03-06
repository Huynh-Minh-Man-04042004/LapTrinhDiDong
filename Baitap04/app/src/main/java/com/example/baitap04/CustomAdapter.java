package com.example.baitap04;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TEXT = 0;
    private static final int IMAGE = 1;
    private static final int USER = 2;

    private Context context;
    private List<Object> mObjects;

    public CustomAdapter(Context context, List<Object> mObjects) {
        this.context = context;
        this.mObjects = mObjects;
    }

    @Override
    public int getItemViewType(int position) {
        if (mObjects.get(position) instanceof String) {
            return TEXT;
        } else if (mObjects.get(position) instanceof Integer) {
            return IMAGE;
        } else {
            return USER;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        switch (viewType) {
            case TEXT:
                view = inflater.inflate(R.layout.row_text, parent, false);
                return new TextViewHolder(view);
            case IMAGE:
                view = inflater.inflate(R.layout.row_image, parent, false);
                return new ImageViewHolder(view);
            case USER:
                view = inflater.inflate(R.layout.row_user, parent, false);
                return new UserViewHolder(view);
            default:
                throw new IllegalStateException("Unexpected viewType: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TEXT:
                ((TextViewHolder) holder).tvText.setText((String) mObjects.get(position));
                break;
            case IMAGE:
                ((ImageViewHolder) holder).imvImage.setImageResource((int) mObjects.get(position));
                break;
            case USER:
                UserModel user = (UserModel) mObjects.get(position);
                UserViewHolder userViewHolder = (UserViewHolder) holder;
                userViewHolder.tvName.setText(user.getName());
                userViewHolder.tvAddress.setText(user.getAddress());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mObjects.size();
    }

    static class TextViewHolder extends RecyclerView.ViewHolder {
        TextView tvText;

        TextViewHolder(View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_text);
        }
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imvImage;

        ImageViewHolder(View itemView) {
            super(itemView);
            imvImage = itemView.findViewById(R.id.imv_image);
        }
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress;

        UserViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAddress = itemView.findViewById(R.id.tv_address);
        }
    }
}
