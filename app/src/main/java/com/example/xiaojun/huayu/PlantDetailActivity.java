package com.example.xiaojun.huayu;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.xiaojun.huayu.HuaHu.MyFocusOnFragment;
import com.example.xiaojun.huayu.HuaYuan.PlantDetailFragment;
import com.example.xiaojun.huayu.HuaYuan.PlantDetailService;
import com.example.xiaojun.huayu.HuaYuan.RemindSettingFragment;

public class PlantDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_detail);
        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment=fragmentManager.findFragmentById(R.id.plant_detail_fragment_container);
        if(fragment==null){
            fragment=new PlantDetailFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.plant_detail_fragment_container,fragment)
                    .commit();
        }

    }

}
