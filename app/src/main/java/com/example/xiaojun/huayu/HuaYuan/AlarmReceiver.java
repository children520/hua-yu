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
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.xiaojun.huayu.HuaYuan.Fragment.PlantDetailFragment;
import com.example.xiaojun.huayu.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {
    private Notification DrinkNotify,FertilizationNotify,ScissorNotify,ChangeSoilNotify,BreedNotify;



    private static int drinkNoifyCount=1;
    private static int breedNoifyCount=1;
    private static int changesoilNoifyCount=1;
    private static int fertilizationNoifyCount=1;
    private static int scissorNoifyCount=1;
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context,PlantDetailService.class);
        isNotify(context,intent);
        context.startService(i);
    }
    private void isNotify(Context context,Intent intent){
            if(PlantDetailService.getvPlantDrinkTime()==0) {
                /*
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String channelId = "chat";
                    String channelName = "聊天消息";
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    createNotificationChannel(channelId, channelName, importance);
                }
                 */
                drinkNoifyCount++;
                Log.d("收到的",PlantDetailService.getvPlantDrinkTime()+"");
                plantNotify(context,PlantDetailService.getPlantChineseName(),"浇水",1);



            }
            if(PlantDetailService.getvPlantBreedTime()==0){
                breedNoifyCount++;
                plantNotify(context,PlantDetailService.getPlantChineseName(),"繁殖",2);

            }
            if(PlantDetailService.getvPlantChangeSoilTime()==0){
                changesoilNoifyCount++;
                plantNotify(context,PlantDetailService.getPlantChineseName(),"换土",3);

            }
            if(PlantDetailService.getvPlantFertilizationTime()==0){
                fertilizationNoifyCount++;
                plantNotify(context,PlantDetailService.getPlantChineseName(),"施肥",4);

            }
            if(PlantDetailService.getvPlantScissorTime()==0){
                scissorNoifyCount++;
                plantNotify(context,PlantDetailService.getPlantChineseName(),"修剪",5);

            }


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
    private void plantNotify(Context context,String plantName,String plantMaintenance,int id){
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Intent i=new Intent(context.getApplicationContext(),PlantDetailFragment.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context.getApplicationContext(),0,i,0);
        Notification notification = new NotificationCompat.Builder(context, "chat")
                .setContentTitle("花屿通知")
                .setContentText("您的"+plantName+"植物需要"+plantMaintenance+"啦！")
                .setTicker("来自花屿的通知信息")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.apple)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.app))
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        manager.notify(id, notification);
    }
    public static int getDrinkNoifyCount() {
        return drinkNoifyCount;
    }

    public static int getBreedNoifyCount() {
        return breedNoifyCount;
    }

    public static int getChangesoilNoifyCount() {
        return changesoilNoifyCount;
    }

    public static int getFertilizationNoifyCount() {
        return fertilizationNoifyCount;
    }

    public static int getScissorNoifyCount() {
        return scissorNoifyCount;
    }

}
