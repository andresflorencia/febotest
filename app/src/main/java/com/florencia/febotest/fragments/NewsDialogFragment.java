package com.florencia.febotest.fragments;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.florencia.febotest.R;
import com.florencia.febotest.models.Article;
import com.florencia.febotest.utils.Utils;

import org.w3c.dom.Text;

public class NewsDialogFragment extends AppCompatDialogFragment {

    TextView tvAutor, tvDate, tvTitle, tvDescription, tvContent, tvRead, tvSource;
    ImageView imgImage;
    View view;
    Article myNews;

    public NewsDialogFragment() {
        // Required empty public constructor
    }
    public NewsDialogFragment(Article news){
        this.myNews = news;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news_dialog, container, false);
        init();
        if(getDialog().getWindow()!=null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        return view;
    }

    private void init(){
        tvAutor = view.findViewById(R.id.tvAutor);
        tvDate = view.findViewById(R.id.tvDate);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvContent = view.findViewById(R.id.tvContent);
        imgImage = view.findViewById(R.id.imgImage);
        tvRead = view.findViewById(R.id.tvRead);
        tvSource = view.findViewById(R.id.tvSource);

        tvAutor.setText(myNews.author);
        tvDate.setText(myNews.publishedAt.split("T")[0]);
        tvTitle.setText(myNews.title);
        tvDescription.setText(myNews.description);
        tvContent.setText(myNews.content);
        tvSource.setText(myNews.source.name);

        Glide
                .with(view)
                .load(myNews.urlToImage)
                .centerCrop()
                .placeholder(R.drawable.empty_search)
                .into(imgImage);
        tvRead.setOnClickListener(v -> Utils.GoToUrl(view.getContext(), myNews.url));
    }

}
