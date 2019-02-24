package com.example.xiaojun.huayu.HuaYuan.Utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.example.xiaojun.huayu.HuaYuan.Bean.Plant;
import com.example.xiaojun.huayu.HuaYuan.BroadcastReceiver.AlarmReceiver;
import com.example.xiaojun.huayu.HuaYuan.Service.PlantDetailService;
import com.google.gson.Gson;

public class PollingUtils {
    public static void startPollingService(Context context, int seconds, Class<?> cls,Plant plant, String action) {
        Log.d("准备","开始服务");
        AlarmManager manager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
        intent.putExtra("plant",new Gson().toJson(plant));
        PendingIntent pendingIntent = PendingIntent.getService(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long triggerAtTime = SystemClock.elapsedRealtime();
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME, triggerAtTime,
                seconds * 1000, pendingIntent);

    }


    public static void stopPollingService(Context context, Class<?> cls,String action) {

        AlarmManager manager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        manager.cancel(pendingIntent);
        Log.d("准备","结束服务");


    }
}
