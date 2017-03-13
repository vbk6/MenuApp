package com.example.vishnubk.menu.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.vishnubk.menu.R;
import com.example.vishnubk.menu.db.DbHelper;
import com.example.vishnubk.menu.models.ItemDetail;

public class ItemDisplayActivity extends AppCompatActivity {

    private DbHelper DbHelper =new DbHelper(this);
    private Cursor cursorData;
    private Cursor cursorPrevCount;
    private Cursor cursorOrderNo;
    private TextView textItemname;
    private TextView textItemprice;
    private NumberPicker numberPicker;
    private String count=null;
    private String number;
    private String orderNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item__display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final int idn=getIntent().getIntExtra("id", 0);
        final String idno=String.valueOf(idn);


        Log.d("idk", idno + "gg");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DbHelper.countinsert(number,idno,orderNo);
                Intent i = new Intent(ItemDisplayActivity.this, OrderListActivity.class);

                startActivity(i);
            }
        });

        FloatingActionButton fab2=(FloatingActionButton)findViewById(R.id.fab2);
         fab2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });

        textItemname=(TextView)findViewById(R.id.textItemname);
        textItemprice=(TextView)findViewById(R.id.textItemprice);
        numberPicker=(NumberPicker)findViewById(R.id.numberPicker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(10);
        numberPicker.setWrapSelectorWheel(false);


        cursorOrderNo=DbHelper.getCustomerNo();
        cursorOrderNo.moveToFirst();
        orderNo=cursorOrderNo.getString(cursorOrderNo.getColumnIndex("id"));
        cursorData= DbHelper.getData(idno);
        cursorPrevCount= DbHelper.getPrevCount(idno,orderNo);
        cursorPrevCount.moveToFirst();
        count=cursorPrevCount.getString(cursorPrevCount.getColumnIndex("count"));







        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                number = String.valueOf(numberPicker.getValue());



            }


        });

        number= String.valueOf(numberPicker.getValue());


        if ((count == null) || (count == "0"))
        {
            numberPicker.setValue(0);

        }

        else {
            numberPicker.setValue(Integer.parseInt(count));

        }


        cursorData.moveToFirst();
        final String Name = cursorData.getString(cursorData.getColumnIndex("name"));
        final String Price=cursorData.getString(cursorData.getColumnIndex("price"));


        textItemname.setText(""+Name);
        textItemprice.setText(""+Price);



    }


}
