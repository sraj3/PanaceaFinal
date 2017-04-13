package com.example.sugandh.panacea1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sugandh on 4/10/2017.
 */

public class UserDbHelper  extends SQLiteOpenHelper{

    SQLiteDatabase sqLiteDatabase;
    public UserDbHelper(Context context) {
        super(context,"Panacea",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table User(id integer,name text,email text,mobile text,address text,pincode text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists User");
        onCreate(db);
    }


    public String add_user(User ob) {
        String msg=null;
        sqLiteDatabase=getWritableDatabase();
        try
        {
            ContentValues values = new ContentValues();
            values.put("id", ob.getId());
            values.put("name", String.valueOf(ob.getName()));
            values.put("email", String.valueOf(ob.getEmail()));
            values.put("mobile", String.valueOf(ob.getMobile()));
            values.put("address", String.valueOf(ob.getAddress()));
            values.put("pincode", String.valueOf(ob.getPincode()));
            sqLiteDatabase.insert("User", "address", values);
            msg = "data saved";
        }
        catch (Exception e)
        {
            msg=e.getMessage();
        }
        return msg;
    }
    public User get_user() {
        Cursor cursor;
        sqLiteDatabase=getWritableDatabase();
        String col[]={"id","name","email","mobile","address","pincode"};
        cursor=sqLiteDatabase.query("User",col,null,null,null,null,null,null);
        int  id;
        String name,email,mobile,address,pincode;
        if(cursor.moveToFirst()) {
            id=cursor.getInt(0);
            name=cursor.getString(1);
            email=cursor.getString(2);
            mobile=cursor.getString(3);
            address=cursor.getString(4);
            pincode=cursor.getString(5);
            User user=new User(id,name,mobile,address, pincode);
            return user;
        }
        return null;
    }
    public void clear_data(SQLiteDatabase db){
        sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.execSQL("drop table if exists User");
    }

}
