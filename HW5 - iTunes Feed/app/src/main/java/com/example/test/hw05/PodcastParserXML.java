package com.example.test.hw05;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by citru_000 on 10/24/2017.
 */

public class PodcastParserXML {

    public static ArrayList<Podcast> parsePodcasts(InputStream inputStream) throws XmlPullParserException, IOException {
        ArrayList<Podcast> podcasts = new ArrayList<>();
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(inputStream, "UTF-8");
        Podcast podcast = null;
        int event = parser.getEventType();

        Log.d("test", "Start parsing");

        while(event != XmlPullParser.END_DOCUMENT) {
            switch (event) {

                case XmlPullParser.START_TAG:

                    if(parser.getName().equals("entry")) {
                        podcast = new Podcast();
                        if (parser.getName().equals("title")) {
                            podcast.title = parser.nextText().trim();
                        }else if (parser.getName().equals("summary")) {
                            podcast.summary = parser.nextText().trim();
                        //}else if (parser.getName().equals("updated")) {
                            //podcast.updated = parser.nextText().trim();
                        }/*else if (parser.getName().equals("im:image")) {
                            if(String.valueOf(parser.getAttributeValue(null, "height")).equals("55")){
                                podcast.smallImgUrl = parser.nextText().trim();
                            }
                        }*/
                    }else{
                        parser.next();
                    }

                    break;

                case XmlPullParser.END_TAG:
                    if(parser.getName().equals("entry")){
                        podcasts.add(podcast);
                    }
                    break;
                default:
                    break;
            }
            event = parser.next();
            Log.d("test", "Event next");
        }

        Log.d("test", "End parsing");

        Log.d("test", podcasts.get(0).toString());
        return podcasts;
    }

}
