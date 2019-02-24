package com.example.xiaojun.huayu.HuaYuan.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.xiaojun.huayu.HuaYuan.Fragment.PlantDetailFragment;
import com.example.xiaojun.huayu.PlantDetailActivity;
import com.example.xiaojun.huayu.R;

/**
 * Polling service
 * @Author Ryan
 * @Create 2013-7-13 上午10:18:44
 */
public class PollingService extends Service {

	public static final String ACTION = "com.example.xiaojun.huayu.PollingService";
	private Notification mNotification;
	private NotificationManager mManager;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		Log.d("pollservice","已收到");
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		showNotification(this,"apple","浇水",1);
		return super.onStartCommand(intent, flags, startId);
	}

	private void showNotification(Context context,String plantName,String plantMaintenance,int id) {
		NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
		Intent i=new Intent(context.getApplicationContext(),PlantDetailFragment.class);
		PendingIntent pendingIntent=PendingIntent.getActivity(context.getApplicationContext(),0,i,0);
		Notification notification = new NotificationCompat.Builder(context, "chat")
				.setContentTitle("花屿通知")
				.setContentText("您的"+plantName+"植物需要"+plantMaintenance+"啦！")
				//.setTicker("来自花屿的通知信息")
				//.setWhen(System.currentTimeMillis())
				.setSmallIcon(R.mipmap.apple)
				.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.app))
				//.setDefaults(Notification.DEFAULT_ALL)
				//.setAutoCancel(true)
				.setContentIntent(pendingIntent)
				.build();
		manager.notify(id, notification);
		Log.d("通知","已发送");
	}

	/**
	 * Polling thread
	 * @Author Ryan
	 * @Create 2013-7-13 上午10:18:34
	 */

	
	@Override
	public void onDestroy() {
		super.onDestroy();

	}

}
