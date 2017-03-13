package com.example.vishnubk.menu.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by vishnubk on 2/2/16.
 */
public class DbHelper extends SQLiteOpenHelper {

    private DbHelper DbHelper;
    private Context context;
    private static final String DATABASE_NAME= "menuadder";
    private static final String  MENU_TABLE_NAME="menuaddtable";
    private static final String  MENU_TABLE_NAME1="menutable";
    private static final String MENU_COLUMN_ID="id";
    private static final String MENU_COLUMN_CATEGORY="category";
    private static final String MENU_COLUMN_NMAE="name";
    private static final String MENU_COLUMN_PRICE="price";
    private static final String MENU_COLUMN_IMAGE="image";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
        DbHelper =this;
    }

    @Override
    public void onCreate(SQLiteDatabase menuadder) {

        menuadder.execSQL("create table menutable(id integer primary key,orderno integer,name text,count text)");
        menuadder.execSQL("create table menuaddtable(id integer primary key,category integer,name text,price text,image BLOB)");
        menuadder.execSQL("create table categorytable(id integer primary key,category text UNIQUE)");
        menuadder.execSQL("create table customertable(id integer primary key,customername text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase menuadder, int oldVersion, int newVersion) {


        menuadder.execSQL("DROP TABLE IF EXISTS menuaddtable");
        menuadder.execSQL("DROP TABLE IF EXISTS menutable");
        menuadder.execSQL("DROP TABLE IF EXISTS categorytable");
        menuadder.execSQL("DROP TABLE IF EXISTS customertable)");
        onCreate(menuadder);
    }

    public void categoryAdd(String category){

        SQLiteDatabase menuadder=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("category", category);
        menuadder.insert("categorytable", null, contentValues);
        menuadder.close();
    }

    public boolean menuAdd(String name,String price, byte[] image,String category)
    {
        SQLiteDatabase menuadder=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name", name);
        contentValues.put("price", price);
        contentValues.put("image", image);
        menuadder.insert("menuaddtable", null, contentValues);
        menuadder.execSQL("UPDATE menuaddtable SET category=(SELECT id FROM categorytable WHERE " +
                "TRIM(category) = '" + category.trim() + "') WHERE category IS NULL");
        menuadder.close();
        return true;
    }

    public Cursor getMenuData(String category){

        Log.d("asd", "kk");
        SQLiteDatabase menuadder=getReadableDatabase();
        Cursor cursor=menuadder.rawQuery("SELECT * FROM menuaddtable WHERE category= " +
                "(SELECT id FROM categorytable WHERE TRIM(category) = '" + category.trim() + "')",null);
        return cursor;
    }

    public void deleteDb(){
        SQLiteDatabase menuadder=this.getWritableDatabase();
        menuadder.execSQL("delete from menutable  ");
        menuadder.execSQL("delete from customertable");

    }

    public Cursor getCategory(){

        SQLiteDatabase menuadder=getReadableDatabase();
        Cursor cursor=menuadder.rawQuery("SELECT DISTINCT category,id FROM categorytable", null);
        return cursor;
    }

    public void deleteCategory( Integer idno){

        SQLiteDatabase menuadder=this.getWritableDatabase();
        menuadder.execSQL("delete from categorytable  WHERE id='"+idno+"'");
        menuadder.execSQL("delete from menuaddtable where category='"+idno+"'");


    }

    public void deleteItem(Integer id){

        SQLiteDatabase menuadder=this.getWritableDatabase();
        menuadder.execSQL("delete from menuaddtable where id='"+id+"'");

    }

    public boolean menuinsert(Integer idno) {
        SQLiteDatabase menuadder = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",idno);
        menuadder.insert("menutable", null, contentValues);
        menuadder.execSQL("UPDATE menutable SET orderno=(SELECT id FROM customertable " +
                "ORDER BY id DESC LIMIT 1) WHERE orderno is null");
        menuadder.close();
        return true;
    }

    public Cursor getData(String id) {
        Log.d("bk", id + "dd");
        SQLiteDatabase menuadder = getReadableDatabase();
        Cursor res = menuadder.rawQuery("SELECT * from menuaddtable where id='"+id+"'",null);
        return res;
    }

    public Cursor getOrderList() {

        SQLiteDatabase menuadder = getReadableDatabase();
        Cursor cursor = menuadder.rawQuery("SELECT menuaddtable.name,menuaddtable.price, menutable.count FROM" +
                " menuaddtable INNER JOIN menutable ON menutable.name=menuaddtable.id GROUP BY menutable.name",null);
        return cursor;
    }



    public boolean countinsert(String count, String name,String orderNo) {

        SQLiteDatabase menuadder = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("count", count);
        menuadder.update("menutable", contentValues, "count IS NULL OR TRIM(name) = '" + name.trim()
                + "'AND orderno='" + orderNo.trim() + "'", null);
        Log.d("count", count + "cc");
        menuadder.close();
        return true;
    }

    public Cursor getPrevCount(String id,String orderNo) {


        SQLiteDatabase menuadder = getReadableDatabase();
        Cursor cursor = menuadder.rawQuery("SELECT count FROM menutable WHERE name = '" + id
                + "' AND orderno='" + orderNo + "'", null);
        return cursor;

    }

    public void deletePrevCount(String id,String orderno) {

        SQLiteDatabase menuadder = getWritableDatabase();
        menuadder.execSQL("DELETE FROM menutable WHERE TRIM(name) = '" + id.trim() + "'AND orderno='"
                + orderno + "'AND id NOT IN (SELECT MAX(id) FROM menutable  GROUP BY orderno)");
    }

    public void deleteZeroData() {

        SQLiteDatabase menuadder = getWritableDatabase();
        String m = String.valueOf(0);

        menuadder.execSQL("DELETE FROM menutable WHERE count=" + m);


    }

    public void insertName(String name){


        SQLiteDatabase menuadder=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("customername",name);
        menuadder.insert("customertable", null, contentValues);
    }

    public Cursor getCustomerList(){

        SQLiteDatabase menuadder=getReadableDatabase();
        Cursor cursor=menuadder.rawQuery("SELECT * FROM customertable",null);

        return cursor;
    }

    public Cursor getCustomerOrderList(String id){
        Log.d("cur",id+"jj");

        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT menuaddtable.name,menuaddtable.price, menutable.count FROM menuaddtable" +
                " INNER JOIN customertable ON '" + id + "'=menutable.orderno INNER JOIN menutable ON " +
                "menutable.name=menuaddtable.id GROUP BY menutable.name",null);

        return  cursor;
    }
