package com.example.abheisenberg.inshortscontest.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;

import com.example.abheisenberg.inshortscontest.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String titleColor = getString(R.string.grey_color);
        getSupportActionBar().setTitle(Html.fromHtml(
                "<font color='"+titleColor+"'>"+getString(R.string.app_name)+"</font>"
        ));

        Button btOpenArticles = (Button) findViewById(R.id.btArticlesOpen);
        btOpenArticles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ArticlesActivity.class));
            }
        });


    }
}
