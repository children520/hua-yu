package com.example.xiaojun.huayu.HuaYuan;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.xiaojun.huayu.HuaYuan.Bean.Plant;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class PlantLab {
    private final Plant plant=new Plant();


    private static PlantLab mPlantLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    public static PlantLab get(Context context){
        if(mPlantLab==null){
            mPlantLab=new PlantLab(context);
        }
        return mPlantLab;
    }

    public PlantLab(Context context){
            mContext=context.getApplicationContext();
            mDatabase=new PlantBaseHelper(mContext).getWritableDatabase();
    }
    private static ContentValues getContentValues(Plant plant){
        ContentValues values=new ContentValues();
        values.put(PlantDbSchema.PlantTable.Cols.PLANTCHINESENAME,plant.getPlantChineseName());
        values.put(PlantDbSchema.PlantTable.Cols.PLANTLATINNNAME,plant.getPlantLatinName());
        values.put(PlantDbSchema.PlantTable.Cols.PLANTFAMILYGENUS,plant.getPlantFamilyGenus());
        values.put(PlantDbSchema.PlantTable.Cols.PLANTMORPHOLOGICALCHARACTERISTICS,plant.getPlantMorphologicalCharacteristics());
        values.put(PlantDbSchema.PlantTable.Cols.PLANTSOIL,plant.getPlantSoil());
        values.put(PlantDbSchema.PlantTable.Cols.PLANTBREEDTIME,plant.getPlantBreedTime());
        values.put(PlantDbSchema.PlantTable.Cols.PLANTCHANGESOILTIME,plant.getPlantChangeSoilTime());
        values.put(PlantDbSchema.PlantTable.Cols.PLANTDRINKTIME,plant.getPlantDrinkTime());
        values.put(PlantDbSchema.PlantTable.Cols.PLANTFERTILIZATETIME,plant.getPlantFertilizateTime());
        values.put(PlantDbSchema.PlantTable.Cols.PLANTSCISSORTIME,plant.getPlantScissorTime());
        values.put(PlantDbSchema.PlantTable.Cols.ISCHOICE,plant.isChoice());
        values.put(PlantDbSchema.PlantTable.Cols.IMAGEURL,plant.getImageUrl());
        values.put(PlantDbSchema.PlantTable.Cols.PLANTBIRTHDAY,getNowTime());
        return values;
    }
    public long addPlant(Plant p){
        ContentValues values=getContentValues(p);
        Cursor cursor=mDatabase.query("Plant",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                String plantchinesename=cursor.getString(cursor.getColumnIndex(PlantDbSchema.PlantTable.Cols.PLANTCHINESENAME));
                if(!p.getPlantChineseName().equals(plantchinesename)){
                    mDatabase.insert(PlantDbSchema.PlantTable.Name,null,values);
                    mDatabase.close();
                    break;
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return mDatabase.insert(PlantDbSchema.PlantTable.Name,null,values);
    }
    public void deletePlant(Plant p){
        mDatabase.delete("Plant","plantchinesename=?",new String[]{p.getPlantChineseName()} );
        mDatabase.close();

}
    public void updatePlant(Plant plant){
        String uuidString=plant.getPlantId().toString();
        ContentValues values=getContentValues(plant);
        mDatabase.update(PlantDbSchema.PlantTable.Name,values,PlantDbSchema.PlantTable.Cols.UUID+" =?",new String[]{uuidString});
    }
    public void displayPlantList(List<Plant> plantList){
        plantList.clear();
        Cursor cursor = mDatabase.query("Plant", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {

                String imageurl = cursor.getString(cursor.getColumnIndex(PlantDbSchema.PlantTable.Cols.IMAGEURL));
                String ischoice = cursor.getString(cursor.getColumnIndex(PlantDbSchema.PlantTable.Cols.ISCHOICE));
                String plantbreedtime = cursor.getString(cursor.getColumnIndex(PlantDbSchema.PlantTable.Cols.PLANTBREEDTIME));
                String plantchangesoiltime = cursor.getString(cursor.getColumnIndex(PlantDbSchema.PlantTable.Cols.PLANTCHANGESOILTIME));
                String plantchinesename = cursor.getString(cursor.getColumnIndex(PlantDbSchema.PlantTable.Cols.PLANTCHINESENAME));
                String plantdrinktime = cursor.getString(cursor.getColumnIndex(PlantDbSchema.PlantTable.Cols.PLANTDRINKTIME));
                String plantfamilygenus = cursor.getString(cursor.getColumnIndex(PlantDbSchema.PlantTable.Cols.PLANTFAMILYGENUS));
                String plantfertilizatetime = cursor.getString(cursor.getColumnIndex(PlantDbSchema.PlantTable.Cols.PLANTFERTILIZATETIME));
                String plantlatinname = cursor.getString(cursor.getColumnIndex(PlantDbSchema.PlantTable.Cols.PLANTLATINNNAME));
                String plantmorphologicalcharacteristics = cursor.getString(cursor.getColumnIndex(PlantDbSchema.PlantTable.Cols.PLANTMORPHOLOGICALCHARACTERISTICS));
                String plantscissortime = cursor.getString(cursor.getColumnIndex(PlantDbSchema.PlantTable.Cols.PLANTSCISSORTIME));
                String plantsoil = cursor.getString(cursor.getColumnIndex(PlantDbSchema.PlantTable.Cols.PLANTSOIL));
                String plantbirthday = cursor.getString(cursor.getColumnIndex(PlantDbSchema.PlantTable.Cols.PLANTBIRTHDAY));
                plant.setChoice(Boolean.getBoolean(ischoice));
                plant.setImageUrl(imageurl);
                plant.setPlantBreedTime(Integer.parseInt(plantbreedtime));
                plant.setPlantChangeSoilTime(Integer.parseInt(plantchangesoiltime));
                plant.setPlantChineseName(plantchinesename);
                plant.setPlantDrinkTime(Integer.parseInt(plantdrinktime));
                plant.setPlantFamilyGenus(plantfamilygenus);
                plant.setPlantFertilizateTime(Integer.parseInt(plantfertilizatetime));
                plant.setPlantLatinName(plantlatinname);
                plant.setPlantMorphologicalCharacteristics(plantmorphologicalcharacteristics);
                plant.setPlantScissorTime(Integer.parseInt(plantscissortime));
                plant.setPlantSoil(plantsoil);
                plant.setPlantBirthday(plantbirthday);
                Log.d("花园显示：",plantbirthday);
                plantList.add(plant);

            } while (cursor.moveToNext());
        }
        cursor.close();

    }
    public static String getNowTime(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String s=simpleDateFormat.format(date);
        return s;
    }
    public void startBackPlantService(Context context,Plant plant){
        Log.d("开始","服务");
        Intent intent=new Intent(context,PlantDetailService.class);
        intent.putExtra("plant",new Gson().toJson(plant));
        //Log.d("这里是开始服务的生日",plant.getPlantBirthday());
        context.startService(intent);
    }
    public void stopBackPlantService(Context context){
        Log.d("暂停","服务");
        Intent intent=new Intent(context,PlantDetailService.class);
        context.stopService(intent);
    }
}
