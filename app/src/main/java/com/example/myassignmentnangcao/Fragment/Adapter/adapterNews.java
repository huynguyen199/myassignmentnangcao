package com.example.myassignmentnangcao.Fragment.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myassignmentnangcao.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class adapterNews extends BaseAdapter {

    private int layout;
    private Context context;
    private List<HashMap<String, Object>> list;

    public adapterNews(int layout, Context context, List<HashMap<String, Object>> list) {
        this.layout = layout;
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder{
        ImageView image;
        TextView title,description,pubdate;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.image = view.findViewById(R.id.image_news);
            holder.title = view.findViewById(R.id.title);
            holder.description = view.findViewById(R.id.description);
            holder.pubdate = view.findViewById(R.id.pubDate);

            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
//        holder.image.setImageURI((Uri) list.get(i).get("img"));
        String value = (String) list.get(i).get("title");
        String value2 = (String) list.get(i).get("description");
        String value3 = (String) list.get(i).get("putdate");
        holder.title.setText(value);
        holder.description.setText(value2);
        holder.pubdate.setText(value3);

        Picasso.with(context)
                .load(String.valueOf(list.get(i).get("img")))
                .into(holder.image);
        return view;
    }
}
