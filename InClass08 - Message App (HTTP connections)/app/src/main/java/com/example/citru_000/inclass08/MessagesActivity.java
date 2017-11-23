package com.example.citru_000.inclass08;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.ocpsoft.prettytime.PrettyTime;

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

public class MessagesActivity extends AppCompatActivity {

    ThreadMessage threadMessage;
    TokenInfo tokenInfo;
    ProgressBar progressBar;
    ArrayList<Message> messageList;
    EditText messageEditText;
    MessageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        messageList= new ArrayList<>();
        progressBar = findViewById(R.id.progressBar2);

        threadMessage = (ThreadMessage) getIntent().getExtras().getSerializable(ThreadsActivity.MESSAGE_KEY);
        tokenInfo = (TokenInfo) getIntent().getExtras().getSerializable(TOKEN_KEY);
        ((TextView)findViewById(R.id.threadTitleText)).setText(threadMessage.title);
        messageEditText = (EditText)findViewById(R.id.messageEditText);
        ((ImageView)findViewById(R.id.sendButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                sendMessage(messageEditText.getText().toString());
            }
        });
        ((ImageView)findViewById(R.id.homeButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getMessages();

        adapter = new MessagesActivity.MessageAdapter(this, R.layout.message_layout, messageList);
        ListView messageListView = (ListView) findViewById(R.id.messageListView);
        messageListView.setAdapter(adapter);

    }

    public class MessageAdapter extends ArrayAdapter<Message> {

        public MessageAdapter(@NonNull Context context, int resource, @NonNull List<Message> objects) {
            super(context, resource, objects);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Log.d("test", "getview called");
            final Message message = getItem(position);
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_layout, parent, false);

            TextView messageLayoutText = (TextView) convertView.findViewById(R.id.messageLayoutText);
            TextView messageNameText = (TextView) convertView.findViewById(R.id.messageNameText);
            TextView deleteMessageButton = (TextView) convertView.findViewById(R.id.messageDeleteText);
            TextView messageTimeText = (TextView) convertView.findViewById(R.id.messageTimeText);
            //ImageView deleteThreadButton = (ImageView) findViewById(R.id.threadDeleteButton);


            deleteMessageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressBar.setVisibility(View.VISIBLE);
                    deleteMessage(message);
                }
            });


            //PrettyTime prettyTime = new PrettyTime();

            //set the data from the email object
            messageLayoutText.setText(message.message);
            messageNameText.setText(message.user_fname + " " + message.user_lname);
            messageTimeText.setText(message.created_at);
            if(message.user_id != tokenInfo.user_id)
                deleteMessageButton.setVisibility(View.INVISIBLE);

            return convertView;
        }

    }

    public void deleteMessage(Message toDelete){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://ec2-54-164-74-55.compute-1.amazonaws.com/api/message/delete/" + toDelete.id)
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
                            getMessages();
                        }
                    });

                }
                else{
                    Log.d("test", "onResponse else: " + response.body().string());
                    // Toast.makeText(MainActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MessagesActivity.this, "Unable to delete", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    public void getMessages(){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://ec2-54-164-74-55.compute-1.amazonaws.com/api/messages/" + threadMessage.id)
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
                    MessageResponse messageResponse = gson.fromJson(response.body().string(), MessageResponse.class);
                   Log.d("mess", "onResponse: " + messageResponse.messages.get(0).toString());
                    messageList.clear();
                    for(int i = messageResponse.messages.size()-1; i >= 0; i--)
                        messageList.add(messageResponse.messages.get(i));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                            adapter.notifyDataSetChanged();
                        }
                    });

                    //adapter.notifyDataSetChanged();


                } else{
                    Log.d("demo", "onResponse: " + response.body().string());
                }
            }
        });
    }

    public void sendMessage(String mess){


        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("message", mess)
                .add("thread_id", Integer.toString(threadMessage.id))
                .build();

        Request request = new Request.Builder()
                .url("http://ec2-54-164-74-55.compute-1.amazonaws.com/api/message/add")
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
                            //progressBar.setVisibility(View.INVISIBLE);
                            getMessages();
                        }
                    });

                    Log.d("test", "message added");

                }
                else{
                    Log.d("test", "onResponse else: " + response.body().string());
                    // Toast.makeText(MainActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
