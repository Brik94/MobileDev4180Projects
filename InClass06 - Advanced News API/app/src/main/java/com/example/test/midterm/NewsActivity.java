package com.example.test.midterm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    Source source;
    ProgressBar loadingIcon;
    TextView loadingArticlesText;
    ArrayList<Article> articleList;
    ArticleAdapter adapter;
    ListView listView;
    ArrayList<Bitmap> imageList;
    public static final String ARTICLE_KEY = "articlekey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        loadingIcon =(ProgressBar) findViewById(R.id.loadingIcon2);
        loadingArticlesText = (TextView) findViewById(R.id.loadingArticlesLabel);
        articleList = new ArrayList<>();
        imageList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.newsListView);
        adapter = new ArticleAdapter(this, R.layout.layout_article, articleList);
        source = (Source) getIntent().getExtras().getSerializable(MainActivity.SOURCE_KEY);
        setTitle(source.name);
        listView.setAdapter(adapter);
        if(isConnected())
            new GetArticlesAsync().execute("https://newsapi.org/v1/articles?source=" + source.id + "&apiKey=f06e1c13ae54477cbaea7bed975f50f3");
        else
            Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show();




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent webIntent = new Intent(NewsActivity.this, WebActivity.class);
                webIntent.putExtra(ARTICLE_KEY, articleList.get(i).url);
                startActivity(webIntent);
            }
        });




    }

    public class GetArticlesAsync extends AsyncTask<String, Void, ArrayList<Article>> {


        @Override
        protected void onPreExecute() {
            loadingIcon.setVisibility(View.VISIBLE);
            loadingArticlesText.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<Article> s) {


            for(int i = 0; i < s.size(); i++)
                articleList.add(s.get(i));

           // new GetImageAsync().execute();
            adapter.notifyDataSetChanged();
            loadingIcon.setVisibility(View.INVISIBLE);
            loadingArticlesText.setVisibility(View.INVISIBLE);

            Log.d("test", "articles loaded");


        }

        @Override
        protected ArrayList<Article> doInBackground(String... params) {
            HttpURLConnection connection = null;
            ArrayList<Article> result = new ArrayList<>();
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json = IOUtils.toString(connection.getInputStream(), "UTF8");

                    JSONObject root = new JSONObject(json);
                    JSONArray articles = root.getJSONArray("articles");

                    for (int i=0;i<articles.length();i++) {

                        JSONObject currentElement = articles.getJSONObject(i);
                        Article tempArticle = new Article();
                        tempArticle.author = currentElement.getString("author");
                        tempArticle.title = currentElement.getString("title");
                        tempArticle.url = currentElement.getString("url");
                        tempArticle.urlToImage = currentElement.getString("urlToImage");
                        tempArticle.publishedAt = currentElement.getString("publishedAt");

                        result.add(tempArticle);

                    }
                }
            } catch (Exception e) {
                //Handle Exceptions
            } finally {

            }
            return result;
        }
    }
/*
    public class GetImageAsync extends AsyncTask<String, Void, String> {

        ArrayList<Bitmap> bitmapList;


        @Override
        protected void onPreExecute() {
            bitmapList = new ArrayList<>();
            loadingIcon.setVisibility(View.VISIBLE);
            loadingArticlesText.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {

            for(int i = 0; i < bitmapList.size(); i++)
                imageList.add(bitmapList.get(i));


            populateArticlesScroll();

        }

        @Override
        protected String doInBackground(String... strings) {



                HttpURLConnection connection = null;
                for(int j = 0; j < articleList.size(); j++) {
                    try {
                        URL url = new URL(articleList.get(j).urlToImage);
                        connection = (HttpURLConnection) url.openConnection();
                        connection.connect();
                        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            bitmapList.add(BitmapFactory.decodeStream(connection.getInputStream()));
                        } else {

                        }
                    } catch (IOException e) {
                        //Handle the exceptions
                    } finally {
                        //Close open connection
                    }
                }

            return null;
        }
    }*/

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

  /*  private void populateArticlesScroll(){

        for(int i = 0; i < articleList.size(); i++){


            TextView temp = new TextView(NewsActivity.this);
            temp.setText(articleList.get(i).title + "\n");
            temp.setId(i);

            ImageView image = new ImageView(NewsActivity.this);
            image.setImageBitmap(imageList.get(i));
            image.setId(i);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String URL = articleList.get(view.getId()).url;
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                    browserIntent.setData(Uri.parse(URL));
                    Log.d("test", view.getId() + " +++++" + URL);
                    startActivity(browserIntent);

                }
            });

            articleLayout.addView(temp);
            articleLayout.addView(image);
        }
        loadingIcon.setVisibility(View.INVISIBLE);
        loadingArticlesText.setVisibility(View.INVISIBLE);

    }*/

    public static class ArticleAdapter extends ArrayAdapter<Article>{


        public ArticleAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Article> objects) {
            super(context, resource, objects);
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            Article article = getItem(position);
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_article, parent, false);

            TextView newsTitleView = (TextView) convertView.findViewById(R.id.newsTitleView);
            TextView dateView = (TextView) convertView.findViewById(R.id.dateView);
            TextView authorView = (TextView) convertView.findViewById(R.id.authorView);
            ImageView articleImage = (ImageView) convertView.findViewById(R.id.newsImageView);

            newsTitleView.setText(article.title);
            dateView.setText(article.publishedAt);
            authorView.setText(article.author);
            Picasso.with(getContext()).load(article.urlToImage).into(articleImage);


            return convertView;

        }
    }




}
