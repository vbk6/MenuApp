package com.example.vishnubk.menu.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vishnubk.menu.R;
import com.example.vishnubk.menu.models.CustomerDetails;
import com.example.vishnubk.menu.ui.CustomerListActivity;

/**
 * Created by vishnubk on 3/3/16.
 */
public class CustomerListAdapter extends BaseAdapter {

    private Cursor cursor;
    private Context context;

    public CustomerListAdapter(Context context, Cursor cursor) {

        this.cursor=cursor;
        this.context=context;
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {

        cursor.moveToPosition(position);
        String  customerName=cursor.getString(cursor.getColumnIndex("customername"));
        String id=cursor.getString(cursor.getColumnIndex("id"));
        return new CustomerDetails(customerName,id);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            LayoutInflater inflater=LayoutInflater.from(context);
            convertView=inflater.inflate(R.layout.customer_list_pattern,null);
        }
        TextView textCustomer= (TextView) convertView.findViewById(R.id.textCustomer);
        cursor.moveToPosition(position);
        String  customerName=cursor.getString(cursor.getColumnIndex("customername"));
        textCustomer.setText(customerName);
        return convertView;
    }
}
