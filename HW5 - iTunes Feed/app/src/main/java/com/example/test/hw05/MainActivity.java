package com.example.test.hw05;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Assignment: HW5
 * Group 5: Scott Schreiber & Brianna Kirkpatrick
 */

public class MainActivity extends AppCompatActivity implements GetPodcastsAsync.iData {



    ArrayList<Podcast> masterPodcastList;
    ArrayList<Podcast> tempPodcastList;
    ArrayAdapter<Podcast> podcastArrayAdapter;
    public static final String PODCAST_KEY = "podcast";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        masterPodcastList = new ArrayList<>();
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.loadingBar);
        TextView loadingText = (TextView) findViewById(R.id.loadingTextView);
        ListView listView = (ListView) findViewById(R.id.listView);
        Button goButton = (Button) findViewById(R.id.goButton);
        Button clearButton = (Button) findViewById(R.id.clearButton);
        final EditText searchField = (EditText) findViewById(R.id.searchField);
        setTitle("iTunes Top Podcasts");
        new GetPodcastsAsync(this).execute("https://itunes.apple.com/us/rss/toppodcasts/limit=30/xml");

        //until we get XML working, fill with placeholder podcasts


        /*for(int i = 0; i < 10; i++) {
            tempPodcastList.add(new Podcast("Test Podcast " + i,"Summary " + i, "11/12/1955",
                    "http://images.clipartpanda.com/apple-20clip-20art-apple-clipart.gif",
                    "https://openclipart.org/image/2400px/svg_to_png/202364/Red-Apple.png" ));
            masterPodcastList.add(new Podcast("Test Podcast " + i,"Summary " + i, "11/12/1955",
                    "http://images.clipartpanda.com/apple-20clip-20art-apple-clipart.gif",
                    "https://openclipart.org/image/2400px/svg_to_png/202364/Red-Apple.png" ));
        }

        tempPodcastList.add(new Podcast("My Podcast ", "Summary ", "11/12/1955",
                "http://images.clipartpanda.com/apple-20clip-20art-apple-clipart.gif",
                "https://openclipart.org/image/2400px/svg_to_png/202364/Red-Apple.png" ));
        tempPodcastList.add(new Podcast("Podcast Grammy", "Summary ", "11/12/1955",
                "http://images.clipartpanda.com/apple-20clip-20art-apple-clipart.gif",
                "https://openclipart.org/image/2400px/svg_to_png/202364/Red-Apple.png" ));
        masterPodcastList.add(new Podcast("My Podcast ", "Summary ", "11/12/1955",
                "http://images.clipartpanda.com/apple-20clip-20art-apple-clipart.gif",
                "https://openclipart.org/image/2400px/svg_to_png/202364/Red-Apple.png" ));
        masterPodcastList.add(new Podcast("Podcast Grammy", "Summary ", "11/12/1955",
                "http://images.clipartpanda.com/apple-20clip-20art-apple-clipart.gif",
                "https://openclipart.org/image/2400px/svg_to_png/202364/Red-Apple.png" ));*/
        loadingText.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        //final PodcastAdapter adapter = new PodcastAdapter(this, R.layout.podcast_layout, tempPodcastList);
        listView.setAdapter(podcastArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("test", masterPodcastList.get(i).title);
                Intent detailsIntent = new Intent(MainActivity.this, DetailsActivity.class);
                detailsIntent.putExtra(PODCAST_KEY, masterPodcastList.get(i));
                startActivity(detailsIntent);
            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchString = searchField.getText().toString().toLowerCase();
                tempPodcastList = new ArrayList<>();

                for(int i = 0; i < masterPodcastList.size(); i++){
                    /*if((tempPodcastList.get(i).title).toLowerCase().contains(searchString.toLowerCase())){
                        Podcast p = tempPodcastList.get(i);
                        tempPodcastList.remove(i);
                        tempPodcastList.add(0, p);
                        podcastArrayAdapter.notifyDataSetChanged();
                    }*/

                    try{
                        tempPodcastList.add((Podcast) masterPodcastList.get(i).clone());
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }

                if(!(searchString.isEmpty())) {
                    for (int x = 0; x < tempPodcastList.size(); x++) {
                        if (tempPodcastList.get(x).getTitle().toLowerCase().contains(searchString)) {
                            Podcast p = null;

                            try{
                                p = (Podcast) tempPodcastList.get(x).clone();
                            } catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                            }
                            if(p != null){
                                p.setHighlight(2);
                            }
                            tempPodcastList.remove(x);
                            tempPodcastList.add(0, p);
                        }else{
                            tempPodcastList.get(x).setHighlight(0);
                        }
                    }
                    //podcastArrayAdapter.notifyDataSetChanged();
                    ListView listView = (ListView) findViewById(R.id.listView);
                    ArrayAdapter<Podcast> arrayadapter2 = new PodcastAdapter(MainActivity.this, 0 , tempPodcastList);
                    listView.setAdapter(arrayadapter2);
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListView listView = (ListView) findViewById(R.id.listView);
                podcastArrayAdapter = new PodcastAdapter(MainActivity.this, R.layout.podcast_layout, masterPodcastList);
                listView.setAdapter(podcastArrayAdapter);
                EditText search = (EditText) findViewById(R.id.searchField);
                search.setText(null);
                Log.d("demo", String.valueOf(masterPodcastList.get(0).getHighlight()));
            }
        });
    }

    @Override
    public void handleData(ArrayList<Podcast> p) {

        masterPodcastList = p;
        //listSort(masterPodcastList, true);
        //tempPodcastList = masterPodcastList;
        ListView listView = (ListView) findViewById(R.id.listView);
        podcastArrayAdapter = new PodcastAdapter(this, R.layout.podcast_layout, masterPodcastList);
        listView.setAdapter(podcastArrayAdapter);

    }

    public void listSort(ArrayList<Podcast> list, final boolean ascOrder){
        Collections.sort(list, new Comparator<Podcast>() {
            @Override
            public int compare(Podcast p1, Podcast p2) {
                if(ascOrder){
                    return p1.getReleaseDate().compareTo(p2.getReleaseDate());
                }else{
                    return p2.getReleaseDate().compareTo(p1.getReleaseDate());
                }
            }
        });
    }
}
