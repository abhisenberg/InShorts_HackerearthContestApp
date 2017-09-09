package com.example.abheisenberg.inshortscontest.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.abheisenberg.inshortscontest.R;
import com.example.abheisenberg.inshortscontest.adapter.ArticleAdapter;
import com.example.abheisenberg.inshortscontest.model.Article;

import java.util.ArrayList;

public class Favourites extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        RecyclerView recyclerViewFav = (RecyclerView) findViewById(R.id.rvFavs);
        final ArticleAdapter articleAdapter = new ArticleAdapter(this, new ArrayList<Article>());

        recyclerViewFav.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewFav.setAdapter(articleAdapter);

        articleAdapter.showFavArticles();

    }
}
