package com.example.vishnubk.menu.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.vishnubk.menu.R;
import com.example.vishnubk.menu.adapters.CustomerListAdapter;
import com.example.vishnubk.menu.db.DbHelper;
import com.example.vishnubk.menu.models.CustomerDetails;

public class CustomerListActivity extends AppCompatActivity {

    public Cursor cursorCustomerList;
    private DbHelper dbHelper=new DbHelper(this);
    private ListView listCustomer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listCustomer=(ListView)findViewById(R.id.customerList);
        cursorCustomerList= dbHelper.getCustomerList();

        final CustomerListAdapter adapter=new CustomerListAdapter(this,cursorCustomerList);
        listCustomer.setAdapter(adapter);

        listCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CustomerDetails customerDetails = (CustomerDetails) adapter.getItem(position);
                String idno = customerDetails.getId();
                Intent i = new Intent(CustomerListActivity.this, CustomerOrderActivity.class);
                i.putExtra("id", idno);
                startActivity(i);
            }
        });

        listCustomer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerListActivity.this);
                builder.setMessage("Do you want to delete ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                CustomerDetails customerDetails = (CustomerDetails) adapter.getItem(position);
                                String idno = customerDetails.getId();
                                dbHelper.deleteCustomer(idno);
                                Intent i = new Intent(CustomerListActivity.this, CustomerListActivity.class);
                                startActivity(i);



                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                            }
                        });

                builder.create().show();


                return true;
            }
        });


    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent i=new Intent(CustomerListActivity.this,HomePageActivity.class);
            startActivity(i);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
