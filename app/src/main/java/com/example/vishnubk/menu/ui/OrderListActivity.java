package com.example.vishnubk.menu.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anwios.alog.Logs;
import com.example.vishnubk.menu.R;
import com.example.vishnubk.menu.adapters.OrderlistAdapter;
import com.example.vishnubk.menu.db.DbHelper;

public class OrderListActivity extends AppCompatActivity {
    public Cursor cursorOrderlist;
    private Cursor cursorDeleteZeroData;
    private Cursor cursorCustomerId;
    private int total=0;
    private DbHelper DbHelper =new DbHelper(this);
    private String customerId;
    private String customerName;
    private ListView orderListView;
    private TextView textViewTotal;
    private TextView textCustomerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fabOk = (FloatingActionButton) findViewById(R.id.fabOk);
        fabOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DbHelper.deleteDb();;
                Toast.makeText(OrderListActivity.this,"ORDER SUCESSFULLY PLACED. PLEASE WAIT...!.",Toast.LENGTH_SHORT).show();

                Intent i=new Intent(OrderListActivity.this,HomePageActivity.class);
                startActivity(i);

            }
        });
        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(OrderListActivity.this, CategoryActivity.class);

                startActivity(intent);

            }
        });



        orderListView=(ListView)findViewById(R.id.orderListView);
        textViewTotal=(TextView)findViewById(R.id.textViewTotal);

        cursorDeleteZeroData= DbHelper.getOrderList();
        int len=cursorDeleteZeroData.getCount();
        for(int i=0;i<len;i++){
            cursorDeleteZeroData.moveToPosition(i);
            String count=cursorDeleteZeroData.getString(cursorDeleteZeroData.getColumnIndex("count"));
            if(count.equals(String.valueOf(0))){
                DbHelper.deleteZeroData();

            }


        }
        cursorCustomerId=DbHelper.getCustomerNo();
        cursorCustomerId.moveToFirst();
        customerId=cursorCustomerId.getString(cursorCustomerId.getColumnIndex("id"));
        customerName=cursorCustomerId.getString(cursorCustomerId.getColumnIndex("customername"));
        textCustomerName=(TextView)findViewById(R.id.textCustomerName);
        textCustomerName.setText(customerName);

        cursorOrderlist= DbHelper.getCustomerOrderList(customerId);
        final OrderlistAdapter adapter=new OrderlistAdapter(OrderListActivity.this,cursorOrderlist);
        orderListView.setAdapter(adapter);


        int listlength=cursorOrderlist.getCount();

            for(int i=0;i<listlength;i++){

                cursorOrderlist.moveToPosition(i);

            String count=cursorOrderlist.getString(cursorOrderlist.getColumnIndex("count"));

            String price=cursorOrderlist.getString(cursorOrderlist.getColumnIndex("price"));
                if ((count == null) || (count == "0")){
                    count= String.valueOf(0);
                }
            total=total+(Integer.valueOf(count) *Integer.valueOf(price));

        }



        Logs.db(DbHelper.test2(), "id","orderno", "name", "count");

        Resources res = getResources();
        String totalData=String.format(res.getString(R.string.Total),total);
        textViewTotal.setText(totalData);


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent i=new Intent(OrderListActivity.this,CategoryActivity.class);
            startActivity(i);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
