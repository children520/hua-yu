package com.example.xiaojun.huayu.HuaYuan.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.xiaojun.huayu.HuaYuan.Fragment.RemindSettingFragment;
import com.example.xiaojun.huayu.HuaYuan.Utils.PollingUtils;

/**
 * Polling service
 * @Author Ryan
 * @Create 2013-7-13 上午10:18:44
 */
public class BreedService extends Service {
	public static final String ACTION = "com.example.xiaojun.huayu.BreedService";
	private Notification mNotification;
	private NotificationManager mManager;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		Log.d("pollservice","已开始");
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(RemindSettingFragment.getSwitchState(this,RemindSettingFragment.ISBREEDOPEN)){
			PollingUtils.ServiceNotification(this,intent,1);
		}

		return super.onStartCommand(intent, flags, startId);
	}

	
	@Override
	public void onDestroy() {
		super.onDestroy();

	}

}
