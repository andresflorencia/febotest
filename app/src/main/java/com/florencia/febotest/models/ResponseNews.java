package com.florencia.febotest.models;

import java.util.ArrayList;
import java.util.List;

public class ResponseNews {
    public String status;
    public Integer totalResults;
    public List<Article> articles;

    public ResponseNews(){
        this.status = "";
        this.totalResults = 0;
        this.articles = new ArrayList<>();
    }
}
