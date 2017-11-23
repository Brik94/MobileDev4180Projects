package com.example.test.hw05;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Podcast podcast = (Podcast) getIntent().getExtras().getSerializable(MainActivity.PODCAST_KEY);
        ((TextView)findViewById(R.id.titleView)).setText(podcast.title);
        ((TextView)findViewById(R.id.updatedView)).setText("Updated on: " + podcast.updated);
        ((TextView)findViewById(R.id.releasedView)).setText("Released on: " + podcast.releaseDate);
        ((TextView)findViewById(R.id.summaryView)).setText(podcast.summary);
        Picasso.with(DetailsActivity.this).load(podcast.largeImgUrl).into(((ImageView)(findViewById(R.id.podcastImageView))));
    }
}
