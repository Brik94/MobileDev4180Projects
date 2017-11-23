/*
*Scott Schreiber
* Brianna Kirkpatrick
*
* Group5_InClass06.zip
*
* */




package com.example.test.midterm;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;




public class MainActivity extends AppCompatActivity {


    ProgressBar loadingIcon;
    ArrayAdapter<Source> adapter;
    TextView loadingSourcesText;
    ArrayList<Source> sourceList;
    ListView listView;
    public static final String SOURCE_KEY = "sourcekey";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Main Activity");
        loadingIcon = (ProgressBar) findViewById(R.id.loadingIcon);
        loadingSourcesText = (TextView) findViewById(R.id.loadingSourcesLabel);
        sourceList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);


        if(isConnected())
            new GetSourcesAsync().execute("https://newsapi.org/v1/sources");
        else
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();


        adapter =
                new ArrayAdapter<Source>(this, android.R.layout.simple_list_item_1,
                        android.R.id.text1, sourceList);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent newsIntent = new Intent(MainActivity.this, NewsActivity.class);
                newsIntent.putExtra(SOURCE_KEY, sourceList.get(i));
                startActivity(newsIntent);
                Log.d("test","Load" + sourceList.get(i).name);


            }
        });


    }


    public class GetSourcesAsync extends AsyncTask<String, Void, ArrayList<Source>>{


        @Override
        protected void onPreExecute() {
            loadingIcon.setVisibility(View.VISIBLE);
            loadingSourcesText.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<Source> s) {

            sourceList.clear();

            for(int i = 0; i < s.size(); i++)
                sourceList.add(s.get(i));

            //add to listview
            adapter.notifyDataSetChanged();

            Log.d("test", "sources loaded");
            loadingIcon.setVisibility(View.INVISIBLE);
            loadingSourcesText.setVisibility(View.INVISIBLE);

        }

        @Override
        protected ArrayList<Source> doInBackground(String... params) {
            HttpURLConnection connection = null;
            ArrayList<Source> result = new ArrayList<>();
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json = IOUtils.toString(connection.getInputStream(), "UTF8");

                    JSONObject root = new JSONObject(json);
                    JSONArray sources = root.getJSONArray("sources");

                    for (int i=0;i<sources.length();i++) {

                        JSONObject currentElement = sources.getJSONObject(i);
                        Source tempSource = new Source();
                        tempSource.id = currentElement.getString("id");
                        tempSource.name = currentElement.getString("name");
                        result.add(tempSource);

                    }
                }
            } catch (Exception e) {
                //Handle Exceptions
            } finally {

            }
            return result;
        }
    }

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




}
