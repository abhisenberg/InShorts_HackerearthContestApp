package com.example.abheisenberg.inshortscontest.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.abheisenberg.inshortscontest.R;
import com.example.abheisenberg.inshortscontest.adapter.ArticleAdapter;
import com.example.abheisenberg.inshortscontest.api.API;
import com.example.abheisenberg.inshortscontest.api.ArticleAPI;
import com.example.abheisenberg.inshortscontest.interfaces.OnItemClickListener;
import com.example.abheisenberg.inshortscontest.model.Article;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticlesActivity extends AppCompatActivity {

    private static String TAG = "ArticleAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvArticleList);
        final ArticleAdapter articleAdapter = new ArticleAdapter(this, new ArrayList<Article>());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(articleAdapter);

        ArticleAPI articleAPI = API.getApiInstance().getArticleAPI();
        Callback<ArrayList<Article>> articleCallback = new Callback<ArrayList<Article>>() {
            @Override
            public void onResponse(Call<ArrayList<Article>> call, Response<ArrayList<Article>> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: size "+response.body().size());
                    articleAdapter.updateArticles(response.body());
                } else {
                    Log.d(TAG, "onResponse: Erorr-> "+response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Article>> call, Throwable t) {
                Log.d(TAG, "onFailure: ERROR");
            }
        };

        articleAPI.getAllArticles().enqueue(articleCallback);
    }
}
