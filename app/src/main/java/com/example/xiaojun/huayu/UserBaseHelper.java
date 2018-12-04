package com.example.xiaojun.huayu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.xiaojun.huayu.UserDbSchema.UserTable;

public class UserBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE_NAME="userBase.db";
    public UserBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table "+UserTable.Name+ "("+
            "_id interger primary key , "+
                UserTable.Cols.UUID+ ", "+
                UserTable.Cols.USERNAME+", "+
                UserTable.Cols.PASSWORD+
                ")"

        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }

}
