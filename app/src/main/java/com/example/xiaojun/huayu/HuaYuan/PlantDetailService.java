package com.example.xiaojun.huayu.HuaYuan;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.xiaojun.huayu.R;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
//service不能停止的bug
public class PlantDetailService extends Service {
    private int PlantDrinkTime;
    private int PlantFertilizationTime;
    private int PlantScissorTime;
    private int PlantChangeSoilTime;
    private int PlantBreedTime;
    private String PlantBirthday;
    private static final String PLANTBIRTHDAY="plantbirthday";
    private static final String PLANTDRINKTIME="plantdrinktime";
    private static final String PLANTFERTILIZATIONTIME="plantfertilizationtime";
    private static final String PLANTSCISSORTIME="plantscissortime";
    private static final String PLANTCHANGESOILTIME="plantchangesoiltime";
    private static final String PLANTBREEDTIME="plantbreedtime";
    private static int vPlantDrinkTime,vPlantFertilizationTime,vPlantScissorTime,vPlantChangeSoilTime,vPlantBreedTime;
    public PlantDetailService() {
    }
    @Override
    public void onCreate() {
        Log.d("提示","服务已经开始");

        super.onCreate();

    }
    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateTime(intent);
            }
        }).start();

        if(PlantBirthday==null||PlantBirthday.equals("")){
            getInitIntentData(intent);
        }
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //这里是定时的,这里设置的是每隔两秒打印一次时间=-=,自己改
        int anHour = 2 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this,AlarmReceiver.class);
        Log.d("DrinkTime",vPlantDrinkTime+"");
        i.putExtra("vPlantDrinkTime",vPlantDrinkTime);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    public void getInitIntentData(Intent intent){
        String plantJson=intent.getStringExtra("plant");
        Plant plant=new Gson().fromJson(plantJson,Plant.class);
        PlantBirthday=plant.getPlantBirthday();
        PlantDrinkTime=plant.getPlantDrinkTime();
        PlantFertilizationTime=plant.getPlantFertilizateTime();
        PlantScissorTime=plant.getPlantScissorTime();
        PlantChangeSoilTime=plant.getPlantChangeSoilTime();
        PlantBreedTime=plant.getPlantBreedTime();
    }
    private void updateTime(Intent intent){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String CurrentDate=simpleDateFormat.format(date);
        DateFormat dateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        try {
            Date d1=dateFormat.parse(PlantBirthday);
            Date d2=dateFormat.parse(CurrentDate);
            long diff=d2.getTime()-d1.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
            long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
            vPlantDrinkTime=PlantDrinkTime-(int)minutes;
            vPlantFertilizationTime=PlantFertilizationTime-(int)hours;
            vPlantScissorTime=PlantScissorTime-(int)days;
            vPlantChangeSoilTime=PlantChangeSoilTime-(int)days;
            vPlantBreedTime=PlantBreedTime-(int)days;
        }catch (Exception e){
            Log.d("提示","获取时间错误");
        }
    }
    @Override
    public void onDestroy() {
        Log.i("提示", "onDestory方法被调用!");
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.cancel(pi);
        super.onDestroy();

    }
    public static int getvPlantDrinkTime() {
        return vPlantDrinkTime;
    }

    public static int getvPlantFertilizationTime() {
        return vPlantFertilizationTime;
    }

    public static int getvPlantScissorTime() {
        return vPlantScissorTime;
    }

    public static int getvPlantChangeSoilTime() {
        return vPlantChangeSoilTime;
    }

    public static int getvPlantBreedTime() {
        return vPlantBreedTime;
    }
}
