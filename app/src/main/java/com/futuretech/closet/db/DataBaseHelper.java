package com.futuretech.closet.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Create by xu on 2019/4/17
 * 仅在需要为数据库增加新表时改动此类中的onCreate方法；
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int version=1;
    public DataBaseHelper(Context context, String name, CursorFactory factory, int version){
               super (context,name,factory,version );
    }
    public DataBaseHelper(Context context, String name){
        this(context,name,version);
    }
    public DataBaseHelper(Context context, String name, int version ){
        this( context,name,null,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        System.out.print("create success");
       // db.execSQL("create table user(id int PRIMARY KEY,name varchar(20),riqi TIMESTAMP)");/*riqi TIMESTAMP)*/
        db.execSQL("create table user(id int,userid varchar(20) PRIMARY KEY,password varchar(20) NOT NULL,nickname varchar(20) NOT NULL,sex varchar(20),phone varchar(20) NOT NULL,email varchar(20),birthday date,gmt_create TIMESTAMP NOT NULL,gmt_modified TIMESTAMP )");
        db.execSQL("create table clothesInformation(dressid int PRIMARY KEY,style varchar(20) NOT NULL,color varchar(20) NOT NULL,thickness varchar(20) NOT NULL,photo varchar(20) NOT NULL,attribute varchar(20),userid varchar(20) NOT NULL,gmt_create TIMESTAMP ,gmt_modified TIMESTAMP )");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion ){
        System.out.print("update a Database");
    }


}
