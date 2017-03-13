package com.example.vishnubk.menu.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vishnubk.menu.R;
import com.example.vishnubk.menu.ui.CustomerOrderActivity;

/**
 * Created by vishnubk on 4/3/16.
 */
public class CategoryOrderAdapter extends BaseAdapter {

    private Context context;
    private Cursor cursor;
    public CategoryOrderAdapter(Context context, Cursor cursor) {

        this.context=context;
        this.cursor=cursor;
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){

            LayoutInflater inflater=LayoutInflater.from(context);
            convertView= inflater.inflate(R.layout.customer_order_list_pattern, null);

        }
        cursor.moveToPosition(position);
        String name=cursor.getString(cursor.getColumnIndex("name"));
        String price=cursor.getString(cursor.getColumnIndex("price"));
        String count=cursor.getString(cursor.getColumnIndex("count"));
        TextView textName=(TextView)convertView.findViewById(R.id.textCusItemname);
        TextView textPrice=(TextView)convertView.findViewById(R.id.textCusItemprice);
        TextView textCount=(TextView)convertView.findViewById(R.id.textCusItemCount);
        textName.setText(name);
        textPrice.setText(price);
        textCount.setText(count);
        return convertView;
    }
}
