package com.example.xiaojun.huayu.HuaYuan.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.xiaojun.huayu.HuaYuan.Fragment.RemindSettingFragment;
import com.example.xiaojun.huayu.HuaYuan.Utils.PollingUtils;

public class DrinkService extends Service {
    public static final String ACTION = "com.example.xiaojun.huayu.DrinkService";
    public DrinkService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(RemindSettingFragment.getSwitchState(this,RemindSettingFragment.ISDRINKOPEN)) {
            PollingUtils.ServiceNotification(this, intent, 3);
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
