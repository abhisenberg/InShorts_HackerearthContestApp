package com.example.abheisenberg.inshortscontest.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.abheisenberg.inshortscontest.R;

public class ArticlesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvArticleList);


    }
}
