package com.example.vishnubk.menu.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anwios.alog.Logs;
import com.example.vishnubk.menu.R;

import com.example.vishnubk.menu.db.DbHelper;

import org.w3c.dom.Text;

import java.util.Locale;

public class HomePageActivity extends AppCompatActivity {

    private ImageView imageFrontIcon;
    private DbHelper DbHelper = new DbHelper(this);
    private EditText editText;
    private ImageButton imageButton;
    private TextView textGerman;
    private TextView textEnglish;
    Context context=this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Logs.db(DbHelper.test1(), "id", "category", "name", "price");

        Logs.db(DbHelper.test2(), "id","orderno", "name", "count");

        Logs.db(DbHelper.test3(),"id","customername");

        Logs.db(DbHelper.test4(),"id","category");


        editText= (EditText) findViewById(R.id.editText);
        imageButton=(ImageButton)findViewById(R.id.imageButton);
        textGerman=(TextView)findViewById(R.id.textGerman);
        textEnglish=(TextView)findViewById(R.id.textEnglish);
        final LinearLayout layout=(LinearLayout)findViewById(R.id.nameLayout);

        textGerman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String languageToLoad  = "de";
                Locale locale = new Locale(languageToLoad);
                Configuration config = new Configuration();
                config.locale = locale;
                context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
                Intent intent = new Intent(HomePageActivity.this, HomePageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }
        });

        textEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String languageToLoad  = "en";
                Locale locale = new Locale(languageToLoad);
                Configuration config = new Configuration();
                config.locale = locale;
                context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
                Intent intent = new Intent(HomePageActivity.this, HomePageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePageActivity.this, AdminActivity.class);
                startActivity(i);
            }
        });

        FloatingActionButton fabName = (FloatingActionButton) findViewById(R.id.fabName);
        fabName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout.setVisibility(view.VISIBLE);


            }
        });
        FloatingActionButton fabCustomerList=(FloatingActionButton)findViewById(R.id.fabCustomer);
        fabCustomerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Intent i = new Intent(HomePageActivity.this, CustomerListActivity.class);
                    startActivity(i);

            }
        });

      imageButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              String name= editText.getText().toString();
             if(TextUtils.isEmpty(name)){
                 editText.setError("Enter Name");
             }

             else {
                 layout.setVisibility(v.INVISIBLE);
                 DbHelper.insertName(name);
             }
          }
      });

        imageFrontIcon = (ImageView) findViewById(R.id.imageView);
        imageFrontIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dbHelper.deleteDb();
                Intent i = new Intent(HomePageActivity.this, CategoryActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            DbHelper.deleteDb();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return false;
    }

}
