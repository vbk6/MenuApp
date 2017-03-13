package com.example.vishnubk.menu.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vishnubk on 6/6/16.
 */
public class dbHelper extends SQLiteOpenHelper {

    private dbHelper dbHelper;
    private Context context;
    private static final String DATABASE_NAME= "motherpalmdb";
    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
        dbHelper =this;
    }

    @Override
    public void onCreate(SQLiteDatabase motherpalmdb) {

        motherpalmdb.execSQL("CREATE TABLE palm_farm(id integer AUTO INCREMENT,palmid text,seedfarmtype integer);");
        motherpalmdb.execSQL("CREATE TABLE palm(id integer AUTO INCREMENT,personname text,palmid text,"+
                "lat REAL,lon REAL,distrctcode text,blockcode integer,panchayatcode integer,surveyno TEXT,"+
                "variety integer,fieldcode text default null,farmtype integer,soiltype integer,irrigtntype integer,"+
                "yield integer,noseeds integer,noimgs integer default 0,imgnames text,server_status boolean default false)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase motherpalmdb, int oldVersion, int newVersion) {

        motherpalmdb.execSQL("DROP TABLE IF EXISTS palm_farm");
        motherpalmdb.execSQL("DROP TABLE IF EXISTS palm");
        onCreate(motherpalmdb);

    }
}
