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

import com.example.xiaojun.huayu.HuaYuan.Bean.Plant;
import com.example.xiaojun.huayu.R;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
//service不能停止的bug
public class PlantDetailService extends Service {


    private static int PlantDrinkTime;
    private  static int PlantFertilizationTime;
    private static  int PlantScissorTime;
    private static  int PlantChangeSoilTime;
    private  static int PlantBreedTime;
    private String PlantBirthday;
    private static String PlantChineseName;
    private static final String PLANTBIRTHDAY="plantbirthday";
    private static final String PLANTDRINKTIME="plantdrinktime";
    private static final String PLANTFERTILIZATIONTIME="plantfertilizationtime";
    private static final String PLANTSCISSORTIME="plantscissortime";
    private static final String PLANTCHANGESOILTIME="plantchangesoiltime";
    private static final String PLANTBREEDTIME="plantbreedtime";
    private Intent intentToActivity;


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
                if(PlantBirthday==null||PlantBirthday.equals("")){
                    getInitIntentData(intent);
                }
                updateTime();
                sendToFragment();
            }
        }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //这里是定时的,这里设置的是每隔两秒打印一次时间=-=,自己改
        int anHour = 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this,AlarmReceiver.class);
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
        PlantChineseName=plant.getPlantChineseName();
        PlantBirthday=plant.getPlantBirthday();
        PlantDrinkTime=plant.getPlantDrinkTime();
        PlantFertilizationTime=plant.getPlantFertilizateTime();
        PlantScissorTime=plant.getPlantScissorTime();
        PlantChangeSoilTime=plant.getPlantChangeSoilTime();
        PlantBreedTime=plant.getPlantBreedTime();
    }
    private void sendToFragment(){
        intentToActivity=new Intent();
        intentToActivity.setAction(".HuaYuan.PlantDetailFragment$PlantDetailReceiver");
        intentToActivity.putExtra(PLANTBREEDTIME,vPlantBreedTime);
        intentToActivity.putExtra(PLANTCHANGESOILTIME,vPlantChangeSoilTime);
        intentToActivity.putExtra(PLANTDRINKTIME,vPlantDrinkTime);
        intentToActivity.putExtra(PLANTFERTILIZATIONTIME,vPlantFertilizationTime);
        intentToActivity.putExtra(PLANTSCISSORTIME,vPlantScissorTime);
        Log.d("发送","广播");
        sendBroadcast(intentToActivity);
    }

    private void updateTime(){
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
            vPlantDrinkTime=PlantDrinkTime*AlarmReceiver.getDrinkNoifyCount()-(int)minutes;
            vPlantFertilizationTime=PlantFertilizationTime*AlarmReceiver.getFertilizationNoifyCount()-(int)minutes;
            vPlantScissorTime=PlantScissorTime*AlarmReceiver.getScissorNoifyCount()-(int)minutes;
            vPlantChangeSoilTime=PlantChangeSoilTime*AlarmReceiver.getChangesoilNoifyCount()-(int)minutes;
            vPlantBreedTime=PlantBreedTime*AlarmReceiver.getBreedNoifyCount()-(int)minutes;
            Log.d("VDrinkTime",vPlantDrinkTime+"");
            Log.d("VFertilizationTime",vPlantFertilizationTime+"");
            Log.d("VScissorTime",vPlantScissorTime+"");
            Log.d("VChangeSoilTime",vPlantChangeSoilTime+"");
            Log.d("VBreedTime",vPlantBreedTime+"");
            Log.d("----------------","--------------------------------");
            /*
            实际用的
            vPlantFertilizationTime=PlantFertilizationTime-(int)minutes;
            vPlantScissorTime=PlantScissorTime-(int)days;
            vPlantChangeSoilTime=PlantChangeSoilTime-(int)days;
            vPlantBreedTime=PlantBreedTime-(int)days;
            */
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
    public static  String getPlantChineseName() {
        return PlantChineseName;
    }
    public static int getPlantDrinkTime() {
        return PlantDrinkTime;
    }

    public static int getPlantFertilizationTime() {
        return PlantFertilizationTime;
    }

    public static int getPlantScissorTime() {
        return PlantScissorTime;
    }

    public static int getPlantChangeSoilTime() {
        return PlantChangeSoilTime;
    }

    public static int getPlantBreedTime() {
        return PlantBreedTime;
    }
    public static void setvPlantDrinkTime(int vPlantDrinkTime) {
        PlantDetailService.vPlantDrinkTime = vPlantDrinkTime;
    }

    public static void setvPlantFertilizationTime(int vPlantFertilizationTime) {
        PlantDetailService.vPlantFertilizationTime = vPlantFertilizationTime;
    }

    public static void setvPlantScissorTime(int vPlantScissorTime) {
        PlantDetailService.vPlantScissorTime = vPlantScissorTime;
    }

    public static void setvPlantChangeSoilTime(int vPlantChangeSoilTime) {
        PlantDetailService.vPlantChangeSoilTime = vPlantChangeSoilTime;
    }

    public static void setvPlantBreedTime(int vPlantBreedTime) {
        PlantDetailService.vPlantBreedTime = vPlantBreedTime;
    }

}
