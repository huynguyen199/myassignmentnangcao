package com.example.myassignmentnangcao.Other;

import com.example.myassignmentnangcao.Model.News;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class MySaxHandler extends DefaultHandler {

    ArrayList<News> listnews;
    News news;
    String chuoi_tam;
    boolean vao_item = false;

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if(vao_item==true)
            chuoi_tam=new String(ch,start,length);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if(qName.equalsIgnoreCase("item")){
            news = new News();
            vao_item = true;
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if(qName.equalsIgnoreCase("item"))
        {
            listnews.add(news);
        }else if(vao_item==true)
        {
            if(qName.equalsIgnoreCase("title"))
                news.setTitle(chuoi_tam);
            if(qName.equalsIgnoreCase("description"))
                news.setDescription(chuoi_tam);
            if(qName.equalsIgnoreCase("link"))
                news.setLink(chuoi_tam);
            if(qName.equalsIgnoreCase("pubdate"))
                news.setPubDate(chuoi_tam);
        }
    }
    public MySaxHandler()
    {
        listnews=new ArrayList<News>();
    }

    public ArrayList<News> getItems()
    {
        return listnews;
    }
}
