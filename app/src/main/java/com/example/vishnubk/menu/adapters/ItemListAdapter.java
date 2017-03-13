package com.example.vishnubk.menu.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vishnubk.menu.R;
import com.example.vishnubk.menu.models.MenuDetail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


/**
 * Created by vishnubk on 2/2/16.
 */
public class ItemListAdapter extends BaseAdapter {

    private Context context;
    private Cursor cursor;
    private Bitmap foodIcon;

    public ItemListAdapter(Context context,Cursor cursor){

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
        String Name = cursor.getString(cursor.getColumnIndex("name"));
        String Price=cursor.getString(cursor.getColumnIndex("price"));
        Integer idno=cursor.getInt(cursor.getColumnIndex("id"));
        return new MenuDetail(Category,Name,Price,idno);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView= inflater.inflate(R.layout.activity_menuadd, null);
        }

        TextView textItemName= (TextView) convertView.findViewById(R.id.nameTextView);
        TextView textItemPrice= (TextView) convertView.findViewById(R.id.priceTextView);
        ImageView icon=(ImageView) convertView.findViewById(R.id.imageViewer);

        cursor.moveToPosition(position);
        String Category=cursor.getString(cursor.getColumnIndex("category"));
        String Name = cursor.getString(cursor.getColumnIndex("name"));
        String Price=cursor.getString(cursor.getColumnIndex("price"));
        byte[] img=cursor.getBlob(cursor.getColumnIndex("image"));
        ByteArrayInputStream imageStream = new ByteArrayInputStream(img);
        ByteArrayInputStream imageStream1 = new ByteArrayInputStream(img);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeStream(imageStream,null,options);
        final int REQUIRED_SIZE=60;
        int scale = 1;
        while(options.outWidth / scale / 2 >= REQUIRED_SIZE &&
                options.outHeight / scale / 2 >= REQUIRED_SIZE) {

            scale *= 2;

        }


        options.inJustDecodeBounds=false;
        options.inScaled=true;
        options.inSampleSize=scale;
        options.inTempStorage=new byte[16 * 1024];

        foodIcon = BitmapFactory.decodeStream(imageStream1,null,options);
        textItemName.setText(Name);
        textItemPrice.setText(Price);
        icon.setImageBitmap(foodIcon);

        return convertView;
    }


}

