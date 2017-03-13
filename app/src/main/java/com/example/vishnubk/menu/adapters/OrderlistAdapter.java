package com.example.vishnubk.menu.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

//import com.example.vishnubk.menu.Logs;

import com.example.vishnubk.menu.models.ItemDetail;
import com.example.vishnubk.menu.R;

import java.util.ArrayList;

/**
 * Created by vishnubk on 1/2/16.
 */
public class OrderlistAdapter extends BaseAdapter {

    private Context context;
    private Cursor cursor;


    public OrderlistAdapter(Context context, Cursor cursor) {
        this.cursor = cursor;
        this.context = context;

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {

        cursor.moveToPosition(position);
        String Name = cursor.getString(cursor.getColumnIndex("name"));
        String Price=cursor.getString(cursor.getColumnIndex("price"));
        String Count=cursor.getString(cursor.getColumnIndex("count"));
        return new ItemDetail(Name,Price,Count);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView= inflater.inflate(R.layout.order_list_pattern, null);
        }
        TextView textItemname= (TextView) convertView.findViewById(R.id.textItemname);
        TextView textItemprice= (TextView) convertView.findViewById(R.id.textItemprice);
        TextView textItemCount= (TextView) convertView.findViewById(R.id.textItemCount);


        cursor.moveToPosition(position);

        String Name =cursor.getString(cursor.getColumnIndex("name"));
        String Price=cursor.getString(cursor.getColumnIndex("price"));
        String count=cursor.getString(cursor.getColumnIndex("count"));


        textItemname.setText(Name);
        textItemprice.setText(Price);
        textItemCount.setText(count);

        return convertView;
    }


}
