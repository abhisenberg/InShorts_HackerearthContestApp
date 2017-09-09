package com.example.abheisenberg.inshortscontest.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abheisenberg on 9/9/17.
 */

public class API {

    private static API apiInstance;

    private ArticleAPI articleAPI;

    public ArticleAPI getArticleAPI(){
        return articleAPI;
    }

    private API() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://starlord.hackerearth.com/")
                .addConverterFactory(
                        GsonConverterFactory.create()
                )
                .build();

        articleAPI = retrofit.create(ArticleAPI.class);
    }

    public static API getApiInstance() {
        if(apiInstance == null){
            apiInstance = new API();
        }

        return apiInstance;
    }
}
