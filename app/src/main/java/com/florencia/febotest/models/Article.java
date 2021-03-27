package com.florencia.febotest.models;

public class Article {
    public String title, description, url, urlToImage, publishedAt, content, author;
    public Source source;

    public Article(){
        this.title = "";
        this.description = "";
        this.url = "";
        this.urlToImage = "";
        this.publishedAt = "";
        this.content = "";
        this.author = "";
        this.source= new Source();
    }

    public class Source{
        public String id, name;
    }
}

