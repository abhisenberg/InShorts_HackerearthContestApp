package com.example.abheisenberg.inshortscontest.activities;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.abheisenberg.inshortscontest.R;
import com.example.abheisenberg.inshortscontest.adapter.ArticleAdapter;
import com.example.abheisenberg.inshortscontest.api.API;
import com.example.abheisenberg.inshortscontest.api.ArticleAPI;
import com.example.abheisenberg.inshortscontest.database.DBHandler;
import com.example.abheisenberg.inshortscontest.interfaces.OnItemClickListener;
import com.example.abheisenberg.inshortscontest.misc.AddToOfflineDB;
import com.example.abheisenberg.inshortscontest.model.Article;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticlesActivity extends AppCompatActivity {

    private static String TAG = "ArticleAct";

    private ArticleAdapter articleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        String titleColor = getString(R.string.grey_color);
        getSupportActionBar().setTitle(Html.fromHtml(
                "<font color='"+titleColor+"'>"+getString(R.string.app_name)+"</font>"
        ));


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvArticleList);
        articleAdapter = new ArticleAdapter(this, new ArrayList<Article>());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(articleAdapter);

        ArticleAPI articleAPI = API.getApiInstance().getArticleAPI();
        Callback<ArrayList<Article>> articleCallback = new Callback<ArrayList<Article>>() {
            @Override
            public void onResponse(Call<ArrayList<Article>> call, Response<ArrayList<Article>> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: size "+response.body().size());

                    if(!doesDBExists()){
                        addToDB(response.body());
                    } else {
                        articleAdapter.showArticles();
                    }

                    removeLoadingSpinner();

                } else {
                    Log.d(TAG, "onResponse: Erorr-> "+response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Article>> call, Throwable t) {
                Log.d(TAG, "onFailure: ERROR");
            }
        };

        if(!isInternetActive()){

            Toast.makeText(this, "No internet! Cannot update data.", Toast.LENGTH_SHORT).show();
            removeLoadingSpinner();

            if(doesDBExists()){
                articleAdapter.showArticles();
            } else {
                Toast.makeText(this, "No data to show.", Toast.LENGTH_SHORT).show();
            }

        } else {
            articleAPI.getAllArticles().enqueue(articleCallback);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.btFavIcon) {
            startActivity(new Intent(this, Favourites.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addToDB(ArrayList<Article> articles){
        Log.d(TAG, "Adding new response to db");

        AddToOfflineDB addToOfflineDB = new AddToOfflineDB(this, articleAdapter);
        addToOfflineDB.execute(articles);
    }

    private boolean isInternetActive(){
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()
                == NetworkInfo.State.CONNECTED
                || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()
                == NetworkInfo.State.CONNECTED;
    }

    private Boolean doesDBExists(){
        File dbFile = getDatabasePath(DBHandler.DATABASE_NAME);
        return dbFile.exists();
    }

    private void removeLoadingSpinner(){
        findViewById(R.id.loadingCircle_articlesAct).setVisibility(View.GONE);
    }

}
