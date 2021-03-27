package com.florencia.febotest.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.florencia.febotest.MainActivity;
import com.florencia.febotest.R;
import com.florencia.febotest.models.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{
    public List<Category> categories;
    MainActivity activity;

    public CategoryAdapter(MainActivity activity, List<Category> categories){
        this.categories = categories;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(categories.get(position));
    }


    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
        }

        void bind(final Category category){
            tvName.setText(category.name.toUpperCase());
            if(category.selected){
                tvName.setTextColor(Color.WHITE);
                itemView.setBackgroundResource(R.drawable.bg_button_blue);
            }else{
                tvName.setTextColor(Color.BLACK);
                itemView.setBackgroundResource(R.drawable.bg_white);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Category myC = categories.get(getAdapterPosition());
                    categories.get(getAdapterPosition()).selected = true;
                    for(Category c:categories){
                        if(!c.id.equals(myC.id))
                            c.selected = false;
                    }
                    notifyDataSetChanged();
                    activity.category_filter = myC.id;
                    activity.SearchNews(myC.id,"");
                }
            });
        }
    }
}
