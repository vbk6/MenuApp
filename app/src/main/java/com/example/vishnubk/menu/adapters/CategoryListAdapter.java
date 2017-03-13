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
import com.example.vishnubk.menu.models.MenuDetail;

/**
 * Created by vishnubk on 3/2/16.
 */
public class CategoryListAdapter extends BaseAdapter {

    private Cursor cursor;
    private Context context;
    private int idno;

    public CategoryListAdapter(Context context,Cursor cursor){

        this.context=context;
        this.cursor=cursor;
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {

        cursor.moveToPosition(position);
        String Category=cursor.getString(cursor.getColumnIndex("category"));
        idno=cursor.getInt(cursor.getColumnIndex("id"));;
        return new MenuDetail(Category,null,null,idno);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater=LayoutInflater.from(context);
            convertView= inflater.inflate(R.layout.category_list_pattern, null);
        }


        TextView categoryView=(TextView) convertView.findViewById(R.id.categoryButton);
        cursor.moveToPosition(position);
        String category=cursor.getString(cursor.getColumnIndex("category"));
        categoryView.setText(category);
        return convertView;
    }
}
