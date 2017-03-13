package com.example.vishnubk.menu.ui;

import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vishnubk.menu.R;
import com.example.vishnubk.menu.adapters.CategoryOrderAdapter;
import com.example.vishnubk.menu.db.DbHelper;

public class CustomerOrderActivity extends AppCompatActivity {

    public Cursor cursorCustomerOrderList;
    private DbHelper dbHelper=new DbHelper(this);
    private ListView customerOrderList;
    private int total=0;
    private Cursor cursorTotal;

    private TextView textViewTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String id=getIntent().getStringExtra("id");
        customerOrderList=(ListView)findViewById(R.id.orderCusListView);
        cursorCustomerOrderList=dbHelper.getCustomerOrderList(id);
        CategoryOrderAdapter adapter=new CategoryOrderAdapter(this,cursorCustomerOrderList);
        customerOrderList.setAdapter(adapter);

        textViewTotal=(TextView)findViewById(R.id.textViewTotal);

        cursorTotal=dbHelper.getCustomerOrderList(id);
        int len1=cursorTotal.getCount();

        for(int i=0;i<len1;i++){

            cursorTotal.moveToPosition(i);

            String count=cursorTotal.getString(cursorTotal.getColumnIndex("count"));

            String price=cursorTotal.getString(cursorTotal.getColumnIndex("price"));
            if ((count == null) || (count == "0")){
                count= String.valueOf(0);
            }
            total=total+(Integer.valueOf(count) *Integer.valueOf(price));
            Log.d("tot", total + "oo");

        }


        Resources res = getResources();
        String totalData=String.format(res.getString(R.string.Total),total);
        textViewTotal.setText(totalData);



    }

}
