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
import com.example.vishnubk.menu.adapters.CategoryListAdapter;
import com.example.vishnubk.menu.db.DbHelper;
import com.example.vishnubk.menu.models.MenuDetail;

public class DeleteActivity extends AppCompatActivity {

    private DbHelper DbHelper =new DbHelper(this);
    public Cursor cursorCategory;
    private ListView categoryListview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cursorCategory= DbHelper.getCategory();

        categoryListview=(ListView)findViewById(R.id.categoryListView);
        final CategoryListAdapter adapter=new CategoryListAdapter(this,cursorCategory);
        categoryListview.setAdapter(adapter);


        categoryListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MenuDetail menuDetail = (MenuDetail) adapter.getItem(position);
                String category = menuDetail.getItemCategory();
                Intent i = new Intent(DeleteActivity.this, DeleteItemActivity.class);
                i.putExtra("category", category);
                startActivity(i);
            }
        });



        categoryListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {


                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteActivity.this);
                builder.setMessage("Do you want to delete ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialogPositive(adapter, position);

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

    public void dialogPositive(CategoryListAdapter adapter, int position)
    {
        MenuDetail menuDetail = (MenuDetail) adapter.getItem(position);
        Integer idno=menuDetail.getId();
        DbHelper.deleteCategory(idno);
        Intent i = new Intent(DeleteActivity.this, DeleteActivity.class);
        startActivity(i);


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent i=new Intent(DeleteActivity.this,AdminActivity.class);
            startActivity(i);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}
