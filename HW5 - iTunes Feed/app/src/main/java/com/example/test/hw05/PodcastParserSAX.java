package com.example.test.hw05;

import android.util.Log;
import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by citru_000 on 10/26/2017.
 */

public class PodcastParserSAX extends DefaultHandler {

    ArrayList<Podcast> podcasts;
    StringBuilder innerXml;
    Podcast tempPodcast;
    public static ArrayList<Podcast> parsePodcasts(InputStream inputStream) throws IOException, SAXException {
        PodcastParserSAX parser = new PodcastParserSAX();
        Xml.parse(inputStream, Xml.Encoding.UTF_8, parser);
        return parser.podcasts;

    }




    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        Log.d("test", "Document start");
        this.podcasts = new ArrayList<>();
        innerXml = new StringBuilder();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        Log.d("test", "Document end");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        Log.d("test","Element started: " + localName);
        if(localName.equals("entry")){
            tempPodcast = new Podcast();
            Log.d("test", "New podcast");
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        Log.d("test", "End element: " + localName);
        if(localName.equals("title")){
            tempPodcast.title = innerXml.toString();
        }else if(localName.equals("podcast")){
            podcasts.add(tempPodcast);
        }

        innerXml.setLength(0);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        innerXml.append(ch, start, length);
    }
}
