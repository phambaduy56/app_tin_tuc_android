package com.example.app_tin_tuc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.app_tin_tuc.Class.cmt;
import com.example.app_tin_tuc.R;

import java.util.ArrayList;

public class CmtAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<cmt> arrayList;

    public CmtAdapter(Context context, int layout, ArrayList<cmt> arrayList) {
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
        TextView txtCmt, txtTen;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        viewholder holder;
        if(convertView == null){
            holder = new viewholder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            holder.txtCmt = (TextView) convertView.findViewById(R.id.textViewNoiDungCmt);
            holder.txtTen = (TextView) convertView.findViewById(R.id.textViewNameCmt);

            convertView.setTag(holder);
        }
        else
        {
            holder = (viewholder) convertView.getTag();
        }

        cmt cm = arrayList.get(position);

        holder.txtCmt.setText(cm.getComment_content());
        holder.txtTen.setText(cm.getName());

        return convertView;
        
    }
}
