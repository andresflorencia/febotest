package com.florencia.febotest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.florencia.febotest.adapters.CategoryAdapter;
import com.florencia.febotest.adapters.NewsAdapter;
import com.florencia.febotest.interfaces.ApiArticles;
import com.florencia.febotest.models.Category;
import com.florencia.febotest.models.ResponseNews;
import com.florencia.febotest.utils.Constants;
import com.florencia.febotest.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    public static String TAG = "TAGMAIN_ACTIVITY";
    SearchView svSearch;

    LinearLayout lyContainer, lyLoading;
    ImageView imgEmptyResult;
    TextView txtMessage;
    ProgressBar pbLoading;
    Toolbar toolbar;

    RecyclerView rvNews, rvCategories;
    CategoryAdapter categoryAdapter;
    NewsAdapter newsAdapter;
    List<Category> categories = new ArrayList<>();
    public  String category_filter = "general";

    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        lyLoading = findViewById(R.id.lyLoading);
        lyContainer = findViewById(R.id.lyContainer);
        svSearch = findViewById(R.id.svSearch);
        pbLoading = findViewById(R.id.pbLoading);
        rvCategories = findViewById(R.id.rvCategories);
        rvNews = findViewById(R.id.rvNews);
        imgEmptyResult = findViewById(R.id.imgEmptyResult);
        txtMessage = findViewById(R.id.txtMessage);
        svSearch.setOnQueryTextListener(this);

        categories = new ArrayList<>();
        categories.add(new Category("general", "General", true));
        categories.add(new Category("sport", "Deportes", false));
        categories.add(new Category("health", "Salud", false));
        categories.add(new Category("business", "Negocios", false));
        categories.add(new Category("entertainment", "Entretenimiento", false));
        categories.add(new Category("science", "Ciencia", false));
        categories.add(new Category("technology", "Tecnología", false));

        categoryAdapter = new CategoryAdapter(MainActivity.this, categories);
        rvCategories.setAdapter(categoryAdapter);

        okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        SearchNews("general","");
    }

    public void SearchNews(String category, String filter){
        String url = Constants.url;
        if(!svSearch.getQuery().toString().trim().equals(""))
            filter = svSearch.getQuery().toString().trim();
        if(!category.equals(""))
            url = url.concat("&category=").concat(category);
        if(!filter.equals(""))
            url = url.concat("&q=").concat(filter);
        url = url.concat("&apiKey=").concat(Constants.apikey);
        Log.d(TAG, url);

        pbLoading.setVisibility(View.VISIBLE);
        lyLoading.setVisibility(View.VISIBLE);
        txtMessage.setText(getString(R.string.loading));
        rvNews.setVisibility(View.GONE);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.url_base)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        ApiArticles api = retrofit.create(ApiArticles.class);
        Call<ResponseNews> response = api.getnews(url);
        response.enqueue(new Callback<ResponseNews>() {
            @Override
            public void onResponse(Call<ResponseNews> call, retrofit2.Response<ResponseNews> response) {
                if(!response.isSuccessful()){
                    Log.d(TAG, "Error: " + response.code() + " -> " + response.message());
                    pbLoading.setVisibility(View.INVISIBLE);
                    imgEmptyResult.setVisibility(View.VISIBLE);
                    txtMessage.setText(getString(R.string.error));
                }

                try{
                    if(response.body()!=null){
                        ResponseNews responseNews = response.body();
                        if(responseNews.status.equalsIgnoreCase("ok")
                        && responseNews.totalResults>0) {
                            newsAdapter = new NewsAdapter(MainActivity.this, responseNews.articles);
                            rvNews.setAdapter(newsAdapter);
                            lyLoading.setVisibility(View.GONE);
                            rvNews.setVisibility(View.VISIBLE);
                        }else{
                            pbLoading.setVisibility(View.INVISIBLE);
                            imgEmptyResult.setVisibility(View.VISIBLE);
                            txtMessage.setText(getString(R.string.empty_result));
                        }
                    }
                }catch (Exception e){
                    Log.d(TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseNews> call, Throwable t) {
                //lyLoading.setVisibility(View.GONE);
                pbLoading.setVisibility(View.INVISIBLE);
                imgEmptyResult.setVisibility(View.VISIBLE);
                txtMessage.setText(getString(R.string.error));
                rvNews.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        SearchNews(category_filter, query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private static long presionado;
    @Override
    public void onBackPressed() {
        if (presionado + 2000 > System.currentTimeMillis())
            super.onBackPressed();
        else
            Utils.showMessage(this, "Vuelve a presionar para salir");
        presionado = System.currentTimeMillis();
    }
}
