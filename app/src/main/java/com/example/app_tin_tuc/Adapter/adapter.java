package com.example.app_tin_tuc.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_tin_tuc.Class.Articles;
import com.example.app_tin_tuc.Home_Category;
import com.example.app_tin_tuc.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class adapter extends BaseAdapter {
    private Home_Category context;
    private int layout;
    private List<Articles> list;
    private ArrayList<Articles> arrayArticles;

    public adapter(Home_Category context, int layout, List<Articles> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;

        this.arrayArticles = new ArrayList<Articles>();
        arrayArticles.addAll(list);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class viewholder{
        TextView txtTitle, txtDesciption, txtDatetime;
        ImageView imgHinhAnh;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        viewholder holder;
        if(convertView == null){
            holder = new viewholder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            holder.txtTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
            holder.txtDesciption = (TextView) convertView.findViewById(R.id.textViewDesciption);
            holder.txtDatetime = (TextView) convertView.findViewById(R.id.textViewDatetime);
            holder.imgHinhAnh = (ImageView) convertView.findViewById(R.id.imageViewHome2);

            convertView.setTag(holder);
        }
        else
        {
            holder = (viewholder) convertView.getTag();
        }

        Articles  ar = list.get(position);


        holder.txtTitle.setText(ar.getNameArticle());
        holder.txtDesciption.setText(ar.getDesciption());
        holder.txtDatetime.setText(ar.getDatetime());
        String url = ar.getImg();

        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                holder.imgHinhAnh.setImageBitmap(response);
            }
        }, 0, 0, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                holder.imgHinhAnh.setImageResource(R.mipmap.ic_launcher);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(imageRequest);

        return convertView;
    }

    public void filter(String text){
        //chuyen ve chu thường
        text = text.toLowerCase(Locale.getDefault());
        list.clear();
        if(text.length() == 0){
            list.addAll(arrayArticles);
        }else {
            for (Articles articles : arrayArticles ){
                if (articles.getNameArticle().toLowerCase(Locale.getDefault()).contains(text)){
                    list.add(articles);
                }
            }
        }
        notifyDataSetChanged();
    }
}
