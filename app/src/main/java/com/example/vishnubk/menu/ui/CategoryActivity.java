package com.example.vishnubk.menu.ui;

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

public class CategoryActivity extends AppCompatActivity {

    private DbHelper DbHelper =new DbHelper(this);
    public Cursor cursorCategory;
    private ListView categoryList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cursorCategory= DbHelper.getCategory();

        categoryList=(ListView)findViewById(R.id.categoryListView);
        final CategoryListAdapter adapter=new CategoryListAdapter(this,cursorCategory);
        categoryList.setAdapter(adapter);

        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MenuDetail menuDetail = (MenuDetail) adapter.getItem(position);
                String category = menuDetail.getItemCategory();
                Intent i = new Intent(CategoryActivity.this, MainActivity.class);
                i.putExtra("category", category);
                startActivity(i);
            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent i=new Intent(CategoryActivity.this,HomePageActivity.class);
            startActivity(i);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }



}
