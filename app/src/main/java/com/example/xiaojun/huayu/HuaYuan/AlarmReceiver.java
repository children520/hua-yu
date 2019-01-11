package com.example.xiaojun.huayu.HuaYuan;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import com.example.xiaojun.huayu.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {
    private Notification DrinkNotify,FertilizationNotify,ScissorNotify,ChangeSoilNotify,BreedNotify;
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context,PlantDetailService.class);
        Log.d("广播DrinkTime",PlantDetailService.getvPlantDrinkTime()+"");
        isNotify(context,intent);
        context.startService(i);
    }
    private void isNotify(Context context,Intent intent){
        if(PlantDetailService.getvPlantDrinkTime()==4){
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String channelId = "chat";
                String channelName = "聊天消息";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                createNotificationChannel(channelId, channelName, importance, context);
                Intent it = new Intent(context, PlantDetailService.class);
                PendingIntent pit = PendingIntent.getActivity(context, 0, it, 0);
                Bitmap AppBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.app);
                Notification.Builder builder = new Notification.Builder(context);
                builder.setContentTitle("花屿通知")
                        .setContentText("您的植物需要浇水啦！")
                        .setTicker("来自花屿的通知信息")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.apple)
                        .setLargeIcon(AppBitmap)
                        .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                        .setAutoCancel(true)
                        .setContentIntent(pit);
                DrinkNotify = builder.build();
                notificationManager.notify(1, DrinkNotify);
            }
            }
            context.stopService(intent);
        }
    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance,Context context) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                NOTIFICATION_SERVICE);
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        notificationManager.createNotificationChannel(channel);
    }
}
