package com.florencia.febotest.adapters;

import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.florencia.febotest.MainActivity;
import com.florencia.febotest.R;
import com.florencia.febotest.fragments.NewsDialogFragment;
import com.florencia.febotest.models.Article;
import com.florencia.febotest.models.Category;
import com.florencia.febotest.utils.Utils;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{
    public List<Article> articles;
    MainActivity activity;

    public NewsAdapter(MainActivity activity, List<Article> articles){
        this.articles = articles;
        this.activity = activity;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.bind(articles.get(position));
    }


    @Override
    public int getItemCount() {
        return articles.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription, tvAutor, tvDate, tvRead;
        ImageView imgImage;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvAutor = itemView.findViewById(R.id.tvAutor);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvRead = itemView.findViewById(R.id.tvRead);
            imgImage = itemView.findViewById(R.id.imgImage);
        }

        void bind(final Article news){
            tvTitle.setText(news.title);
            if(news.author == null)
                news.author = "Desconocido";
            tvAutor.setText(news.author);
            tvDate.setText(news.publishedAt.split("T")[0]);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                tvDescription.setText(Html.fromHtml(news.description, Html.FROM_HTML_MODE_COMPACT));
            else
                tvDescription.setText(Html.fromHtml(news.description));

            Glide
                    .with(activity)
                    .load(news.urlToImage)
                    .centerCrop()
                    .placeholder(R.drawable.empty_search)
                    .into(imgImage);

            itemView.setOnClickListener(v->{
                DialogFragment dialogFragment = new NewsDialogFragment(articles.get(getAdapterPosition()));
                dialogFragment.show(activity.getSupportFragmentManager(), "dialog");
            });

            tvRead.setOnClickListener(v->Utils.GoToUrl(v.getContext(), articles.get(getAdapterPosition()).url));
        }
    }
}
