package com.example.test.hw05;

import android.net.ParseException;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Assignment: HW5
 * Group 5: Scott Schreiber & Brianna Kirkpatrick
 */

public class GetPodcastsAsync extends AsyncTask<String, Void, ArrayList<Podcast>> {
    MainActivity activity;

    public GetPodcastsAsync(MainActivity activity){
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(ArrayList<Podcast> podcasts) {
       Log.d("test", "Async finished");
        activity.handleData(podcasts);
    }

    @Override
    protected ArrayList<Podcast> doInBackground(String... strings) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection connection = null;
        ArrayList<Podcast> result = new ArrayList<>();
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(reader);

            int eventType = xpp.getEventType();
            String hold = "";
            Podcast podcast = null;
            int imgSize = 0;
            podcast = new Podcast();

            Log.d("test", strings[0]);
            while (eventType != XmlPullParser.END_DOCUMENT){

                String tagname = xpp.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if(tagname.equalsIgnoreCase("entry")){
                            podcast = new Podcast();
                        }

                        if(tagname.equalsIgnoreCase("im:image")){

                            if(xpp.getAttributeValue(null, "height").equals("55")){
                                imgSize = 1;
                            }
                            if(xpp.getAttributeValue(null, "height").equals("170")){
                                imgSize = 2;
                            }

                        }
                        //CODE TO PARSE RELEASE DATE. JUST UNCOMMENT.
                        if(tagname.equalsIgnoreCase("im:releaseDate")){
                            podcast.releaseDate = (xpp.getAttributeValue(null, "label"));
                        }

                        break;

                    case XmlPullParser.TEXT:
                        hold = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if(tagname.equalsIgnoreCase("entry")){
                            result.add(podcast);
                        }
                        if(tagname.equalsIgnoreCase("title")){
                            podcast.title = hold;
                        }
                        if(tagname.equalsIgnoreCase("summary")){
                            podcast.summary = hold;
                        }
                        if(tagname.equalsIgnoreCase("updated")) {
                            String xmlDate = hold;
                            Date date = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss").parse(xmlDate);
                            String updatedDate = new SimpleDateFormat("MM/dd/yyy").format(date);
                            podcast.updated = updatedDate;
                        }
                        if(tagname.equalsIgnoreCase("im:image") && imgSize == 1){
                            podcast.smallImgUrl = hold;
                        }
                        if(tagname.equalsIgnoreCase("im:image") && imgSize == 2){
                            podcast.largeImgUrl = hold;
                        }
                        break;
                    default:

                        break;

                }
                eventType = xpp.next();
            }

            reader.close();

            Log.d("test", result.get(0).toString());
            return result;
        }catch (IOException e) {
            e.printStackTrace();
        }catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        } finally {
            if(connection != null){
                connection.disconnect();
            }
        }
        return null;
    }

    public interface iData{
        public void handleData(ArrayList<Podcast> p);
    }
}
