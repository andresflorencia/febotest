package com.florencia.febotest.interfaces;

import com.florencia.febotest.models.ResponseNews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiArticles {

    @GET
    Call<ResponseNews> getnews(@Url String url);
}
