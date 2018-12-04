package com.example.xiaojun.huayu;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class UserLab {
    private static UserLab mUserLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    public static UserLab get(Context context){
        if(mUserLab==null){
            mUserLab=new UserLab(context);
        }
        return mUserLab;
    }

    public UserLab(Context context){
        mContext=context.getApplicationContext();
        mDatabase=new UserBaseHelper(mContext).getWritableDatabase();
    }
    private static ContentValues getContentValues(User user){
        ContentValues values=new ContentValues();
        values.put(UserDbSchema.UserTable.Cols.USERNAME,user.getUserName());
        values.put(UserDbSchema.UserTable.Cols.PASSWORD,user.getPassword());
        return values;
    }
    public long addUser(User u){
        ContentValues values=getContentValues(u);
        Cursor cursor=mDatabase.query("User",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                String username=cursor.getString(cursor.getColumnIndex(UserDbSchema.UserTable.Cols.USERNAME));
                String password=cursor.getString(cursor.getColumnIndex(UserDbSchema.UserTable.Cols.PASSWORD));
                if(!u.getUserName().equals(username)){
                    mDatabase.insert(UserDbSchema.UserTable.Name,null,values);
                    break;
                }
            }while (cursor.moveToNext());
        }
        cursor.close();

        return mDatabase.insert(UserDbSchema.UserTable.Name,null,values);
    }
    public long queryUser(String userName,String passWord) {
        Cursor cursor = mDatabase.query("User", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(cursor.getColumnIndex(UserDbSchema.UserTable.Cols.USERNAME));
                String password = cursor.getString(cursor.getColumnIndex(UserDbSchema.UserTable.Cols.PASSWORD));
                if (userName.equals(username)&&passWord.equals(password)) {
                    return 1;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return -1;
    }



    public void updateUser(User user){
        String uuidString=user.getId().toString();
        ContentValues values=getContentValues(user);
        mDatabase.update(UserDbSchema.UserTable.Name,values,UserDbSchema.UserTable.Cols.UUID+" =?",new String[]{uuidString});
    }


}
