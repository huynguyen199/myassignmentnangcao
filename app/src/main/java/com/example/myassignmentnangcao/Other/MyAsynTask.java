package com.example.myassignmentnangcao.Other;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.myassignmentnangcao.Fragment.Adapter.adapterNews;
import com.example.myassignmentnangcao.Fragment.FragmentWeb;
import com.example.myassignmentnangcao.Model.News;
import com.example.myassignmentnangcao.R;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyAsynTask extends AsyncTask<Void,Void,Void> {
    Context context;
    String link;
    ListView lv;
    News news;
    ArrayList<News> list = new ArrayList<>();
    HashMap<String,Object> hm;
    List<HashMap<String,Object>> ds = new ArrayList<HashMap<String,Object>>();



    public MyAsynTask(Context context, String link, ListView lv) {
        this.context = context;
        this.link = link;
        this.lv = lv;
    }



    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL(link);
            URLConnection connection = url.openConnection();
            InputStream is = connection.getInputStream();
            list = MySaxParser.xmlParser(is);

            for(int i=0;i<list.size();i++)
            {
                hm = new HashMap<>();
                hm.put("title",list.get(i).getTitle());
                String description = list.get(i).getDescription();

                hm.put("description",description.substring(description.indexOf("</br>")+5,description.lastIndexOf(".")+1));

                hm.put("putdate",list.get(i).getPubDate());
                String img = list.get(i).getDescription();
                String chuoi = img.substring(img.indexOf("<img src=\"")+10,img.indexOf("\" >"));
                hm.put("img",chuoi);
                ds.add(hm);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        adapterNews adapter = new adapterNews(R.layout.item_news,context,ds);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String link = list.get(i).getLink();

                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameContainer,new FragmentWeb(link)).addToBackStack( "tag" ).commit();

            }
        });
        super.onPostExecute(aVoid);
    }
}
