package com.example.xiaojun.huayu.HuaYuan.Utils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.xiaojun.huayu.HuaYuan.Bean.Plant;
import com.example.xiaojun.huayu.HuaYuan.Fragment.PlantDetailFragment;
import com.example.xiaojun.huayu.HuaYuan.Service.BreedService;
import com.example.xiaojun.huayu.HuaYuan.Service.ChangeSoilService;
import com.example.xiaojun.huayu.HuaYuan.Service.DrinkService;
import com.example.xiaojun.huayu.HuaYuan.Service.FertilizateService;
import com.example.xiaojun.huayu.HuaYuan.Service.ScissorService;
import com.example.xiaojun.huayu.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PollingUtils {
    public static void startPollingService(Context context, int seconds, Class<?> cls, String sort,String action,Plant plant) {
        AlarmManager manager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
        intent.putExtra("sort",sort);
        intent.putExtra("name",plant.getPlantChineseName());
        PendingIntent pendingIntent = PendingIntent.getService(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long triggerAtTime = SystemClock.elapsedRealtime();
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime+60*seconds*1000,60*seconds*1000,pendingIntent);

    }

    public static long timeDiff(String birthday){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String CurrentDate=simpleDateFormat.format(date);
        Log.d("time",CurrentDate);
        Log.d("birth",birthday);
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long minutes=0;
        try {
            Date d1=dateFormat.parse(birthday);
            Date d2=dateFormat.parse(CurrentDate);
            long diff=d2.getTime()-d1.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
            minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
        }catch (Exception e){
            Log.d("提示","获取时间错误");
            Log.d("error",e.getMessage());
        }
        return minutes;
    }

    public static void stopPollingService(Context context, Class<?> cls,String action) {
        AlarmManager manager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        manager.cancel(pendingIntent);

    }
    public static void showNotification(Context context,String plantName,String plantMaintenance,int id) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent i=new Intent(context.getApplicationContext(),PlantDetailFragment.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context.getApplicationContext(),0,i,0);
        Notification notification = new NotificationCompat.Builder(context, "chat")
                .setContentTitle("花屿通知")
                .setContentText("您的"+plantName+"植物需要"+plantMaintenance+"啦！")
                .setTicker("来自花屿的通知信息")
                //.setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.apple)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.app))
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        manager.notify(id, notification);
        Log.d("通知","已发送");
    }
    public static void ServiceNotification(Context context,Intent intent,int id){
        String sort=intent.getStringExtra("sort");
        String name=intent.getStringExtra("name");
        PollingUtils.showNotification(context,name,sort,id);
    }
    public static void startAllService(Context context,Plant plant){
        PollingUtils.startPollingService(context,plant.getPlantDrinkTime(), DrinkService.class,"浇水",DrinkService.ACTION,plant);
        PollingUtils.startPollingService(context,plant.getPlantBreedTime(), BreedService.class,"繁殖",BreedService.ACTION,plant);
        PollingUtils.startPollingService(context,plant.getPlantScissorTime(), ScissorService.class,"修剪",ScissorService.ACTION,plant);
        PollingUtils.startPollingService(context,plant.getPlantChangeSoilTime(), ChangeSoilService.class,"换土",ChangeSoilService.ACTION,plant);
        PollingUtils.startPollingService(context,plant.getPlantFertilizateTime(), FertilizateService.class,"施肥",FertilizateService.ACTION,plant);
    }
    public static void stopAllService(Context context){
        PollingUtils.stopPollingService(context,BreedService.class,BreedService.ACTION);
        PollingUtils.stopPollingService(context,ChangeSoilService.class,ChangeSoilService.ACTION);
        PollingUtils.stopPollingService(context,DrinkService.class,DrinkService.ACTION);
        PollingUtils.stopPollingService(context,FertilizateService.class,FertilizateService.ACTION);
        PollingUtils.stopPollingService(context,ScissorService.class,ScissorService.ACTION);




    }
}
