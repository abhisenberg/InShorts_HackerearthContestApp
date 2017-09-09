package com.example.abheisenberg.inshortscontest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.abheisenberg.inshortscontest.model.Article;

import java.util.ArrayList;

/**
 * Created by abheisenberg on 9/9/17.
 */

public class DBHandler extends SQLiteOpenHelper {

    public static final String TAG = "DB";

    private Context context;

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "inshortscontestdb";
    public static final String TABLE_NAME = "articles_data";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_URL = "url";
    private static final String KEY_PUBLISHER = "publisher";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_HOSTNAME = "hostname";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_FAV = "favourite";

    private static final String CREATE = " CREATE ";
    private static final String DROP = " DROP ";
    private static final String IF = " IF ";
    private static final String EXISTS = " EXISTS ";
    private static final String TABLE = " TABLE ";
    private static final String PRIMARY = " PRIMARY ";
    private static final String TEXT = " TEXT ";
    private static final String INTEGER = " INTEGER ";
    private static final String KEY = " KEY ";
    private static final String OPEN_BRACKET = " ( ";
    private static final String CLOSE_BRACKET =  " ) ";
    private static final String COMMA = " , ";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public DBHandler(Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ARTICLES_TABLE =
                  CREATE + TABLE
                + TABLE_NAME + OPEN_BRACKET
                + KEY_ID + INTEGER + PRIMARY + KEY + COMMA
                + KEY_TITLE + TEXT + COMMA
                + KEY_URL + TEXT + COMMA
                + KEY_PUBLISHER + TEXT + COMMA
                + KEY_CATEGORY + TEXT + COMMA
                + KEY_HOSTNAME + TEXT + COMMA
                + KEY_TIMESTAMP + TEXT + COMMA
                + KEY_FAV + INTEGER + CLOSE_BRACKET;

        db.execSQL(CREATE_ARTICLES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE =
                  DROP
                + TABLE
                + IF
                + EXISTS
                + TABLE_NAME;
        db.execSQL(DROP_TABLE);

        onCreate(db);
    }

    public void addArticle(Article article){
        Log.d(TAG, "adding article: "+article.getID());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, article.getID());
        values.put(KEY_TITLE, article.getTITLE());
        values.put(KEY_CATEGORY, article.getCATEGORY());
        values.put(KEY_HOSTNAME, article.getHOSTNAME());
        values.put(KEY_TIMESTAMP, article.getTIMESTAMP());
        values.put(KEY_PUBLISHER, article.getPUBLISHER());
        values.put(KEY_URL, article.getURL());
        values.put(KEY_FAV, article.getFAV());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Article> getFavArticles(){
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        ArrayList<Article> articles = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            Log.d(TAG, "Queries found ");

            do {

                Log.d(TAG, cursor.getString(0) + " is fav -> "+cursor.getString(7));

                if(Integer.parseInt(cursor.getString(7)) == 1){

                    Article article = new Article(
                            Integer.parseInt(cursor.getString(0)),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            Long.parseLong(cursor.getString(6)),
                            Integer.parseInt(cursor.getString(7))
                    );
                    articles.add(article);
                }

            } while(cursor.moveToNext());

        }

        Log.d(TAG, "getFavArticles: "+articles.size());
        
        return articles;
    }

    public ArrayList<Article> getAllArticles(){
        return querying("SELECT * FROM "+TABLE_NAME);
    }

    private ArrayList<Article> querying(String query){
        ArrayList<Article> articles = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            Log.d(TAG, "Queries found ");

            do {

                Log.d(TAG, "querying: artclie "+cursor.getString(0));

                Article article = new Article(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        Long.parseLong(cursor.getString(6)),
                        Integer.parseInt(cursor.getString(7))
                );

                articles.add(article);

            } while(cursor.moveToNext());

        }

        return articles;
    }


}
