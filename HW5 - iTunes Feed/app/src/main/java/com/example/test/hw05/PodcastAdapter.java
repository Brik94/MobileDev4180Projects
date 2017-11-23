package com.example.test.hw05;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Assignment: HW5
 * Group 5: Scott Schreiber & Brianna Kirkpatrick
 */

public class PodcastAdapter extends ArrayAdapter<Podcast> {
    ArrayList<Podcast> podcastList;

    public PodcastAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Podcast> objects) {
        super(context, resource, objects);
        podcastList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Podcast podcast = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.podcast_layout, parent, false);

        TextView podcastTextView = (TextView) convertView.findViewById(R.id.podcastTextView);
        ImageView podcastImageView = (ImageView) convertView.findViewById(R.id.podcastImageView);
        RelativeLayout rl = convertView.findViewById(R.id.highlightID);

        if(podcastList.get(position).getHighlight() != 0){
            rl.setBackgroundColor(Color.parseColor("#228B22"));
        }else{
            rl.setBackgroundColor(Color.parseColor("White"));
        }

        //set the data from the email object
        podcastTextView.setText(podcast.title);
        Picasso.with(getContext()).load(podcast.smallImgUrl).into(podcastImageView);

        return convertView;

    }
}
