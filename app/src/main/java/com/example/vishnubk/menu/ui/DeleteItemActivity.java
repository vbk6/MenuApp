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
import com.example.vishnubk.menu.adapters.ItemListAdapter;
import com.example.vishnubk.menu.db.DbHelper;
import com.example.vishnubk.menu.models.MenuDetail;

public class DeleteItemActivity extends AppCompatActivity {


    private DbHelper DbHelper =new DbHelper(this);
    private ListView itemListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        itemListView=(ListView)findViewById(R.id.listView);
        final String category= getIntent().getStringExtra("category");
        final Cursor cursor= DbHelper.getMenuData(category);

        final ItemListAdapter adapter=new ItemListAdapter(this,cursor);
        itemListView.setAdapter(adapter);


        itemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteItemActivity.this);
                builder.setMessage("Do you want to delete this")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialogPositive(adapter,position,category);

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

    public void dialogPositive(ItemListAdapter adapter, int position, String category)
    {
        MenuDetail menuDetail = (MenuDetail) adapter.getItem(position);
        Integer idnos=menuDetail.getId();
        DbHelper.deleteItem(idnos);
        Intent i = new Intent(DeleteItemActivity.this, DeleteItemActivity.class);
        i.putExtra("category", category);
        startActivity(i);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent i=new Intent(DeleteItemActivity.this,DeleteActivity.class);
            startActivity(i);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}
