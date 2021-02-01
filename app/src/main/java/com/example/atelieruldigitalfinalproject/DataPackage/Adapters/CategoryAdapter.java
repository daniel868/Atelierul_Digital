package com.example.atelieruldigitalfinalproject.DataPackage.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atelieruldigitalfinalproject.DataPackage.Listeners.OnClickListener;
import com.example.atelieruldigitalfinalproject.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {


    private final List<Category> categoryList = new ArrayList<>();
    private Context context;
    private OnClickListener listener;

    public CategoryAdapter(Context context, OnClickListener listener) {
        this.context = context;
        this.listener = listener;

        categoryList.add(new Category(context.getString(R.string.mountains), R.drawable.mountains));
        categoryList.add(new Category(context.getString(R.string.sea_side), R.drawable.sea_side));
        categoryList.add(new Category(context.getString(R.string.city_break), R.drawable.cities));

    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card_view, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bindData(categoryList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView cardView;
        private TextView textView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.categoryImageView);
            textView = itemView.findViewById(R.id.categoryName);
        }

        public void bindData(Category category, OnClickListener listener) {
            cardView.setImageResource(category.getPictureId());
            textView.setText(category.getCategoryName());

            itemView.setOnClickListener(v -> {
                listener.onClick(getAdapterPosition());
            });
        }
    }
}
