package com.example.abheisenberg.inshortscontest.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.abheisenberg.inshortscontest.R;

public class BrowserActivity extends AppCompatActivity {

    private static final String TAG = "BrowserAct";
    public static final String ARTICLE_URL = "article_url";

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        String titleColor = getString(R.string.grey_color);
        getSupportActionBar().setTitle(Html.fromHtml(
                "<font color='"+titleColor+"'>"+"Full Article"+"</font>"
        ));

        WebView webView = (WebView) findViewById(R.id.wvBrowserAct);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        progressDialog = ProgressDialog.show(this, "Loading Article", "Please wait...");
        progressDialog.setCancelable(false);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progressDialog.show();
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();
            }
        });
        webView.loadUrl(getIntent().getStringExtra(ARTICLE_URL));

    }
}
