package com.example.citru_000.inclass08;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.citru_000.inclass08.MainActivity.TOKEN_KEY;

public class ThreadsActivity extends AppCompatActivity {

    TokenInfo tokenInfo;
    ArrayList<ThreadMessage> threadList;
    ProgressBar progressBar;
    public static final String MESSAGE_KEY = "mess";
    ThreadAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);
        threadList= new ArrayList<>();
        progressBar = findViewById(R.id.progressBar3);
        tokenInfo = (TokenInfo) getIntent().getExtras().getSerializable(TOKEN_KEY);
        ((TextView)findViewById(R.id.nameTextView)).setText(tokenInfo.user_fname + " " + tokenInfo.user_lname);


        findViewById(R.id.logoutButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.VISIBLE);
                addThread();

                /*Log.d("test", "Add");
                Log.d("test", "test thread: " + threadList.get(0).toString());
                Log.d("test", "adapter count: " + adapter.getCount());*/


            }
        });
        getThreadMessages();
/*
        ThreadMessage testMessage = new ThreadMessage();
        testMessage.user_fname = "test";
        testMessage.user_lname = "testl";
        testMessage.title = "testt";
        testMessage.user_id = 999;
        threadList.add(testMessage);*/
        ListView listView = (ListView)findViewById(R.id.listView);
        adapter = new ThreadAdapter(this, R.layout.thread_layout, threadList);
        listView.setAdapter(adapter);
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Intent messageIntent = new Intent(ThreadsActivity.this, MessagesActivity.class);
               messageIntent.putExtra(MESSAGE_KEY, threadList.get(i));
               messageIntent.putExtra(MainActivity.TOKEN_KEY, tokenInfo);
               startActivity(messageIntent);
           }
       });
    }

    public class ThreadAdapter extends ArrayAdapter<ThreadMessage>{

        public ThreadAdapter(@NonNull Context context, int resource, @NonNull List<ThreadMessage> objects) {
            super(context, resource, objects);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Log.d("test", "getview called");
            final ThreadMessage threadMessage = getItem(position);
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.thread_layout, parent, false);

            TextView titleText = (TextView) convertView.findViewById(R.id.threadTitleTextView);
            TextView deleteTextButton = (TextView) convertView.findViewById(R.id.deleteTextButton);
            //ImageView deleteThreadButton = (ImageView) findViewById(R.id.threadDeleteButton);

            deleteTextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressBar.setVisibility(View.VISIBLE);
                    deleteThread(threadMessage);
                }
            });



            //set the data from the email object
            titleText.setText(threadMessage.title);
            if(threadMessage.user_id != tokenInfo.user_id)
                deleteTextButton.setVisibility(View.INVISIBLE);

            return convertView;
        }

    }
    public void deleteThread(ThreadMessage toDelete){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://ec2-54-164-74-55.compute-1.amazonaws.com/api/thread/delete/" + toDelete.id)
                .header("Authorization", "BEARER " + tokenInfo.token)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("test", "Failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){

                    String res = response.body().string();
                    //Log.d("test", "onResponse: " + res);
                    Log.d("test", res);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //progressBar.setVisibility(View.INVISIBLE);
                            getThreadMessages();
                        }
                    });

                    Log.d("test", res);

                }
                else{
                    Log.d("test", "onResponse else: " + response.body().string());
                    // Toast.makeText(MainActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    public void addThread(){
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("title", ((EditText)findViewById(R.id.addThreadEditText)).getText().toString())
                .build();

        Request request = new Request.Builder()
                .url("http://ec2-54-164-74-55.compute-1.amazonaws.com/api/thread/add")
                .header("Authorization", "BEARER " + tokenInfo.token)
                .post(formBody)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("test", "Failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){

                    String res = response.body().string();
                    //Log.d("test", "onResponse: " + res);

                    Log.d("test", res);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           // progressBar.setVisibility(View.INVISIBLE);
                           getThreadMessages();
                        }
                    });


                }
                else{
                    Log.d("test", "onResponse else: " + response.body().string());
                    // Toast.makeText(MainActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void getThreadMessages(){

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://ec2-54-164-74-55.compute-1.amazonaws.com/api/thread")
                .header("Authorization", "BEARER " + tokenInfo.token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("demo", "onFailure: " + "error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    //Log.d("demo", "onResponse: " + response.body().string());
                    Gson gson = new Gson();
                    ThreadMessageResponse threadMessageResponse = gson.fromJson(response.body().string(), ThreadMessageResponse.class);
                    Log.d("demo", "onResponse: " + threadMessageResponse.threads.get(0).toString());
                    threadList.clear();
                    for(int i = 0; i < threadMessageResponse.threads.size(); i++)
                        threadList.add(threadMessageResponse.threads.get(i));

                    //adapter.notifyDataSetChanged();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                            adapter.notifyDataSetChanged();
                        }
                    });

                } else{
                    Log.d("demo", "onResponse: " + response.body().string());
                }
            }
        });

    }

}
