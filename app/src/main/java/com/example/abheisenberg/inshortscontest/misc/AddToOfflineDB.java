package com.example.abheisenberg.inshortscontest.misc;

import android.content.Context;
import android.os.AsyncTask;

import com.example.abheisenberg.inshortscontest.adapter.ArticleAdapter;
import com.example.abheisenberg.inshortscontest.database.DBHandler;
import com.example.abheisenberg.inshortscontest.model.Article;

import java.util.ArrayList;

/**
 * Created by abheisenberg on 9/9/17.
 */

public class AddToOfflineDB extends AsyncTask< ArrayList<Article>, Float, String> {

    Context context;
    ArticleAdapter articleAdapter;

    public AddToOfflineDB(Context context, ArticleAdapter articleAdapter) {
        this.context = context;
        this.articleAdapter = articleAdapter;
    }

    @Override
    protected String doInBackground(ArrayList<Article>... params) {

        DBHandler db = new DBHandler(context);

        for(Article thisArt: params[0]){
            thisArt.setFAV(0);
            db.addArticle(thisArt);
        }

        return "Voohoo";
    }

    @Override
    protected void onPostExecute(String s) {
        articleAdapter.showArticles();
        super.onPostExecute(s);
    }
}
