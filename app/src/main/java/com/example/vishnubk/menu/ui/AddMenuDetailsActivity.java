package com.example.vishnubk.menu.ui;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vishnubk.menu.R;
import com.example.vishnubk.menu.db.DbHelper;

import java.io.ByteArrayOutputStream;

public class AddMenuDetailsActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE =1;
    private EditText itemCategory;
    private EditText itemName;
    private EditText itemPrice;
    private String selectedImagePath;
    public byte[] Image;
    private DbHelper dbhelper=new DbHelper(this);
    private int size=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        itemCategory = (EditText) findViewById(R.id.itemCategory);
        itemName = (EditText) findViewById(R.id.itemName);
        itemPrice = (EditText) findViewById(R.id.itemPrice);

        FloatingActionButton fabImage = (FloatingActionButton) findViewById(R.id.fabImage);
        fabImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

            }
        });



        FloatingActionButton fabDone = (FloatingActionButton) findViewById(R.id.fabDone);
        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String category = itemCategory.getText().toString();
                final String name = itemName.getText().toString();
                final String price = itemPrice.getText().toString();


                if (TextUtils.isEmpty(category)) {
                    itemCategory.setError("Enter Category");
                    return;
                } else if (TextUtils.isEmpty(name)) {
                    itemName.setError("Enter Name");
                    return;
                } else if (TextUtils.isEmpty(price)) {
                    itemPrice.setError("Enter Price");
                    return;
                } else if (Image==null) {
                    Toast.makeText(AddMenuDetailsActivity.this, "Add Picture", Toast.LENGTH_SHORT).show();
                    return;
                } else if(size/30444>=700){

                    Log.d("img",size/30444+"kk");

                    Toast.makeText(AddMenuDetailsActivity.this, "Image size large", Toast.LENGTH_SHORT).show();
                }

                  else {

                    dbhelper.categoryAdd(category);
                    dbhelper.menuAdd(name, price, Image,category);
                    Snackbar.make(view, "DATA ADDED", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }

            }
        });


    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();


                selectedImagePath = getPath(selectedImageUri);



                /** FileInputStream instream = null;
                try {
                    instream = new FileInputStream(selectedImagePath);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                BufferedInputStream bif = new BufferedInputStream(instream);

                try {
                    byteImage1 = new byte[bif.available()];


                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    bif.read(byteImage1);
                } catch (IOException e) {
                    e.printStackTrace();
                } */

            /*
            get the bitmap of selected image
             */


                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath, options);

                size=bitmap.getByteCount();


                /*
                to compress the image
                 */



                ByteArrayOutputStream out = new ByteArrayOutputStream();
                if(size/30444<=700) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, out);
                }


                /*
                convert that bitmap to byte array
                 */

                 Image = out.toByteArray();




            }
        }

    }

       @SuppressWarnings("deprecation")
        public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }



}