/**to get complete order list of all customers
    public Cursor getCompleteOrderList(){

        SQLiteDatabase menuadder=getReadableDatabase();
        Cursor cursor=menuadder.rawQuery("SELECT  DISTINCT customertable.customername,menuaddtable.name," +
                "menuaddtable.price, menutable.count FROM menuaddtable INNER JOIN customertable ON" +
                " customertable.id=menutable.orderno INNER JOIN menutable ON menutable.name=menuaddtable.id",null);
        return  cursor;

    }*/

    public void deleteCustomer(String id){

        SQLiteDatabase menuadder=getWritableDatabase();
        menuadder.execSQL("delete from menutable where orderno=" +id);
        menuadder.execSQL("delete from customertable where id="+id);

    }


    public Cursor getCustomerNo(){
        SQLiteDatabase menuadder=getReadableDatabase();
        Cursor cursor=menuadder.rawQuery("SELECT * FROM customertable WHERE ID = (SELECT MAX(ID)  FROM" +
                " customertable)",null);
        return cursor;

    }


    public Cursor test1(){

        SQLiteDatabase menuadder=getReadableDatabase();
        Cursor cursor=menuadder.rawQuery("SELECT * FROM menuaddtable ", null);
        return cursor;
    }


    public Cursor test2(){

        SQLiteDatabase menuadder=getReadableDatabase();
        Cursor cursor=menuadder.rawQuery("SELECT * FROM menutable",null);
        return cursor;
    }

    public Cursor test3(){
        SQLiteDatabase menuadder=getReadableDatabase();
        Cursor cursor=menuadder.rawQuery("SELECT * FROM customertable",null);
        return cursor;

    }

    public Cursor test4(){

        SQLiteDatabase menuadder=getReadableDatabase();
        Cursor cursor=menuadder.rawQuery("SELECT * FROM categorytable",null);
        return cursor;
    }




}