package com.example.abheisenberg.inshortscontest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by abheisenberg on 9/9/17.
 */

public class ArticleAdapter
        extends RecyclerView.Adapter <ArticleAdapter.ArticleViewHolder> {

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ArticleViewHolder extends  RecyclerView.ViewHolder {

        public ArticleViewHolder(View itemView) {
            super(itemView);
        }
    }

}
