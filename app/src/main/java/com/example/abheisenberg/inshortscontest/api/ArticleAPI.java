package com.example.abheisenberg.inshortscontest.api;

import com.example.abheisenberg.inshortscontest.model.Article;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by abheisenberg on 9/9/17.
 */

public interface ArticleAPI {
    @GET("/newsjson")
    Call<ArrayList<Article>> getAllArticles();
}
