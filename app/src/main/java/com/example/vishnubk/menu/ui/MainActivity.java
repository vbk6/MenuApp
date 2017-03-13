package com.example.vishnubk.menu.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.vishnubk.menu.R;
import com.example.vishnubk.menu.adapters.ItemListAdapter;

import com.example.vishnubk.menu.db.DbHelper;
import com.example.vishnubk.menu.models.MenuDetail;

public class MainActivity extends AppCompatActivity {

    private DbHelper DbHelper =new DbHelper(this);
    private ListView itemListView;
    private Cursor cursorOrderNo;
    private String orderNo;
    public Cursor cursorCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        itemListView=(ListView)findViewById(R.id.listView);
        final String category= getIntent().getStringExtra("category");
        cursorCategory= DbHelper.getMenuData(category);
        final ItemListAdapter adapter=new ItemListAdapter(this,cursorCategory);
        itemListView.setAdapter(adapter);
        cursorOrderNo=DbHelper.getCustomerNo();
        cursorOrderNo.moveToFirst();
        orderNo=cursorOrderNo.getString(cursorOrderNo.getColumnIndex("id"));


        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MenuDetail menuDetail = (MenuDetail) adapter.getItem(position);
                Integer idno=menuDetail.getId();

                DbHelper.deletePrevCount(String.valueOf(idno),orderNo);
                DbHelper.menuinsert(idno);

                Intent i = new Intent(MainActivity.this, ItemDisplayActivity.class);
                i.putExtra("id", idno);
                startActivity(i);
            }
        });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent i=new Intent(MainActivity.this,CategoryActivity.class);
            startActivity(i);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
