package com.example.test.midterm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        setTitle("InClass06");

        String url = getIntent().getExtras().getString(NewsActivity.ARTICLE_KEY);
        Log.d("test", "Null?: " + url);
        WebView webView = (WebView) findViewById(R.id.webView);

        webView.loadUrl(url);
    }
}
