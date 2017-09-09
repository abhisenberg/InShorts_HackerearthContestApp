package com.example.abheisenberg.inshortscontest.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abheisenberg.inshortscontest.R;
import com.example.abheisenberg.inshortscontest.activities.BrowserActivity;
import com.example.abheisenberg.inshortscontest.database.DBHandler;
import com.example.abheisenberg.inshortscontest.interfaces.OnItemClickListener;
import com.example.abheisenberg.inshortscontest.model.Article;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by abheisenberg on 9/9/17.
 */

public class ArticleAdapter
        extends RecyclerView.Adapter <ArticleAdapter.ArticleViewHolder> {

    private static final String TAG = "ArticleAdapter";

    private Context context;
    private ArrayList<Article> articlesList;

    private SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");

    public ArticleAdapter(Context context, ArrayList<Article> articlesList){
        this.context = context;
        this.articlesList = articlesList;
    }

//    public void updateArticles(ArrayList<Article> articlesList){
//        this.articlesList = articlesList;
//        notifyDataSetChanged();
//        Log.d(TAG, "updateArticles, new size: "+articlesList.size());
//    }

    public void showArticles(){
        DBHandler dbHandler = new DBHandler(context);
        this.articlesList = dbHandler.getAllArticles();
        Toast.makeText(context, "New size "+articlesList.size(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "updateArticles, new size: "+articlesList.size());
        notifyDataSetChanged();
    }

    public void showFavArticles(){
        DBHandler dbHandler = new DBHandler(context);
        this.articlesList = dbHandler.getFavArticles();
        Log.d(TAG, "updateArticles, new size: "+articlesList.size());
        notifyDataSetChanged();
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.list_item_article, parent, false);
        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ArticleViewHolder holder, int position) {
        final Article thisArticle = articlesList.get(position);

        holder.ivFav.setImageResource(setFavImg(thisArticle.getFAV()));
        holder.tvHeadline.setText(thisArticle.getTITLE());
        holder.tvPublisher.setText(cleanPublisherName(thisArticle.getPUBLISHER()));
        holder.tvTime.setText(timestampToDate(thisArticle.getTIMESTAMP()));
        holder.thisView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fullArticle = new Intent(context, BrowserActivity.class);
                fullArticle.putExtra(BrowserActivity.ARTICLE_URL, thisArticle.getURL());

                context.startActivity(fullArticle);
            }
        });
        holder.ivFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if(thisArticle.getFAV() == 0){
                    thisArticle.setFAV(1);
                    Toast.makeText(context, "Added to favourites!", Toast.LENGTH_SHORT).show();
                    holder.ivFav.setImageResource(R.drawable.fav);

                } else {
                    thisArticle.setFAV(0);
                    Toast.makeText(context, "Removed from favourites!", Toast.LENGTH_SHORT).show();
                    holder.ivFav.setImageResource(R.drawable.not_fav);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    class ArticleViewHolder extends  RecyclerView.ViewHolder {
        TextView tvHeadline, tvTime, tvPublisher;
        ImageView ivFav;
        View thisView;

        private ArticleViewHolder(View itemView) {
            super(itemView);
            thisView = itemView;
            tvHeadline = (TextView) itemView.findViewById(R.id.tvHeadline);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvPublisher = (TextView) itemView.findViewById(R.id.tvPublisher);
            ivFav = (ImageView) itemView.findViewById(R.id.ivFav);
        }
    }


    private String timestampToDate(Long timestamp){
        String dateString;

        Date date = new Date(timestamp);
        Log.d(TAG, "timestampToDate: "+date.toString());
        dateString = format.format(date);

        return dateString;
    }

    private String cleanPublisherName(String publisherName){
        int indexOfBackSlash = publisherName.indexOf('\\');
        if(indexOfBackSlash != -1){
            return publisherName.substring(0, indexOfBackSlash);
        }

        return publisherName;
    }

    private int setFavImg(int isFav){
        if(isFav == 0){
            return R.drawable.not_fav;
        } else {
            return R.drawable.not_fav;
        }
    }

}
