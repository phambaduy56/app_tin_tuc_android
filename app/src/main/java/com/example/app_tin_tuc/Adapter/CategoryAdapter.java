package com.example.app_tin_tuc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_tin_tuc.Class.Category;
import com.example.app_tin_tuc.R;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Category> arrayList;

    public CategoryAdapter(Context context, int layout, ArrayList<Category> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
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
        TextView txtCategory;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       viewholder holder;
        if(convertView == null){
            holder = new viewholder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            holder.txtCategory = (TextView) convertView.findViewById(R.id.textViewCategory);

            convertView.setTag(holder);
        }
        else
        {
            holder = (viewholder) convertView.getTag();
        }

        Category ct = arrayList.get(position);

        holder.txtCategory.setText(ct.getName_category());

        return convertView;
    }
}
