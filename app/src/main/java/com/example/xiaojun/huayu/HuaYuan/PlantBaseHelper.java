package com.example.xiaojun.huayu.HuaYuan;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;




public class PlantBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE_NAME="plantBase.db";
    public PlantBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists "+PlantDbSchema.PlantTable.Name + "("+
                "_id interger primary key , "+
                PlantDbSchema.PlantTable.Cols.USERNAME + ","+
                PlantDbSchema.PlantTable.Cols.PLANTCHINESENAME+","+
                PlantDbSchema.PlantTable.Cols.PLANTLATINNNAME+","+
                PlantDbSchema.PlantTable.Cols.PLANTBREEDTIME+","+
                PlantDbSchema.PlantTable.Cols.PLANTBIRTHDAY+","+
                PlantDbSchema.PlantTable.Cols.PLANTCHANGESOILTIME+"," +
                PlantDbSchema.PlantTable.Cols.PLANTFAMILYGENUS+","+
                PlantDbSchema.PlantTable.Cols.PLANTFERTILIZATETIME+","+
                PlantDbSchema.PlantTable.Cols.PLANTMORPHOLOGICALCHARACTERISTICS+","+
                PlantDbSchema.PlantTable.Cols.PLANTSCISSORTIME+","+
                PlantDbSchema.PlantTable.Cols.PLANTSOIL+","+
                PlantDbSchema.PlantTable.Cols.PLANTDRINKTIME+","+
                PlantDbSchema.PlantTable.Cols.ISCHOICE+","+
                PlantDbSchema.PlantTable.Cols.IMAGEURL+
                ")"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
